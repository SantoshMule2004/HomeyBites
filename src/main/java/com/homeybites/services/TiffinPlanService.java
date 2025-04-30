package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.TiffinDays;
import com.homeybites.entities.TiffinPlan;
import com.homeybites.entities.Log.TiffinPlanLog;
import com.homeybites.payloads.UpdateMenuItemDto;

public interface TiffinPlanService {

	// to check if plan is present by given name
	boolean isPlanPresent(String planName, Integer userId);

	// add tiffin plan
	TiffinPlan addTiffinPlan(TiffinPlan tiffinPlan, Integer providerId);

	// update tiffin plan
	TiffinPlan updateTiffinPlan(TiffinPlan tiffinPlan, Integer planId);

	// update menuitems on specific day
	TiffinPlan updateMenuItemOnDay(Integer planId, String day, UpdateMenuItemDto updateMenuItemDto);

	// get tiffin plan
	TiffinPlan getTiffinPlan(Integer planId);

	// get tiffin plan log
	TiffinPlanLog getTiffinPlanLog(Integer planId);

	// get all tiffin plans of a provider
	List<TiffinPlan> getAllTiffinPlansOfProvider(Integer providerId);

	// get all tiffin plans
	List<TiffinPlan> getAllTiffinPlans();

	// get all tiffin Days
	List<TiffinDays> getAllTiffinDaysByMenuItem(Integer menuId);

	// delete tiffin plan
	void deleteTiffinPlan(Integer planId);

	// delete tiffin plan log
	void deleteTiffinPlanLog(Integer planId);
}
