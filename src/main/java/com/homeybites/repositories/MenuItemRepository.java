package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.MenuItem;
import com.homeybites.payloads.MenuItemDto;

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

	// getting menuitem with provider details
	@Query("""
        SELECT new com.homeybites.payloads.MenuItemDto(
            m.menuId, m.menuName, m.price, m.description, m.count, 
            m.isActive, m.menuType, m.imagePublicId, m.imageUrl, 
            m.format, m.categoryId, 
            p.userId, p.businessName, p.latitude, p.longitude, p.serviceRadius
        )
        FROM MenuItem m
        LEFT JOIN User p ON m.providerId = p.userId
    """)
    List<MenuItemDto> getAllMenusWithProviders();
}
