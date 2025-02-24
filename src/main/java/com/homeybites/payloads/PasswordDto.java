package com.homeybites.payloads;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordDto {
	
	private String oldPassword;
	
	@Size(min = 8, max = 16, message = "password should have minimum of 8 characters and maximum of 16 characters..!")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "password must contain atleast one lowercase letter, atleast one uppercase letter, atleast one digit and atleast one special character..!")
	private String newPassword;
	
	private String cPassword;
	
	public PasswordDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getcPassword() {
		return cPassword;
	}
	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}
}
