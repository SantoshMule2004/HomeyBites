package com.homeybites.payloads;

public class ProviderMenuRequestDTO {
	private String dayOfWeek; // MONDAY, TUESDAY...
	private MealType mealType; // BREAKFAST, LUNCH, DINNER
	private String foodItems; // e.g., "4 Roti, Dal, Rice"
	
	public ProviderMenuRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProviderMenuRequestDTO(String dayOfWeek, MealType mealType, String foodItems) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.mealType = mealType;
		this.foodItems = foodItems;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
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
}
