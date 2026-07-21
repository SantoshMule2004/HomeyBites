package com.homeybites.controllers;

import java.util.List;

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

import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.ProviderMenuResponse;
import com.homeybites.payloads.UpdateProviderMenuRequest;
import com.homeybites.services.ProviderMenuService;

@RestController
@RequestMapping("/api/v1/provider/menu")
public class ProviderMenuController {
	private final ProviderMenuService menuService;

	public ProviderMenuController(ProviderMenuService menuService) {
		this.menuService = menuService;
	}

	// 1. CREATE DEFAULT PROVIDER MENU
	@PostMapping
	public ResponseEntity<ApiResponse> createDefault(@RequestAttribute("userId") Long providerId) {
		this.menuService.createDefaultMenus(providerId);
		return new ResponseEntity<>(new ApiResponse(true, "Created"), HttpStatus.OK);
	}

	// 2. GET THE MENU
	@GetMapping
	public ResponseEntity<List<ProviderMenuResponse>> getMenu(@RequestAttribute("userId") Long providerId) {
		return new ResponseEntity<>(this.menuService.getProviderMenus(providerId), HttpStatus.OK);
	}

	// 3. UPDATE MENU ITEMS
	@PutMapping
	public ResponseEntity<ApiResponse> updateMenu(@RequestAttribute("userId") Long providerId,
			@RequestBody UpdateProviderMenuRequest request) {
		menuService.updateProviderMenu(providerId, request);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Menu updated successfully..!", true),
				HttpStatus.NO_CONTENT);
	}

	// 4. TOGGLE MENU STATUS
	@PutMapping("/status/{menuId}")
	public ResponseEntity<ApiResponse> toggleMenuStatus(@RequestAttribute("userId") Long providerId,
			@PathVariable Long menuId, @RequestParam Boolean isActive) {
		menuService.toggleMenuStatus(providerId, menuId, isActive);

		if (isActive)
			return new ResponseEntity<ApiResponse>(new ApiResponse("Menu activated successfully..!", true),
					HttpStatus.NO_CONTENT);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Menu de-activated successfully..!", true),
				HttpStatus.NO_CONTENT);
	}

	// 5. DELETE/CANCEL A MENU
	@DeleteMapping("/{menuId}")
	public ResponseEntity<ApiResponse> removeMenu(@RequestAttribute("userId") Long providerId,
			@PathVariable Long menuId) {
		menuService.removeMenu(providerId, menuId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Menu successfully removed.", true), HttpStatus.OK);
	}
}
