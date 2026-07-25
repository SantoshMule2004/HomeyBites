package com.homeybites.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.homeybites.payloads.SubscriptionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private Long planId;
	private Long providerId;

	@Column(name = "plan_name", nullable = false)
	private String planName;

	@Column(name = "validity_days", nullable = false)
	private Integer validityDays;

	private boolean includesBreakfast;
	private boolean includesLunch;
	private boolean includesDinner;

	@Column(name = "breakfast_price", precision = 10, scale = 2)
	private BigDecimal breakfastPrice;

	@Column(name = "lunch_price", precision = 10, scale = 2)
	private BigDecimal lunchPrice;

	@Column(name = "dinner_price", precision = 10, scale = 2)
	private BigDecimal dinnerPrice;

//	private BigDecimal amountPaid;
	private LocalDate startDate;
	private LocalDate currentEndDate;
	private int totalPausedDays;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private SubscriptionStatus status; // ACTIVE, PAUSED, COMPLETED, CANCELLED

	private LocalDate pauseStartDate;
	private LocalDate autoResumeDate;

//	private Long deliveryAddressId;
	private String deliveryAddress;
	private String receiverName;
	private String receiverContactNo;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public Subscription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public boolean isIncludesBreakfast() {
		return includesBreakfast;
	}

	public void setIncludesBreakfast(boolean includesBreakfast) {
		this.includesBreakfast = includesBreakfast;
	}

	public boolean isIncludesLunch() {
		return includesLunch;
	}

	public void setIncludesLunch(boolean includesLunch) {
		this.includesLunch = includesLunch;
	}

	public boolean isIncludesDinner() {
		return includesDinner;
	}

	public void setIncludesDinner(boolean includesDinner) {
		this.includesDinner = includesDinner;
	}

//	public BigDecimal getAmountPaid() {
//		return amountPaid;
//	}
//
//	public void setAmountPaid(BigDecimal amountPaid) {
//		this.amountPaid = amountPaid;
//	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getCurrentEndDate() {
		return currentEndDate;
	}

	public void setCurrentEndDate(LocalDate currentEndDate) {
		this.currentEndDate = currentEndDate;
	}

	public int getTotalPausedDays() {
		return totalPausedDays;
	}

	public void setTotalPausedDays(int totalPausedDays) {
		this.totalPausedDays = totalPausedDays;
	}

	public SubscriptionStatus getStatus() {
		return status;
	}

	public void setStatus(SubscriptionStatus status) {
		this.status = status;
	}

	public LocalDate getPauseStartDate() {
		return pauseStartDate;
	}

	public void setPauseStartDate(LocalDate pauseStartDate) {
		this.pauseStartDate = pauseStartDate;
	}

	public LocalDate getAutoResumeDate() {
		return autoResumeDate;
	}

	public void setAutoResumeDate(LocalDate autoResumeDate) {
		this.autoResumeDate = autoResumeDate;
	}

//	public Long getDeliveryAddressId() {
//		return deliveryAddressId;
//	}
//
//	public void setDeliveryAddressId(Long deliveryAddressId) {
//		this.deliveryAddressId = deliveryAddressId;
//	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverContactNo() {
		return receiverContactNo;
	}

	public void setReceiverContactNo(String receiverContactNo) {
		this.receiverContactNo = receiverContactNo;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getValidityDays() {
		return validityDays;
	}

	public void setValidityDays(Integer validityDays) {
		this.validityDays = validityDays;
	}

	public BigDecimal getBreakfastPrice() {
		return breakfastPrice;
	}

	public void setBreakfastPrice(BigDecimal breakfastPrice) {
		this.breakfastPrice = breakfastPrice;
	}

	public BigDecimal getLunchPrice() {
		return lunchPrice;
	}

	public void setLunchPrice(BigDecimal lunchPrice) {
		this.lunchPrice = lunchPrice;
	}

	public BigDecimal getDinnerPrice() {
		return dinnerPrice;
	}

	public void setDinnerPrice(BigDecimal dinnerPrice) {
		this.dinnerPrice = dinnerPrice;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", userId=" + userId + ", planId=" + planId + ", providerId=" + providerId
				+ ", planName=" + planName + ", validityDays=" + validityDays + ", includesBreakfast="
				+ includesBreakfast + ", includesLunch=" + includesLunch + ", includesDinner=" + includesDinner
				+ ", breakfastPrice=" + breakfastPrice + ", lunchPrice=" + lunchPrice + ", dinnerPrice=" + dinnerPrice
				+ ", startDate=" + startDate + ", currentEndDate=" + currentEndDate + ", totalPausedDays="
				+ totalPausedDays + ", status=" + status + ", pauseStartDate=" + pauseStartDate + ", autoResumeDate="
				+ autoResumeDate + ", deliveryAddress=" + deliveryAddress + ", receiverName=" + receiverName
				+ ", receiverContactNo=" + receiverContactNo + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}
}