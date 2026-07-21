package com.homeybites.payloads;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserDetailsDto {
    @NotBlank(message = "first name cannot be empty..!")
	private String firstName;
	
	@NotBlank(message = "last name cannot be empty..!")
	private String lastName;
	
	private String dob;
	
	private String gender;
	
	private String phoneNo;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
