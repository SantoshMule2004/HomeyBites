package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.CartItem;

public interface CartService {

	// add item to cart
	void addItemsToCart(Long cartId, Long itemId);

	// update cart item
	void updateCartItem(Long cartItemId, Integer quantity);

	// get cart items
	List<CartItem> getCart(Long userId);

	// delete cart item
	void deleteItemFromCart(Long cartItemId);

	// delete cart
	void deleteCart(Long cartId);
}
