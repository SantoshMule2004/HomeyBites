package com.homeybites.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.homeybites.entities.Log.SubscriptionLog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "tiffinPlanId")
public class TiffinPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tiffinPlanId;
	
	@NotBlank(message = "Plan name cannot be empty")
	private String planName;
	
	@NotBlank(message = "Plan type cannot be empty")
	private String planType;
	
	@NotNull(message = "Price cannot be empty")
	private double price;
	
	private String addOns;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "tiffinPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Subscription> subscription;
	
	@OneToMany(mappedBy = "tiffinPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<SubscriptionLog> subscriptionLog;
	
	@OneToMany(mappedBy = "tiffinPlan", cascade = CascadeType.ALL)
	private List<TiffinDays> tiffinDays = new ArrayList<>();
	
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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

	public List<SubscriptionLog> getSubscriptionLog() {
		return subscriptionLog;
	}

	public void setSubscriptionLog(List<SubscriptionLog> subscriptionLog) {
		this.subscriptionLog = subscriptionLog;
	}
}
