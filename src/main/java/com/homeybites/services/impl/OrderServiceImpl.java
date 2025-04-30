package com.homeybites.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.MenuItem;
import com.homeybites.entities.OrderInfo;
import com.homeybites.entities.Subscription;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.repositories.OrderRepository;
import com.homeybites.repositories.SubscriptionRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Override
	public OrderInfo placeOrder(OrderInfo order, Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		if (!order.getMenuIds().isEmpty()) {
			List<MenuItem> menuItems = new ArrayList<>();
			order.getMenuIds().forEach(menuId -> {
				MenuItem newMenuItem = this.menuItemRepository.findById(menuId)
						.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));
				menuItems.add(newMenuItem);
			});
			order.setMenuItems(menuItems);
		}

		if (order.getSubId() != null) {
			Subscription subscription = this.subscriptionRepository.findById(order.getSubId())
					.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", order.getSubId()));

			order.setSubscription(subscription);
		}

		order.setOrderDate(LocalDateTime.now());
		order.setOrderStatus("Placed");
		order.setUser(user);
		return orderRepository.save(order);
	}

	@Override
	public Optional<OrderInfo> getOrderById(Integer id) {
		return orderRepository.findById(id);
	}

	@Override
	public List<OrderInfo> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public List<OrderInfo> getOrderHistory() {
		return orderRepository.findByOrderStatus("Completed");
	}

	@Override
	public OrderInfo updateOrderStatus(Integer id, String status) {
		return orderRepository.findById(id).map(order -> {
			order.setOrderStatus(status);
			return orderRepository.save(order);
		}).orElse(null);
	}

	@Override
	public boolean cancelOrder(Integer id) {
		return orderRepository.findById(id).map(order -> {
			orderRepository.deleteById(id);
			return true;
		}).orElse(false);
	}

	@Override
	public List<OrderInfo> getOrderOfTiffinProvider(Integer userId, String status) {
		this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		List<OrderInfo> collect = this.orderRepository.findAll().stream().filter(order -> {

			boolean fromSub = order.getSubscription() != null
					&& order.getSubscription().getTiffinPlan().getUser().getUserId().equals(userId);

			boolean fromMenu = order.getMenuItems().stream()
					.anyMatch(menu -> menu.getUser().getUserId().equals(userId));

			return fromSub || fromMenu;
		}).collect((Collectors.toList()));

		return collect;
	}

	@Override
	public List<OrderInfo> getSubscriptionOrderOfTiffinProvider(Integer userId, String status) {
		this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return this.orderRepository.findSubscriptionOrder(status, userId);
	}

	@Override
	public List<OrderInfo> getMenuItemOrderOfTiffinProvider(Integer userId, String status) {
		this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return this.orderRepository.findMenuItemOrder(status, userId);
	}

	@Override
	public List<OrderInfo> getTodaysOrder(Integer userId) {
		this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return this.orderRepository.findTodayOrders("Completed", userId);
	}

	@Override
	public List<OrderInfo> getOrderByStatus(String status) {
		return this.orderRepository.findByOrderStatus(status);
	}

	@Override
	public List<OrderInfo> getAllTodaysOrder() {
		return this.orderRepository.findAllTodayOrders("Completed");
	}

	@Override
	public List<OrderInfo> getAllOrderOfTiffinProvider(Integer userId, LocalDate sDate, LocalDate eDate) {
		return this.orderRepository.findOrderOfProvider(userId, sDate, eDate);
	}

	@Override
	public Integer getOrderCount(Integer providerId) {
		return this.orderRepository.getMenuOrderCount(providerId);
	}

	@Override
	public Integer getTotalOrderCount() {
		return this.orderRepository.getAllMenuOrderCount();
	}

	@Override
	public List<OrderInfo> getAllOrderInRange(LocalDate sDate, LocalDate eDate) {
		return this.orderRepository.findAllOrdersInrange(sDate, eDate);
	}
}
