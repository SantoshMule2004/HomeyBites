package com.homeybites.payloads;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as = NearbyMenuProjection.class)
public interface NearbyMenuProjection {
    Long getMenuId();
    String getMenuName();
    Double getPrice();
    String getDescription();
    Long getCount();
    Boolean getIsActive();
    String getMenuType();
    String getImagePublicId();
    String getImageUrl();
    String getFormat();
    Long getCategoryId();

    Long getProviderId();
    String getBusinessName();
    Double getLatitude();
    Double getLongitude();
    Double getServiceRadius();

    Double getDistanceInMeters();
}
