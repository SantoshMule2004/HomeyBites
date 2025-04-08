package com.homeybites.payloads;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TiffinDaysDto {

	private Integer tiffinDayId;

	private String weekDay;

	@JsonIgnore
	private TiffinPlanDto tiffinPlan;
	
	@JsonIgnore
	private TiffinPlanLogDto tiffinPlanLog;

	private List<MenuItemDto> menuItem;
	
	private List<Integer> menuIds;

	public Integer getTiffinDayId() {
		return tiffinDayId;
	}

	public void setTiffinDayId(Integer tiffinDayId) {
		this.tiffinDayId = tiffinDayId;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public TiffinPlanDto getTiffinPlan() {
		return tiffinPlan;
	}

	public void setTiffinPlan(TiffinPlanDto tiffinPlan) {
		this.tiffinPlan = tiffinPlan;
	}

	public TiffinPlanLogDto getTiffinPlanLog() {
		return tiffinPlanLog;
	}

	public void setTiffinPlanLog(TiffinPlanLogDto tiffinPlanLog) {
		this.tiffinPlanLog = tiffinPlanLog;
	}

	public List<MenuItemDto> getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(List<MenuItemDto> menuItem) {
		this.menuItem = menuItem;
	}

	public List<Integer> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}
}
