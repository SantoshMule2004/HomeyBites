package com.homeybites.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.CartItem;
import com.homeybites.entities.MenuItem;
import com.homeybites.entities.UserCart;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.CartItemDto;
import com.homeybites.repositories.CartItemRepository;
import com.homeybites.repositories.CartRepository;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.services.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Override
	public void addItemsToCart(Long userId, Long itemId) {
		UserCart usercart = this.cartRepository.findByUserIdAndIsActive(userId, true)
				.orElseGet(() -> createNewCart(userId));

		Optional<CartItem> existingCartItem = this.cartItemRepository.findByCartIdAndMenuItemId(usercart.getCartId(),
				itemId);

		if (existingCartItem.isPresent()) {
			CartItem cartItem = existingCartItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + 1);
			this.cartItemRepository.save(cartItem);
		} else {
			MenuItem menuItem = this.menuItemRepository.findById(itemId)
					.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", itemId));

			CartItem cartItem = new CartItem();
			cartItem.setMenuItemId(itemId);
			cartItem.setPriceWhenAdded(menuItem.getPrice());
			cartItem.setCartId(usercart.getCartId());
			cartItem.setCurrentPrice(menuItem.getPrice());
			cartItem.setPriceChanged(false);
			cartItem.setQuantity(1);

			this.cartItemRepository.save(cartItem);
		}
	}

	@Override
	public void updateCartItem(Long cartItemId, Integer quantity) {
		CartItem cartItem = this.cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItem", "Id", cartItemId));

		cartItem.setQuantity(quantity);
		this.cartItemRepository.save(cartItem);
	}

	@Override
	public List<CartItem> getCart(Long userId) {
		UserCart usercart = this.cartRepository.findByUserIdAndIsActive(userId, true)
				.orElseGet(() -> createNewCart(userId));

		return this.cartItemRepository.findByCartId(usercart.getCartId());
	}

	@Override
	public void deleteItemFromCart(Long cartItemId) {
		CartItem cartItem = this.cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItem", "Id", cartItemId));
		this.cartItemRepository.delete(cartItem);
	}

	@Override
	public void deleteCart(Long cartId) {
		List<CartItem> cartItems = this.cartItemRepository.findByCartId(cartId);
		this.cartItemRepository.deleteAll(cartItems);

	}

	public UserCart createNewCart(Long userId) {
		return this.cartRepository.save(new UserCart(userId, true));
	}

	@Override
	public List<CartItemDto> getCartItemWithMenuItems(Long userId) {
		UserCart usercart = this.cartRepository.findByUserIdAndIsActive(userId, true)
				.orElseGet(() -> createNewCart(userId));

		return this.cartItemRepository.findCartItemsWithMenuDetails(usercart.getCartId());
	}
}
