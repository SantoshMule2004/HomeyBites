package com.homeybites.payloads;

import java.math.BigDecimal;

public interface RevenueSummaryProjection {

    BigDecimal getGrossRevenue();

    BigDecimal getRefundedAmount();

    BigDecimal getNetRevenue();

    Long getSuccessfulPayments();

    Long getPendingPayments();

    Long getFailedPayments();

    Long getRefundedPayments();
}
