package com.homeybites.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemId;
	
	@Column(name = "cart_id")
	private Long cartId;
	
	@Column(name = "menu_id", nullable = true)
	private Long menuItemId;

	private Integer quantity;

	private double priceWhenAdded;
	
	private double currentPrice;
	
	private boolean isPriceChanged = false;
	

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(Long menuItemId) {
		this.menuItemId = menuItemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getPriceWhenAdded() {
		return priceWhenAdded;
	}

	public void setPriceWhenAdded(double priceWhenAdded) {
		this.priceWhenAdded = priceWhenAdded;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public boolean isPriceChanged() {
		return isPriceChanged;
	}

	public void setPriceChanged(boolean isPriceChanged) {
		this.isPriceChanged = isPriceChanged;
	}

	@Override
	public String toString() {
		return "CartItem [cartItemId=" + cartItemId + ", cartId=" + cartId + ", menuItemId=" + menuItemId
				+ ", quantity=" + quantity + ", priceWhenAdded=" + priceWhenAdded + ", currentPrice=" + currentPrice
				+ ", isPriceChanged=" + isPriceChanged + "]";
	}
}
