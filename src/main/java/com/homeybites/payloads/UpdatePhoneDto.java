package com.homeybites.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePhoneDto {
    @NotBlank(message = "Phone number cannot be empty..!")
	@Size(min = 10, max = 10, message = "phone number should be of 10 numbers..!")
	private String phoneNo;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
