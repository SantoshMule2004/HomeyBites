package com.homeybites.payloads;

import java.math.BigDecimal;

public interface DashboardRevenueProjection {

    BigDecimal getRevenue();

    Long getSuccessfulPayments();

    Long getPendingPayments();

    Long getFailedPayments();

    Long getRefundedPayments();
}