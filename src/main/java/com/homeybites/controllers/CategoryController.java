package com.homeybites.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.Category;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody Category category) {
		Category savedategory = this.categoryService.addCategory(category);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category added successfully..!", true, savedategory),
				HttpStatus.CREATED);
	}

	@GetMapping("/{cId}")
	public ResponseEntity<Category> getCategory(@PathVariable Integer cId) {
		Category category = this.categoryService.getCategory(cId);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<Category>> getAllCategory() {
		List<Category> allCategory = this.categoryService.getAllCategory();
		return new ResponseEntity<List<Category>>(allCategory, HttpStatus.OK);
	}

	@PutMapping("/{cId}")
	public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody Category category, @PathVariable Integer cId) {
		Category updateCategory = this.categoryService.updateCategory(category, cId);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Category updated successfully..!", true, updateCategory), HttpStatus.OK);
	}

	@DeleteMapping("/{cId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer cId) {
		this.categoryService.deleteCategory(cId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully..!", true),
				HttpStatus.NO_CONTENT);
	}

}
