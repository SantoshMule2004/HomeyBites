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

import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.SubscriptionDto;
import com.homeybites.services.SubscriptionService;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	// add subscription plan
	@PostMapping("/user/{userId}/plan/{planId}")
	public ResponseEntity<ApiResponse> addSubscriptionPlan(@RequestBody SubscriptionDto dto,
			@PathVariable Integer userId, @PathVariable Integer planId) {

		SubscriptionDto subscriptionPlan = this.subscriptionService.addSubscriptionPlan(dto, userId, planId);

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
	public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable Integer subId) {
		SubscriptionDto subscription = this.subscriptionService.getSubscription(subId);
		return new ResponseEntity<SubscriptionDto>(subscription, HttpStatus.OK);
	}

	// get all subscriptions of user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<SubscriptionDto>> getAllSubscriptionsOfUser(@PathVariable Integer userId) {
		List<SubscriptionDto> allSubscriptions = this.subscriptionService.getAllSubscriptionOfUser(userId);
		return new ResponseEntity<List<SubscriptionDto>>(allSubscriptions, HttpStatus.OK);
	}

	// get all subscriptions of user
	@GetMapping("/tiffin-provider/{userId}")
	public ResponseEntity<List<SubscriptionDto>> getAllSubscriptionsOfTiffinProvider(@PathVariable Integer userId) {
		List<SubscriptionDto> allSubscriptions = this.subscriptionService.getAllSubscriptionOfTiffinProvider(userId);
		return new ResponseEntity<List<SubscriptionDto>>(allSubscriptions, HttpStatus.OK);
	}

	// get all subscription
	@GetMapping("/")
	public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
		List<SubscriptionDto> allSubscriptions = this.subscriptionService.getAllSubscriptions();
		return new ResponseEntity<List<SubscriptionDto>>(allSubscriptions, HttpStatus.OK);
	}

	// delete subscription
	@DeleteMapping("/{planId}")
	public ResponseEntity<ApiResponse> deleteSubscription(@PathVariable Integer planId) {
		this.subscriptionService.deleteSubscriptionPlan(planId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("plan deleted successfully.!"), HttpStatus.OK);
	}

	// delete subscription
	@DeleteMapping("/log/{planId}")
	public ResponseEntity<ApiResponse> deleteSubscriptionLog(@PathVariable Integer planId) {
		this.subscriptionService.deleteSubscriptionPlanLog(planId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("plan deleted successfully.!"), HttpStatus.OK);
	}
}
