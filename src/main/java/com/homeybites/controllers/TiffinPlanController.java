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

import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.TiffinPlanDto;
import com.homeybites.payloads.UpdateMenuItemDto;
import com.homeybites.services.TiffinPlanService;

@RestController
@RequestMapping("/api/v1/tiffinplan")
public class TiffinPlanController {

	@Autowired
	private TiffinPlanService tiffinPlanService;

	// add tiffin plan
	@PostMapping("/tiffin-provider/{providerId}")
	public ResponseEntity<ApiResponse> addTiffinPlan(@RequestBody TiffinPlanDto planDto,
			@PathVariable Integer providerId) {
		
		System.out.println("provider Id" + providerId);
		TiffinPlanDto tiffinPlan = this.tiffinPlanService.addTiffinPlan(planDto, providerId);
		ApiResponse response = new ApiResponse("Tiffin plan added successfully..!", true, tiffinPlan);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// update tiffin plan
	@PutMapping("/{planId}")
	public ResponseEntity<ApiResponse> updateTiffinPlan(@RequestBody TiffinPlanDto planDto,
			@PathVariable Integer planId) {
		TiffinPlanDto tiffinPlan = this.tiffinPlanService.updateTiffinPlan(planDto, planId);
		ApiResponse response = new ApiResponse("Tiffin plan updated successfully..!", true, tiffinPlan);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// update tiffin plan
	@PutMapping("/{planId}/day/{day}")
	public ResponseEntity<ApiResponse> updateMenuItems(@PathVariable Integer planId, @PathVariable String day,
			@RequestBody UpdateMenuItemDto updateMenuItemDto) {
		TiffinPlanDto tiffinPlan = this.tiffinPlanService.updateMenuItemOnDay(planId, day, updateMenuItemDto);
		ApiResponse response = new ApiResponse("Menuitems updated successfully..!", true, tiffinPlan);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// get tiffin plan
	@GetMapping("/{planId}")
	public ResponseEntity<TiffinPlanDto> getTiffinPlan(@PathVariable Integer planId) {
		TiffinPlanDto tiffinPlan = this.tiffinPlanService.getTiffinPlan(planId);
		return new ResponseEntity<TiffinPlanDto>(tiffinPlan, HttpStatus.OK);
	}

	// get all tiffin plan of a provider
	@GetMapping("/tiffin-provider/{providerId}")
	public ResponseEntity<List<TiffinPlanDto>> getAllTiffinPlanOfProvider(@PathVariable Integer providerId) {
		List<TiffinPlanDto> allTiffinPlans = this.tiffinPlanService.getAllTiffinPlansOfProvider(providerId);
		return new ResponseEntity<List<TiffinPlanDto>>(allTiffinPlans, HttpStatus.OK);
	}

	// get all tiffin plan
	@GetMapping("/")
	public ResponseEntity<List<TiffinPlanDto>> getAllTiffinPlan() {
		List<TiffinPlanDto> allTiffinPlans = this.tiffinPlanService.getAllTiffinPlans();
		return new ResponseEntity<List<TiffinPlanDto>>(allTiffinPlans, HttpStatus.OK);
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
