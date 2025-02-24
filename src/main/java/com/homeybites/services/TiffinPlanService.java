package com.homeybites.services;

import java.util.List;

import com.homeybites.payloads.TiffinPlanDto;

public interface TiffinPlanService {
	
	// add tiffin plan
	TiffinPlanDto addTiffinPlan(TiffinPlanDto tiffinPlanDto);
	
	// add Menu items to a plan
	TiffinPlanDto addMenuItems(Integer planId, List<Integer> menuIds);
	
	// update tiffin plan
	TiffinPlanDto updateTiffinPlan(TiffinPlanDto tiffinPlanDto, Integer planId);
	
	// get tiffin plan
	TiffinPlanDto getTiffinPlan(Integer planId);
	
	// get all tiffin plans
	List<TiffinPlanDto> getAllTiffinPlans();
	
	// delete tiffin plan
	void deleteTiffinPlan(Integer planId);
	
	// delete specific menu item from a plan
	TiffinPlanDto deleteMenuItemFromPlan(Integer planId, Integer menuId);
}
