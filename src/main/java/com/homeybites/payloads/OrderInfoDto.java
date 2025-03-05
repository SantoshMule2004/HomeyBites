package com.homeybites.payloads;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class OrderInfoDto {
	
	private Integer orderId;
	private String orderAddress;
	private Date orderDate;
	private double price;
	private String quantity;
	private String orderStatus;
	
	@JsonIgnore
	private UserDto user;
	
	private SubscriptionDto subscription;
	
	private List<MenuItemDto> menuItems;
	
	@JsonIgnore
	private PaymentDto payment;
	
	public OrderInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderAddress() {
		return orderAddress;
	}
	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public SubscriptionDto getSubscription() {
		return subscription;
	}
	public void setSubscription(SubscriptionDto subscription) {
		this.subscription = subscription;
	}
	public PaymentDto getPayment() {
		return payment;
	}
	public void setPayment(PaymentDto payment) {
		this.payment = payment;
	}
	public List<MenuItemDto> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItemDto> menuItems) {
		this.menuItems = menuItems;
	}
}
