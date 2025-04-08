package com.homeybites.payloads;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homeybites.entities.Log.SubscriptionLog;


public class TiffinPlanLogDto {

	private Integer tiffinPlanId;
	private String planName;
	private String planType;
	private double price;
	private String addOns;
	private boolean isActive;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime archievedAt;
	
	@JsonIgnore
	private UserDto user;
	
	@JsonIgnore
	private List<SubscriptionDto> subscription;
	
	@JsonIgnore
	private List<SubscriptionLog> subscriptionLog;
	
	private List<TiffinDaysDto> tiffinDays = new ArrayList<>();
	
	
	public TiffinPlanLogDto() {
		super();
	}

	public Integer getTiffinPlanId() {
		return tiffinPlanId;
	}

	public void setTiffinPlanId(Integer tiffinPlanId) {
		this.tiffinPlanId = tiffinPlanId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAddOns() {
		return addOns;
	}

	public void setAddOns(String addOns) {
		this.addOns = addOns;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public List<SubscriptionDto> getSubscription() {
		return subscription;
	}

	public void setSubscription(List<SubscriptionDto> subscription) {
		this.subscription = subscription;
	}

	public List<TiffinDaysDto> getTiffinDays() {
		return tiffinDays;
	}

	public void setTiffinDays(List<TiffinDaysDto> tiffinDays) {
		this.tiffinDays = tiffinDays;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getArchievedAt() {
		return archievedAt;
	}

	public void setArchievedAt(LocalDateTime archievedAt) {
		this.archievedAt = archievedAt;
	}

	public List<SubscriptionLog> getSubscriptionLog() {
		return subscriptionLog;
	}

	public void setSubscriptionLog(List<SubscriptionLog> subscriptionLog) {
		this.subscriptionLog = subscriptionLog;
	}
}
