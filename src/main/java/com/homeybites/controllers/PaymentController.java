package com.homeybites.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.Payment;
import com.homeybites.services.PaymentService;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	// API to place order
	@PostMapping("/{userId}")
	public ResponseEntity<Payment> AddPaymentInfo(@RequestBody Payment payment, @PathVariable Integer userId,
			@RequestParam Integer orderId) {
		Payment added = this.paymentService.addPayment(payment, userId, orderId);
		return ResponseEntity.ok(added);
	}

	@GetMapping("/revenue")
	public ResponseEntity<Double> getTotalRevenue() {
		Double revenue = this.paymentService.getTotalRevenue();
		return new ResponseEntity<Double>(revenue, HttpStatus.OK);
	}
	
	@GetMapping("/revenue/menuitem")
	public ResponseEntity<Double> getTotalMenuRevenue() {
		Double revenue = this.paymentService.getMenuitemRevenue();
		return new ResponseEntity<Double>(revenue, HttpStatus.OK);
	}
	
	@GetMapping("/revenue/subscription")
	public ResponseEntity<Double> getTotalSubRevenue() {
		Double revenue = this.paymentService.getSubscriptionRevenue();
		return new ResponseEntity<Double>(revenue, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Payment>> getPaymentsOfUser(@PathVariable Integer userId) {
		List<Payment> payments = this.paymentService.getAllPaymentOfUser(userId);
		return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Payment>> getAllPayments() {
		List<Payment> payments = this.paymentService.getAllPayment();
		return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<Payment> getPayment(@PathVariable Integer paymentId) {
		Payment payment = this.paymentService.getPaymentInfo(paymentId);
		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}

	@GetMapping("/menuitem/{providerId}")
	public ResponseEntity<List<Payment>> getAllMenuItemTransactions(@PathVariable Integer providerId) {
		List<Payment> payments = this.paymentService.getAllMenuTransactionOfProvider(providerId);
		return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
	}

	@GetMapping("/subscription/{providerId}")
	public ResponseEntity<List<Payment>> getAllSubTransactions(@PathVariable Integer providerId) {
		List<Payment> payments = this.paymentService.getAllSubTransactionOfProvider(providerId);
		return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
	}

	@GetMapping("/provider/{providerId}")
	public ResponseEntity<List<Payment>> getAllTransactions(@PathVariable Integer providerId) {
		List<Payment> payments = this.paymentService.getAllTransactionOfProvider(providerId);
		return new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
	}

	@GetMapping("/revenue/provider/{providerId}")
	public ResponseEntity<Double> getTotalRevenueOfProvider(@PathVariable Integer providerId) {
		Double revenue = this.paymentService.getTotalRevenueByProvider(providerId);
		return new ResponseEntity<Double>(revenue, HttpStatus.OK);
	}

	@GetMapping("/revenue/menuitem/{providerId}")
	public ResponseEntity<Double> getTotalMenuRevenueOfProvider(@PathVariable Integer providerId) {
		Double revenue = this.paymentService.getMenuitemRevenueByProvider(providerId);
		return new ResponseEntity<Double>(revenue, HttpStatus.OK);
	}

	@GetMapping("/revenue/subscription/{providerId}")
	public ResponseEntity<Double> getTotalSubRevenueOfProvider(@PathVariable Integer providerId) {
		Double revenue = this.paymentService.getSubscriptionRevenueByProvider(providerId);
		return new ResponseEntity<Double>(revenue, HttpStatus.OK);
	}
	
	@GetMapping("/history/provider/{providerId}")
	public ResponseEntity<List<Object[]>> getPaymentHistoryOfProvider(@PathVariable Integer providerId, @RequestParam String date) {
		LocalDate pastDate = LocalDate.parse(date);
		List<Object[]> list = this.paymentService.getPastRevenueByProvider(providerId, pastDate);
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/history")
	public ResponseEntity<List<Object[]>> getPaymentHistory(@RequestParam String date) {
		LocalDate pastDate = LocalDate.parse(date);
		List<Object[]> list = this.paymentService.getPastRevenueRecords(pastDate);
		return new ResponseEntity<List<Object[]>>(list, HttpStatus.OK);
	}
}
