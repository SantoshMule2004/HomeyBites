package com.homeybites.services;

import java.util.List;

import com.homeybites.payloads.UserCartDto;

public interface CartService {

	// add item to cart
	UserCartDto addItemsToCart(Integer userId, Integer itemId);

	// update cart item
	void updateItemInfo(Integer cartId, Integer quantity);

	// get cart items
	List<UserCartDto> getCart(Integer userId);

	// delete cart item
	void deleteItemFromCart(Integer cartId);

	// delete all cart items
	void deleteAllItems(Integer userId);
}
