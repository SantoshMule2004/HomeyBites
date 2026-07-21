package com.homeybites.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PaymentDetailsProjection;
import com.homeybites.payloads.PaymentFilterRequest;
import com.homeybites.payloads.PaymentHistoryProjection;
import com.homeybites.payloads.RevenueChartProjection;
import com.homeybites.payloads.RevenueGroupBy;
import com.homeybites.payloads.RevenueSummaryProjection;
import com.homeybites.services.PaymentService;

@RestController
@RequestMapping("/api/v1/admin/payments")
public class AdminPaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping
	public ResponseEntity<PageResponse<PaymentHistoryProjection>> getPayments(PaymentFilterRequest filter,
			Pageable pageable) {
		return ResponseEntity.ok(paymentService.getPayments(filter, pageable));
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentDetailsProjection> getPaymentDetails(@PathVariable Long paymentId) {
		return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId));
	}

	@GetMapping("/revenue/summary")
	public ResponseEntity<RevenueSummaryProjection> getPlatformRevenueSummary(
			@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {
		return ResponseEntity.ok(paymentService.getPlatformRevenueSummary(startDate, endDate));
	}

	@GetMapping("/revenue/chart")
	public ResponseEntity<List<RevenueChartProjection>> getPlatformRevenueChart(
			@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate,
			@RequestParam(defaultValue = "DAY") RevenueGroupBy groupBy) {
		return ResponseEntity.ok(paymentService.getPlatformRevenueChart(startDate, endDate, groupBy));
	}

}
