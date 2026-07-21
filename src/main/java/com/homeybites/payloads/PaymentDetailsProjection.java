package com.homeybites.payloads;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentDetailsProjection {

    Long getPaymentId();

    Long getCustomerOrderId();

    Long getSubscriptionId();

    Long getCustomerId();

    Long getProviderId();

    String getCustomerName();

    String getProviderName();

    BigDecimal getAmount();

    BigDecimal getTaxAmount();

    BigDecimal getDiscountAmount();

    BigDecimal getRefundedAmount();

    String getRefundTransactionId();

    String getPaymentType();

    String getPaymentMethod();

    String getPaymentStatus();

    String getGatewayName();

    String getTransactionId();

    String getGatewayOrderId();

    String getGatewayPaymentId();

    String getFailureReason();

    LocalDateTime getPaidAt();

    LocalDateTime getRefundedAt();

    LocalDateTime getCreatedAt();
}