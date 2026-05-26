package com.homeybites.payloads;

public class JwtResponse {
	
	private String status;
	private String message;
	private String token;
	private UserInfo user;

	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JwtResponse(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public JwtResponse(String status, String message, String token, UserInfo user) {
		super();
		this.status = status;
		this.message = message;
		this.token = token;
		this.user = user;
	}


	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public JwtResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
}
