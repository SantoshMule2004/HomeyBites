package com.homeybites.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.CartMenuItem;
import com.homeybites.entities.MenuItem;
import com.homeybites.entities.UserCart;

public interface CartMenuItemRepository extends JpaRepository<CartMenuItem, Integer>{
	
	Optional<CartMenuItem> findByCartAndMenuItem(UserCart cart, MenuItem menuItem);
}
