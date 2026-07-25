package com.homeybites.services.impl;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.homeybites.entities.DailyDelivery;
import com.homeybites.payloads.DailyDeliveryStatus;
import com.homeybites.payloads.MealType;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.ProviderDeliveryViewProjection;
import com.homeybites.repositories.DailyDeliveryRepository;
import com.homeybites.services.DailyDeliveryService;

import jakarta.transaction.Transactional;

@Service
public class DailyDeliveryServiceImpl implements DailyDeliveryService {

	private final DailyDeliveryRepository deliveryRepository;

	public DailyDeliveryServiceImpl(DailyDeliveryRepository delRepo) {
		this.deliveryRepository = delRepo;
	}

	@Override
	public PageResponse<ProviderDeliveryViewProjection> getTodaysDeliveries(Long providerId, MealType mealType,
			DailyDeliveryStatus status, String search, Pageable pageable) {
		LocalDate today = LocalDate.now();
		String mealTypeStr = (mealType != null) ? mealType.name() : null;
		String statusStr = (status != null) ? status.name() : null;

		Page<ProviderDeliveryViewProjection> page = deliveryRepository.findDeliveriesWithUserDetails(providerId, today,
				mealTypeStr, statusStr, search, pageable);

		return new PageResponse<>(page);
	}

	@Transactional
	@Override
	public void updateDeliveryStatus(Long providerId, Long deliveryId, DailyDeliveryStatus newStatus) {
		DailyDelivery delivery = deliveryRepository.findById(deliveryId)
				.orElseThrow(() -> new RuntimeException("Delivery task not found."));

		// Security Check: Ensure the kitchen actually owns this delivery
		if (!delivery.getProviderId().equals(providerId)) {
			throw new SecurityException("Unauthorized to update this delivery.");
		}

		// Prevent unnecessary database updates if the status is already correct
		if (delivery.getStatus() != newStatus) {
			delivery.setStatus(newStatus);
			deliveryRepository.save(delivery);
		}
	}
}
