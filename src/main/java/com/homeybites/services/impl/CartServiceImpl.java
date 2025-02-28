package com.homeybites.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.MenuItem;
import com.homeybites.entities.User;
import com.homeybites.entities.UserCart;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.UserCartDto;
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
	private ModelMapper modelMapper;

	@Override
	public UserCartDto addItemsToCart(Integer userId, Integer itemId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		MenuItem menuItem = this.menuItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", itemId));

		UserCart byMenuItem = this.cartRepository.findByMenuItem(menuItem);

		if (byMenuItem != null) {
			byMenuItem.setQuantity(byMenuItem.getQuantity() + 1);
			byMenuItem.setTotalPrice(byMenuItem.getQuantity() * menuItem.getPrice());
			this.cartRepository.save(byMenuItem);
			return this.modelMapper.map(byMenuItem, UserCartDto.class);
		} else {
			UserCart cart = new UserCart();
			cart.setMenuItem(menuItem);
			cart.setUser(user);
			cart.setQuantity(1);
			cart.setTotalPrice(menuItem.getPrice());
			UserCart userCart = this.cartRepository.save(cart);
			return this.modelMapper.map(userCart, UserCartDto.class);
		}
	}

	@Override
	public void updateItemInfo(Integer cartId, Integer quantity) {
		UserCart cart = this.cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "Id", cartId));

		cart.setQuantity(quantity);
		cart.setTotalPrice(quantity * (cart.getMenuItem().getPrice()));
		this.cartRepository.save(cart);
	}

	@Override
	public List<UserCartDto> getCart(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		List<UserCart> list = this.cartRepository.findByUser(user);
		List<UserCartDto> collect = list.stream().map(cart -> this.modelMapper.map(cart, UserCartDto.class))
				.collect(Collectors.toList());

		return collect;
	}

	@Override
	public void deleteItemFromCart(Integer cartId) {
		UserCart cart = this.cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "Id", cartId));

		this.cartRepository.delete(cart);
	}

	@Override
	public void deleteAllItems(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		List<UserCart> list = this.cartRepository.findByUser(user);
		this.cartRepository.deleteAll(list);
	}
}
