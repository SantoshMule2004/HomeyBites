package com.homeybites.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterUserRequest {
	@NotBlank(message = "first name cannot be empty..!")
	private String firstName;
	private String middleName;
	
	@NotBlank(message = "last name cannot be empty..!")
	private String lastName;
	
	@Column(nullable = false)
	@Email(regexp = ".*?@?[^@]*\\.+.*")
	private String emailId;
	
	private boolean isVerified = false;
	
	@NotBlank(message = "Phone number cannot be empty..!")
	@Size(min = 10, max = 10, message = "phone number should be of 10 numbers..!")
	private String phoneNo;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String cPassword;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getcPassword() {
		return cPassword;
	}

	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}
}
