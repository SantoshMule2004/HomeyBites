package com.homeybites.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tiffin_plans")
public class TiffinPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long providerId;
    private String planName;
    private int validityDays;
    
    private boolean offersBreakfast;
    private boolean offersLunch;
    private boolean offersDinner;
    
    private BigDecimal pricePerBreakfast;
    private BigDecimal pricePerLunch;
    private BigDecimal pricePerDinner;
    
    private int maxCapacity;
    private int activeSubscribers;
    private boolean isActive;
    
    @CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	
	public TiffinPlan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TiffinPlan(Long id, Long providerId, String planName, int validityDays, boolean offersBreakfast,
			boolean offersLunch, boolean offersDinner, BigDecimal pricePerBreakfast, BigDecimal pricePerLunch,
			BigDecimal pricePerDinner, int maxCapacity, int activeSubscribers, boolean isActive) {
		super();
		this.id = id;
		this.providerId = providerId;
		this.planName = planName;
		this.validityDays = validityDays;
		this.offersBreakfast = offersBreakfast;
		this.offersLunch = offersLunch;
		this.offersDinner = offersDinner;
		this.pricePerBreakfast = pricePerBreakfast;
		this.pricePerLunch = pricePerLunch;
		this.pricePerDinner = pricePerDinner;
		this.maxCapacity = maxCapacity;
		this.activeSubscribers = activeSubscribers;
		this.isActive = isActive;
	}
	
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
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public int getValidityDays() {
		return validityDays;
	}
	public void setValidityDays(int validityDays) {
		this.validityDays = validityDays;
	}
	public boolean isOffersBreakfast() {
		return offersBreakfast;
	}
	public void setOffersBreakfast(boolean offersBreakfast) {
		this.offersBreakfast = offersBreakfast;
	}
	public boolean isOffersLunch() {
		return offersLunch;
	}
	public void setOffersLunch(boolean offersLunch) {
		this.offersLunch = offersLunch;
	}
	public boolean isOffersDinner() {
		return offersDinner;
	}
	public void setOffersDinner(boolean offersDinner) {
		this.offersDinner = offersDinner;
	}
	public BigDecimal getPricePerBreakfast() {
		return pricePerBreakfast;
	}
	public void setPricePerBreakfast(BigDecimal pricePerBreakfast) {
		this.pricePerBreakfast = pricePerBreakfast;
	}
	public BigDecimal getPricePerLunch() {
		return pricePerLunch;
	}
	public void setPricePerLunch(BigDecimal pricePerLunch) {
		this.pricePerLunch = pricePerLunch;
	}
	public BigDecimal getPricePerDinner() {
		return pricePerDinner;
	}
	public void setPricePerDinner(BigDecimal pricePerDinner) {
		this.pricePerDinner = pricePerDinner;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public int getActiveSubscribers() {
		return activeSubscribers;
	}
	public void setActiveSubscribers(int activeSubscribers) {
		this.activeSubscribers = activeSubscribers;
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
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}