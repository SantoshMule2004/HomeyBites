package com.homeybites.entities;

import java.util.List;

import com.homeybites.entities.Log.TiffinPlanLog;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class TiffinDays {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tiffinDayId;

	private String weekDay;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tiffin_plan_id", nullable = true)
	private TiffinPlan tiffinPlan;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tiffin_log_id", nullable = true)
	private TiffinPlanLog tiffinPlanLog;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tiffin_days_menuitem", joinColumns = @JoinColumn(name = "tiffin_day_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	private List<MenuItem> menuItem;

	@Transient
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

	public TiffinPlan getTiffinPlan() {
		return tiffinPlan;
	}

	public void setTiffinPlan(TiffinPlan tiffinPlan) {
		this.tiffinPlan = tiffinPlan;
	}

	public List<MenuItem> getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(List<MenuItem> menuItem) {
		this.menuItem = menuItem;
	}

	public List<Integer> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}

	public TiffinPlanLog getTiffinPlanLog() {
		return tiffinPlanLog;
	}

	public void setTiffinPlanLog(TiffinPlanLog tiffinPlanLog) {
		this.tiffinPlanLog = tiffinPlanLog;
	}
}
