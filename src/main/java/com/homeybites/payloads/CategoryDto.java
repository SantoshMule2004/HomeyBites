package com.homeybites.payloads;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank(message = "category name cannot be empty..!")
	private String categoryName;
	
	private boolean isActive;
	
	private List<MenuItemDto> menuItems = new ArrayList<>();
	
	public CategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public List<MenuItemDto> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItemDto> menuItems) {
		this.menuItems = menuItems;
	}
}
