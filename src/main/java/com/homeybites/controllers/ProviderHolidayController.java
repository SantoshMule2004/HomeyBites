package com.homeybites.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.ProviderHoliday;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.ProviderHolidayDTO;
import com.homeybites.services.ProviderHolidayService;

@RestController
@RequestMapping("/api/v1/provider/holidays")
public class ProviderHolidayController {
	private final ProviderHolidayService holidayService;

	public ProviderHolidayController(ProviderHolidayService holidayService) {
		this.holidayService = holidayService;
	}

	// 1. ADD A NEW HOLIDAY
	@PostMapping
	public ResponseEntity<ApiResponse> addHoliday(@RequestAttribute("userId") Long providerId,
			@RequestBody ProviderHolidayDTO request) {
		System.out.println("Closed date: " + request);
		ProviderHoliday savedHoliday = holidayService.addHoliday(providerId, request);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, savedHoliday), HttpStatus.OK);
	}

	// 2. GET ALL UPCOMING HOLIDAYS (For the Dashboard UI)
	@GetMapping
	public ResponseEntity<ApiResponse> getUpcomingHolidays(@RequestAttribute("userId") Long providerId,
			Pageable pageable) {
		return new ResponseEntity<ApiResponse>(
				new ApiResponse(true, holidayService.getUpcomingHolidays(providerId, pageable)), HttpStatus.OK);
	}

	// 3. GET ALL UPCOMING HOLIDAYS (For the Dashboard UI)
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllHolidays(@RequestAttribute("userId") Long providerId) {
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, holidayService.getAllHolidays(providerId)),
				HttpStatus.OK);
	}

	// 4. UPDATE A HOLIDAY
	@PutMapping("/{holidayId}")
	public ResponseEntity<ApiResponse> updateHoliday(@RequestAttribute("userId") Long providerId,
			@PathVariable Long holidayId, @RequestBody ProviderHolidayDTO request) {
		holidayService.updateHoliday(providerId, holidayId, request);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Holiday updated successfully.", true), HttpStatus.OK);
	}

	// 5. TOGGLE HOLIDAY STATUS
	@PutMapping("/status/{holidayId}")
	public ResponseEntity<ApiResponse> toggleHolidayStatus(@RequestAttribute("userId") Long providerId,
			@PathVariable Long holidayId, @RequestParam Boolean isActive) {
		holidayService.toggleHolidayStatus(providerId, holidayId, isActive);

		if (isActive)
			return new ResponseEntity<ApiResponse>(new ApiResponse("Holiday activated successfully..!", true),
					HttpStatus.NO_CONTENT);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Holiday de-activated successfully..!", true),
				HttpStatus.NO_CONTENT);
	}

	// 6. DELETE/CANCEL A HOLIDAY
	@DeleteMapping("/{holidayId}")
	public ResponseEntity<ApiResponse> removeHoliday(@RequestAttribute("userId") Long providerId,
			@PathVariable Long holidayId) {
		holidayService.removeHoliday(providerId, holidayId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Holiday successfully removed.", true), HttpStatus.OK);
	}
}
