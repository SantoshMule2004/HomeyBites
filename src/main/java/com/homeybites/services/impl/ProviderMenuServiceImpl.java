package com.homeybites.services.impl;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeybites.entities.ProviderMenu;
import com.homeybites.entities.ProviderMenuItem;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.MealType;
import com.homeybites.payloads.ProviderMenuItemProjection;
import com.homeybites.payloads.ProviderMenuProjection;
import com.homeybites.payloads.ProviderMenuResponse;
import com.homeybites.payloads.UpdateProviderMenuRequest;
import com.homeybites.payloads.UpdateProviderMenuRequest.MealRequest;
import com.homeybites.repositories.ProviderMenuItemRepository;
import com.homeybites.repositories.ProviderMenuRepository;
import com.homeybites.services.ProviderMenuService;

@Service
public class ProviderMenuServiceImpl implements ProviderMenuService {

	private final ProviderMenuRepository menuRepository;
	private final ProviderMenuItemRepository providerMenuItemRepository;

	public ProviderMenuServiceImpl(ProviderMenuRepository menuRepo, ProviderMenuItemRepository providerMenuItemRepo) {
		this.menuRepository = menuRepo;
		this.providerMenuItemRepository = providerMenuItemRepo;
	}

	@Override
	public void createDefaultMenus(Long providerId) {
		for (DayOfWeek day : DayOfWeek.values()) {

			ProviderMenu menu = new ProviderMenu();
			menu.setProviderId(providerId);
			menu.setDayOfWeek(day.name());
			menu.setIsActive(true);

			menuRepository.save(menu);
		}
	}

	@Override
	public List<ProviderMenuResponse> getProviderMenus(Long providerId) {
		List<ProviderMenuProjection> menus = menuRepository.findMenusByProviderId(providerId);

		if (menus.isEmpty()) {
			return Collections.emptyList();
		}

		List<Long> menuIds = menus.stream().map(ProviderMenuProjection::getId).toList();

		List<ProviderMenuItemProjection> mealItems = providerMenuItemRepository.findByProviderMenuIds(menuIds);

		Map<Long, List<ProviderMenuItemProjection>> mealsMap = mealItems.stream()
				.collect(Collectors.groupingBy(ProviderMenuItemProjection::getProviderMenuId));

		List<ProviderMenuResponse> response = new ArrayList<>();

		for (ProviderMenuProjection menu : menus) {

			ProviderMenuResponse dto = new ProviderMenuResponse();

			dto.setId(menu.getId());
			dto.setProviderId(menu.getProviderId());
			dto.setDayOfWeek(menu.getDayOfWeek());
			dto.setIsActive(menu.getIsActive());

			List<ProviderMenuItemProjection> mealDtos = mealsMap.getOrDefault(menu.getId(), Collections.emptyList());

			dto.setMeals(mealDtos);

			response.add(dto);
		}

		return response;
	}

	@Override
	@Transactional
	public void updateProviderMenu(Long providerId, UpdateProviderMenuRequest request) {
		ProviderMenu menu = menuRepository.findByIdAndProviderId(request.getProviderMenuId(), providerId)
				.orElseThrow(() -> new ResourceNotFoundException("ProviderMenu", "Id", request.getProviderMenuId()));

		// Fetch all existing meal items in one query
		List<ProviderMenuItem> existingItems = providerMenuItemRepository.findByProviderMenuId(menu.getId());

		// Convert to Map<MealType, ProviderMenuItem>
		Map<MealType, ProviderMenuItem> existingMap = existingItems.stream()
				.collect(Collectors.toMap(ProviderMenuItem::getMealType, Function.identity()));

		List<ProviderMenuItem> itemsToSave = new ArrayList<>();

		for (MealRequest meal : request.getMeals()) {

			ProviderMenuItem item = existingMap.get(meal.getMealType());

			if (item == null) {
				item = new ProviderMenuItem();
				item.setProviderMenId(menu.getId());
				item.setMealType(meal.getMealType());
			}

			item.setFoodItems(meal.getFoodItems());

			itemsToSave.add(item);
		}

		providerMenuItemRepository.saveAll(itemsToSave);
	}

	@Override
	@Transactional
	public void removeMenu(Long providerId, Long providerMenuId) {
		ProviderMenu menu = this.menuRepository.findById(providerMenuId)
				.orElseThrow(() -> new ResourceNotFoundException("ProviderMenu", "id", providerMenuId));

		// Security Check: Only the owner can delete their holiday
		if (!menu.getProviderId().equals(providerId)) {
			throw new SecurityException("Unauthorized to remove this menu.");
		}

		// Delete child records first
		providerMenuItemRepository.deleteByProviderMenuId(providerMenuId);

		this.menuRepository.delete(menu);
	}

	@Override
	public void toggleMenuStatus(Long providerId, Long providerMenuId, boolean isActive) {
		ProviderMenu menu = this.menuRepository.findById(providerMenuId)
				.orElseThrow(() -> new ResourceNotFoundException("ProviderMenu", "id", providerMenuId));

		// Security Check: Only the owner can delete their holiday
		if (!menu.getProviderId().equals(providerId)) {
			throw new SecurityException("Unauthorized to remove this menu.");
		}

		menu.setIsActive(isActive);

		this.menuRepository.save(menu);
	}
}
