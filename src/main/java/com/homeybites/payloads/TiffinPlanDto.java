package com.homeybites.payloads;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class TiffinPlanDto {
	
	private Integer tiffinPlanId;
	private String planName;
	private String planType;
	private double price;
	private String addOns;
	private boolean isActive = false;
	private LocalDateTime createdAt;
	
	@JsonManagedReference
	private UserDto user;
	
	@JsonManagedReference(value = "tiffin-menuitem")
	private List<MenuItemDto> menuItems = new ArrayList<>();
	
	@JsonBackReference
	private SubscriptionDto subscription;
	
	public TiffinPlanDto() {
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
	public UserDto getUserDto() {
		return user;
	}
	public void setUserDto(UserDto user) {
		this.user = user;
	}
	public List<MenuItemDto> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItemDto> menuItems) {
		this.menuItems = menuItems;
	}
	public SubscriptionDto getSubscriptions() {
		return subscription;
	}
	public void setSubscriptions(SubscriptionDto subscription) {
		this.subscription = subscription;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
}
