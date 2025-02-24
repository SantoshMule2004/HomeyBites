package com.homeybites.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_menuitem")
public class CartMenuItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cmId;
	
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false)
	private UserCart cart;
	
	@ManyToOne
	@JoinColumn(name = "menu_id", nullable = false)
	private MenuItem menuItem;
	
	private Integer quantity;

	
	public Integer getCmId() {
		return cmId;
	}

	public void setCmId(Integer cmId) {
		this.cmId = cmId;
	}

	public UserCart getCart() {
		return cart;
	}

	public void setCart(UserCart cart) {
		this.cart = cart;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
