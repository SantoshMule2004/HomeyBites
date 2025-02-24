package com.homeybites.services;

import java.util.List;

import com.homeybites.payloads.CategoryDto;

public interface CategoryService {
	
	//add new category
	CategoryDto addCategory(CategoryDto categoryDto);
	
	//get category
	CategoryDto getCategory(Integer categoryId);
	
	//get all category
	List<CategoryDto> getAllCategory();
	
	//update category
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete category
	void deleteCategory(Integer categoryId);
}
