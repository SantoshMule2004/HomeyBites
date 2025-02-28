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

import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.UserCartDto;
import com.homeybites.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	// get user cart
	@GetMapping("/{userId}")
	public ResponseEntity<List<UserCartDto>> getUserCart(@PathVariable Integer userId) {
		List<UserCartDto> cart = this.cartService.getCart(userId);
		return new ResponseEntity<List<UserCartDto>>(cart, HttpStatus.OK);
	}

	// add item to cart
	@PostMapping("/{userId}/item/{itemId}")
	public ResponseEntity<ApiResponse> addItemToCart(@PathVariable Integer userId, @PathVariable Integer itemId) {
		UserCartDto itemsToCart = this.cartService.addItemsToCart(userId, itemId);
		ApiResponse response = new ApiResponse("Item added successfully..!", true, itemsToCart);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// update menu item in cart
	@PutMapping("/{cartId}/quantity/{quantity}")
	public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Integer cartId, @PathVariable Integer quantity) {
		this.cartService.updateItemInfo(cartId, quantity);

		ApiResponse response = new ApiResponse();
		response.setMessage("quantity updated successfully..!");
		response.setSuccess(true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// delete item from cart
	@DeleteMapping("/delete/{cartId}")
	public ResponseEntity<ApiResponse> deleteItemFromCart(@PathVariable Integer cartId) {
		this.cartService.deleteItemFromCart(cartId);
		ApiResponse response = new ApiResponse("Item deleted successfully..!", true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// delete all items from cart
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteAllItems(@PathVariable Integer userId) {
		this.cartService.deleteAllItems(userId);
		ApiResponse response = new ApiResponse("All items removed successfully..!", true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
}
