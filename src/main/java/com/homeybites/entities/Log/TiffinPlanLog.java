package com.homeybites.entities.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.homeybites.entities.Subscription;
import com.homeybites.entities.TiffinDays;
import com.homeybites.entities.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class TiffinPlanLog {
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
	private LocalDateTime archievedAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "tiffinPlan", cascade = CascadeType.ALL)
	private List<Subscription> subscription;
	
	@OneToMany(mappedBy = "tiffinPlan", cascade = CascadeType.ALL)
	private List<TiffinDays> tiffinDays = new ArrayList<>();
	
	public TiffinPlanLog() {
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

	public LocalDateTime getCreatedAt() {
		return archievedAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.archievedAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Subscription> getSubscription() {
		return subscription;
	}

	public void setSubscription(List<Subscription> subscription) {
		this.subscription = subscription;
	}

	public List<TiffinDays> getTiffinDays() {
		return tiffinDays;
	}

	public void setTiffinDays(List<TiffinDays> tiffinDays) {
		this.tiffinDays = tiffinDays;
	}
}
