package com.homeybites.services;

import java.util.List;
import com.homeybites.payloads.TiffinPlanDto;

public interface TiffinPlanService {

	// add tiffin plan
	TiffinPlanDto addTiffinPlan(TiffinPlanDto tiffinPlanDto, Integer providerId);

	// update tiffin plan
	TiffinPlanDto updateTiffinPlan(TiffinPlanDto tiffinPlanDto, Integer planId);
	
	// update menuitems on specific day
	TiffinPlanDto updateMenuItemOnDay(Integer planId, String day, Integer oldMenuId, Integer newMenuId);

	// get tiffin plan
	TiffinPlanDto getTiffinPlan(Integer planId);

	// get all tiffin plans of a provider
	List<TiffinPlanDto> getAllTiffinPlansOfProvider(Integer providerId);

	// get all tiffin plans
	List<TiffinPlanDto> getAllTiffinPlans();

	// delete tiffin plan
	void deleteTiffinPlan(Integer planId);
}
