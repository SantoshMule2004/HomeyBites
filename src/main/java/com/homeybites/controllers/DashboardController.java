package com.homeybites.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.payloads.AdminDashboardDTO;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.DashboardFilter;
import com.homeybites.payloads.ProviderDashboardDTO;
import com.homeybites.payloads.RevenueDashboardDTO;
import com.homeybites.services.DashboardService;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;

	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping("/tiffin-provider")
	public ResponseEntity<ApiResponse> getProviderDashboard(@RequestAttribute("userId") Long providerId) {
		ProviderDashboardDTO dto = this.dashboardService.getProviderDashboard(providerId);
		return ResponseEntity.ok(new ApiResponse(true, dto));
	}

	@GetMapping("/admin")
	public ResponseEntity<ApiResponse> getAdminDashboard() {
		AdminDashboardDTO dto = this.dashboardService.getAdminDashboard();
		return ResponseEntity.ok(new ApiResponse(true, dto));
	}

	@GetMapping("/revenue/admin")
	public ResponseEntity<ApiResponse> getAdminRevenueDashboard(DashboardFilter filter) {
		RevenueDashboardDTO dto = this.dashboardService.getAdminRevenueDashboard(filter);
		return ResponseEntity.ok(new ApiResponse(true, dto));
	}

	@GetMapping("/revenue/tiffin-provider")
	public ResponseEntity<ApiResponse> getProviderRevenueDashboard(@RequestAttribute("userId") Long providerId,
			DashboardFilter filter) {
		RevenueDashboardDTO dto = this.dashboardService.getProviderRevenueDashboard(providerId, filter);
		return ResponseEntity.ok(new ApiResponse(true, dto));
	}
}
