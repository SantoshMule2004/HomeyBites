//package com.homeybites.entities;
//
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//@Entity
//public class TiffinPlan {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long tiffinPlanId;
//	
//	@NotBlank(message = "Plan name cannot be empty")
//	private String planName;
//	
//	@NotBlank(message = "Plan type cannot be empty")
//	private String planType;
//	
//	@NotNull(message = "Price cannot be empty")
//	private double price;
//	
//	@Column(name = "is_active", nullable = false)
//	private boolean isActive;
//	
//	@Column(nullable = false, updatable = false)
//	private LocalDateTime createdAt;
//	
//	@Column(name = "provider_id", nullable = false)
//	private Long providerId;
//	
//
//	public Long getTiffinPlanId() {
//		return tiffinPlanId;
//	}
//
//	public void setTiffinPlanId(Long tiffinPlanId) {
//		this.tiffinPlanId = tiffinPlanId;
//	}
//
//	public String getPlanName() {
//		return planName;
//	}
//
//	public void setPlanName(String planName) {
//		this.planName = planName;
//	}
//
//	public String getPlanType() {
//		return planType;
//	}
//
//	public void setPlanType(String planType) {
//		this.planType = planType;
//	}
//
//	public double getPrice() {
//		return price;
//	}
//
//	public void setPrice(double price) {
//		this.price = price;
//	}
//
//	public boolean isActive() {
//		return isActive;
//	}
//
//	public void setActive(boolean isActive) {
//		this.isActive = isActive;
//	}
//
//	public LocalDateTime getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(LocalDateTime createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public Long getProviderId() {
//		return providerId;
//	}
//
//	public void setProviderId(Long providerId) {
//		this.providerId = providerId;
//	}
//}
