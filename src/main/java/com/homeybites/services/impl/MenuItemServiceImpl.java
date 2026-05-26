package com.homeybites.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.homeybites.entities.MenuItem;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.ImageInfo;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.ImageService;
//import com.homeybites.services.ImageService;
import com.homeybites.services.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageService imageService;

	@Override
	public MenuItem addMenuItem(MenuItem menuItemData, MultipartFile file, Long categoryId, Long providerId)
			throws IOException {

		ImageInfo uploadImage = this.imageService.uploadImage(file);
		menuItemData.setImagePublicId(uploadImage.getPublicId());
		menuItemData.setImageUrl(uploadImage.getSecuredUrl());
		menuItemData.setFormat(uploadImage.getFormat());

		menuItemData.setCategoryId(categoryId);
		menuItemData.setProviderId(providerId);
		MenuItem savedMenu = this.menuItemRepository.save(menuItemData);

		return savedMenu;
	}

	@Override
	public MenuItem UploadMenuImage(MultipartFile file, Long menuId) throws IOException {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		ImageInfo uploadImage = this.imageService.uploadImage(file);
		menuItem.setImagePublicId(uploadImage.getPublicId());
		menuItem.setImageUrl(uploadImage.getSecuredUrl());
		menuItem.setFormat(uploadImage.getFormat());

		return this.menuItemRepository.save(menuItem);
	}

	@Override
	public MenuItem getMenuItem(Long menuId) {
		return this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));
	}

	@Override
	public List<MenuItem> getMenuItemByCategory(Long cId) {
		return this.menuItemRepository.findByCategoryId(cId);
	}

	@Override
	public List<MenuItem> getAllMenuItem() {
		return this.menuItemRepository.findAll();
	}

	@Override
	public List<MenuItem> getMenuItemByTiffinProvider(Long userId) {
		return this.menuItemRepository.getMenuItemByuserAndActive(userId);
	}

	@Override
	public List<MenuItem> getAllNearbyMenuItem(double latitude, double longitude) {
		// collecting nearby tiffin providers
		List<Integer> providers = this.userRepository.findProvidersDeliveringToLocation(latitude, longitude);

		// finding nearby menu item
		return this.menuItemRepository.findByProviderIdInAndIsActiveTrue(providers);
	}

	@Override
	public MenuItem updateMenuItem(MenuItem menuItemDto, Long menuId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		// creating new menu item after updating previous one
		MenuItem menuItemLog = new MenuItem();
		menuItemLog.setMenuName(menuItemDto.getMenuName());
		menuItemLog.setMenuType(menuItem.getMenuType());
		menuItemLog.setActive(menuItemDto.isActive());
		menuItemLog.setDescription(menuItemDto.getDescription());
		menuItemLog.setImageUrl(menuItem.getImageUrl());
		System.out.println("Image Url" + menuItem.getImageUrl());
		menuItemLog.setImagePublicId(menuItem.getImagePublicId());
		System.out.println("Image Id" + menuItem.getImagePublicId());
		menuItemLog.setFormat(menuItem.getFormat());
		System.out.println("Image Format" + menuItem.getFormat());
		menuItemLog.setPrice(menuItemDto.getPrice());
		menuItemLog.setProviderId(menuItem.getProviderId());
		menuItemLog.setCategoryId(menuItem.getCategoryId());

		// saving new menu item
		MenuItem savedItem = this.menuItemRepository.save(menuItemLog);

		// deleting menu item
		deleteMenuItem(menuItem);

		return savedItem;
	}

	@Override
	public void deleteMenuItem(Long menuId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		menuItem.setActive(false);
		this.menuItemRepository.save(menuItem);
//		this.menuItemRepository.delete(menuItem);
	}

	@Override
	public List<MenuItem> getAllMenuItemByType(String menuType) {
		return this.menuItemRepository.findByMenuType(menuType);
	}

	@Override
	public MenuItem deleteMenuItem(MenuItem menuItem) {
		menuItem.setActive(false);
		return this.menuItemRepository.save(menuItem);
	}
}
