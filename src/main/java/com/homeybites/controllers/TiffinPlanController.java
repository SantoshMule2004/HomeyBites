package com.homeybites.controllers;

import java.util.List;
import java.util.Map;

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
import com.homeybites.services.TiffinPlanService;

@RestController
@RequestMapping("/api/v1/tiffinplan")
public class TiffinPlanController {

	@Autowired
	private TiffinPlanService tiffinPlanService;

	// add tiffin plan
	@PostMapping("/")
	public ResponseEntity<ApiResponse> addTiffinPlan(@RequestBody TiffinPlanDto planDto) {
		TiffinPlanDto tiffinPlan = this.tiffinPlanService.addTiffinPlan(planDto);
		ApiResponse response = new ApiResponse("Tiffin plan added successfully..!", true, tiffinPlan);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// add menu item to a tiffin plan
	@PostMapping("/{planId}/menuitems")
	public ResponseEntity<ApiResponse> addMenuItem(@PathVariable Integer planId, @RequestBody Map<String, List<Integer>> menuIds) {
		List<Integer> ids = menuIds.get("menuIds");
		TiffinPlanDto tiffinPlan = this.tiffinPlanService.addMenuItems(planId, ids);
		ApiResponse response = new ApiResponse("Menuitems added to tiffin plan successfully..!", true, tiffinPlan);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// update tiffin plan
	@PutMapping("/{planId}")
	public ResponseEntity<ApiResponse> updateTiffinPlan(@RequestBody TiffinPlanDto planDto,
			@PathVariable Integer planId) {
		TiffinPlanDto tiffinPlan = this.tiffinPlanService.updateTiffinPlan(planDto, planId);
		ApiResponse response = new ApiResponse("Tiffin plan updated successfully..!", true, tiffinPlan);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// get tiffin plan
	@GetMapping("/{planId}")
	public ResponseEntity<TiffinPlanDto> getTiffinPlan(@PathVariable Integer planId) {
		TiffinPlanDto tiffinPlan = this.tiffinPlanService.getTiffinPlan(planId);
		return new ResponseEntity<TiffinPlanDto>(tiffinPlan, HttpStatus.OK);
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
		ApiResponse response = new ApiResponse("Tiffin plan deleted successfully..!", true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// delete specific menu item from a tiffin plan
	@DeleteMapping("/{planId}/menuitem/{menuId}")
	public ResponseEntity<ApiResponse> deleteMenuItem(@PathVariable Integer planId, @PathVariable Integer menuId) {
		TiffinPlanDto tiffinPlanDto = this.tiffinPlanService.deleteMenuItemFromPlan(planId, menuId);
		ApiResponse response = new ApiResponse("Menuitem removed from tiffin plan successfully..!", true,
				tiffinPlanDto);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
}
