package com.homeybites.entities;

import com.homeybites.payloads.MealType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "provider_menu_item", uniqueConstraints = @UniqueConstraint(columnNames = { "provider_menu_id",
		"meal_type" }))
public class ProviderMenuItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private MealType mealType; // BREAKFAST, LUNCH, DINNER
	
	private String foodItems;
	
	private long providerMenuId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public String getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(String foodItems) {
		this.foodItems = foodItems;
	}

	public long getProviderMenId() {
		return providerMenuId;
	}

	public void setProviderMenId(long providerMenuId) {
		this.providerMenuId = providerMenuId;
	}
}
