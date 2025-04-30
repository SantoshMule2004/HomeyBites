package com.homeybites.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.MenuItem;
import com.homeybites.entities.User;
import com.homeybites.entities.UserCart;
import com.homeybites.exceptions.ResourceNotFoundException;
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

	@Override
	public UserCart addItemsToCart(Integer userId, Integer itemId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		MenuItem menuItem = this.menuItemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", itemId));

		UserCart byMenuItem = this.cartRepository.findByUserAndMenuItem(user, menuItem);

		if (byMenuItem != null) {
			byMenuItem.setQuantity(byMenuItem.getQuantity() + 1);
			byMenuItem.setTotalPrice(byMenuItem.getQuantity() * menuItem.getPrice());
			return this.cartRepository.save(byMenuItem);
		} else {
			UserCart cart = new UserCart();
			cart.setMenuItem(menuItem);
			cart.setUser(user);
			cart.setQuantity(1);
			cart.setTotalPrice(menuItem.getPrice());
			return this.cartRepository.save(cart);
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
	public List<UserCart> getCart(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.cartRepository.findByUser(user);
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
