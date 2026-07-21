package com.homeybites.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.homeybites.payloads.DailyDeliveryStatus;
import com.homeybites.payloads.MealType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "daily_deliveries")
public class DailyDelivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long subscriptionId;
    private Long providerId;
    private Long userId;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "meal_type")  // BREAKFAST, LUNCH, DINNER
    private MealType mealType;
    private String foodItems;
    private LocalDate deliveryDate;
    
//    private Long deliveryAddressId;
    private String deliveryAddress;
    private String receiverName;
    private String receiverContactNo;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "status")
    private DailyDeliveryStatus status; // PENDING, DELIVERED, FAILED
    
    @CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
    
    
	public DailyDelivery() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Long getProviderId() {
		return providerId;
	}
	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public MealType getMealType() {
		return mealType;
	}
	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}
	public String getFoodItems() {
		return foodItems;
	}
	public void setFoodItems(String foodItems) {
		this.foodItems = foodItems;
	}
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
//	public Long getDeliveryAddressId() {
//		return deliveryAddressId;
//	}
//	public void setDeliveryAddressId(Long deliveryAddressId) {
//		this.deliveryAddressId = deliveryAddressId;
//	}
	public DailyDeliveryStatus getStatus() {
		return status;
	}
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

	public void setStatus(DailyDeliveryStatus status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}