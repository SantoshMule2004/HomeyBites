package com.homeybites.payloads;

import java.time.LocalDate;

public interface RecentSubscriptionProjection {

    Long getSubscriptionId();

    String getCustomerName();

    String getPlanName();

    LocalDate getStartDate();

    LocalDate getCurrentEndDate();

    String getStatus();

}