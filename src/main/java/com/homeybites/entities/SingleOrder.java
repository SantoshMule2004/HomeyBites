//package com.homeybites.entities;
//
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class SingleOrder {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long singleOrderId;
//	
//	@Column(name = "address_id",nullable = false)
//	private Long addressId;
//	private LocalDateTime orderDate;
//	private double total;
//	private String orderStatus;
//	private String paymentStatus;
//
//	@Column(name = "user_id")
//	private Long userId;
//
//	public Long getCustomerOrderId() {
//		return singleOrderId;
//	}
//
//	public void setCustomerOrderId(Long singleOrderId) {
//		this.singleOrderId = singleOrderId;
//	}
//
//	public Long getAddressId() {
//		return addressId;
//	}
//
//	public void setAddressId(Long addressId) {
//		this.addressId = addressId;
//	}
//
//	public LocalDateTime getOrderDate() {
//		return orderDate;
//	}
//
//	public void setOrderDate(LocalDateTime orderDate) {
//		this.orderDate = orderDate;
//	}
//
//	public double getTotal() {
//		return total;
//	}
//
//	public void setTotal(double total) {
//		this.total = total;
//	}
//
//	public String getOrderStatus() {
//		return orderStatus;
//	}
//
//	public void setOrderStatus(String orderStatus) {
//		this.orderStatus = orderStatus;
//	}
//
//	public String getPaymentStatus() {
//		return paymentStatus;
//	}
//
//	public void setPaymentStatus(String paymentStatus) {
//		this.paymentStatus = paymentStatus;
//	}
//
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//}
