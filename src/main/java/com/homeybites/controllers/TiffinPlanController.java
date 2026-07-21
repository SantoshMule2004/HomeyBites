package com.homeybites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;

import com.homeybites.entities.TiffinPlan;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.CreateTiffinPlanDTO;
import com.homeybites.payloads.NearbyTiffinPlanProjection;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.TiffinPlanFilterRequest;
import com.homeybites.services.TiffinPlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tiffinplan")
public class TiffinPlanController {

	@Autowired
	private TiffinPlanService tiffinPlanService;

	// add tiffin plan
	@PostMapping("/tiffin-provider/{providerId}")
	public ResponseEntity<ApiResponse> addTiffinPlan(@Valid @RequestBody CreateTiffinPlanDTO plan,
			@PathVariable Long providerId) {

		boolean planPresent = this.tiffinPlanService.isPlanPresent(plan.getPlanName(), providerId);
		ApiResponse response = new ApiResponse();

		if (planPresent) {
			response.setMessage("Tiffin plan already exists with the name '" + plan.getPlanName() + "'");
			response.setSuccess(false);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.CONFLICT);
		}

		TiffinPlan tiffinPlan = this.tiffinPlanService.createPlan(providerId, plan);
		response.setClassObj(tiffinPlan);
		response.setMessage("Tiffin plan created successfully..!");
		response.setSuccess(true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// update tiffin plan
	@PutMapping("/{planId}/tiffin-provider/{providerId}")
	public ResponseEntity<ApiResponse> updateTiffinPlan(@Valid @RequestBody CreateTiffinPlanDTO plan,
			@PathVariable Long planId, @PathVariable Long providerId) {
		TiffinPlan tiffinPlan = this.tiffinPlanService.updatePlan(providerId, planId, plan);
		ApiResponse response = new ApiResponse("Tiffin plan updated successfully..!", true, tiffinPlan);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// get tiffin plan by Id
	@GetMapping("/{planId}")
	public ResponseEntity<ApiResponse> getTiffinPlanById(@PathVariable Long planId) {
		TiffinPlan tiffinPlan = this.tiffinPlanService.getTiffinPlanId(planId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, tiffinPlan), HttpStatus.OK);
	}

	// get all tiffin plan of a provider
	@GetMapping("/tiffin-provider/{providerId}")
	public ResponseEntity<PageResponse<TiffinPlan>> getAllTiffinPlanOfProvider(@PathVariable Long providerId,
			@ModelAttribute TiffinPlanFilterRequest filter, Pageable pageable) {
		PageResponse<TiffinPlan> allTiffinPlans = this.tiffinPlanService.getAllProviderPlans(providerId, filter,
				pageable);
		return new ResponseEntity<PageResponse<TiffinPlan>>(allTiffinPlans, HttpStatus.OK);
	}

	// get all tiffin plans nearby user with filterations
	@GetMapping("/public/tiffinplan-nearby")
	public ResponseEntity<PageResponse<NearbyTiffinPlanProjection>> getAllNearbyTiffinPlans(@RequestParam Double lat,
			@RequestParam Double lng, @RequestParam(defaultValue = "10000") Double platformRadius,
			@RequestParam(required = false) Boolean wantsBreakfast, @RequestParam(required = false) Boolean wantsLunch,
			@RequestParam(required = false) Boolean wantsDinner, Pageable pageable) {

		PageResponse<NearbyTiffinPlanProjection> allMenuItem = this.tiffinPlanService.findNearbyTiffinPlans(lat, lng,
				platformRadius, wantsBreakfast, wantsLunch, wantsDinner, pageable);
		return new ResponseEntity<PageResponse<NearbyTiffinPlanProjection>>(allMenuItem, HttpStatus.OK);
	}

	// toggle activate menu
	@PatchMapping("/tiffin-provider/{providerId}/toggle/{planId}")
	public ResponseEntity<ApiResponse> togglePlanStatus(@PathVariable Long providerId, @PathVariable Long planId,
			@RequestParam Boolean isActive) {
		this.tiffinPlanService.togglePlanStatus(providerId, planId, isActive);

		if (isActive)
			return new ResponseEntity<ApiResponse>(new ApiResponse("TiffinPlan activated successfully..!", true),
					HttpStatus.NO_CONTENT);

		return new ResponseEntity<ApiResponse>(new ApiResponse("TiffinPlan de-activated successfully..!", true),
				HttpStatus.NO_CONTENT);
	}

	// delete tiffin plan
	@DeleteMapping("/{planId}/tiffin-provider/{providerId}")
	public ResponseEntity<ApiResponse> deleteTiffinPlan(@PathVariable Long planId, @PathVariable Long providerId) {
		this.tiffinPlanService.deleteTiffinPlan(planId, providerId);
		ApiResponse apiResponse = new ApiResponse("Tiffin plan deleted successfully..!", true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}
