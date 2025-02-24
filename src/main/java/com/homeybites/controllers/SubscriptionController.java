package com.homeybites.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.payloads.SubscriptionDto;
import com.homeybites.services.SubscriptionService;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	// get Subscription info
	@GetMapping("/{subId}")
	public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable Integer subId) {
		SubscriptionDto subscription = this.subscriptionService.getSubscription(subId);
		return new ResponseEntity<SubscriptionDto>(subscription, HttpStatus.OK);
	}
	
	//get all subscription
	@GetMapping("/")
	public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
		List<SubscriptionDto> allSubscriptions = this.subscriptionService.getAllSubscriptions();
		return new ResponseEntity<List<SubscriptionDto>>(allSubscriptions, HttpStatus.OK);
	}
}
