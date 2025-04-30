package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.UserCart;

public interface CartService {

	// add item to cart
	UserCart addItemsToCart(Integer userId, Integer itemId);

	// update cart item
	void updateItemInfo(Integer cartId, Integer quantity);

	// get cart items
	List<UserCart> getCart(Integer userId);

	// delete cart item
	void deleteItemFromCart(Integer cartId);

	// delete all cart items
	void deleteAllItems(Integer userId);
}
