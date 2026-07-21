package com.homeybites.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import com.homeybites.entities.MenuItem;
import com.homeybites.payloads.NearbyMenuProjection;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.MenuFilterRequest;
import com.homeybites.payloads.MenuProjection;

public interface MenuItemService {

	// add new menu item with image
	MenuItem addMenuItemWithImage(MenuItem menuItemData, MultipartFile file, Long categoryId, Long providerId)
			throws IOException;

	// add new menu item
	MenuItem addMenuItem(MenuItem menuItemData, Long categoryId, Long providerId);

	// uploading menu image
	MenuItem UploadMenuImage(MultipartFile file, Long menuId) throws IOException;

	// get menu item
	NearbyMenuProjection getMenuItem(Long menuId, double userLat, double userLng);

	// get menu items by category
	List<MenuItem> getMenuItemByCategory(Long cId);

	// get menu items of tiffin provider
	PageResponse<MenuProjection> getMenuItemByTiffinProvider(Long providerId, MenuFilterRequest menuFilters, Pageable pageable);

	// get all nearby menuitems from user
	PageResponse<NearbyMenuProjection> findProviderNearbyUsers(double userLat, double userLng,
			double maxAbsolutePlatformRadiusInMeters, Long categoryId, String menuType, Double maxPrice,
			Pageable pageable);

	// get all menu items
	List<MenuItem> getAllMenuItem();

	// get all menu items by menu type
	List<MenuItem> getAllMenuItemByType(String menuType);

	// update menu item
	MenuItem updateMenuItem(MenuItem menuItem, Long menuId);

	// delete menu item
	void toggleActivateMenu(Long menuId, boolean isActive);

	// delete menu item
	void deleteMenuItem(Long menuId);
}
