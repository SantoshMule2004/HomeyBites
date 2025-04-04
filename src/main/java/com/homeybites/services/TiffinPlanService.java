package com.homeybites.services;

import java.util.List;
import com.homeybites.payloads.TiffinPlanDto;
import com.homeybites.payloads.UpdateMenuItemDto;

public interface TiffinPlanService {

	// add tiffin plan
	TiffinPlanDto addTiffinPlan(TiffinPlanDto tiffinPlanDto, Integer providerId);

	// update tiffin plan
	TiffinPlanDto updateTiffinPlan(TiffinPlanDto tiffinPlanDto, Integer planId);

	// update menuitems on specific day
	TiffinPlanDto updateMenuItemOnDay(Integer planId, String day, UpdateMenuItemDto updateMenuItemDto);

	// get tiffin plan
	TiffinPlanDto getTiffinPlan(Integer planId);

	// get all tiffin plans of a provider
	List<TiffinPlanDto> getAllTiffinPlansOfProvider(Integer providerId);

	// get all tiffin plans
	List<TiffinPlanDto> getAllTiffinPlans();

	// delete tiffin plan
	void deleteTiffinPlan(Integer planId);

	// delete tiffin plan log
	void deleteTiffinPlanLog(Integer planId);
}
