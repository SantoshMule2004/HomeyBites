package com.homeybites.payloads;

import java.math.BigDecimal;
import java.util.List;

public class AdminDashboardDTO {
	private Long totalUsers;

    private Long totalProviders;

    private Long activeProviders;

    private Long todayOrders;

    private Long pendingOrders;

    private Long activeSubscriptions;
    
    private Long todayDeliveries;

    private BigDecimal todayRevenue;

    private BigDecimal averageOrderValue;

    // Revenue
    private RevenueSummaryProjection revenueSummary;

    private List<RevenueChartProjection> revenueChart;

    // Tables
    private List<RecentOrderProjection> recentOrders;

    private List<RecentPaymentProjection> recentPayments;

    private List<RecentUserProjection> recentUsers;

    private List<RecentProviderProjection> recentProviders;

	public Long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(Long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Long getTotalProviders() {
		return totalProviders;
	}

	public void setTotalProviders(Long totalProviders) {
		this.totalProviders = totalProviders;
	}

	public Long getActiveProviders() {
		return activeProviders;
	}

	public void setActiveProviders(Long activeProviders) {
		this.activeProviders = activeProviders;
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

	public Long getTodayDeliveries() {
		return todayDeliveries;
	}

	public void setTodayDeliveries(Long todayDeliveries) {
		this.todayDeliveries = todayDeliveries;
	}

	public BigDecimal getTodayRevenue() {
		return todayRevenue;
	}

	public void setTodayRevenue(BigDecimal todayRevenue) {
		this.todayRevenue = todayRevenue;
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

	public List<RecentPaymentProjection> getRecentPayments() {
		return recentPayments;
	}

	public void setRecentPayments(List<RecentPaymentProjection> recentPayments) {
		this.recentPayments = recentPayments;
	}

	public List<RecentUserProjection> getRecentUsers() {
		return recentUsers;
	}

	public void setRecentUsers(List<RecentUserProjection> recentUsers) {
		this.recentUsers = recentUsers;
	}

	public List<RecentProviderProjection> getRecentProviders() {
		return recentProviders;
	}

	public void setRecentProviders(List<RecentProviderProjection> recentProviders) {
		this.recentProviders = recentProviders;
	}
}
