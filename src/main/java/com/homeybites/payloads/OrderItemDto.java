package com.homeybites.payloads;

import java.math.BigDecimal;

public class OrderItemDto {
	private Long orderItemId;
	private Long providerOrderId;
	private Long menuItemId;
	private String itemName;
	private BigDecimal purchasedPrice;
	private Integer quantity;
	private BigDecimal totalPrice;

	public OrderItemDto(OrderItemProjection projection) {
		this.orderItemId = projection.getOrderItemId();
		this.providerOrderId = projection.getProviderOrderId();
		this.menuItemId = projection.getMenuItemId();
		this.itemName = projection.getItemName();
		this.purchasedPrice = projection.getPurchasedPrice();
		this.quantity = projection.getQuantity();
		this.totalPrice = projection.getTotalPrice();
	}

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getProviderOrderId() {
		return providerOrderId;
	}

	public void setProviderOrderId(Long providerOrderId) {
		this.providerOrderId = providerOrderId;
	}

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getPurchasedPrice() {
		return purchasedPrice;
	}

	public void setPurchasedPrice(BigDecimal purchasedPrice) {
		this.purchasedPrice = purchasedPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
}
