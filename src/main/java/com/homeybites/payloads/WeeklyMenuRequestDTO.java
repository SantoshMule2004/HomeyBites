package com.homeybites.payloads;

import java.util.List;

public class WeeklyMenuRequestDTO {
	private List<ProviderMenuRequestDTO> menus;

	public List<ProviderMenuRequestDTO> getMenus() {
		return menus;
	}

	public void setMenus(List<ProviderMenuRequestDTO> menus) {
		this.menus = menus;
	}
}
