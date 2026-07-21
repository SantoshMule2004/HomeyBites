package com.homeybites.payloads;

import java.util.List;

public class ProviderMenuResponse {

    private Long id;
    private Long providerId;
    private String dayOfWeek;
    private Boolean isActive;

    private List<ProviderMenuItemProjection> meals;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<ProviderMenuItemProjection> getMeals() {
		return meals;
	}

	public void setMeals(List<ProviderMenuItemProjection> meals) {
		this.meals = meals;
	}
}