package com.homeybites.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
