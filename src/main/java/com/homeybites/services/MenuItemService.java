package com.homeybites.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.homeybites.entities.MenuItem;
import com.homeybites.payloads.NearbyMenuProjection;

public interface MenuItemService {

	// add new menu item
	MenuItem addMenuItem(MenuItem menuItemData, MultipartFile file, Long categoryId, Long providerId)
			throws IOException;

	// uploading menu image
	MenuItem UploadMenuImage(MultipartFile file, Long menuId) throws IOException;

	// get menu item
	NearbyMenuProjection getMenuItem(Long menuId, double userLat, double userLng);

	// get menu items by category
	List<MenuItem> getMenuItemByCategory(Long cId);

	// get menu items of tiffin provider
	List<MenuItem> getMenuItemByTiffinProvider(Long userId);

	// get all nearby menuitems from user
	List<NearbyMenuProjection> findProviderNearbyUsers(double userLat, double userLng,
			double maxAbsolutePlatformRadiusInMeters, Long categoryId,
			String menuType, Double maxPrice);

	// get all menu items
	List<MenuItem> getAllMenuItem();

	// get all menu items by menu type
	List<MenuItem> getAllMenuItemByType(String menuType);

	// get all menu items within 5km radius of user
	List<MenuItem> getAllNearbyMenuItem(double latitude, double longitude);

	// update menu item
	MenuItem updateMenuItem(MenuItem menuItem, Long menuId);

	// delete menu item
	void deleteMenuItem(Long menuId);

	// delete menu item
	MenuItem deleteMenuItem(MenuItem menuItem);
}
