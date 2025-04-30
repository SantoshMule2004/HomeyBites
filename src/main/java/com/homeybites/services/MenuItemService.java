package com.homeybites.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.homeybites.entities.MenuItem;

public interface MenuItemService {

	// add new menu item
	MenuItem addMenuItem(MenuItem menuItemData, MultipartFile file, Integer categoryId, Integer userId)
			throws IOException;

	// uploading menu image
	MenuItem UploadMenuImage(MultipartFile file, Integer menuId) throws IOException;

	// get menu item
	MenuItem getMenuItem(Integer menuId);

	// get menu items by category
	List<MenuItem> getMenuItemByCategory(Integer cId);

	// get menu items of tiffin provider
	List<MenuItem> getMenuItemByTiffinProvider(Integer userId);

	// get all menu items
	List<MenuItem> getAllMenuItem();

	// get all menu items by menu type
	List<MenuItem> getAllMenuItemByType(String menuType);

	// get all menu items within 5km radius of user
	List<MenuItem> getAllNearbyMenuItem(double latitude, double longitude);

	// update menu item
	MenuItem updateMenuItem(MenuItem menuItem, Integer menuId);

	// delete menu item
	void deleteMenuItem(Integer menuId);

	// delete menu item
	MenuItem deleteMenuItem(MenuItem menuItem);

	// calculate distance between tiffin provider and user
	double calculateDistance(double lat1, double lon1, double lat2, double lon2);
}
