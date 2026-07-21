package com.homeybites.payloads;

public interface ProviderMenuProjection {
    Long getId();

    Long getProviderId();

    String getDayOfWeek();

    Boolean getIsActive();
}
