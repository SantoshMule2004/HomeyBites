//package com.homeybites.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class TiffinDaysMenuItem {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long tiffinMenuId;
//	
//	@Column(name = "tiffin_day_id", nullable = false)
//	private Long tiffinDayId;
//	
//	@Column(name = "menu_id", nullable = false)
//	private Long menuItemId;
//	
//	private String menuType;
//	
//
//	public Long getTiffinMenuId() {
//		return tiffinMenuId;
//	}
//
//	public void setTiffinMenuId(Long tiffinMenuId) {
//		this.tiffinMenuId = tiffinMenuId;
//	}
//
//	public Long getTiffinDayId() {
//		return tiffinDayId;
//	}
//
//	public void setTiffinDayId(Long tiffinDayId) {
//		this.tiffinDayId = tiffinDayId;
//	}
//
//	public Long getMenuItemId() {
//		return menuItemId;
//	}
//
//	public void setMenuItemId(Long menuItemId) {
//		this.menuItemId = menuItemId;
//	}
//
//	public String getMenuType() {
//		return menuType;
//	}
//
//	public void setMenuType(String menuType) {
//		this.menuType = menuType;
//	}
//}
