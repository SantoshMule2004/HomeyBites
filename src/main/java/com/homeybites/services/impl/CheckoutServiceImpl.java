package com.homeybites.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Address;
import com.homeybites.entities.CustomerOrder;
import com.homeybites.entities.OrderItem;
import com.homeybites.entities.Payment;
import com.homeybites.entities.ProviderOrder;
import com.homeybites.entities.UserCart;
import com.homeybites.exceptions.BadRequestException;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.CancelOrderProjection;
import com.homeybites.payloads.CartItemDto;
import com.homeybites.payloads.FulfillmentStatus;
import com.homeybites.payloads.OrderFilterDto;
import com.homeybites.payloads.OrderItemDto;
import com.homeybites.payloads.OrderItemProjection;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PaymentStatus;
import com.homeybites.payloads.PaymentType;
import com.homeybites.payloads.OrderProjection;
import com.homeybites.payloads.OrderResponseDto;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.repositories.CartRepository;
import com.homeybites.repositories.CustomerOrderRepository;
import com.homeybites.repositories.OrderItemRepository;
import com.homeybites.repositories.PaymentRepository;
import com.homeybites.repositories.ProviderOrderRepository;
import com.homeybites.services.CartService;
import com.homeybites.services.CheckoutService;

import jakarta.transaction.Transactional;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CustomerOrderRepository orderRepository;

	@Autowired
	private ProviderOrderRepository providerOrderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Transactional
	@Override
	public void processCheckout(Long customerId, String paymentMethod, String paymentStatus, Long addId) {

		UserCart cart = cartRepository.findByUserId(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", customerId));

		// 1. Fetch Cart and Items
		List<CartItemDto> cartItems = cartService.getCartItemWithMenuItems(customerId);

		Address address = this.addressRepository.findByAddId(addId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addId));

		String deliveryAddress = address.getAddressLine() + ", " + address.getArea();

		// 2. Create the Parent Customer Order (The overall receipt)
		CustomerOrder parentOrder = new CustomerOrder();
		parentOrder.setUserId(customerId);
		parentOrder.setDeliveryAddress(deliveryAddress);
		parentOrder.setReceiverName(address.getReceiverName());
		parentOrder.setReceiverContactNo(address.getReceiverContactNo());
		parentOrder.setGrandTotal(BigDecimal.valueOf(cart.getGrandTotal()));
//		parentOrder.setPaymentMethod(paymentMethod);
		parentOrder.setCreatedAt(LocalDateTime.now());
//		parentOrder.setPaymentStatus(paymentStatus);

		parentOrder.setDiscountTotal(BigDecimal.valueOf(0));
		parentOrder.setTaxTotal(BigDecimal.valueOf(0));
//		parentOrder.setPaymentGatewayRef("PaymentGatewayRef");
		// ... calculate, save parentOrder ...

		parentOrder = orderRepository.save(parentOrder);

		System.out.println("Cart items: " + cartItems);
		// 3. Create the Provider Order

		Long providerId = cartItems.get(0).getProviderId();

		ProviderOrder providerOrder = new ProviderOrder();

		providerOrder.setCustomerOrderId(parentOrder.getId());
		providerOrder.setProviderId(providerId);
		providerOrder.setFulfillmentStatus("PENDING");
		providerOrder.setVendorSubtotal(
				BigDecimal.valueOf(cartItems.stream().mapToDouble(i -> i.getCurrentPrice() * i.getQuantity()).sum()));

		providerOrder.setVendorTax(BigDecimal.ZERO);
		providerOrder.setEstimatedDelivery(LocalDateTime.now());

		ProviderOrder po = providerOrderRepository.save(providerOrder);

		// 4. save the orderItems
		List<OrderItem> items = cartItems.stream().map(item -> {

			OrderItem oi = new OrderItem();

			oi.setProviderOrderId(po.getId());
			oi.setMenuItemId(item.getMenuItemId());
			oi.setItemName(item.getMenuName());
			oi.setPurchasedPrice(BigDecimal.valueOf(item.getPriceWhenAdded()));
			oi.setQuantity(item.getQuantity());
			oi.setTotalPrice(BigDecimal.valueOf(item.getCurrentPrice() * item.getQuantity()));

			return oi;

		}).toList();

		orderItemRepository.saveAll(items);

		// 5. create payment

		Payment payment = new Payment();

		payment.setCustomerOrderId(parentOrder.getId());
		payment.setPaymentType(PaymentType.ORDER.name());

		payment.setUserId(customerId);
		payment.setProviderId(providerId);

		payment.setAmount(parentOrder.getGrandTotal());

		payment.setTaxAmount(parentOrder.getTaxTotal());
		payment.setDiscountAmount(parentOrder.getDiscountTotal());

		payment.setPaymentMethod(paymentMethod);

		if ("COD".equalsIgnoreCase(paymentMethod)) {
			payment.setPaymentStatus(PaymentStatus.PENDING.name());
		} else {
			payment.setPaymentStatus(PaymentStatus.PAID.name());
			payment.setPaidAt(LocalDateTime.now());
		}

		// For now
		payment.setTransactionId(UUID.randomUUID().toString());

		// Razorpay later
		// payment.setGatewayOrderId(...);
		// payment.setGatewayPaymentId(...);

		paymentRepository.save(payment);

		// 6. Clear the Cart
		System.out.println("Clearing cart");
		cartService.deleteCart(cart.getUserId());
	}

	@Transactional
	public void cancelOrder(Long customerId, Long providerOrderId) {

		CancelOrderProjection order = providerOrderRepository.findOrderForCancellation(providerOrderId, customerId)
				.orElseThrow(() -> new ResourceNotFoundException("ProviderOrder", "id", providerOrderId));

		if (!FulfillmentStatus.PENDING.name().equals(order.getFulfillmentStatus())) {
			throw new BadRequestException("Only pending orders can be cancelled.");
		}

		Payment payment = paymentRepository
				.findByCustomerOrderIdAndPaymentStatus(order.getCustomerOrderId(), PaymentStatus.PAID.name())
				.orElseThrow(
						() -> new ResourceNotFoundException("Payment", "customerOrderId", order.getCustomerOrderId()));

		BigDecimal refundAmount = payment.getAmount();

		/*
		 * TODO
		 *
		 * String refundTransactionId = razorpayService.refund(
		 * payment.getGatewayPaymentId(), refundAmount);
		 */

		payment.setRefundedAmount(refundAmount);
		payment.setRefundedAt(LocalDateTime.now());

		// payment.setRefundTransactionId(refundTransactionId);

		payment.setPaymentStatus(PaymentStatus.REFUNDED.name());

		paymentRepository.save(payment);

		providerOrderRepository.cancelOrder(providerOrderId);
	}

	@Override
	public void updateProviderOrderStatus(Long providerOrderId, Long providerId, String status) {
		int rows = providerOrderRepository.updateStatus(providerOrderId, providerId, status);

		if (rows == 0) {
			throw new ResourceNotFoundException("ProviderOrder", "Id", providerOrderId);
		}
	}

	@Override
	public PageResponse<OrderProjection> getCustomerOrderHistory(Long userId, OrderFilterDto filter,
			Pageable pageable) {

		filter = this.verifyDateFilter(filter);

		Page<OrderProjection> page = orderRepository.findCustomerOrders(userId, filter.getStartDate(),
				filter.getEndDate(), filter.getStatus(), pageable);

		return new PageResponse<>(page);
	}

	@Override
	public OrderResponseDto getCustomerOrderDetails(Long orderId, Long userId) {
		OrderProjection order = orderRepository.findCustomerOrder(orderId, userId);

		OrderResponseDto dto = new OrderResponseDto(order);

		List<OrderItemDto> items = orderItemRepository.findItems(order.getProviderOrderId()).stream()
				.map(OrderItemDto::new).toList();

		dto.setOrderItems(items);

		return dto;
	}

	@Override
	public PageResponse<OrderResponseDto> findOrders(Long providerId, OrderFilterDto filter,
			Pageable pageable) {

		filter = this.verifyDateFilter(filter);

		Page<OrderProjection> orders = providerOrderRepository.findOrders(providerId,
				filter.getStartDate(), filter.getEndDate(), filter.getStatus(), filter.getPaymentStatus(),
				filter.getReceiverName(), pageable);

		List<Long> providerOrderIds = orders.stream().map(OrderProjection::getProviderOrderId).toList();

		Map<Long, List<OrderItemProjection>> itemMap = orderItemRepository.findItems(providerOrderIds).stream()
				.collect(Collectors.groupingBy(OrderItemProjection::getProviderOrderId));

		List<OrderResponseDto> dtos = orders.stream().map(order -> {

			OrderResponseDto dto = new OrderResponseDto(order);

			dto.setOrderItems(itemMap.getOrDefault(order.getProviderOrderId(), Collections.emptyList()).stream()
					.map(OrderItemDto::new).toList());

			return dto;
		}).toList();

		PageImpl<OrderResponseDto> pageImpl = new PageImpl<>(dtos, pageable, orders.getTotalElements());

		return new PageResponse<>(pageImpl);
	}

	@Override
	public OrderResponseDto findOrderDetails(Long providerOrderId, Long providerId) {
		OrderProjection order = providerOrderRepository.findOrder(providerOrderId, providerId)
				.orElseThrow(() -> new ResourceNotFoundException("ProviderOrder", "Id", providerOrderId));

		OrderResponseDto dto = new OrderResponseDto(order);

		dto.setOrderItems(orderItemRepository.findItems(providerOrderId).stream().map(OrderItemDto::new).toList());

		return dto;
	}

	private OrderFilterDto verifyDateFilter(OrderFilterDto filter) {
		final LocalDate MIN_DATE = LocalDate.of(1970, 1, 1);

		LocalDate today = LocalDate.now();

		if (filter.getStartDate() == null && filter.getEndDate() == null) {
			filter.setStartDate(today);
			filter.setEndDate(today);
		} else if (filter.getStartDate() != null && filter.getEndDate() == null) {
			filter.setEndDate(filter.getStartDate());
		} else if (filter.getStartDate() == null) {
			filter.setStartDate(MIN_DATE);
		}

		if (filter.getStartDate().isAfter(filter.getEndDate())) {
			throw new BadRequestException("Start date cannot be after end date.");
		}

		return filter;
	}

