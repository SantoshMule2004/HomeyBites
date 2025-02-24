package com.homeybites.payloads;

public class EmailData {
	
	private String to;
	private String subject;
	private String message;
	
	public EmailData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
