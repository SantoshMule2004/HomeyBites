//package com.homeybites.entities;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.NotNull;
//
//@Entity
//public class Subscription {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long subPlanId;
//	
//	@NotNull(message = "Please enter the start date")
//	private LocalDate startDate;
//	
//	@NotNull(message = "Please enter the end date")
//	private LocalDate endDate;
//	
//	private double totalPrice;
//	private String status;
//
//	@Column(nullable = false, updatable = false)
//	private LocalDateTime createdAt;
//
//	@Column(name = "user_id")
//	private Long userId;
//
//	@Column(name = "tiffin_id", nullable = false)
//	private Long tiffinId;
//
//	public LocalDate getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(LocalDate startDate) {
//		this.startDate = startDate;
//	}
//
//	public LocalDate getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(LocalDate endDate) {
//		this.endDate = endDate;
//	}
//
//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
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
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//
//	public Long getTiffinId() {
//		return tiffinId;
//	}
//
//	public void setTiffinId(Long tiffinId) {
//		this.tiffinId = tiffinId;
//	}
//
//	public Long getSubPlanId() {
//		return subPlanId;
//	}
//
//	public void setSubPlanId(Long subPlanId) {
//		this.subPlanId = subPlanId;
//	}
//
//	public double getTotalPrice() {
//		return totalPrice;
//	}
//
//	public void setTotalPrice(double totalPrice) {
//		this.totalPrice = totalPrice;
//	}
//
////	private boolean isBreakFast = false;
////	private boolean isLunch = false;
////	private boolean isDinner = false;
//}
