package com.homeybites.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class JwtRequest {
	
	@Email(regexp = ".*?@?[^@]*\\.+.*")
	private String username;
	
	@NotEmpty(message = "Password cannot be empty..!")
	@Size(min = 8, max = 16, message = "password should have minimum of 8 characters.")
	private String password;
	
	public JwtRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
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
