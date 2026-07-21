package com.homeybites.payloads;

import java.util.List;

public class RevenueDashboardDTO {

	private RevenueSummaryProjection revenueSummary;

	private List<RevenueChartProjection> revenueChart;

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
}