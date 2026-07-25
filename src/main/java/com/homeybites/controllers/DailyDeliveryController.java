package com.homeybites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.DailyDeliveryStatus;
import com.homeybites.payloads.MealType;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.ProviderDeliveryViewProjection;
import com.homeybites.services.DailyDeliveryService;

@RestController
@RequestMapping("/api/v1/daily-delivery")
public class DailyDeliveryController {

	@Autowired
	private DailyDeliveryService dailyDeliveryService;

	// get todays deliveries for a provider
	@GetMapping
	public ResponseEntity<PageResponse<ProviderDeliveryViewProjection>> getTodaysDeliveries(
			@RequestAttribute("userId") Long providerId, @RequestParam(required = false) MealType mealType,
			@RequestParam(required = false) DailyDeliveryStatus status, @RequestParam(required = false) String search,
			Pageable pageable) {

		PageResponse<ProviderDeliveryViewProjection> deliveries = this.dailyDeliveryService
				.getTodaysDeliveries(providerId, mealType, status, search, pageable);
		return new ResponseEntity<>(deliveries, HttpStatus.OK);
	}

	// updating delivery status
	@PutMapping("/{deliveryId}/status")
	public ResponseEntity<ApiResponse> updateStatus(@RequestAttribute("userId") Long providerId,
			@PathVariable Long deliveryId, @RequestParam DailyDeliveryStatus status) {
		dailyDeliveryService.updateDeliveryStatus(providerId, deliveryId, status);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Delivery status updated to" + status.name(), true),
				HttpStatus.OK);
	}
}
