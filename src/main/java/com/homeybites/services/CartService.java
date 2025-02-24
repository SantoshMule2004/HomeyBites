package com.homeybites.services;

import com.homeybites.payloads.UserCartDto;

public interface CartService {

	// add item to cart
	UserCartDto addItemsToCart(Integer cartId, Integer itemId);

	// update cart item
	void updateItemInfo(Integer cartId, Integer itemId, Integer quantity);

	// get cart items
	UserCartDto getCart(Integer userId);

	// delete cart item
	UserCartDto deleteItemFromCart(Integer cartId, Integer itemId);

	// delete all cart items
	UserCartDto deleteAllItems(Integer cartId);
}
