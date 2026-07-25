package com.homeybites.services;

import org.springframework.data.domain.Pageable;

import com.homeybites.payloads.DailyDeliveryStatus;
import com.homeybites.payloads.MealType;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.ProviderDeliveryViewProjection;

public interface DailyDeliveryService {
	PageResponse<ProviderDeliveryViewProjection> getTodaysDeliveries(Long providerId, MealType mealType,
			DailyDeliveryStatus status, String search, Pageable pageable);

	void updateDeliveryStatus(Long providerId, Long deliveryId, DailyDeliveryStatus newStatus);
}
