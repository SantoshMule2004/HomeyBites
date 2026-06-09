package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.CartItem;
import com.homeybites.payloads.CartItemDto;

public interface CartService {

	// add item to cart
	void addItemsToCart(Long cartId, Long itemId);

	// update cart item
	void updateCartItem(Long cartItemId, Integer quantity);

	// get cart items
	List<CartItem> getCart(Long userId);

	// get cart items with menu details
	List<CartItemDto> getCartItemWithMenuItems(Long userId);

	// delete cart item
	void deleteItemFromCart(Long cartItemId);

	// delete cart
	void deleteCart(Long userId);

	long countItemsInCart(Long userId);

	Double getGrandTotal(Long userId);
}
