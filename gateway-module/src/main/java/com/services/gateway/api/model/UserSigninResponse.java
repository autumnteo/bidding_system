package com.services.gateway.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class UserSigninResponse {
	private String accessToken;

	public UserSigninResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserSigninResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	
}
