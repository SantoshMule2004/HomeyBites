package com.homeybites.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.Subscription;
import com.homeybites.entities.Log.SubscriptionLog;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.services.SubscriptionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	// add subscription plan
	@PostMapping("/user/{userId}/plan/{planId}")
	public ResponseEntity<ApiResponse> addSubscriptionPlan(@Valid @RequestBody Subscription sub,
			@PathVariable Integer userId, @PathVariable Integer planId) {

		Subscription subscriptionPlan = this.subscriptionService.addSubscriptionPlan(sub, userId, planId);

		if (subscriptionPlan == null)
			return new ResponseEntity<ApiResponse>(new ApiResponse("Cannot create subscription plan..!"),
					HttpStatus.CONFLICT);
		else
			return new ResponseEntity<ApiResponse>(
					new ApiResponse("Subscription plan created successfully..!", true, subscriptionPlan),
					HttpStatus.CREATED);
	}

	// get Subscription info
	@GetMapping("/{subId}")
	public ResponseEntity<Subscription> getSubscription(@PathVariable Integer subId) {
		Subscription subscription = this.subscriptionService.getSubscription(subId);
		return new ResponseEntity<Subscription>(subscription, HttpStatus.OK);
	}

	// get Subscription info
	@GetMapping("/count/{providerId}")
	public ResponseEntity<Integer> getSubscriptionCount(@PathVariable Integer providerId) {
		Integer count = this.subscriptionService.getSubscriptionCount(providerId);
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	// get Subscription info
	@GetMapping("/count")
	public ResponseEntity<Integer> getAllSubscriptionCount() {
		Integer count = this.subscriptionService.getAllSubscriptionCount();
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	// get all subscriptions of user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Subscription>> getAllSubscriptionsOfUser(@PathVariable Integer userId) {
		List<Subscription> allSubscriptions = this.subscriptionService.getAllSubscriptionOfUser(userId);
		return new ResponseEntity<List<Subscription>>(allSubscriptions, HttpStatus.OK);
	}

	// get all subscriptions of user
	@GetMapping("/history/{userId}")
	public ResponseEntity<List<SubscriptionLog>> getSubscriptionHistory(@PathVariable Integer userId) {
		List<SubscriptionLog> allSubscriptions = this.subscriptionService.getAllSubscriptionHistoryOfUser(userId);
		return new ResponseEntity<List<SubscriptionLog>>(allSubscriptions, HttpStatus.OK);
	}

	// get all subscriptions of user
	@GetMapping("/tiffin-provider/{userId}")
	public ResponseEntity<List<Subscription>> getAllSubscriptionsOfTiffinProvider(@PathVariable Integer userId) {
		List<Subscription> allSubscriptions = this.subscriptionService.getAllSubscriptionOfTiffinProvider(userId);
		return new ResponseEntity<List<Subscription>>(allSubscriptions, HttpStatus.OK);
	}

	// get all subscription
	@GetMapping("/")
	public ResponseEntity<List<Subscription>> getAllSubscriptions() {
		List<Subscription> allSubscriptions = this.subscriptionService.getAllSubscriptions();
		return new ResponseEntity<List<Subscription>>(allSubscriptions, HttpStatus.OK);
	}

	// delete subscription
	@DeleteMapping("/{planId}")
	public ResponseEntity<ApiResponse> deleteSubscription(@PathVariable Integer planId) {
		this.subscriptionService.deleteSubscriptionPlan(planId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("plan deleted successfully.!"), HttpStatus.OK);
	}

	// delete subscription log
	@DeleteMapping("/log/{planId}")
	public ResponseEntity<ApiResponse> deleteSubscriptionLog(@PathVariable Integer planId) {
		this.subscriptionService.deleteSubscriptionPlanLog(planId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("plan deleted successfully.!"), HttpStatus.OK);
	}
}
