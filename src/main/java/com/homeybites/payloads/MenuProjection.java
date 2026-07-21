package com.homeybites.payloads;

import java.time.LocalDateTime;

public interface MenuProjection {
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
    String getCategoryName();
    Long getProviderId();
    LocalDateTime getCreatedAt();
}
