package com.homeybites.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.homeybites.entities.MenuItem;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.ImageInfo;
import com.homeybites.payloads.MenuFilterRequest;
import com.homeybites.payloads.NearbyMenuProjection;
import com.homeybites.payloads.PageResponse;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.services.ImageService;
import com.homeybites.services.MenuItemService;
import com.homeybites.payloads.MenuProjection;

@Service
public class MenuItemServiceImpl implements MenuItemService {

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Autowired
	private ImageService imageService;

	@Override
	public MenuItem addMenuItemWithImage(MenuItem menuItemData, MultipartFile file, Long categoryId, Long providerId)
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
	public MenuItem addMenuItem(MenuItem menuItemData, Long categoryId, Long providerId) {
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
	public NearbyMenuProjection getMenuItem(Long menuId, double userLat, double userLng) {
		return this.menuItemRepository.getMenuItemById(menuId, userLat, userLng);
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
	public PageResponse<MenuProjection> getMenuItemByTiffinProvider(Long providerId, MenuFilterRequest menuFilters,
			Pageable pageable) {
		Page<MenuProjection> page = this.menuItemRepository.getMenusByProvider(providerId, menuFilters.getMenuType(),
				menuFilters.getIsActive(), menuFilters.getCategoryId(), menuFilters.getSearch(), pageable);

		return new PageResponse<>(page);
	}

	@Override
	public MenuItem updateMenuItem(MenuItem menuItemDto, Long menuId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		menuItem.setMenuName(menuItemDto.getMenuName());
		menuItem.setDescription(menuItemDto.getDescription());
		menuItem.setPrice(menuItemDto.getPrice());
		menuItem.setMenuType(menuItemDto.getMenuType());

		menuItem.setActive(menuItemDto.isActive());

		menuItem.setCategoryId(
				menuItemDto.getCategoryId() != null ? menuItemDto.getCategoryId() : menuItem.getCategoryId());

		menuItem.setImageUrl(menuItem.getImageUrl());
		menuItem.setImagePublicId(menuItem.getImagePublicId());
		menuItem.setFormat(menuItem.getFormat());
		menuItem.setProviderId(menuItem.getProviderId());
		menuItem.setCreatedAt(menuItem.getCreatedAt());

		// saving menu item
		MenuItem savedItem = this.menuItemRepository.save(menuItem);

		return savedItem;
	}

	@Override
	public void toggleActivateMenu(Long menuId, boolean isActive) {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		menuItem.setActive(isActive);
		this.menuItemRepository.save(menuItem);
	}

	@Override
	public List<MenuItem> getAllMenuItemByType(String menuType) {
		return this.menuItemRepository.findByMenuType(menuType);
	}

	@Override
	public void deleteMenuItem(Long menuId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		this.menuItemRepository.delete(menuItem);
	}

	@Override
	public PageResponse<NearbyMenuProjection> findProviderNearbyUsers(double userLat, double userLng,
			double maxAbsolutePlatformRadiusInMeters, Long categoryId, String menuType, Double maxPrice,
			Pageable pageable) {
		Page<NearbyMenuProjection> page = this.menuItemRepository.findProviderNearbyUsers(userLat, userLng,
				maxAbsolutePlatformRadiusInMeters, categoryId, menuType, maxPrice, pageable);

		return new PageResponse<>(page);
	}
}
