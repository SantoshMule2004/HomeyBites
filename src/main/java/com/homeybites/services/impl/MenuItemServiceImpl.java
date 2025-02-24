package com.homeybites.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.homeybites.entities.Address;
import com.homeybites.entities.Category;
import com.homeybites.entities.MenuItem;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.ImageInfo;
import com.homeybites.payloads.MenuItemDto;
import com.homeybites.payloads.UserRoles;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.repositories.CategoryRepository;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.ImageService;
import com.homeybites.services.MenuItemService;

@Service
public class MenuItemServiceImpl implements MenuItemService {

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ImageService imageService;

	@Override
	public MenuItemDto addMenuItem(MenuItemDto menuItemDto, Integer categoryId, Integer userId) {

		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

		MenuItem menuItem = this.modelMapper.map(menuItemDto, MenuItem.class);
		menuItem.setCategory(category);
		menuItem.setUser(user);
		MenuItem savedMenu = this.menuItemRepository.save(menuItem);

		return this.modelMapper.map(savedMenu, MenuItemDto.class);
	}

	@Override
	public MenuItemDto UploadMenuImage(MultipartFile file, Integer menuId) throws IOException {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		ImageInfo uploadImage = this.imageService.uploadImage(file);
		menuItem.setImagePublicId(uploadImage.getPublicId());
		menuItem.setImageUrl(uploadImage.getSecuredUrl());
		menuItem.setFormat(uploadImage.getFormat());

		MenuItem save = this.menuItemRepository.save(menuItem);

		return this.modelMapper.map(save, MenuItemDto.class);
	}

	@Override
	public MenuItemDto getMenuItem(Integer menuId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));
		return this.modelMapper.map(menuItem, MenuItemDto.class);
	}

	@Override
	public List<MenuItemDto> getMenuItemByCategory(Integer cId) {
		Category category = this.categoryRepository.findById(cId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", cId));

		List<MenuItem> findByCategory = this.menuItemRepository.findByCategory(category);
		List<MenuItemDto> allMenuItems = findByCategory.stream()
				.map(menuItem -> this.modelMapper.map(menuItem, MenuItemDto.class)).collect(Collectors.toList());

		return allMenuItems;
	}

	@Override
	public List<MenuItemDto> getAllMenuItem() {
		List<MenuItem> allMenus = this.menuItemRepository.findAll();

		List<MenuItemDto> allMenuItems = allMenus.stream()
				.map(menuItem -> this.modelMapper.map(menuItem, MenuItemDto.class)).collect(Collectors.toList());
		return allMenuItems;
	}

	@Override
	public List<MenuItemDto> getMenuItemByTiffinProvider(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

		List<MenuItem> byUser = this.menuItemRepository.findByUser(user);
		List<MenuItemDto> allMenuItems = byUser.stream()
				.map(menuItem -> this.modelMapper.map(menuItem, MenuItemDto.class)).collect(Collectors.toList());
		return allMenuItems;
	}

	@Override
	public List<MenuItemDto> getAllNearbyMenuItem(double latitude, double longitude) {
		// getting address of tiffin providers
		List<Address> all = this.addressRepository.findByUserRoles(UserRoles.TIFFIN_PROVIDER);

		// filtering out nearby address
		List<Address> nearbyProviders = all.stream().filter(address -> this.calculateDistance(latitude, longitude,
				address.getLatitude(), address.getLongitude()) <= 10).collect(Collectors.toList());

		// collecting nearby tiffin providers
		List<User> users = this.userRepository.findByAddressIn(nearbyProviders);

		// finding nearby menu item
		List<MenuItem> nearbyMenuItems = this.menuItemRepository.findByUserIn(users);

		List<MenuItemDto> collect = nearbyMenuItems.stream()
				.map(menuItem -> this.modelMapper.map(menuItem, MenuItemDto.class)).collect(Collectors.toList());

		return collect;
	}

	@Override
	public MenuItemDto updateMenuItem(MenuItemDto menuItemDto, Integer menuId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		menuItem.setMenuName(menuItemDto.getMenuName());
		menuItem.setPrice(menuItemDto.getPrice());
		menuItem.setDescription(menuItemDto.getDescription());
		menuItem.setActive(menuItemDto.isActive());
		menuItem.setMenuName(menuItemDto.getMenuName());

		MenuItem updatedMenu = this.menuItemRepository.save(menuItem);

		return this.modelMapper.map(updatedMenu, MenuItemDto.class);
	}

	@Override
	public void deleteMenuItem(Integer menuId) {
		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));

		this.menuItemRepository.delete(menuItem);

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
	public List<MenuItemDto> getAllMenuItemByType(String menuType) {
		List<MenuItem> byMenuType = this.menuItemRepository.findByMenuType(menuType);
		List<MenuItemDto> collect = byMenuType.stream()
				.map(menuItem -> this.modelMapper.map(menuItem, MenuItemDto.class)).collect(Collectors.toList());
		return collect;
	}
}
