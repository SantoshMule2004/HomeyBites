package com.homeybites.payloads;

import java.time.LocalDate;

public class DashboardFilter {
	private LocalDate startDate;
	private LocalDate endDate;
	private RevenueGroupBy groupBy;
	private DateFilter dateFilter;

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

	public RevenueGroupBy getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(RevenueGroupBy groupBy) {
		this.groupBy = groupBy;
	}

	public DateFilter getDateFilter() {
		return dateFilter;
	}

	public void setDateFilter(DateFilter dateFilter) {
		this.dateFilter = dateFilter;
	}
}
