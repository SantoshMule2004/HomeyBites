package com.homeybites.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.CartItem;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.CartDto;
import com.homeybites.payloads.CartItemDto;
import com.homeybites.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	// get user cart
	@GetMapping("/{userId}")
	public ResponseEntity<List<CartItem>> getUserCart(@PathVariable Long userId) {
		List<CartItem> cart = this.cartService.getCart(userId);
		return new ResponseEntity<List<CartItem>>(cart, HttpStatus.OK);
	}

	// get user cart with menu details
	@GetMapping("/items/{userId}")
	public ResponseEntity<CartDto> getUserCartWithMenuDetials(@PathVariable Long userId) {
		List<CartItemDto> cart = this.cartService.getCartItemWithMenuItems(userId);
		Double total = this.cartService.getGrandTotal(userId);
		CartDto cartDto = new CartDto(cart, total);
		return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
	}

	// get cart items count
	@GetMapping("/items/count/{userId}")
	public ResponseEntity<Long> getUserCartItemCount(@PathVariable Long userId) {
		long count = this.cartService.countItemsInCart(userId);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	// add item to cart
	@PostMapping("/{userId}/item/{itemId}")
	public ResponseEntity<ApiResponse> addItemToCart(@PathVariable Long userId, @PathVariable Long itemId) {
		Integer result = this.cartService.addItemsToCart(userId, itemId);

		if (result.equals(1)) {
			ApiResponse response = new ApiResponse(
					"Your cart contains items from another provider. Please clear your cart to add this item.", false,
					null);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.CONFLICT);
		}

		ApiResponse response = new ApiResponse("Item added successfully..!", true, null);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// update menu item in cart
	@PutMapping("/{cartItemId}/quantity/{quantity}")
	public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartItemId, @PathVariable Integer quantity) {
		this.cartService.updateCartItem(cartItemId, quantity);

		ApiResponse response = new ApiResponse();
		response.setMessage("quantity updated successfully..!");
		response.setSuccess(true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// delete item from cart
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteItemFromCart(@PathVariable Long cartItemId) {
		this.cartService.deleteItemFromCart(cartItemId);
		ApiResponse response = new ApiResponse("Item deleted successfully..!", true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// delete all items from cart
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteAllItems(@PathVariable Long userId) {
		this.cartService.deleteCart(userId);
		ApiResponse response = new ApiResponse("All items removed successfully..!", true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
}
