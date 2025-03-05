package com.homeybites.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planId;
	private LocalDate startDate;
	private LocalDate endDate;
	private double totalPrice;
	private String status;
	private long planDuration;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(mappedBy = "subscription", cascade = CascadeType.ALL)
	private OrderInfo order;

	@ManyToOne
	@JoinColumn(name = "tiffin_id", nullable = false)
	private TiffinPlan tiffinPlan;
	
	private boolean monday = false;
	private boolean tuesday = false;
	private boolean wednesday = false;
	private boolean thursday = false;
	private boolean friday = false;
	private boolean saturday = false;
	private boolean sunday = false;
	
	private boolean isBreakFast = false;
	private boolean isLunch = false;
	private boolean isDinner = false;

	public Subscription() {
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

	public long getPlanDuration() {
		return planDuration;
	}

	public void setPlanDuration(long planDuration) {
		this.planDuration = planDuration;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrderInfo getOrder() {
		return order;
	}

	public void setOrder(OrderInfo order) {
		this.order = order;
	}

	public TiffinPlan getTiffinPlan() {
		return tiffinPlan;
	}

	public void setTiffinPlan(TiffinPlan tiffinPlan) {
		this.tiffinPlan = tiffinPlan;
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
}
