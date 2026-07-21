package com.homeybites.payloads;

import java.time.LocalDate;

public class ProviderHolidayDTO {
	private LocalDate closedDate;
    private String name;
    private String description;
    private Boolean isActive;
    
	public LocalDate getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(LocalDate closedDate) {
		this.closedDate = closedDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	@Override
	public String toString() {
		return "ProviderHolidayDTO [closedDate=" + closedDate + ", name=" + name + ", description=" + description
				+ ", isActive=" + isActive + "]";
	}
}
