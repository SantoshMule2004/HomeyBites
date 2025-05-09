package com.homeybites.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.OrderInfo;
import com.homeybites.entities.Payment;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.repositories.OrderRepository;
import com.homeybites.repositories.PaymentRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public Double getTotalRevenue() {
		Double revenue = this.paymentRepository.getTotalRevenue();

		if (revenue == null)
			return 0.0;

		return revenue;
	}

	@Override
	public Double getTotalRevenueByProvider(Integer providerId) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("Tiffin provider", "Id", providerId));

		Double revenueByMenuItem = this.paymentRepository.getTotalMenuItemRevenueByProvider(providerId);
		Double RevenueBySub = this.paymentRepository.getTotalSubscriptionRevenueByProvider(providerId);

		if (revenueByMenuItem == null && RevenueBySub == null)
			return 0.0;

		if (revenueByMenuItem == null)
			return RevenueBySub;

		if (RevenueBySub == null)
			return revenueByMenuItem;

		return revenueByMenuItem + RevenueBySub;
	}

	@Override
	public Double getMenuitemRevenueByProvider(Integer providerId) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("Tiffin provider", "Id", providerId));

		Double revenueByProvider = this.paymentRepository.getTotalMenuItemRevenueByProvider(providerId);

		if (revenueByProvider == null)
			return 0.0;

		return revenueByProvider;
	}

	@Override
	public Double getSubscriptionRevenueByProvider(Integer providerId) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("Tiffin provider", "Id", providerId));

		Double revenueByProvider = this.paymentRepository.getTotalSubscriptionRevenueByProvider(providerId);

		if (revenueByProvider == null)
			return 0.0;

		return revenueByProvider;
	}

	@Override
	public List<Object[]> getPastRevenueRecords(LocalDate date) {
		return this.paymentRepository.getMonthlyRevenueLastSixMonths(date);
	}

	@Override
	public List<Object[]> getPastMenuRevenueByProvider(Integer providerId, LocalDate date) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("Tiffin provider", "Id", providerId));

		return this.paymentRepository.getMonthlyMenuItemRevenueLastSixMonthsByProvider(providerId, date);
	}

	@Override
	public List<Object[]> getPastSubRevenueByProvider(Integer providerId, LocalDate date) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("Tiffin provider", "Id", providerId));

		return this.paymentRepository.getMonthlySubscriptionRevenueLastSixMonthsByProvider(providerId, date);
	}

	@Override
	public Payment addPayment(Payment payment, Integer userId, Integer orderId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		OrderInfo order = this.orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));

		payment.setUser(user);
		payment.setPaymentDate(LocalDate.now());
		Payment save = this.paymentRepository.save(payment);

		order.setPayment(save);
		this.orderRepository.save(order);

		return save;
	}

	@Override
	public Payment getPaymentInfo(Integer paymentId) {
		return this.paymentRepository.findById(paymentId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment", "Id", paymentId));
	}

	@Override
	public List<Payment> getAllPayment() {
		return this.paymentRepository.findAll();
	}

	@Override
	public List<Payment> getAllPaymentOfUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return this.paymentRepository.findByUser(user);
	}

	@Override
	public List<Payment> getAllTransactionOfProvider(Integer providerId) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));

		List<Payment> menuTransaction = this.paymentRepository.getMenuTransaction(providerId);
		List<Payment> subTransaction = this.paymentRepository.getSubTransaction(providerId);

		menuTransaction.addAll(subTransaction);
		return menuTransaction;
	}

	@Override
	public List<Payment> getAllMenuTransactionOfProvider(Integer providerId) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));
		return this.paymentRepository.getMenuTransaction(providerId);
	}

	@Override
	public List<Payment> getAllSubTransactionOfProvider(Integer providerId) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));
		return this.paymentRepository.getSubTransaction(providerId);
	}

	@Override
	public List<Object[]> getPastRevenueByProvider(Integer providerId, LocalDate date) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("Tiffin provider", "Id", providerId));

		List<Object[]> menuRevenue = this.paymentRepository.getMonthlyMenuItemRevenueLastSixMonthsByProvider(providerId,
				date);
		List<Object[]> subRevenue = this.paymentRepository
				.getMonthlySubscriptionRevenueLastSixMonthsByProvider(providerId, date);
		menuRevenue.addAll(subRevenue);

		return menuRevenue;
	}

	@Override
	public Double getMenuitemRevenue() {
		Double revenue = this.paymentRepository.getTotalMenuItemRevenue();
		if (revenue == null)
			return 0.0;

		return revenue;
	}

	@Override
	public Double getSubscriptionRevenue() {
		Double revenue = this.paymentRepository.getTotalSubscriptionRevenue();

		if (revenue == null)
			return 0.0;

		return revenue;
	}
}
