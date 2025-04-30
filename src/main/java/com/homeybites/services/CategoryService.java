package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.Category;

public interface CategoryService {
	
	//add new category
	Category addCategory(Category category);
	
	//get category
	Category getCategory(Integer categoryId);
	
	//get all category
	List<Category> getAllCategory();
	
	//update category
	Category updateCategory(Category category, Integer categoryId);
	
	//delete category
	void deleteCategory(Integer categoryId);
}
