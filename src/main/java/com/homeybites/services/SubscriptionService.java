package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.Subscription;
import com.homeybites.entities.Log.SubscriptionLog;

public interface SubscriptionService {

	// add subscription plan of user
	Subscription addSubscriptionPlan(Subscription subscription, Integer userId, Integer planId);

	// get subscription plan
	Subscription getSubscription(Integer subId);

	// get subscription plans of user
	List<Subscription> getAllSubscriptionOfUser(Integer userId);

	// get subscription plans of user
	List<SubscriptionLog> getAllSubscriptionHistoryOfUser(Integer userId);

	// get subscription plans of user who subscribed to a tiffin provider
	List<Subscription> getAllSubscriptionOfTiffinProvider(Integer userId);

	// get all subscription plans
	List<Subscription> getAllSubscriptions();
	
	Integer getSubscriptionCount(Integer providerId);
	
	Integer getAllSubscriptionCount();

	// delete subscription plan
	void deleteSubscriptionPlan(Integer subId);

	// delete subscription plan log
	void deleteSubscriptionPlanLog(Integer subId);

	// deleting expired plans
	void deleteExpiredPlans();
}
