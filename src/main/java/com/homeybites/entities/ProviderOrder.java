package com.homeybites.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProviderOrder {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_order_id", nullable = false)
    private Long customerOrderId; // Soft ID up to the Parent Receipt

    @Column(name = "provider_id", nullable = false)
    private Long providerId; // Soft ID to the Kitchen

    @Column(name = "vendor_subtotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal vendorSubtotal;

    @Column(name = "vendor_tax", precision = 10, scale = 2, nullable = false)
    private BigDecimal vendorTax;

    @Column(name = "fulfillment_status", length = 50, nullable = false)
    private String fulfillmentStatus; // PENDING, PREPARING, OUT_FOR_DELIVERY, DELIVERED

    @Column(name = "delivery_partner_id")
    private Long deliveryPartnerId; // Soft ID to the Driver (Nullable until assigned)

    @Column(name = "estimated_delivery")
    private LocalDateTime estimatedDelivery;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

	public ProviderOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProviderOrder(Long id, Long customerOrderId, Long providerId, BigDecimal vendorSubtotal,
			BigDecimal vendorTax, String fulfillmentStatus, Long deliveryPartnerId, LocalDateTime estimatedDelivery,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.customerOrderId = customerOrderId;
		this.providerId = providerId;
		this.vendorSubtotal = vendorSubtotal;
		this.vendorTax = vendorTax;
		this.fulfillmentStatus = fulfillmentStatus;
		this.deliveryPartnerId = deliveryPartnerId;
		this.estimatedDelivery = estimatedDelivery;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getVendorTax() {
		return vendorTax;
	}

	public void setVendorTax(BigDecimal vendorTax) {
		this.vendorTax = vendorTax;
	}

	public String getFulfillmentStatus() {
		return fulfillmentStatus;
	}

	public void setFulfillmentStatus(String fulfillmentStatus) {
		this.fulfillmentStatus = fulfillmentStatus;
	}

	public Long getDeliveryPartnerId() {
		return deliveryPartnerId;
	}

	public void setDeliveryPartnerId(Long deliveryPartnerId) {
		this.deliveryPartnerId = deliveryPartnerId;
	}

	public LocalDateTime getEstimatedDelivery() {
		return estimatedDelivery;
	}

	public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "ProviderOrder [id=" + id + ", customerOrderId=" + customerOrderId + ", providerId=" + providerId
				+ ", vendorSubtotal=" + vendorSubtotal + ", vendorTax=" + vendorTax + ", fulfillmentStatus="
				+ fulfillmentStatus + ", deliveryPartnerId=" + deliveryPartnerId + ", estimatedDelivery="
				+ estimatedDelivery + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
}
