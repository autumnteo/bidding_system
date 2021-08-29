package com.services.gateway.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.services.gateway.api.security.BidUserDetailsService;
import com.services.gateway.api.security.JwtTokenProvider;
import com.services.gateway.api.security.exception.UnauthorisedUserException;

@Service
public class UserLoginService {
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BidUserDetailsService userService;
	
	public String login(String username, String password) {
		try {
			try {
				User loginUser = userService.loadUserByUsername(username);
				
			} catch (UsernameNotFoundException | UnauthorisedUserException e) {
				return e.getMessage();
			}
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
			String tokenString = jwtTokenProvider.createToken(username, userService.getRolesByUsername(username),userService.getUserIdByUsername(username));
			return tokenString;
			
		} catch (AuthenticationException e) {
			return "Invalid username or password";
		}		
	}
	
    public String createNewToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String>roleList = jwtTokenProvider.getRoleList(token);
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        String newToken =  jwtTokenProvider.createToken(username,roleList,userId);
        return newToken;
    }
    
    public Boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
    
    
}
