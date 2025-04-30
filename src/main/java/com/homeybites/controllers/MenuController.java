package com.homeybites.controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homeybites.entities.MenuItem;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.services.MenuItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class MenuController {

	@Autowired
	private MenuItemService menuItemService;

	// add menu item
	@PostMapping("/user/{userId}/category/{cId}/menuitem/")
	public ResponseEntity<ApiResponse> addMenuItem(@Valid @RequestPart MenuItem menuItemData,
			@RequestPart MultipartFile file, @PathVariable Integer cId, @PathVariable Integer userId)
			throws IOException {

		MenuItem menuItem = this.menuItemService.addMenuItem(menuItemData, file, cId, userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("MenuItem added successfully..!", true, menuItem),
				HttpStatus.CREATED);
	}

	// upload menu item image
	@PostMapping("/menuitem/upload/{menuId}")
	public ResponseEntity<?> uploadMenuImage(@RequestParam MultipartFile file, @PathVariable Integer menuId)
			throws IOException {
		this.menuItemService.UploadMenuImage(file, menuId);
		return new ResponseEntity<>("Image uploaded successfully..! ", HttpStatus.OK);
	}

	// get menu item by id
	@GetMapping("/menuitem/{menuId}")
	public ResponseEntity<MenuItem> getMenuItem(@PathVariable Integer menuId) {
		MenuItem menuItem = this.menuItemService.getMenuItem(menuId);
		return new ResponseEntity<MenuItem>(menuItem, HttpStatus.OK);
	}

	// get all menu items
	@GetMapping("/menuitems")
	public ResponseEntity<List<MenuItem>> getAllMenuItems() {
		List<MenuItem> allMenuItem = this.menuItemService.getAllMenuItem();
		return new ResponseEntity<List<MenuItem>>(allMenuItem, HttpStatus.OK);
	}

	// get all menu items
	@GetMapping("/menuitems/type")
	public ResponseEntity<List<MenuItem>> getAllMenuItemsByType(@RequestParam String menuType) {
		List<MenuItem> allMenuItem = this.menuItemService.getAllMenuItemByType(menuType);
		return new ResponseEntity<List<MenuItem>>(allMenuItem, HttpStatus.OK);
	}

	// get all menu items of a category
	@GetMapping("/category/{cId}/menuitems")
	public ResponseEntity<List<MenuItem>> getMenuItemByCategory(@PathVariable Integer cId) {
		List<MenuItem> menuItems = this.menuItemService.getMenuItemByCategory(cId);
		return new ResponseEntity<List<MenuItem>>(menuItems, HttpStatus.OK);
	}

	// get all menu items of a tiffin provider
	@GetMapping("/tiffin-provider/{userId}/menuitems")
	public ResponseEntity<List<MenuItem>> getMenuItemByTiffinProvider(@PathVariable Integer userId) {
		List<MenuItem> menuItems = this.menuItemService.getMenuItemByTiffinProvider(userId);
		return new ResponseEntity<List<MenuItem>>(menuItems, HttpStatus.OK);
	}

	// get all nearby menu items
	@GetMapping("/nearby-menuitems")
	public ResponseEntity<List<MenuItem>> getNearbyMenuItem(@RequestParam double lat, @RequestParam double lon) {
		List<MenuItem> menuItems = this.menuItemService.getAllNearbyMenuItem(lat, lon);
		return new ResponseEntity<List<MenuItem>>(menuItems, HttpStatus.OK);
	}

	// Update menu item
	@PutMapping("/menuitem/{menuId}")
	public ResponseEntity<ApiResponse> updateMenuItem(@Valid @RequestBody MenuItem menuItem,
			@PathVariable Integer menuId) {
		MenuItem updatedMenuItem = this.menuItemService.updateMenuItem(menuItem, menuId);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("menuitem updated successfully.>!", true, updatedMenuItem), HttpStatus.OK);
	}

	// delete menu item
	@DeleteMapping("/menuitem/{menuId}")
	public ResponseEntity<ApiResponse> deleteMenuItem(@PathVariable Integer menuId) {
		this.menuItemService.deleteMenuItem(menuId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("menuitem deleted successfully..!", true),
				HttpStatus.NO_CONTENT);
	}
}
