package com.services.user.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class updateUserDetailsForm {
	private String username;
	private String email;
	private String password;
	private String fullName;
	private String companyName;
	private String position;
	private String uen;
	private String phoneNumber;
	private String address;
	
	public updateUserDetailsForm() {
		super();
	}
		
}
