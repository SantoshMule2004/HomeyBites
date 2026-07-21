package com.homeybites.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PaymentDetailsProjection;
import com.homeybites.payloads.PaymentFilterRequest;
import com.homeybites.payloads.PaymentHistoryProjection;
import com.homeybites.payloads.RevenueChartProjection;
import com.homeybites.payloads.RevenueGroupBy;
import com.homeybites.payloads.RevenueSummaryProjection;
import com.homeybites.payloads.UpdatePaymentStatusDto;
import com.homeybites.services.PaymentService;

@RestController
@RequestMapping("/api/v1/provider/payments")
public class ProviderPaymentController {

	@Autowired
	private PaymentService paymentService;

	@PutMapping("/{paymentId}")
	public ResponseEntity<ApiResponse> updatePaymentStatus(@RequestAttribute("userId") Long providerId,
			@PathVariable Long paymentId, @RequestBody UpdatePaymentStatusDto dto) {
		this.paymentService.updatePaymentStatus(paymentId, dto, providerId);
		return ResponseEntity.ok(new ApiResponse("", true));
	}

	@GetMapping
	public ResponseEntity<PageResponse<PaymentHistoryProjection>> getPaymentHistory(
			@RequestAttribute("userId") Long providerId, PaymentFilterRequest filter, Pageable pageable) {

		return ResponseEntity.ok(paymentService.getProviderPayments(providerId, filter, pageable));
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentDetailsProjection> getPaymentDetails(@RequestAttribute("userId") Long providerId,
			@PathVariable Long paymentId) {
		return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId, providerId));
	}

	@GetMapping("/revenue/summary")
	public ResponseEntity<RevenueSummaryProjection> getRevenueSummary(@RequestAttribute("userId") Long providerId,
			@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate) {

		return ResponseEntity.ok(paymentService.getProviderRevenueSummary(providerId, startDate, endDate));
	}

	@GetMapping("/revenue/chart")
	public ResponseEntity<List<RevenueChartProjection>> getRevenueChart(@RequestAttribute("userId") Long providerId,
			@RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate,
			@RequestParam(defaultValue = "DAY") RevenueGroupBy groupBy) {

		return ResponseEntity.ok(paymentService.getProviderRevenueChart(providerId, startDate, endDate, groupBy));
	}
}
