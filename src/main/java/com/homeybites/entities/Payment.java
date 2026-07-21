package com.homeybites.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Either customerOrderId or subscriptionId will be set
	@Column(name = "customer_order_id")
	private Long customerOrderId;

	@Column(name = "subscription_id")
	private Long subscriptionId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "provider_id", nullable = false)
	private Long providerId;

	@Column(name = "amount", precision = 10, scale = 2, nullable = false)
	private BigDecimal amount;

	@Column(name = "tax_amount", precision = 10, scale = 2)
	private BigDecimal taxAmount;

	@Column(name = "discount_amount", precision = 10, scale = 2)
	private BigDecimal discountAmount;

	private BigDecimal refundedAmount;

	private LocalDateTime refundedAt;

	private String refundTransactionId;
	
	private String paymentType; // ORDER, SUBSCRIPTION

	@Column(name = "payment_method", length = 30)
	private String paymentMethod; // UPI, CARD, NET_BANKING, WALLET, COD

	@Column(name = "payment_status", length = 30, nullable = false)
	private String paymentStatus; // PENDING, SUCCESS, FAILED, REFUNDED

	@Column(name = "gateway_name", length = 30)
	private String gatewayName; // RAZORPAY, STRIPE

	@Column(name = "transaction_id", unique = true)
	private String transactionId;

	@Column(name = "gateway_order_id")
	private String gatewayOrderId;

	@Column(name = "gateway_payment_id")
	private String gatewayPaymentId;

	@Column(name = "failure_reason")
	private String failureReason;

//    @Column(name = "provider_amount", precision = 10, scale = 2)
//    private BigDecimal providerAmount;
//
//    @Column(name = "platform_commission", precision = 10, scale = 2)
//    private BigDecimal platformCommission;

	@Column(name = "paid_at")
	private LocalDateTime paidAt;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public Payment() {
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

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
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

	public String getGatewayName() {
		return gatewayName;
	}

	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getGatewayOrderId() {
		return gatewayOrderId;
	}

	public void setGatewayOrderId(String gatewayOrderId) {
		this.gatewayOrderId = gatewayOrderId;
	}

	public String getGatewayPaymentId() {
		return gatewayPaymentId;
	}

	public void setGatewayPaymentId(String gatewayPaymentId) {
		this.gatewayPaymentId = gatewayPaymentId;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
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

	public BigDecimal getRefundedAmount() {
		return refundedAmount;
	}

	public void setRefundedAmount(BigDecimal refundedAmount) {
		this.refundedAmount = refundedAmount;
	}

	public LocalDateTime getRefundedAt() {
		return refundedAt;
	}

	public void setRefundedAt(LocalDateTime refundedAt) {
		this.refundedAt = refundedAt;
	}

	public String getRefundTransactionId() {
		return refundTransactionId;
	}

	public void setRefundTransactionId(String refundTransactionId) {
		this.refundTransactionId = refundTransactionId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
}