package com.homeybites.payloads;

public class ApiException {
	private String message;
	private boolean status;
	
	public ApiException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApiException(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
