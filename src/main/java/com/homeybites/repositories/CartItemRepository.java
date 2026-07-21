package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.CartItem;
import com.homeybites.payloads.CartItemDto;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);

    boolean findByMenuItemId(Long menuId);
    
    Optional<CartItem> findFirstByCartId(Long cartId);

    Optional<CartItem> findByCartIdAndMenuItemId(Long cartId, Long menuItemId);

    @Query("""
                SELECT new com.homeybites.payloads.CartItemDto(
                    c.cartItemId,
                    c.cartId,
                    c.quantity,
                    c.priceWhenAdded,
                    m.price,
                    CASE WHEN c.priceWhenAdded = m.price THEN false ELSE true END,
                    m.menuId,
                    m.menuName,
                    m.price,
                    m.description,
                    m.count,
                    m.isActive,
                    m.menuType,
                    m.imagePublicId,
                    m.imageUrl,
                    m.format,
                    m.categoryId,
                    m.providerId,
                    u.businessName
                )
                FROM CartItem c
                JOIN MenuItem m ON c.menuItemId = m.menuId
                LEFT JOIN User u ON m.providerId = u.userId
                WHERE c.cartId = :cartId
            """)
    List<CartItemDto> findCartItemsWithMenuDetails(@Param("cartId") Long cartId);

    @Query("SELECT COUNT(c) FROM CartItem c WHERE c.cartId = :cartId")
    long countItemsInCart(@Param("cartId") Long cartId);
}