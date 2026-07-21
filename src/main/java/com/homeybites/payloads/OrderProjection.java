package com.homeybites.payloads;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderProjection {
	// customer order details
	Long getCustomerOrderId();
	LocalDateTime getCreatedAt();
	BigDecimal getGrandTotal();
	String getReceiverName();
	String getReceiverContactNo();
	String getDeliveryAddress();
	
	// payment details
	String getPaymentMethod();
    String getPaymentStatus();
    
    // provider order details
    Long getProviderOrderId();
    Long getProviderId();
    BigDecimal getVendorSubtotal();
    String getFulfillmentStatus();
    
    // provider details
    String getProviderName();
    String getBusinessName();
}
