package com.homeybites.services;

import java.time.LocalDate;
import java.util.List;

import com.homeybites.entities.Payment;

public interface PaymentService {

	Payment addPayment(Payment payment, Integer userId, Integer orderId);

	Double getTotalRevenue();

	Double getMenuitemRevenue();

	Double getSubscriptionRevenue();

	Double getTotalRevenueByProvider(Integer providerId);

	Double getMenuitemRevenueByProvider(Integer providerId);

	Double getSubscriptionRevenueByProvider(Integer providerId);

	List<Object[]> getPastRevenueRecords(LocalDate date);

	List<Object[]> getPastRevenueByProvider(Integer providerId, LocalDate date);

	List<Object[]> getPastMenuRevenueByProvider(Integer providerId, LocalDate date);

	List<Object[]> getPastSubRevenueByProvider(Integer providerId, LocalDate date);

	Payment getPaymentInfo(Integer paymentId);

	List<Payment> getAllPayment();

	List<Payment> getAllPaymentOfUser(Integer userId);

	List<Payment> getAllTransactionOfProvider(Integer providerId);

	List<Payment> getAllMenuTransactionOfProvider(Integer providerId);

	List<Payment> getAllSubTransactionOfProvider(Integer providerId);
}
