package com.services.gateway.api.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AccountNotActivated extends AuthenticationException {
	
	private String message;	
	private HttpStatus status;
	
	public AccountNotActivated(String message) {
		super(message);
		this.setStatus(HttpStatus.UNAUTHORIZED);
	}

}
