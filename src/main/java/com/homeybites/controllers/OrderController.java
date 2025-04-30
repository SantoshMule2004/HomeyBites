package com.homeybites.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.OrderInfo;
import com.homeybites.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	// API to place order
	@PostMapping("/place/{userId}")
	public ResponseEntity<OrderInfo> placeOrder(@RequestBody OrderInfo order, @PathVariable Integer userId) {
		OrderInfo newOrder = orderService.placeOrder(order, userId);
		return ResponseEntity.ok(newOrder);
	}

	// API to get order by id
	@GetMapping("/count/{id}")
	public ResponseEntity<Integer> getOrderCount(@PathVariable Integer id) {
		Integer count = this.orderService.getOrderCount(id);
		return ResponseEntity.ok(count);
	}

	// API to get order by id
	@GetMapping("/count")
	public ResponseEntity<Integer> getTotalOrderCount() {
		Integer count = this.orderService.getTotalOrderCount();
		return ResponseEntity.ok(count);
	}

	// API to get order by id
	@GetMapping("/{id}")
	public ResponseEntity<OrderInfo> getOrder(@PathVariable Integer id) {
		Optional<OrderInfo> order = orderService.getOrderById(id);
		return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// API to get all order
	@GetMapping("/all")
	public ResponseEntity<List<OrderInfo>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	// API to get todays orders
	@GetMapping("/today/{providerId}")
	public ResponseEntity<List<OrderInfo>> getAllTodaysOrders(@PathVariable Integer providerId) {
		return ResponseEntity.ok(orderService.getTodaysOrder(providerId));
	}

	// API to get all todays orders
	@GetMapping("/today/all")
	public ResponseEntity<List<OrderInfo>> getAllTodaysOrders() {
		return ResponseEntity.ok(orderService.getAllTodaysOrder());
	}

	// API to get all orders by status
	@GetMapping("/{status}")
	public ResponseEntity<List<OrderInfo>> getAllOrdersByStatus(@PathVariable String status) {
		return ResponseEntity.ok(orderService.getOrderByStatus(status));
	}

	// API to get all order belonging to tiffin provider
	@GetMapping("/all/{providerId}")
	public ResponseEntity<List<OrderInfo>> getAllOrdersOfTiffinProvider(@PathVariable Integer providerId,
			@RequestParam String status) {
		return ResponseEntity.ok(orderService.getOrderOfTiffinProvider(providerId, status));
	}

	// API to get all order belonging to tiffin provider
	@GetMapping("/provider/{providerId}")
	public ResponseEntity<List<OrderInfo>> getTiffinProviderOrders(@PathVariable Integer providerId,
			@RequestParam String startDate, @RequestParam String endDate) {
		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);
		return ResponseEntity.ok(orderService.getAllOrderOfTiffinProvider(providerId, start, end));
	}

	// API to get all order belonging to tiffin provider
	@GetMapping("/range")
	public ResponseEntity<List<OrderInfo>> getOrdersInrange(@RequestParam String startDate,
			@RequestParam String endDate) {
		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);
		return ResponseEntity.ok(orderService.getAllOrderInRange(start, end));
	}

	// API to get all subscription order belonging to tiffin provider
	@GetMapping("/subscription/{providerId}")
	public ResponseEntity<List<OrderInfo>> getSubscriptionOrdersOfTiffinProvider(@PathVariable Integer providerId,
			@RequestParam String status) {
		return ResponseEntity.ok(orderService.getSubscriptionOrderOfTiffinProvider(providerId, status));
	}

	// API to get all menuitem order belonging to tiffin provider
	@GetMapping("/menuitem/{providerId}")
	public ResponseEntity<List<OrderInfo>> getMenuItemOrdersOfTiffinProvider(@PathVariable Integer providerId,
			@RequestParam String status) {
		return ResponseEntity.ok(orderService.getMenuItemOrderOfTiffinProvider(providerId, status));
	}

	// API to get order history
	@GetMapping("/history/{userId}")
	public ResponseEntity<List<OrderInfo>> getOrderHistory(@PathVariable Integer userId) {
		return ResponseEntity.ok(orderService.getOrderHistory());
	}

	// update order status
	@PutMapping("/update/{id}")
	public ResponseEntity<OrderInfo> updateOrder(@PathVariable Integer id, @RequestParam String status) {
		OrderInfo order = orderService.updateOrderStatus(id, status);
		return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
	}

	// cancel order
	@DeleteMapping("/cancel/{id}")
	public ResponseEntity<String> cancelOrder(@PathVariable Integer id) {
		boolean deleted = orderService.cancelOrder(id);
		return deleted ? ResponseEntity.ok("Order canceled successfully") : ResponseEntity.notFound().build();
	}
}
