package com.homeybites.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class TiffinPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tiffinPlanId;
	private String planName;
	private String planType;
	private double price;
	private String addOns;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinTable(name = "tiffin_menuitem", joinColumns = @JoinColumn(name = "tiffin_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	private List<MenuItem> menuItems = new ArrayList<>();
	
	@OneToOne(mappedBy = "tiffinPlan", cascade = CascadeType.ALL)
	private Subscription subscription;
	
	public TiffinPlan() {
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	public Subscription getSubscriptions() {
		return subscription;
	}
	public void setSubscriptions(Subscription subscription) {
		this.subscription = subscription;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
