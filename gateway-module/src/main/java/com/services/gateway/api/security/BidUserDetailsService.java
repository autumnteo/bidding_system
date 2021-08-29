package com.services.gateway.api.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.services.gateway.api.interfaces.UserServiceInterface;
import com.services.gateway.api.model.UserResponse;
import com.services.gateway.api.security.exception.UnauthorisedUserException;

@Component
public class BidUserDetailsService implements UserDetailsService {
	
	private UserServiceInterface userInterface;
	
    public BidUserDetailsService(UserServiceInterface userInterface) {
        this.userInterface = userInterface;
    }
    
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException, UnauthorisedUserException {
        UserResponse userResponse = userInterface.fetchByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username:" + username + " not found"));

        List<GrantedAuthority> grantedAuthorities = Arrays.stream(userResponse.getUserType().split(","))
        		.map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        
        // if user is not activated, throw a unauthorised user exception        
        if (!userResponse.isEnabled()) {
        	throw new UnauthorisedUserException(username + " has not been activated. Please check email.");
        }

        return new User(userResponse.getUsername(),userResponse.getPassword(),true,true,true,true,grantedAuthorities);
    }
    
    public List<String> getRolesByUsername(String username) throws UsernameNotFoundException{
        UserResponse userResponse = userInterface.fetchByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username:" + username + " not found"));
         return Arrays.asList(userResponse.getUserType().split(","));

    }
    
    public String getUserIdByUsername(String username) throws UsernameNotFoundException{
    	UserResponse userResponse = userInterface.fetchByUsername(username)
    		.orElseThrow(() -> new UsernameNotFoundException("Username:" + username + " not found"));
    	return String.valueOf(userResponse.getUserId());
    	
    }
    
    
}
