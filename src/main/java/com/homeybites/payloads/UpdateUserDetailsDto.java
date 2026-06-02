package com.homeybites.payloads;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserDetailsDto {
    @NotBlank(message = "first name cannot be empty..!")
	private String firstName;
	
	@NotBlank(message = "last name cannot be empty..!")
	private String lastName;

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
}
