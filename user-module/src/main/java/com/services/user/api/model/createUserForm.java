package com.services.user.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class createUserForm {
	private String username;
	private String email;
	private String password;
	private String fullName;
	private String companyName;
	private String position;
	private String uen;
	private String phoneNumber;
	private String address;
	
	public createUserForm() {
		super();
	}

	public createUserForm(String username, String email, String password, String fullName, String companyName,
			String position, String uen, String phoneNumber, String address) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.companyName = companyName;
		this.position = position;
		this.uen = uen;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getUen() {
		return uen;
	}

	public void setUen(String uen) {
		this.uen = uen;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
		
}
