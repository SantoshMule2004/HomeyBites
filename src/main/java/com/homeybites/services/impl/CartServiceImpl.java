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

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Override
	@Transactional
	public void addItemsToCart(Long userId, Long itemId) {
		UserCart usercart = this.cartRepository.findByUserIdAndIsActive(userId, true)
				.orElseGet(() -> createNewCart(userId));

		Optional<CartItem> existingCartItem = this.cartItemRepository.findByCartIdAndMenuItemId(usercart.getCartId(),
				itemId);

		System.out.println("Existing total: " + usercart.getGrandTotal());

		CartItem cartItem = new CartItem();

		if (existingCartItem.isPresent()) {
			cartItem = existingCartItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + 1);
			cartItem = this.cartItemRepository.save(cartItem);
		} else {
			MenuItem menuItem = this.menuItemRepository.findById(itemId)
					.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", itemId));

			cartItem.setMenuItemId(itemId);
			cartItem.setPriceWhenAdded(menuItem.getPrice());
			cartItem.setCartId(usercart.getCartId());
			cartItem.setCurrentPrice(menuItem.getPrice());
			cartItem.setPriceChanged(false);
			cartItem.setQuantity(1);

			cartItem = this.cartItemRepository.save(cartItem);
		}

		System.out.println("CartItem: " + cartItem);

		usercart.setGrandTotal(
				(usercart.getGrandTotal() != null ? usercart.getGrandTotal() : 0.0) + cartItem.getCurrentPrice());
		usercart = this.cartRepository.save(usercart);

		System.out.println(usercart);
	}

	@Override
	@Transactional
	public void updateCartItem(Long cartItemId, Integer quantity) {
		CartItem cartItem = this.cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new ResourceNotFoundException("CartItem", "Id", cartItemId));

		UserCart usercart = this.cartRepository.findById(cartItem.getCartId())
				.orElseGet(() -> createNewCart(cartItem.getCartId()));

//		Double grandTotal = (usercart.getGrandTotal() != null ? usercart.getGrandTotal() : 0.0)
//				- (cartItem.getCurrentPrice() * cartItem.getQuantity()) + (cartItem.getCurrentPrice() * quantity);
//		usercart.setGrandTotal(grandTotal);

		Double grandTotal = (usercart.getGrandTotal() != null ? usercart.getGrandTotal() : 0.0)
				+ (cartItem.getCurrentPrice() * (quantity - cartItem.getQuantity()));
		usercart.setGrandTotal(grandTotal);

		System.out.println("Usercart" + usercart);

		cartItem.setQuantity(quantity);

		this.cartItemRepository.save(cartItem);
		this.cartRepository.save(usercart);
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
	@Transactional
	public void deleteCart(Long userId) {
		UserCart usercart = this.cartRepository.findByUserId(userId).orElseGet(() -> createNewCart(userId));

		usercart.setGrandTotal(0.0);
		List<CartItem> cartItems = this.cartItemRepository.findByCartId(usercart.getCartId());
		System.out.println("cartitems- " + cartItems.size());
		this.cartItemRepository.deleteAll(cartItems);
		this.cartRepository.save(usercart);
	}

	public UserCart createNewCart(Long userId) {
		return this.cartRepository.save(new UserCart(userId, true, 0.0));
	}

	@Override
	public List<CartItemDto> getCartItemWithMenuItems(Long userId) {
		UserCart usercart = this.cartRepository.findByUserIdAndIsActive(userId, true)
				.orElseGet(() -> createNewCart(userId));

		return this.cartItemRepository.findCartItemsWithMenuDetails(usercart.getCartId());
	}
}
