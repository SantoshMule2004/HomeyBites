package com.homeybites.payloads;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SubscriptionWithUserProjection {

	Long getId();

	Long getUserId();

	Long getPlanId();

	Long getProviderId();

	String getPlanName();

	Integer getValidityDays();

	Boolean getIncludesBreakfast();

	Boolean getIncludesLunch();

	Boolean getIncludesDinner();

	BigDecimal getBreakfastPrice();

	BigDecimal getLunchPrice();

	BigDecimal getDinnerPrice();

	LocalDate getStartDate();

	LocalDate getCurrentEndDate();

	Integer getTotalPausedDays();

	String getStatus();

	LocalDate getPauseStartDate();

	LocalDate getAutoResumeDate();

	Long getDeliveryAddressId();

	LocalDateTime getCreatedAt();

	String getEmailId();

	String getPhoneNo();

	Boolean getIsVerified();

	String getCustomerName();

	String getProviderName();
}