//	@Transactional
//	@Override
//	public void processCheckout(Long customerId, String paymentMethod, String paymentStatus, Long addId) {
//
//		UserCart cart = cartRepository.findByUserId(customerId)
//				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", customerId));
//
//		// 1. Fetch Cart and Items
//		List<CartItemDto> cartItems = cartService.getCartItemWithMenuItems(customerId);
//
//		Address address = this.addressRepository.findByAddId(addId)
//				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addId));
//
//		String deliveryAddress = address.getAddressLine() + ", " + address.getArea();
//		
//		// 2. Create the Parent Customer Order (The overall receipt)
//		CustomerOrder parentOrder = new CustomerOrder();
//		parentOrder.setUserId(customerId);
//		parentOrder.setDeliveryAddress(deliveryAddress);
//		parentOrder.setReceiverName(address.getReceiverName());
//		parentOrder.setReceiverContactNo(address.getReceiverContactNo());
//		parentOrder.setGrandTotal(BigDecimal.valueOf(cart.getGrandTotal()));
//		parentOrder.setPaymentMethod(paymentMethod);
//		parentOrder.setCreatedAt(LocalDateTime.now());
//		parentOrder.setPaymentStatus(paymentStatus);
//		
//		parentOrder.setDiscountTotal(BigDecimal.valueOf(0));
//		parentOrder.setTaxTotal(BigDecimal.valueOf(0));
//		parentOrder.setPaymentGatewayRef("PaymentGatewayRef");
//		// ... calculate, save parentOrder ...
//
//		parentOrder = orderRepository.save(parentOrder);
//
//		// 3. Group the cart items by Provider ID
//		
//		System.out.println("Cart items: " + cartItems);
//
//		// This creates a Map where the Key is ProviderId, and Value is their list of
//		// items
//		Map<Long, List<CartItemDto>> itemsByProvider = cartItems.stream()
//				.collect(Collectors.groupingBy(CartItemDto::getProviderId));
//
//		System.out.println("Creating provider order: " + itemsByProvider);
//		// 4. Create a Vendor Order for each Provider
//
//		for (Map.Entry<Long, List<CartItemDto>> entry : itemsByProvider.entrySet()) {
//			
//			System.out.println("Inside for loop");
//			
//			Long providerId = entry.getKey();
//			List<CartItemDto> providerItems = entry.getValue();
//			ProviderOrder subOrder = new ProviderOrder();
//			
//			subOrder.setCustomerOrderId(parentOrder.getId());
//			subOrder.setProviderId(providerId);
//			subOrder.setFulfillmentStatus("PENDING");
//			subOrder.setCreatedAt(LocalDateTime.now());
//			subOrder.setVendorSubtotal(BigDecimal.valueOf(
//					providerItems.stream().mapToDouble(item -> item.getCurrentPrice() * item.getQuantity()).sum()));
//
//			subOrder.setVendorTax(BigDecimal.valueOf(0));
//			subOrder.setEstimatedDelivery(LocalDateTime.now());
//			// ... calculate subtotal for just this vendor's items ...
//			// updatedAt
//
//			System.out.println("Saving provider order");
//			ProviderOrder subProviderOrder = providerOrderRepository.save(subOrder);
//
//			// 5. Save the Order Items linked to the Sub-Order
//
//			List<OrderItem> orderItems = providerItems.stream().map(item -> {
//				
//				System.out.println("Inside order item stream");
//				OrderItem oi = new OrderItem();
//				oi.setProviderOrderId(subProviderOrder.getId());
//				oi.setMenuItemId(item.getMenuItemId());
//				oi.setPurchasedPrice(BigDecimal.valueOf(item.getPriceWhenAdded()));
//				oi.setItemName(item.getMenuName());
//				oi.setQuantity(item.getQuantity());
//				oi.setTotalPrice(BigDecimal.valueOf(item.getCurrentPrice() * item.getQuantity()));
//				oi.setSpecialInstructions("");
//
//				return oi;
//			}).toList();
//
//			System.out.println("Saving order item");
//			orderItemRepository.saveAll(orderItems);
//		}
//
//		// 6. Clear the Cart
//		System.out.println("Clearing cart");
//		cartService.deleteCart(cart.getUserId());
//	}
}
