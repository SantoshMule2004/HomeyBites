package com.homeybites.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Address;
import com.homeybites.entities.CustomerOrder;
import com.homeybites.entities.OrderItem;
import com.homeybites.entities.ProviderOrder;
import com.homeybites.entities.UserCart;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.CartItemDto;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.repositories.CartRepository;
import com.homeybites.repositories.CustomerOrderRepository;
import com.homeybites.repositories.OrderItemRepository;
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

	@Transactional
	@Override
	public void processCheckout(Long customerId, String paymentMethod, String paymentStatus, Long addId) {

		UserCart cart = cartRepository.findByUserId(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", customerId));

		// 1. Fetch Cart and Items
		List<CartItemDto> cartItems = cartService.getCartItemWithMenuItems(customerId);

		Address address = this.addressRepository.findByAddId(addId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addId));

		// 2. Create the Parent Customer Order (The overall receipt)
		CustomerOrder parentOrder = new CustomerOrder();

		parentOrder.setUserId(customerId);
		String deliveryAddress = address.getAddressLine() + ", " + address.getArea();
		parentOrder.setDeliveryAddress(deliveryAddress);
		parentOrder.setGrandTotal(BigDecimal.valueOf(cart.getGrandTotal()));
		parentOrder.setPaymentMethod(paymentMethod);
		parentOrder.setCreatedAt(LocalDateTime.now());
		parentOrder.setPaymentStatus(paymentStatus);
		
		parentOrder.setDiscountTotal(BigDecimal.valueOf(0));
		parentOrder.setTaxTotal(BigDecimal.valueOf(0));
		parentOrder.setPaymentGatewayRef("PaymentGatewayRef");
		// ... calculate, save parentOrder ...

		parentOrder = orderRepository.save(parentOrder);

		// 3. Group the cart items by Provider ID
		
		System.out.println("Cart items: " + cartItems);

		// This creates a Map where the Key is ProviderId, and Value is their list of
		// items
		Map<Long, List<CartItemDto>> itemsByProvider = cartItems.stream()
				.collect(Collectors.groupingBy(CartItemDto::getProviderId));

		System.out.println("Creating provider order: " + itemsByProvider);
		// 4. Create a Vendor Order for each Provider

		for (Map.Entry<Long, List<CartItemDto>> entry : itemsByProvider.entrySet()) {
			
			System.out.println("Inside for loop");
			
			Long providerId = entry.getKey();
			List<CartItemDto> providerItems = entry.getValue();
			ProviderOrder subOrder = new ProviderOrder();
			
			subOrder.setCustomerOrderId(parentOrder.getId());
			subOrder.setProviderId(providerId);
			subOrder.setFulfillmentStatus("PENDING");
			subOrder.setCreatedAt(LocalDateTime.now());
			subOrder.setVendorSubtotal(BigDecimal.valueOf(
					providerItems.stream().mapToDouble(item -> item.getCurrentPrice() * item.getQuantity()).sum()));

			subOrder.setVendorTax(BigDecimal.valueOf(0));
			subOrder.setEstimatedDelivery(LocalDateTime.now());
			// ... calculate subtotal for just this vendor's items ...
			// updatedAt

			System.out.println("Saving provider order");
			ProviderOrder subProviderOrder = providerOrderRepository.save(subOrder);

			// 5. Save the Order Items linked to the Sub-Order

			List<OrderItem> orderItems = providerItems.stream().map(item -> {
				
				System.out.println("Inside order item stream");
				OrderItem oi = new OrderItem();
				oi.setProviderOrderId(subProviderOrder.getId());
				oi.setMenuItemId(item.getMenuItemId());
				oi.setPurchasedPrice(BigDecimal.valueOf(item.getPriceWhenAdded()));
				oi.setItemName(item.getMenuName());
				oi.setQuantity(item.getQuantity());
				oi.setTotalPrice(BigDecimal.valueOf(item.getCurrentPrice() * item.getQuantity()));
				oi.setSpecialInstructions("");

				return oi;
			}).toList();

			System.out.println("Saving order item");
			orderItemRepository.saveAll(orderItems);
		}

		// 6. Clear the Cart
		System.out.println("Clearing cart");
		cartService.deleteCart(cart.getUserId());
	}
}
