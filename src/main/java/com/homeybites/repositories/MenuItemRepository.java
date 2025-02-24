package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.Category;
import com.homeybites.entities.MenuItem;
import com.homeybites.entities.User;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
	
	// get all menu items by category
	List<MenuItem> findByCategory(Category category);
	
	//get all menu items of tiffin provider
	List<MenuItem> findByUser(User user);
	
	List<MenuItem> findByUserIn(List<User> users);
	
	List<MenuItem> findByMenuType(String menuType);
}
