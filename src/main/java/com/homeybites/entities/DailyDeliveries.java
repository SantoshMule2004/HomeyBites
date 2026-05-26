//package com.homeybites.entities;
//
//import java.time.LocalDate;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class DailyDeliveries {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long dailyDeliveryId;
//	
//	private LocalDate deliveryDate;
//	
//	private String menuType;
//	
//	private String deliveryStatus;
//	
//	@Column(name = "sub_id", nullable = false)
//	private Long subId;
//	
//	@Column(name = "menu_item_id", nullable = false)
//	private Long menuItemId;
//
//	
//	public Long getDailyDeliveryId() {
//		return dailyDeliveryId;
//	}
//
//	public void setDailyDeliveryId(Long dailyDeliveryId) {
//		this.dailyDeliveryId = dailyDeliveryId;
//	}
//
//	public LocalDate getDeliveryDate() {
//		return deliveryDate;
//	}
//
//	public void setDeliveryDate(LocalDate deliveryDate) {
//		this.deliveryDate = deliveryDate;
//	}
//
//	public String getMenuType() {
//		return menuType;
//	}
//
//	public void setMenuType(String menuType) {
//		this.menuType = menuType;
//	}
//
//	public String getDeliveryStatus() {
//		return deliveryStatus;
//	}
//
//	public void setDeliveryStatus(String deliveryStatus) {
//		this.deliveryStatus = deliveryStatus;
//	}
//
//	public Long getSubId() {
//		return subId;
//	}
//
//	public void setSubId(Long subId) {
//		this.subId = subId;
//	}
//
//	public Long getMenuItemId() {
//		return menuItemId;
//	}
//
//	public void setMenuItemId(Long menuItemId) {
//		this.menuItemId = menuItemId;
//	}
//}
