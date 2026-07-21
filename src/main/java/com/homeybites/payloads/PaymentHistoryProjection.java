package com.homeybites.payloads;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentHistoryProjection {

    Long getPaymentId();

    String getTransactionId();

    BigDecimal getAmount();

    BigDecimal getRefundedAmount();

    String getPaymentType();

    String getPaymentMethod();

    String getPaymentStatus();

    LocalDateTime getPaidAt();

    String getCustomerName();

    String getProviderName();
}
