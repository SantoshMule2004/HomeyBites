package com.homeybites.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.CartMenuItem;
import com.homeybites.entities.MenuItem;
import com.homeybites.entities.User;
import com.homeybites.entities.UserCart;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.UserCartDto;
import com.homeybites.repositories.CartMenuItemRepository;
import com.homeybites.repositories.CartRepository;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Autowired
	private CartMenuItemRepository cartMenuItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserCartDto addItemsToCart(Integer cartId, Integer itemId) {
		UserCart cart = this.cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "Id", cartId));

		MenuItem menuItem = this.menuItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", itemId));
		cart.getMenuItems().add(menuItem);
		UserCart userCart = this.cartRepository.save(cart);
		return this.modelMapper.map(userCart, UserCartDto.class);
	}

	@Override
	public void updateItemInfo(Integer cartId, Integer itemId, Integer quantity) {
		UserCart cart = this.cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "Id", cartId));

		MenuItem menuItem = this.menuItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", itemId));

		CartMenuItem cartMenuItem = this.cartMenuItemRepository.findByCartAndMenuItem(cart, menuItem).get();
		
		cartMenuItem.setQuantity(quantity);
		this.cartMenuItemRepository.save(cartMenuItem);
	}

	@Override
	public UserCartDto getCart(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		UserCart userCart = this.cartRepository.findByUser(user);
		return this.modelMapper.map(userCart, UserCartDto.class);
	}

	@Override
	public UserCartDto deleteItemFromCart(Integer cartId, Integer itemId) {
		UserCart cart = this.cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "Id", cartId));

		MenuItem menuItem = this.menuItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", itemId));

		cart.getMenuItems().remove(menuItem);
		UserCart userCart = this.cartRepository.save(cart);

		return this.modelMapper.map(userCart, UserCartDto.class);
	}

	@Override
	public UserCartDto deleteAllItems(Integer cartId) {
		UserCart cart = this.cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "Id", cartId));

		cart.getMenuItems().clear();
		UserCart userCart = this.cartRepository.save(cart);

		return this.modelMapper.map(userCart, UserCartDto.class);
	}
}
