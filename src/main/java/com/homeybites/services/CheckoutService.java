package com.homeybites.services;

import org.springframework.data.domain.Pageable;

import com.homeybites.payloads.OrderFilterDto;
import com.homeybites.payloads.OrderProjection;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.OrderResponseDto;

public interface CheckoutService {
	void processCheckout(Long customerId, String paymentMethod, String paymentStatus, Long addId);

	PageResponse<OrderProjection> getCustomerOrderHistory(Long userId, OrderFilterDto filter, Pageable pageable);

	OrderResponseDto getCustomerOrderDetails(Long orderId, Long userId);

	PageResponse<OrderResponseDto> findOrders(Long providerId, OrderFilterDto filter, Pageable pageable);

	OrderResponseDto findOrderDetails(Long providerOrderId, Long providerId);

	void updateProviderOrderStatus(Long providerOrderId, Long providerId, String status);

	void cancelOrder(Long customerId, Long providerOrderId);
}
