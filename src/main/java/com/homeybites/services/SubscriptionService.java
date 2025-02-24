package com.homeybites.services;

import java.util.List;

import com.homeybites.payloads.SubscriptionDto;

public interface SubscriptionService {
	
	// get all subscription plans
	List<SubscriptionDto> getAllSubscriptions();
	
	// get subscription plan
	SubscriptionDto getSubscription(Integer subId);
}
