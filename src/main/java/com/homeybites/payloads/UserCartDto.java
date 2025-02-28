package com.homeybites.payloads;

public class UserCartDto {

	private Integer cId;

	private UserDto user;

	private MenuItemDto menuItem;

	private Integer quantity;

	private double totalPrice;

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public MenuItemDto getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItemDto menuItem) {
		this.menuItem = menuItem;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
