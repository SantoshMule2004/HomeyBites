package com.homeybites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.Subscription;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.SubscriptionFilterRequest;
import com.homeybites.payloads.SubscriptionRequestDTO;
import com.homeybites.payloads.SubscriptionWithUserProjection;
import com.homeybites.services.SubscriptionCheckoutService;
import com.homeybites.services.SubscriptionManagementService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

	@Autowired
	private SubscriptionCheckoutService checkoutService;

	@Autowired
	private SubscriptionManagementService managementService;

	// add subscription plan
	@PostMapping("/user/{userId}/plan/{planId}")
	public ResponseEntity<ApiResponse> addSubscriptionPlan(@Valid @RequestBody SubscriptionRequestDTO sub,
			@PathVariable Long userId, @PathVariable Long planId) {

		boolean result = this.checkoutService.processSubscriptionCheckout(userId, planId, sub);

		if (!result)
			return new ResponseEntity<ApiResponse>(new ApiResponse("Cannot create subscription plan..!"),
					HttpStatus.CONFLICT);
		else
			return new ResponseEntity<ApiResponse>(
					new ApiResponse("Subscription plan created successfully..!", true, null), HttpStatus.CREATED);
	}

	// pause subscription
	@PutMapping("/pause/{subId}")
	public ResponseEntity<ApiResponse> pauseSubscription(@PathVariable Long subId,
			@RequestParam("1") Long requestedDays) {
		this.managementService.pauseSubscription(subId, requestedDays.intValue());
		return new ResponseEntity<ApiResponse>(new ApiResponse("Subscription paused successfully..!", true, null),
				HttpStatus.OK);
	}

	// cancel subscription
	@PutMapping("/cancel/{subId}")
	public ResponseEntity<ApiResponse> cancelSubscription(@PathVariable Long subId) {
		this.managementService.cancelSubscription(subId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Subscription Cancelled successfully..!", true, null),
				HttpStatus.OK);
	}

	// get Subscription info by Id
	@GetMapping("/{subId}")
	public ResponseEntity<ApiResponse> getSubscription(@PathVariable Long subId) {
		Subscription subscription = this.managementService.getSubscription(subId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, subscription), HttpStatus.OK);
	}

	// get all subscriptions of user (status - ACTIVE, PAUSED, CANCELLED, COMPLETED,
	// HISTORY)
	@GetMapping("/user/{userId}")
	public ResponseEntity<PageResponse<Subscription>> getAllSubscriptionsOfUser(@PathVariable Long userId,
			@ModelAttribute SubscriptionFilterRequest filter, Pageable pageable) {
		PageResponse<Subscription> allSubscriptions = this.managementService.getAllSubscriptionOfUser(userId, filter,
				pageable);
		return new ResponseEntity<PageResponse<Subscription>>(allSubscriptions, HttpStatus.OK);
	}

	// get Subscription count for a provider
	@GetMapping("/count/tiffin-provider/{providerId}")
	public ResponseEntity<ApiResponse> getSubscriptionCountByProvider(@PathVariable Long providerId) {
		Long count = this.managementService.getSubscriptionCountByProvider(providerId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, count), HttpStatus.OK);
	}

	// get Subscription count
	@GetMapping("/count/all")
	public ResponseEntity<ApiResponse> getAllSubscriptionCount() {
		Long count = this.managementService.getSubscriptionCount();
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, count), HttpStatus.OK);
	}

	// get subscriptions of user by provider (status - ACTIVE, PAUSED, CANCELLED,
	// COMPLETED, HISTORY)
	@GetMapping("/tiffin-provider/{providerId}")
	public ResponseEntity<PageResponse<SubscriptionWithUserProjection>> getSubscriptionsForTiffinProvider(
			@PathVariable Long providerId, @ModelAttribute SubscriptionFilterRequest filter, Pageable pageable) {
		PageResponse<SubscriptionWithUserProjection> allSubscriptions = this.managementService
				.getSubscriptionsForProvider(providerId, filter, pageable);
		return new ResponseEntity<>(allSubscriptions, HttpStatus.OK);
	}

	// get subscriptions for Admin (status - ACTIVE, PAUSED, CANCELLED,
	// COMPLETED, HISTORY)
	@GetMapping("/admin")
	public ResponseEntity<PageResponse<SubscriptionWithUserProjection>> getSubscriptionsForAdmin(
			@ModelAttribute SubscriptionFilterRequest filter, Pageable pageable) {
		PageResponse<SubscriptionWithUserProjection> allSubscriptions = this.managementService
				.getSubscriptionsForAdmin(filter, pageable);
		return new ResponseEntity<>(allSubscriptions, HttpStatus.OK);
	}
}
