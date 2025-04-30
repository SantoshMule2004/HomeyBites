package com.homeybites.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.homeybites.entities.Address;
import com.homeybites.entities.Category;
import com.homeybites.entities.MenuItem;
import com.homeybites.entities.TiffinDays;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.ImageInfo;
import com.homeybites.payloads.UserRoles;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.repositories.CategoryRepository;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.repositories.TiffindaysRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.ImageService;
import com.homeybites.services.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Autowired
	private TiffindaysRepository tiffindaysRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageService imageService;

	@Override
	public MenuItem addMenuItem(MenuItem menuItemData, MultipartFile file, Integer categoryId, Integer userId)
			throws IOException {

		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

		ImageInfo uploadImage = this.imageService.uploadImage(file);
		menuItemData.setImagePublicId(uploadImage.getPublicId());
		menuItemData.setImageUrl(uploadImage.getSecuredUrl());
		menuItemData.setFormat(uploadImage.getFormat());

		menuItemData.setCategory(category);
		menuItemData.setUser(user);
		MenuItem savedMenu = this.menuItemRepository.save(menuItemData);

		return savedMenu;
	}

	@Override
	public MenuItem UploadMenuImage(MultipartFile file, Integer menuId) throws IOException {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		ImageInfo uploadImage = this.imageService.uploadImage(file);
		menuItem.setImagePublicId(uploadImage.getPublicId());
		menuItem.setImageUrl(uploadImage.getSecuredUrl());
		menuItem.setFormat(uploadImage.getFormat());

		return this.menuItemRepository.save(menuItem);
	}

	@Override
	public MenuItem getMenuItem(Integer menuId) {
		return this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));
	}

	@Override
	public List<MenuItem> getMenuItemByCategory(Integer cId) {
		Category category = this.categoryRepository.findById(cId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", cId));

		return this.menuItemRepository.findByCategory(category);
	}

	@Override
	public List<MenuItem> getAllMenuItem() {
		return this.menuItemRepository.findAll();
	}

	@Override
	public List<MenuItem> getMenuItemByTiffinProvider(Integer userId) {
		this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		return this.menuItemRepository.getMenuItemByuserAndActive(userId);
	}

	@Override
	public List<MenuItem> getAllNearbyMenuItem(double latitude, double longitude) {
		// getting address of tiffin providers
		List<Address> all = this.addressRepository.findByUserRoles(UserRoles.TIFFIN_PROVIDER);

		// filtering out nearby address
		List<Address> nearbyProviders = all.stream().filter(address -> this.calculateDistance(latitude, longitude,
				address.getLatitude(), address.getLongitude()) <= 10).collect(Collectors.toList());

		// collecting nearby tiffin providers
		List<User> users = this.userRepository.findByAddressIn(nearbyProviders);

		// finding nearby menu item
		return this.menuItemRepository.findByUserIn(users);
	}

	@Override
	public MenuItem updateMenuItem(MenuItem menuItemDto, Integer menuId) {
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
		menuItemLog.setUser(menuItem.getUser());
		menuItemLog.setCategory(menuItem.getCategory());

		// saving new menu item
		MenuItem savedItem = this.menuItemRepository.save(menuItemLog);

		// updating tiffin days with new menu item
		List<TiffinDays> tiffinDays = menuItem.getTiffinDays();

		for (TiffinDays day : tiffinDays) {
			day.getMenuItem().remove(menuItem);
			day.getMenuItem().add(menuItemLog);
		}

		// saving tiffin days
		this.tiffindaysRepository.saveAll(tiffinDays);

		// deleting menu item
		deleteMenuItem(menuItem);

		return savedItem;
	}

	@Override
	public void deleteMenuItem(Integer menuId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		menuItem.setActive(false);
		this.menuItemRepository.save(menuItem);
//		this.menuItemRepository.delete(menuItem);
	}

	@Override
	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double radius = 6371;
		double c = 2 * Math.asin(Math.sqrt(a));

		return radius * c;
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
