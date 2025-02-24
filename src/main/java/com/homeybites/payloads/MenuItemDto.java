package com.homeybites.payloads;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MenuItemDto {
	
	private Integer menuId;
	
	@NotBlank(message = "menu cannot be empty..!")
	private String menuName;
	
	@NotNull(message = "Price cannot be empty..!")
	private double price;
	
	@NotBlank(message = "Description cannot be empty..!")
	private String description;
	
	private boolean isActive = false;
	
	private String imagePublicId;
	
	private String imageUrl;
	
	private String format;
	
	@NotBlank(message = "menu type cannot be empty..!")
	private String menuType;
	
	@JsonBackReference(value = "category-menuitem")
	private CategoryDto category;
	
	@JsonBackReference(value = "tiffin-menuitem")
	private List<TiffinPlanDto> tiffinPlan;
	
	@JsonBackReference(value = "order-menuitem")
	private List<OrderInfoDto> order;
	
	@JsonBackReference(value ="user-menuitem")
	private UserDto userDto;
	
	@JsonBackReference(value = "cart-menuitem")
	private List<UserCartDto> userCarts = new ArrayList<>();
	
	public MenuItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public CategoryDto getCategory() {
		return category;
	}
	public void setCategory(CategoryDto category) {
		this.category = category;
	}
	public List<TiffinPlanDto> getTiffinPlan() {
		return tiffinPlan;
	}
	public void setTiffinPlan(List<TiffinPlanDto> tiffinPlan) {
		this.tiffinPlan = tiffinPlan;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImagePublicId() {
		return imagePublicId;
	}
	public void setImagePublicId(String imagePublicId) {
		this.imagePublicId = imagePublicId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	public List<OrderInfoDto> getOrder() {
		return order;
	}
	public void setOrder(List<OrderInfoDto> order) {
		this.order = order;
	}
	public List<UserCartDto> getUserCarts() {
		return userCarts;
	}
	public void setUserCarts(List<UserCartDto> userCarts) {
		this.userCarts = userCarts;
	}
}
