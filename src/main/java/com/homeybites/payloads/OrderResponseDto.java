package com.homeybites.payloads;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {
	// customer order details
	private Long customerOrderId;
	private LocalDateTime createdAt;
	private String receiverName;
	private String receiverContactNo;
	private String deliveryAddress;

	// provider order details
	private Long providerOrderId;
	private Long providerId;
	private BigDecimal vendorSubtotal;
	private String fulfillmentStatus;

	// payment details
	private String paymentMethod;
	private String paymentStatus;

	private String providerName;;
	private String businessName;;

	// order item details
	private List<OrderItemDto> orderItems;

	public OrderResponseDto(OrderProjection projection) {
		this.providerOrderId = projection.getProviderOrderId();
		this.customerOrderId = projection.getCustomerOrderId();
		this.providerId = projection.getProviderId();
		this.vendorSubtotal = projection.getVendorSubtotal();
		this.fulfillmentStatus = projection.getFulfillmentStatus();
		this.createdAt = projection.getCreatedAt();

		this.paymentMethod = projection.getPaymentMethod();
		this.paymentStatus = projection.getPaymentStatus();

		this.providerName = projection.getProviderName();
		this.businessName = projection.getBusinessName();

		this.receiverName = projection.getReceiverName();
		this.receiverContactNo = projection.getReceiverContactNo();
		this.deliveryAddress = projection.getDeliveryAddress();
	}

	public Long getProviderOrderId() {
		return providerOrderId;
	}

	public void setProviderOrderId(Long providerOrderId) {
		this.providerOrderId = providerOrderId;
	}

	public Long getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(Long customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public BigDecimal getVendorSubtotal() {
		return vendorSubtotal;
	}

	public void setVendorSubtotal(BigDecimal vendorSubtotal) {
		this.vendorSubtotal = vendorSubtotal;
	}

	public String getFulfillmentStatus() {
		return fulfillmentStatus;
	}

	public void setFulfillmentStatus(String fulfillmentStatus) {
		this.fulfillmentStatus = fulfillmentStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverContactNo() {
		return receiverContactNo;
	}

	public void setReceiverContactNo(String receiverContactNo) {
		this.receiverContactNo = receiverContactNo;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
