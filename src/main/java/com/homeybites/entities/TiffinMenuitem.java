package com.homeybites.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tiffin_menuitem")
public class TiffinMenuitem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "tiffin_id", nullable = false)
	private TiffinPlan tiffinPlan;
	
	@ManyToOne
	@JoinColumn(name = "menu_id", nullable = false)
	private MenuItem menuItem;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TiffinPlan getTiffinPlan() {
		return tiffinPlan;
	}

	public void setTiffinPlan(TiffinPlan tiffinPlan) {
		this.tiffinPlan = tiffinPlan;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
}
