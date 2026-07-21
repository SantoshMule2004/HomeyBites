package com.homeybites.payloads;

import java.time.LocalDateTime;

public interface RecentUserProjection {

    Long getUserId();

    String getUserName();

    String getEmailId();

    LocalDateTime getCreatedAt();
}
