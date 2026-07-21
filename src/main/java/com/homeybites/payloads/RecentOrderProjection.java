package com.homeybites.payloads;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RecentOrderProjection {

    Long getProviderOrderId();

    Long getCustomerOrderId();

    String getCustomerName();

    BigDecimal getAmount();

    String getFulfillmentStatus();

    LocalDateTime getCreatedAt();
}
