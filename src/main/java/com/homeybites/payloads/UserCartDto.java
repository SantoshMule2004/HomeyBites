package com.homeybites.payloads;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class UserCartDto {
	
	private Integer cId;
	
	@JsonManagedReference
	private UserDto user;
	
	@JsonManagedReference(value = "cart-menuitem")
	private List<MenuItemDto> menuItems = new ArrayList<>();

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

	public List<MenuItemDto> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItemDto> menuItems) {
		this.menuItems = menuItems;
	}
}
