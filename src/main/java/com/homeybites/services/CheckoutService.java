package com.homeybites.services;

public interface CheckoutService {
	void processCheckout(Long customerId, String paymentMethod, String paymentStatus, Long addId);
}
