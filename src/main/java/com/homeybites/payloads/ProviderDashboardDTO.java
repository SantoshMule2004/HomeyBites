package com.homeybites.payloads;

import java.math.BigDecimal;
import java.util.List;

import com.homeybites.entities.ProviderHoliday;

public class ProviderDashboardDTO {
	// Cards
	private BigDecimal todayRevenue;

	private Long todayOrders;

	private Long pendingOrders;

	private Long activeSubscriptions;

	private Long todaySubscriptions;
	
	private Long todayDeliveries;

	private Long totalMenuItems;

	private Long activePlans;

	private BigDecimal averageOrderValue;

	// Revenue
	private RevenueSummaryProjection revenueSummary;

	private List<RevenueChartProjection> revenueChart;

	// Tables
	private List<RecentOrderProjection> recentOrders;

	private List<RecentSubscriptionProjection> recentSubscriptions;
	
	private List<ProviderHoliday> recentHolidays;

	public BigDecimal getTodayRevenue() {
		return todayRevenue;
	}

	public void setTodayRevenue(BigDecimal todayRevenue) {
		this.todayRevenue = todayRevenue;
	}

	public Long getTodayOrders() {
		return todayOrders;
	}

	public void setTodayOrders(Long todayOrders) {
		this.todayOrders = todayOrders;
	}

	public Long getPendingOrders() {
		return pendingOrders;
	}

	public void setPendingOrders(Long pendingOrders) {
		this.pendingOrders = pendingOrders;
	}

	public Long getActiveSubscriptions() {
		return activeSubscriptions;
	}

	public void setActiveSubscriptions(Long activeSubscriptions) {
		this.activeSubscriptions = activeSubscriptions;
	}

	public Long getTodaySubscriptions() {
		return todaySubscriptions;
	}

	public Long getTodayDeliveries() {
		return todayDeliveries;
	}

	public void setTodayDeliveries(Long todayDeliveries) {
		this.todayDeliveries = todayDeliveries;
	}

	public void setTodaySubscriptions(Long todaySubscriptions) {
		this.todaySubscriptions = todaySubscriptions;
	}

	public Long getTotalMenuItems() {
		return totalMenuItems;
	}

	public void setTotalMenuItems(Long totalMenuItems) {
		this.totalMenuItems = totalMenuItems;
	}

	public Long getActivePlans() {
		return activePlans;
	}

	public void setActivePlans(Long activePlans) {
		this.activePlans = activePlans;
	}

	public BigDecimal getAverageOrderValue() {
		return averageOrderValue;
	}

	public void setAverageOrderValue(BigDecimal averageOrderValue) {
		this.averageOrderValue = averageOrderValue;
	}

	public RevenueSummaryProjection getRevenueSummary() {
		return revenueSummary;
	}

	public void setRevenueSummary(RevenueSummaryProjection revenueSummary) {
		this.revenueSummary = revenueSummary;
	}

	public List<RevenueChartProjection> getRevenueChart() {
		return revenueChart;
	}

	public void setRevenueChart(List<RevenueChartProjection> revenueChart) {
		this.revenueChart = revenueChart;
	}

	public List<RecentOrderProjection> getRecentOrders() {
		return recentOrders;
	}

	public void setRecentOrders(List<RecentOrderProjection> recentOrders) {
		this.recentOrders = recentOrders;
	}

	public List<RecentSubscriptionProjection> getRecentSubscriptions() {
		return recentSubscriptions;
	}

	public void setRecentSubscriptions(List<RecentSubscriptionProjection> recentSubscriptions) {
		this.recentSubscriptions = recentSubscriptions;
	}

	public List<ProviderHoliday> getRecentHolidays() {
		return recentHolidays;
	}

	public void setRecentHolidays(List<ProviderHoliday> recentHolidays) {
		this.recentHolidays = recentHolidays;
	}
}
