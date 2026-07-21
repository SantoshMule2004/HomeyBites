package com.homeybites.payloads;

import java.util.List;

public class UpdateProviderMenuRequest {
    private Long providerMenuId;
    private List<MealRequest> meals;

    public Long getProviderMenuId() {
		return providerMenuId;
	}
	public void setProviderMenuId(Long providerMenuId) {
		this.providerMenuId = providerMenuId;
	}
	public List<MealRequest> getMeals() {
		return meals;
	}
	public void setMeals(List<MealRequest> meals) {
		this.meals = meals;
	}

	public static class MealRequest {
        private MealType mealType;
        private String foodItems;
        
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
}
