package com.homeybites.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homeybites.entities.MenuItem;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.NearbyMenuProjection;
import com.homeybites.services.MenuItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class MenuController {

	@Autowired
	private MenuItemService menuItemService;

	// add menu item
	@PostMapping(value = "/user/{userId}/category/{cId}/menuitem/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse> addMenuItem(@Valid @RequestPart MenuItem menuItemData,
			@RequestPart MultipartFile file, @PathVariable Long cId, @PathVariable Long userId)
			throws IOException {

		MenuItem menuItem = this.menuItemService.addMenuItem(menuItemData, file, cId, userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("MenuItem added successfully..!", true, menuItem),
				HttpStatus.CREATED);
	}

	// upload menu item image
	@PostMapping("/menuitem/upload/{menuId}")
	public ResponseEntity<?> uploadMenuImage(@RequestParam MultipartFile file, @PathVariable Long menuId)
			throws IOException {
		this.menuItemService.UploadMenuImage(file, menuId);
		return new ResponseEntity<>("Image uploaded successfully..! ", HttpStatus.OK);
	}

	// get menu item by id
	@GetMapping("/public/menuitem/{menuId}")
	public ResponseEntity<NearbyMenuProjection> getMenuItem(@PathVariable Long menuId, @RequestParam Double lat,
			@RequestParam Double lng) {
		NearbyMenuProjection menuItem = this.menuItemService.getMenuItem(menuId, lat, lng);
		return new ResponseEntity<NearbyMenuProjection>(menuItem, HttpStatus.OK);
	}

	// get all menu items
	@GetMapping("/public/menuitems")
	public ResponseEntity<List<MenuItem>> getAllMenuItems() {
		List<MenuItem> allMenuItem = this.menuItemService.getAllMenuItem();
		return new ResponseEntity<List<MenuItem>>(allMenuItem, HttpStatus.OK);
	}

	// get all menuitems nearby user with filterations (category, menutype,
	// maxprice, platformradius)
	@GetMapping("/public/menuitem-nearby")
	public ResponseEntity<List<NearbyMenuProjection>> getAllMenusWithProviders(@RequestParam Double lat,
			@RequestParam Double lng, @RequestParam(defaultValue = "10000") Double platformRadius,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) String menuType,
			@RequestParam(required = false) Double maxPrice) {
		List<NearbyMenuProjection> allMenuItem = this.menuItemService.findProviderNearbyUsers(lat, lng, platformRadius,
				categoryId, menuType, maxPrice);
		return new ResponseEntity<List<NearbyMenuProjection>>(allMenuItem, HttpStatus.OK);
	}

	// get all menu items of a tiffin provider
	@GetMapping("/tiffin-provider/{userId}/menuitems")
	public ResponseEntity<List<MenuItem>> getMenuItemByTiffinProvider(@PathVariable Long userId) {
		List<MenuItem> menuItems = this.menuItemService.getMenuItemByTiffinProvider(userId);
		return new ResponseEntity<List<MenuItem>>(menuItems, HttpStatus.OK);
	}

	// Update menu item
	@PutMapping("/menuitem/{menuId}")
	public ResponseEntity<ApiResponse> updateMenuItem(@Valid @RequestBody MenuItem menuItem,
			@PathVariable Long menuId) {
		MenuItem updatedMenuItem = this.menuItemService.updateMenuItem(menuItem, menuId);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("menuitem updated successfully.>!", true, updatedMenuItem), HttpStatus.OK);
	}

	// delete menu item
	@DeleteMapping("/menuitem/{menuId}")
	public ResponseEntity<ApiResponse> deleteMenuItem(@PathVariable Long menuId) {
		this.menuItemService.deleteMenuItem(menuId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("menuitem deleted successfully..!", true),
				HttpStatus.NO_CONTENT);
	}
}
