package com.homeybites.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.homeybites.entities.OrderInfo;
import com.homeybites.payloads.OrderResponse;

public interface OrderService {

	// place order
	OrderInfo placeOrder(OrderInfo order, Integer userId);

	Integer getOrderCount(Integer providerId);

	Integer getTotalOrderCount();

	// get order by id
	Optional<OrderInfo> getOrderById(Integer id);

	// get all orders
	OrderResponse getAllOrders(Integer pageNumber, Integer pageSize, String sortBy, String sortIn);

	// get orders by status
	List<OrderInfo> getOrderByStatus(String status);

	// get order history
	List<OrderInfo> getOrderHistory();

	// get todays orders belonging to tiffin provider
	List<OrderInfo> getTodaysOrder(Integer userId);

	// get all todays orders
	List<OrderInfo> getAllTodaysOrder();

	// get all orders belonging to tiffin provider
	List<OrderInfo> getOrderOfTiffinProvider(Integer userId, String status);

	// get all orders belonging to tiffin provider
	List<OrderInfo> getAllOrderInRange(LocalDate sDate, LocalDate eDate);

	// get all orders belonging to tiffin provider
	List<OrderInfo> getAllOrderOfTiffinProvider(Integer userId, LocalDate sDate, LocalDate eDate);

	// get all subscription orders belonging to tiffin provider
	List<OrderInfo> getSubscriptionOrderOfTiffinProvider(Integer userId, String status);

	// get all menuitem orders belonging to tiffin provider
	List<OrderInfo> getMenuItemOrderOfTiffinProvider(Integer userId, String status);

	// update order
	OrderInfo updateOrderStatus(Integer id, String status);

	// cancel order
	boolean cancelOrder(Integer id);
}
