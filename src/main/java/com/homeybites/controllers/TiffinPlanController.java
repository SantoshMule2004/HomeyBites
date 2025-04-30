package com.homeybites.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.TiffinDays;
import com.homeybites.entities.TiffinPlan;
import com.homeybites.entities.Log.TiffinPlanLog;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.UpdateMenuItemDto;
import com.homeybites.services.TiffinPlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tiffinplan")
public class TiffinPlanController {

	@Autowired
	private TiffinPlanService tiffinPlanService;

	// add tiffin plan
	@PostMapping("/tiffin-provider/{providerId}")
	public ResponseEntity<ApiResponse> addTiffinPlan(@Valid @RequestBody TiffinPlan plan,
			@PathVariable Integer providerId) {

		boolean planPresent = this.tiffinPlanService.isPlanPresent(plan.getPlanName(), providerId);
		ApiResponse response = new ApiResponse();

		if (planPresent) {
			response.setMessage("Tiffin plan already exists with the name '" + plan.getPlanName() + "'");
			response.setSuccess(false);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.CONFLICT);
		}
		TiffinPlan tiffinPlan = this.tiffinPlanService.addTiffinPlan(plan, providerId);
		response.setClassObj(tiffinPlan);
		response.setMessage("Tiffin plan created successfully..!");
		response.setSuccess(true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// update tiffin plan
	@PutMapping("/{planId}")
	public ResponseEntity<ApiResponse> updateTiffinPlan(@Valid @RequestBody TiffinPlan plan,
			@PathVariable Integer planId) {
		TiffinPlan tiffinPlan = this.tiffinPlanService.updateTiffinPlan(plan, planId);
		ApiResponse response = new ApiResponse("Tiffin plan updated successfully..!", true, tiffinPlan);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// update tiffin plan
	@PutMapping("/{planId}/day/{day}")
	public ResponseEntity<ApiResponse> updateMenuItems(@PathVariable Integer planId, @PathVariable String day,
			@RequestBody UpdateMenuItemDto updateMenuItemDto) {
		TiffinPlan tiffinPlan = this.tiffinPlanService.updateMenuItemOnDay(planId, day, updateMenuItemDto);
		ApiResponse response = new ApiResponse("Menuitems updated successfully..!", true, tiffinPlan);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// get tiffin plan
	@GetMapping("/{planId}")
	public ResponseEntity<TiffinPlan> getTiffinPlan(@PathVariable Integer planId) {
		TiffinPlan tiffinPlan = this.tiffinPlanService.getTiffinPlan(planId);
		return new ResponseEntity<TiffinPlan>(tiffinPlan, HttpStatus.OK);
	}

	// get tiffin plan
	@GetMapping("/log/{planId}")
	public ResponseEntity<TiffinPlanLog> getTiffinPlanLog(@PathVariable Integer planId) {
		TiffinPlanLog tiffinPlan = this.tiffinPlanService.getTiffinPlanLog(planId);
		return new ResponseEntity<TiffinPlanLog>(tiffinPlan, HttpStatus.OK);
	}

	// get all tiffin plan of a provider
	@GetMapping("/tiffin-provider/{providerId}")
	public ResponseEntity<List<TiffinPlan>> getAllTiffinPlanOfProvider(@PathVariable Integer providerId) {
		List<TiffinPlan> allTiffinPlans = this.tiffinPlanService.getAllTiffinPlansOfProvider(providerId);
		return new ResponseEntity<List<TiffinPlan>>(allTiffinPlans, HttpStatus.OK);
	}

	// get all tiffin plan
	@GetMapping("/")
	public ResponseEntity<List<TiffinPlan>> getAllTiffinPlan() {
		List<TiffinPlan> allTiffinPlans = this.tiffinPlanService.getAllTiffinPlans();
		return new ResponseEntity<List<TiffinPlan>>(allTiffinPlans, HttpStatus.OK);
	}

	// get all tiffin plan
	@GetMapping("/tiffinDays/{menuId}")
	public ResponseEntity<List<TiffinDays>> getAllTiffinDays(@PathVariable Integer menuId) {
		List<TiffinDays> list = this.tiffinPlanService.getAllTiffinDaysByMenuItem(menuId);
		return new ResponseEntity<List<TiffinDays>>(list, HttpStatus.OK);
	}

	// delete tiffin plan
	@DeleteMapping("/{planId}")
	public ResponseEntity<ApiResponse> deleteTiffinPlan(@PathVariable Integer planId) {
		this.tiffinPlanService.deleteTiffinPlan(planId);
		ApiResponse apiResponse = new ApiResponse("Tiffin plan deleted successfully..!", true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}

	// delete tiffin plan
	@DeleteMapping("/log/{planId}")
	public ResponseEntity<ApiResponse> deleteTiffinPlanLog(@PathVariable Integer planId) {
		this.tiffinPlanService.deleteTiffinPlanLog(planId);
		ApiResponse apiResponse = new ApiResponse("Tiffin plan log deleted successfully..!", true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}
