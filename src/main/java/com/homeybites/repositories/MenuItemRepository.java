package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

	// get all menu items by category
	List<MenuItem> findByCategoryId(Long categoryId);

	// get all menu items of tiffin provider
	List<MenuItem> findByProviderId(Long providerId);
	
	@Query("SELECT m FROM MenuItem m WHERE m.providerId=:providerId AND m.isActive = true")
	List<MenuItem> getMenuItemByuserAndActive(@Param("providerId") Long providerId);
	
	// Optional: Only get active items from those providers
    List<MenuItem> findByProviderIdInAndIsActiveTrue(List<Integer> providerIds);

	List<MenuItem> findByMenuType(String menuType);
}
