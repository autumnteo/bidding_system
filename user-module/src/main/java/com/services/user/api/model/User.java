package com.services.user.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter
@Setter
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	private String username;
	private String email;
	private String password;
	private String fullName;
	private String companyName;
	private String position;
	private String uen;
	private String phoneNumber;
	private String address;
	private boolean isEnabled;
	private String userType;
	private boolean isRequestingPartner;
	
	public User() {
		super();
	}
	
	
}
