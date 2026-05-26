//package com.homeybites.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class ProviderOrder {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long orderId;
//	
//	@Column(name = "customer_order_id")
//	private Long singleOrderId;
//	
//	@Column(name = "provider_id")
//	private Long providerId;
//	
//	@Column(name = "order_item_id")
//	private Long orderItemId;
//	
//	private String deliveryStatus;
//
//	public Long getOrderId() {
//		return orderId;
//	}
//
//	public void setOrderId(Long orderId) {
//		this.orderId = orderId;
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
//	public Long getProviderId() {
//		return providerId;
//	}
//
//	public void setProviderId(Long providerId) {
//		this.providerId = providerId;
//	}
//
//	public Long getOrderItemId() {
//		return orderItemId;
//	}
//
//	public void setOrderItemId(Long orderItemId) {
//		this.orderItemId = orderItemId;
//	}
//
//	public String getDeliveryStatus() {
//		return deliveryStatus;
//	}
//
//	public void setDeliveryStatus(String deliveryStatus) {
//		this.deliveryStatus = deliveryStatus;
//	}
//}
