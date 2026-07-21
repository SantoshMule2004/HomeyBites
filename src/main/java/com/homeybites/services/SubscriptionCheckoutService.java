package com.homeybites.services;

import com.homeybites.payloads.SubscriptionRequestDTO;

public interface SubscriptionCheckoutService {
	boolean processSubscriptionCheckout(Long userId, Long planId, SubscriptionRequestDTO req);
}
