package com.homeybites.payloads;

import java.time.LocalDateTime;

public class OtpDto {
	
	private String otp;
	private LocalDateTime expirationTime;
	
	public OtpDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}
}
