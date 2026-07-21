package com.homeybites.payloads;

public interface CancelOrderProjection {
	Long getProviderOrderId();

	String getFulfillmentStatus();
	
	Long getCustomerOrderId();
}
