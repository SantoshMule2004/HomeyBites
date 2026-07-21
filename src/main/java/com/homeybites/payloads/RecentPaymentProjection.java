package com.homeybites.payloads;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RecentPaymentProjection {

    Long getPaymentId();

    String getCustomerName();

    String getProviderName();

    BigDecimal getAmount();

    String getPaymentMethod();

    String getPaymentStatus();

    LocalDateTime getPaidAt();
}