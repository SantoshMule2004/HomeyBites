package com.homeybites.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class MenuItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer menuId;
	private String menuName;
	private double price;
	private String description;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive;
	
	private String menuType;
	private String imagePublicId;
	private String imageUrl;
	private String format;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToMany(mappedBy = "menuItems")
	private List<TiffinPlan> tiffinPlan = new ArrayList<>();
	
	@ManyToMany(mappedBy = "menuItems")
	private List<OrderInfo> order = new ArrayList<>();
	
	@ManyToMany(mappedBy = "menuItems")
	private List<UserCart> userCarts = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public MenuItem() {
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<TiffinPlan> getTiffinPlan() {
		return tiffinPlan;
	}
	public void setTiffinPlan(List<TiffinPlan> tiffinPlan) {
		this.tiffinPlan = tiffinPlan;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public List<OrderInfo> getOrder() {
		return order;
	}
	public void setOrder(List<OrderInfo> order) {
		this.order = order;
	}
	public List<UserCart> getUserCarts() {
		return userCarts;
	}
	public void setUserCarts(List<UserCart> userCarts) {
		this.userCarts = userCarts;
	}
}
