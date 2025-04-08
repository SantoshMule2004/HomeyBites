package com.homeybites.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tiffin_days_menuitem")
public class TiffinDaysMenuItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tiffinMenuId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tiffin_day_id", nullable = false)
	private TiffinDays tiffinDays;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id", nullable = false)
	private MenuItem menuItem;
	
	

	public Integer getTiffinMenuId() {
		return tiffinMenuId;
	}

	public void setTiffinMenuId(Integer tiffinMenuId) {
		this.tiffinMenuId = tiffinMenuId;
	}

	public TiffinDays getTiffinDays() {
		return tiffinDays;
	}

	public void setTiffinDays(TiffinDays tiffinDays) {
		this.tiffinDays = tiffinDays;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
}
