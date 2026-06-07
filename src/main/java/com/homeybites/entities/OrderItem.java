package com.homeybites.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrderItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_order_id", nullable = false)
    private Long providerOrderId; // Soft ID up to the Vendor Package

    @Column(name = "menu_item_id", nullable = false)
    private Long menuItemId; // Soft ID to the Menu Catalog

    @Column(name = "item_name", length = 255, nullable = false)
    private String itemName; // Historical Snapshot

    @Column(name = "purchased_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal purchasedPrice; // Historical Snapshot

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice; // (purchasedPrice * quantity)

    @Column(name = "special_instructions", length = 500)
    private String specialInstructions; // e.g., "Extra spicy"

	public OrderItem(Long id, Long providerOrderId, Long menuItemId, String itemName, BigDecimal purchasedPrice,
			Integer quantity, BigDecimal totalPrice, String specialInstructions) {
		super();
		this.id = id;
		this.providerOrderId = providerOrderId;
		this.menuItemId = menuItemId;
		this.itemName = itemName;
		this.purchasedPrice = purchasedPrice;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.specialInstructions = specialInstructions;
	}

	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", providerOrderId=" + providerOrderId + ", menuItemId=" + menuItemId
				+ ", itemName=" + itemName + ", purchasedPrice=" + purchasedPrice + ", quantity=" + quantity
				+ ", totalPrice=" + totalPrice + ", specialInstructions=" + specialInstructions + "]";
	}	
}
