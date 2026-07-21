package com.homeybites.payloads;

import java.time.LocalDateTime;

public interface RecentProviderProjection {

    Long getProviderId();

    String getBusinessName();

    String getOwnerName();

    String getEmailId();

    Boolean getActive();

    LocalDateTime getCreatedAt();

}