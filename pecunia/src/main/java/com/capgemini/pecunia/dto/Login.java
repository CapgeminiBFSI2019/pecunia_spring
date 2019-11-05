package com.capgemini.pecunia.dto;

import org.springframework.stereotype.Component;

@Component
//Specifying datatypes for Login class in DTO
//Specifying getter and setter method for Login class in DTO
public class Login {

	public Login() {

	}

	public Login(String username, String password) {
		super();
		this.username = username;
		this.password = password;

	}

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
