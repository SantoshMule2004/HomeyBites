package com.homeybites.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Category;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.repositories.CategoryRepository;
import com.homeybites.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category addCategory(Category category) {
		return this.categoryRepository.save(category);
	}

	@Override
	public Category getCategory(Integer categoryId) {
		return this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
	}

	@Override
	public List<Category> getAllCategory() {
		return this.categoryRepository.findAll();
	}

	@Override
	public Category updateCategory(Category category, Integer categoryId) {
		Category existingCategory = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		existingCategory.setCategoryName(category.getCategoryName());
		existingCategory.setActive(category.isActive());

		return this.categoryRepository.save(existingCategory);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		this.categoryRepository.delete(category);
	}

}
