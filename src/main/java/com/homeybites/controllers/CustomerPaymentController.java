package com.homeybites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PaymentDetailsProjection;
import com.homeybites.payloads.PaymentFilterRequest;
import com.homeybites.payloads.PaymentHistoryProjection;
import com.homeybites.services.PaymentService;

@RestController
@RequestMapping("/api/v1/customer/payments")
public class CustomerPaymentController {

	@Autowired
	private PaymentService paymentService;

	@GetMapping
	public ResponseEntity<PageResponse<PaymentHistoryProjection>> getPaymentHistory(@RequestAttribute Long userId,
			PaymentFilterRequest filter, Pageable pageable) {
		return ResponseEntity.ok(paymentService.getCustomerPayments(userId, filter, pageable));
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentDetailsProjection> getPaymentDetails(@RequestAttribute Long userId,
			@PathVariable Long paymentId) {
		return ResponseEntity.ok(paymentService.getPaymentDetails(paymentId, userId));
	}
}
