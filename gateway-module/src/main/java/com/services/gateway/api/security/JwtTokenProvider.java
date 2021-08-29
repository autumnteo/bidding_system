package com.services.gateway.api.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.services.gateway.api.security.exception.UnauthorisedUserException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	private static Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Autowired
	private final BidUserDetailsService userDetailsService;
	
	private String secretKey ="secret-key";
	
	private long validityInMilliseconds = 3600000; // 1h
	
    public JwtTokenProvider(BidUserDetailsService userDetailsService) {        
    	this.userDetailsService = userDetailsService;
    }
    
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
    
    public String createToken(String username, List<String> roles, String userId) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);
        claims.put("auth",roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String token =  Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
        return token;
    }
    
    public List<String> getRoleList(String token) {
        return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).
                getBody().get("auth");
    }
    
    public String getUserIdFromToken(String token) {
    	return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).
                getBody().get("userId");
    }
    
    public Authentication getAuthentication(String token) throws UsernameNotFoundException, UnauthorisedUserException {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");

        return (!Objects.isNull(bearerToken) && bearerToken.startsWith("Bearer")) ?
                bearerToken.substring(7, bearerToken.length()) : null;
    }

    public boolean validateToken(String token) {

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return (!claims.getBody().getExpiration().before(new Date()));
        } catch (JwtException | IllegalArgumentException e) {
            LOGGER.error("Expired or invalid JWT token");
            throw new UnauthorisedUserException("JWT Token Invalid");
        }
    }
}
