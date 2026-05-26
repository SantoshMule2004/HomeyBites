//package com.homeybites.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class TiffinDays {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long tiffinDayId;
//
//	private Integer dayNumber;
//
//	@Column(name = "tiffin_plan_id", nullable = true)
//	private Long tiffinPlanId;
//
//	public Long getTiffinDayId() {
//		return tiffinDayId;
//	}
//
//	public void setTiffinDayId(Long tiffinDayId) {
//		this.tiffinDayId = tiffinDayId;
//	}
//
//	public Integer getDayNumber() {
//		return dayNumber;
//	}
//
//	public void setDayNumber(Integer dayNumber) {
//		this.dayNumber = dayNumber;
//	}
//
//	public Long getTiffinPlanId() {
//		return tiffinPlanId;
//	}
//
//	public void setTiffinPlanId(Long tiffinPlanId) {
//		this.tiffinPlanId = tiffinPlanId;
//	}
//}
