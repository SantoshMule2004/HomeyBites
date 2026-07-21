package com.homeybites.payloads;

import java.math.BigDecimal;

public interface RevenueChartProjection {

    String getPeriod();

    BigDecimal getRevenue();

    Long getPaymentCount();

    Long getRefundCount();

}