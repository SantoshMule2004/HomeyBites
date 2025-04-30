package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.Category;
import com.homeybites.entities.MenuItem;
import com.homeybites.entities.User;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

	// get all menu items by category
	List<MenuItem> findByCategory(Category category);

	// get all menu items of tiffin provider
	List<MenuItem> findByUser(User user);
	
	@Query("SELECT m FROM MenuItem m WHERE m.user.userId=:userId AND m.isActive = true")
	List<MenuItem> getMenuItemByuserAndActive(@Param("userId") Integer userId);

	List<MenuItem> findByUserIn(List<User> users);

	List<MenuItem> findByMenuType(String menuType);
}
