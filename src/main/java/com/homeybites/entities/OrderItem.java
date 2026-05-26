//package com.homeybites.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//public class OrderItem {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long orderItemId;
//	
//	@Column(name = "provider_order_id", nullable = false)
//	private Long providerOrderId;
//	
//	@Column(name = "menu_id", nullable = false)
//	private Long menuItemId;
//
//	private Integer quantity;
//
//	private double purchasedPrice;
//	
//
//	public Long getOrderItemId() {
//		return orderItemId;
//	}
//
//	public void setOrderItemId(Long orderItemId) {
//		this.orderItemId = orderItemId;
//	}
//
//	public Long getMenuItemId() {
//		return menuItemId;
//	}
//
//	public void setMenuItemId(Long menuItemId) {
//		this.menuItemId = menuItemId;
//	}
//
//	public Integer getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//	}
//
//	public double getPurchasedPrice() {
//		return purchasedPrice;
//	}
//
//	public void setPurchasedPrice(double purchasedPrice) {
//		this.purchasedPrice = purchasedPrice;
//	}
//
//	public Long getProviderOrderId() {
//		return providerOrderId;
//	}
//
//	public void setProviderOrderId(Long providerOrderId) {
//		this.providerOrderId = providerOrderId;
//	}
//}
