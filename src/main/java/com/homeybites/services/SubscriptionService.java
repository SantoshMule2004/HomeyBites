package com.homeybites.services;

import java.util.List;

import com.homeybites.payloads.SubscriptionDto;

public interface SubscriptionService {

	// add subscription plan of user
	SubscriptionDto addSubscriptionPlan(SubscriptionDto subscriptionDto, Integer userId, Integer planId);

	// get subscription plan
	SubscriptionDto getSubscription(Integer subId);

	// get subscription plans of user
	List<SubscriptionDto> getAllSubscriptionOfUser(Integer userId);

	// get subscription plans of user who subscribed to a tiffin provider
	List<SubscriptionDto> getAllSubscriptionOfTiffinProvider(Integer userId);

	// get all subscription plans
	List<SubscriptionDto> getAllSubscriptions();

	// delete subscription plan
	void deleteSubscriptionPlan(Integer subId);

	// delete subscription plan log
	void deleteSubscriptionPlanLog(Integer subId);
}
