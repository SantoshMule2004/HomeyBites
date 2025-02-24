package com.homeybites.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.homeybites.payloads.MenuItemDto;

public interface MenuItemService {
	
	//add new menu item
	MenuItemDto addMenuItem(MenuItemDto menuItemDto, Integer categoryId, Integer userId);
	
	// uploading menu image
	MenuItemDto UploadMenuImage(MultipartFile file, Integer menuId) throws IOException;
	
	//get menu item
	MenuItemDto getMenuItem(Integer menuId);
	
	//get menu items by category
	List<MenuItemDto> getMenuItemByCategory(Integer cId);
	
	//get menu items of tiffin provider
		List<MenuItemDto> getMenuItemByTiffinProvider(Integer userId);
	
	//get all menu items
	List<MenuItemDto> getAllMenuItem();
	
	//get all menu items by menu type
	List<MenuItemDto> getAllMenuItemByType(String menuType);
	
	// get all menu items within 5km radius of user
	List<MenuItemDto> getAllNearbyMenuItem(double latitude, double longitude);
	
	//update menu item
	MenuItemDto updateMenuItem(MenuItemDto menuItemDto, Integer menuId);
	
	//delete menu item
	void deleteMenuItem(Integer menuId);
	
	// calculate distance between tiffin provider and user
	double calculateDistance(double lat1, double lon1, double lat2, double lon2);
}
