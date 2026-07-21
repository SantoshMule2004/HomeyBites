package com.homeybites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.OrderFilterDto;
import com.homeybites.payloads.OrderProjection;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.OrderResponseDto;
import com.homeybites.services.CheckoutService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	@Autowired
	private CheckoutService checkoutService;

	// Place Order
	@PostMapping("/place/{userId}")
	public ResponseEntity<?> placeOrder(@PathVariable Long userId, @RequestParam Long addId,
			@RequestParam String pmethod, @RequestParam String pstatus) {
		this.checkoutService.processCheckout(userId, pmethod, pstatus, addId);
		return ResponseEntity.ok("Order placed successfully..!");
	}

	// Get Customer orders
	@GetMapping("/customer/{userId}")
	public ResponseEntity<PageResponse<OrderProjection>> getCustomerOrders(@PathVariable Long userId,
			@ModelAttribute OrderFilterDto filter, Pageable pageable) {
		PageResponse<OrderProjection> orders = checkoutService.getCustomerOrderHistory(userId, filter, pageable);
		return ResponseEntity.ok(orders);
	}

	// get customer order details
	@GetMapping("/{orderId}/customer/{userId}/")
	public ResponseEntity<ApiResponse> getCustomerOrder(@PathVariable Long userId, @PathVariable Long orderId) {
		OrderResponseDto order = checkoutService.getCustomerOrderDetails(userId, orderId);
		return ResponseEntity.ok(new ApiResponse("Order fetched Successfully..!", true, order));
	}

	// cancel order
	@PutMapping("{userId}/customer/{providerOrderId}/cancel")
	public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long userId, @PathVariable Long providerOrderId) {
		checkoutService.cancelOrder(userId, providerOrderId);
		return ResponseEntity.ok(new ApiResponse("Order cancelled successfully.", true, null));
	}

	/* provider APIs */

	// get all provider orders
	@GetMapping("/provider/{providerId}/all")
	public ResponseEntity<PageResponse<OrderResponseDto>> getProviderOrders(@PathVariable Long providerId,
			@ModelAttribute OrderFilterDto filter, Pageable pageable) {
		PageResponse<OrderResponseDto> orders = checkoutService.findOrders(providerId, filter, pageable);
		return ResponseEntity.ok(orders);
	}

	// get provider order details
	@GetMapping("/provider/{providerId}/provider-order/{providerOrderId}")
	public ResponseEntity<ApiResponse> getProviderOrder(@PathVariable Long providerId,
			@PathVariable Long providerOrderId) {
		OrderResponseDto order = checkoutService.findOrderDetails(providerOrderId, providerId);
		return ResponseEntity.ok(new ApiResponse("Order fetched successfully.", true, order));
	}

	// update order status - PENDING, PREPARING, OUT_FOR_DELIVERY, DELIVERED
	@PutMapping("/provider/{providerId}/status/{providerOrderId}")
	public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable Long providerId,
			@PathVariable Long providerOrderId, @RequestParam String status) {
		checkoutService.updateProviderOrderStatus(providerOrderId, providerId, status);
		return ResponseEntity.ok(new ApiResponse(true, "Order status updated successfully."));
	}

	/* Admin APIs */

	// get all provider orders
	@GetMapping("/admin/all")
	public ResponseEntity<PageResponse<OrderResponseDto>> getAdminOrders(@ModelAttribute OrderFilterDto filter,
			Pageable pageable) {
		PageResponse<OrderResponseDto> orders = checkoutService.findOrders(null, filter, pageable);
		return ResponseEntity.ok(orders);
	}

	// get provider order details
	@GetMapping("/admin/order/{orderId}")
	public ResponseEntity<ApiResponse> getAdminOrder(@PathVariable Long orderId) {
		OrderResponseDto order = checkoutService.findOrderDetails(orderId, null);
		return ResponseEntity.ok(new ApiResponse("Order fetched successfully.", true, order));
	}
}
