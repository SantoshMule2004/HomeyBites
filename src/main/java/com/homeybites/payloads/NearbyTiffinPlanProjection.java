package com.homeybites.payloads;

import java.math.BigDecimal;

public interface NearbyTiffinPlanProjection {
	Long getPlanId();

	String getPlanName();

	Integer getValidityDays();

	Boolean getOffersBreakfast();

	Boolean getOffersLunch();

	Boolean getOffersDinner();

	BigDecimal getPricePerBreakfast();

	BigDecimal getPricePerLunch();

	BigDecimal getPricePerDinner();

	// Provider Details
	Long getProviderId();

	String getBusinessName();

	Double getLatitude();

	Double getLongitude();

	Double getServiceRadius();

	Double getDistanceInMeters();
}
