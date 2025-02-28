package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.MenuItem;
import com.homeybites.entities.User;
import com.homeybites.entities.UserCart;

public interface CartRepository extends JpaRepository<UserCart, Integer> {

	List<UserCart> findByUser(User user);
	
	UserCart findByUserAndMenuItem(User user, MenuItem menuItem);
	
	UserCart findByMenuItem(MenuItem menuItem);
}
