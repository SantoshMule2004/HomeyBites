package com.homeybites.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	private Boolean isActive = false;
	
	private Double grandTotal;

	public UserCart() {
		super();
		this.grandTotal = 0.0;
		// TODO Auto-generated constructor stub
	}
	public UserCart(Long userId, Boolean isActive, Double grandTotal) {
		super();
		this.userId = userId;
		this.isActive = isActive;
		this.grandTotal = grandTotal;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	@Override
	public String toString() {
		return "UserCart [cartId=" + cartId + ", userId=" + userId + ", isActive=" + isActive + ", grandTotal="
				+ grandTotal + "]";
	}
}
