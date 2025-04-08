package com.homeybites.payloads;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SubscriptionDto {
	
	private Integer planId;
	private LocalDate startDate;
	private LocalDate endDate;
	private double totalPrice;
	private String status;
	private LocalDateTime createdAt;
	private int planDuration;

	private UserDto user;

	@JsonIgnore
	private OrderInfoDto order;

	private TiffinPlanDto tiffinPlan;
	
	private TiffinPlanLogDto tiffinPlanLog;
	
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	
	private boolean isBreakFast;
	private boolean isLunch;
	private boolean isDinner;

	public SubscriptionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public OrderInfoDto getOrder() {
		return order;
	}

	public void setOrder(OrderInfoDto order) {
		this.order = order;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public TiffinPlanDto getTiffinPlan() {
		return tiffinPlan;
	}

	public void setTiffinPlan(TiffinPlanDto tiffinPlan) {
		this.tiffinPlan = tiffinPlan;
	}

	public TiffinPlanLogDto getTiffinPlanLog() {
		return tiffinPlanLog;
	}

	public void setTiffinPlanLog(TiffinPlanLogDto tiffinPlanLog) {
		this.tiffinPlanLog = tiffinPlanLog;
	}

	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	public boolean isBreakFast() {
		return isBreakFast;
	}

	public void setBreakFast(boolean isBreakFast) {
		this.isBreakFast = isBreakFast;
	}

	public boolean isLunch() {
		return isLunch;
	}

	public void setLunch(boolean isLunch) {
		this.isLunch = isLunch;
	}

	public boolean isDinner() {
		return isDinner;
	}

	public void setDinner(boolean isDinner) {
		this.isDinner = isDinner;
	}

	public int getPlanDuration() {
		return planDuration;
	}

	public void setPlanDuration(int planDuration) {
		this.planDuration = planDuration;
	}
}
