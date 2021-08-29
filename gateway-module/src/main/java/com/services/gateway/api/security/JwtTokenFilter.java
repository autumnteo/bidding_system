package com.services.gateway.api.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.services.gateway.api.security.exception.UnauthorisedUserException;

public class JwtTokenFilter extends OncePerRequestFilter{
	
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException{
    	String token = jwtTokenProvider.resolveToken(request);
    	
    	try {
            if (!Objects.isNull(token) && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (!Objects.isNull(auth))
                    SecurityContextHolder.getContext().setAuthentication(auth);
            }
    	} catch (UsernameNotFoundException | UnauthorisedUserException e) {
    		SecurityContextHolder.clearContext();
    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED	, e.getMessage());
//    		authenticationEntryPoint.commence(request, response, e);
            return;
    	}

        filterChain.doFilter(request, response);
    }
}
