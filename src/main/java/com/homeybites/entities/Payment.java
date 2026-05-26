//package com.homeybites.entities;
//
//import java.time.LocalDate;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class Payment {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long paymentId;
//	private LocalDate paymentDate;
//	private double amount;
//
//	@Column(name = "single_order_id", nullable = true)
//	private Long singleOrderId;
//	
//	@Column(name = "subscription_id", nullable = true)
//	private Long subscriptionId;
//	
//	private String paymentMethod;
//	private String paymentStatus;
//
//	
//	public Payment() {
//		super();
//	}
//
//	public Long getPaymentId() {
//		return paymentId;
//	}
//
//	public void setPaymentId(Long paymentId) {
//		this.paymentId = paymentId;
//	}
//
//	public LocalDate getPaymentDate() {
//		return paymentDate;
//	}
//
//	public void setPaymentDate(LocalDate paymentDate) {
//		this.paymentDate = paymentDate;
//	}
//
//	public double getAmount() {
//		return amount;
//	}
//
//	public void setAmount(double amount) {
//		this.amount = amount;
//	}
//	
//	public Long getSingleOrderId() {
//		return singleOrderId;
//	}
//
//	public void setSingleOrderId(Long singleOrderId) {
//		this.singleOrderId = singleOrderId;
//	}
//
//	public Long getSubscriptionId() {
//		return subscriptionId;
//	}
//
//	public void setSubscriptionId(Long subscriptionId) {
//		this.subscriptionId = subscriptionId;
//	}
//
//	public String getPaymentMethod() {
//		return paymentMethod;
//	}
//
//	public void setPaymentMethod(String paymentMethod) {
//		this.paymentMethod = paymentMethod;
//	}
//
//	public String getPaymentStatus() {
//		return paymentStatus;
//	}
//
//	public void setPaymentStatus(String paymentStatus) {
//		this.paymentStatus = paymentStatus;
//	}
//}
