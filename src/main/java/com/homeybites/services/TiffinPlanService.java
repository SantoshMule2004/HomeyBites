package com.homeybites.services;

import com.homeybites.entities.TiffinPlan;
import com.homeybites.payloads.CreateTiffinPlanDTO;
import com.homeybites.payloads.NearbyTiffinPlanProjection;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.TiffinPlanFilterRequest;

import org.springframework.data.domain.Pageable;

public interface TiffinPlanService {
	// add tiffin plan
	TiffinPlan createPlan(Long providerId, CreateTiffinPlanDTO req);

	TiffinPlan updatePlan(Long providerId, Long planId, CreateTiffinPlanDTO req);

	void togglePlanStatus(Long providerId, Long planId, boolean isActive);

	PageResponse<TiffinPlan> getAllProviderPlans(Long providerId, TiffinPlanFilterRequest filter, Pageable pageable);

	// get all nearby tiffinplans from user
	PageResponse<NearbyTiffinPlanProjection> findNearbyTiffinPlans(double userLat, double userLng,
			double maxAbsolutePlatformRadiusInMeters, Boolean wantsBreakfast, Boolean wantsLunch, Boolean wantsDinner,
			Pageable pageable);

//	 to check if plan is present by given name
	boolean isPlanPresent(String planName, Long providerId);

	// get tiffin plan
	TiffinPlan getTiffinPlanId(Long planId);

	// delete tiffin plan
	void deleteTiffinPlan(Long planId, Long providerId);
	
//	// update tiffin plan
//	TiffinPlan updateTiffinPlan(TiffinPlan tiffinPlan, Integer planId);
//
//	// get tiffin plan log
////	TiffinPlanLog getTiffinPlanLog(Integer planId);
//
//	// get all tiffin plans of a provider
//	List<TiffinPlan> getAllTiffinPlansOfProvider(Integer providerId);
//
//	// get all tiffin plans
//	List<TiffinPlan> getAllTiffinPlans();
//
//
//	// delete tiffin plan log
//	void deleteTiffinPlanLog(Integer planId);
}
