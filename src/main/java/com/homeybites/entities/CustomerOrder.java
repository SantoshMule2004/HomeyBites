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
public class CustomerOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "grand_total", precision = 10, scale = 2, nullable = false)
	private BigDecimal grandTotal;

	@Column(name = "tax_total", precision = 10, scale = 2, nullable = false)
	private BigDecimal taxTotal;

	@Column(name = "discount_total", precision = 10, scale = 2, nullable = false)
	private BigDecimal discountTotal;

//	@Column(name = "payment_method", length = 50, nullable = false)
//	private String paymentMethod; // UPI, CARD, COD
//
//	@Column(name = "payment_status", length = 50, nullable = false)
//	private String paymentStatus; // PENDING, SUCCESS, FAILED, REFUNDED
//
//	@Column(name = "payment_gateway_ref", length = 255)
//	private String paymentGatewayRef; // e.g., Razorpay/Stripe transaction ID

	// Storing as a JSON string or plain text to preserve the exact historical
	// address
	@Column(name = "delivery_address", columnDefinition = "TEXT", nullable = false)
	private String deliveryAddress;

	private String receiverName;
	private String receiverContactNo;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public CustomerOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerOrder(Long id, Long userId, BigDecimal grandTotal, BigDecimal taxTotal, BigDecimal discountTotal,
			String deliveryAddress, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.grandTotal = grandTotal;
		this.taxTotal = taxTotal;
		this.discountTotal = discountTotal;
		this.deliveryAddress = deliveryAddress;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public BigDecimal getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(BigDecimal taxTotal) {
		this.taxTotal = taxTotal;
	}

	public BigDecimal getDiscountTotal() {
		return discountTotal;
	}

	public void setDiscountTotal(BigDecimal discountTotal) {
		this.discountTotal = discountTotal;
	}

//	public String getPaymentStatus() {
//		return paymentStatus;
//	}
//
//	public void setPaymentStatus(String paymentStatus) {
//		this.paymentStatus = paymentStatus;
//	}
//
//	public String getPaymentGatewayRef() {
//		return paymentGatewayRef;
//	}
//
//	public void setPaymentGatewayRef(String paymentGatewayRef) {
//		this.paymentGatewayRef = paymentGatewayRef;
//	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

//	public String getPaymentMethod() {
//		return paymentMethod;
//	}
//
//	public void setPaymentMethod(String paymentMethod) {
//		this.paymentMethod = paymentMethod;
//	}

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

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "CustomerOrder [id=" + id + ", userId=" + userId + ", grandTotal=" + grandTotal + ", taxTotal="
				+ taxTotal + ", discountTotal=" + discountTotal + ", deliveryAddress=" + deliveryAddress
				+ ", receiverName=" + receiverName + ", receiverContactNo=" + receiverContactNo + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}
}
