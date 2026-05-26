package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByCartId(Long cartId);
	
	boolean findByMenuItemId(Long menuId);
	
    Optional<CartItem> findByCartIdAndMenuItemId(Long cartId, Long menuItemId);
}