package com.homeybites.services;

import java.util.List;
import com.homeybites.payloads.ProviderMenuResponse;
import com.homeybites.payloads.UpdateProviderMenuRequest;

public interface ProviderMenuService {
//	public Map<String, Map<MealType, String>> getWeeklyMenu(Long providerId);
//
//	ProviderMenu addSingleMenu(Long providerId, ProviderMenuRequestDTO req);
//
//	void updateWeeklyMenu(Long providerId, List<ProviderMenuRequestDTO> weeklyMenuRequests);
//
//	List<ProviderMenu> getMenuForEditing(Long providerId);

	void createDefaultMenus(Long providerId);

	void updateProviderMenu(Long providerId, UpdateProviderMenuRequest request);

	List<ProviderMenuResponse> getProviderMenus(Long providerId);

	void removeMenu(Long providerId, Long providerMenuId);

	void toggleMenuStatus(Long providerId, Long providerMenuId, boolean isActive);
}
