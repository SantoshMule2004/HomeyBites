package com.homeybites.services;

import org.springframework.data.domain.Pageable;

import com.homeybites.entities.Subscription;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.SubscriptionFilterRequest;
import com.homeybites.payloads.SubscriptionWithUserProjection;

public interface SubscriptionManagementService {
	void pauseSubscription(Long subscriptionId, int requestedPauseDays);

	void cancelSubscription(Long subscriptionId);

	void resumeSubscription(Long subscriptionId);

	Subscription getSubscription(Long subId);

//	void updateSubscriptionAddressDetails();

	PageResponse<Subscription> getAllSubscriptionOfUser(Long userId, SubscriptionFilterRequest filter,
			Pageable pageable);

	Long getSubscriptionCount();

	Long getSubscriptionCountByProvider(Long providerId);

	PageResponse<SubscriptionWithUserProjection> getSubscriptionsForProvider(Long providerId,
			SubscriptionFilterRequest filter, Pageable pageable);

	PageResponse<SubscriptionWithUserProjection> getSubscriptionsForAdmin(SubscriptionFilterRequest filter,
			Pageable pageable);
}
