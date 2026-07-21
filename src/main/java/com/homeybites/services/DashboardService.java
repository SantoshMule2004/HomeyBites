package com.homeybites.services;

import com.homeybites.payloads.AdminDashboardDTO;
import com.homeybites.payloads.DashboardFilter;
import com.homeybites.payloads.ProviderDashboardDTO;
import com.homeybites.payloads.RevenueDashboardDTO;

public interface DashboardService {
	ProviderDashboardDTO getProviderDashboard(Long providerId);

	AdminDashboardDTO getAdminDashboard();

	public RevenueDashboardDTO getProviderRevenueDashboard(Long providerId, DashboardFilter filter);

	public RevenueDashboardDTO getAdminRevenueDashboard(DashboardFilter filter);
}


//// Today's orders
//countOrders(providerId,
//        LocalDate.now().atStartOfDay(),
//        LocalDate.now().atTime(LocalTime.MAX));
//
//// Last 7 days
//countOrders(providerId,
//        LocalDate.now().minusDays(6).atStartOfDay(),
//        LocalDate.now().atTime(LocalTime.MAX));
//
//// This month
//countOrders(providerId,
//        LocalDate.now().withDayOfMonth(1).atStartOfDay(),
//        LocalDate.now().atTime(LocalTime.MAX));
//
//// All time
//countOrders(providerId, null, null);