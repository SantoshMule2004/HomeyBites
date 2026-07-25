package com.homeybites.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeybites.entities.Payment;
import com.homeybites.exceptions.BadRequestException;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PaymentDetailsProjection;
import com.homeybites.payloads.PaymentFilterRequest;
import com.homeybites.payloads.PaymentHistoryProjection;
import com.homeybites.payloads.PaymentStatus;
import com.homeybites.payloads.RevenueChartProjection;
import com.homeybites.payloads.RevenueGroupBy;
import com.homeybites.payloads.RevenueSummaryProjection;
import com.homeybites.payloads.UpdatePaymentStatusDto;
import com.homeybites.repositories.PaymentRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public PageResponse<PaymentHistoryProjection> getCustomerPayments(Long customerId, PaymentFilterRequest filter,
			Pageable pageable) {
		filter = this.verifyDateFilter(filter);

		LocalDateTime startDateTime = filter.getStartDate().atStartOfDay();

		LocalDateTime endDateTime = filter.getEndDate().atTime(23, 59, 59);

		String paymentStatus = filter.getPaymentStatus() != null ? filter.getPaymentStatus().name() : null;

		Page<PaymentHistoryProjection> page = paymentRepository.findCustomerPayments(customerId, filter.getSearch(),
				filter.getPaymentMethod(), paymentStatus, startDateTime, endDateTime, pageable);

		return new PageResponse<>(page);
	}

	@Override
	public PageResponse<PaymentHistoryProjection> getProviderPayments(Long providerId, PaymentFilterRequest filter,
			Pageable pageable) {
		filter = this.verifyDateFilter(filter);

		LocalDateTime startDateTime = filter.getStartDate().atStartOfDay();

		LocalDateTime endDateTime = filter.getEndDate().atTime(23, 59, 59);

		String paymentStatus = filter.getPaymentStatus() != null ? filter.getPaymentStatus().name() : null;

		Page<PaymentHistoryProjection> page = paymentRepository.findProviderPayments(providerId, filter.getSearch(),
				filter.getPaymentMethod(), paymentStatus, startDateTime, endDateTime, pageable);

		return new PageResponse<>(page);
	}

	@Override
	public PageResponse<PaymentHistoryProjection> getPayments(PaymentFilterRequest filter, Pageable pageable) {

		filter = this.verifyDateFilter(filter);

		LocalDateTime startDateTime = filter.getStartDate().atStartOfDay();

		LocalDateTime endDateTime = filter.getEndDate().atTime(23, 59, 59);

		String paymentStatus = filter.getPaymentStatus() != null ? filter.getPaymentStatus().name() : null;

		Page<PaymentHistoryProjection> page = paymentRepository.findPayments(filter.getSearch(),
				filter.getPaymentMethod(), paymentStatus, startDateTime, endDateTime, pageable);

		return new PageResponse<>(page);
	}

	@Override
	public PaymentDetailsProjection getPaymentDetails(Long paymentId) {
		PaymentDetailsProjection projection = paymentRepository.getPaymentDetails(paymentId);

		if (projection == null) {
			throw new ResourceNotFoundException("Payment", "id", paymentId);
		}

		return projection;
	}

	@Override
	public PaymentDetailsProjection getPaymentDetails(Long paymentId, Long id) {

		this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

		PaymentDetailsProjection projection = paymentRepository.getPaymentDetails(paymentId);

		if (projection == null) {
			throw new ResourceNotFoundException("Payment", "id", paymentId);
		}

		return projection;
	}

	@Override
	public RevenueSummaryProjection getProviderRevenueSummary(Long providerId, LocalDate startDate, LocalDate endDate) {
		LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;

		LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

		RevenueSummaryProjection projection = paymentRepository.getProviderRevenueSummary(providerId, startDateTime,
				endDateTime);

		return projection;
	}

	@Override
	public RevenueSummaryProjection getPlatformRevenueSummary(LocalDate startDate, LocalDate endDate) {
		LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;

		LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

		RevenueSummaryProjection projection = paymentRepository.getPlatformRevenueSummary(startDateTime, endDateTime);

		return projection;
	}

	@Override
	public List<RevenueChartProjection> getProviderRevenueChart(Long providerId, LocalDate startDate, LocalDate endDate,
			RevenueGroupBy groupBy) {

		LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;

		LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

		List<RevenueChartProjection> projections;

		switch (groupBy) {

		case DAY:
			projections = paymentRepository.getDailyRevenueChart(providerId, startDateTime, endDateTime);
			break;

		case WEEK:
			projections = paymentRepository.getWeeklyRevenueChart(providerId, startDateTime, endDateTime);
			break;

		case MONTH:
			projections = paymentRepository.getMonthlyRevenueChart(providerId, startDateTime, endDateTime);
			break;

		case YEAR:
			projections = paymentRepository.getYearlyRevenueChart(providerId, startDateTime, endDateTime);
			break;

		default:
			throw new IllegalArgumentException("Invalid Revenue Group");
		}

		return projections;
	}

	@Override
	public List<RevenueChartProjection> getPlatformRevenueChart(LocalDate startDate, LocalDate endDate,
			RevenueGroupBy groupBy) {

		LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;

		LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

		List<RevenueChartProjection> projections;

		switch (groupBy) {

		case DAY:
			projections = paymentRepository.getDailyRevenueChart(null, startDateTime, endDateTime);
			break;

		case WEEK:
			projections = paymentRepository.getWeeklyRevenueChart(null, startDateTime, endDateTime);
			break;

		case MONTH:
			projections = paymentRepository.getMonthlyRevenueChart(null, startDateTime, endDateTime);
			break;

		case YEAR:
			projections = paymentRepository.getYearlyRevenueChart(null, startDateTime, endDateTime);
			break;

		default:
			throw new IllegalArgumentException("Invalid Revenue Group");
		}

		return projections;
	}

	@Override
	@Transactional
	public void updatePaymentStatus(Long paymentId, UpdatePaymentStatusDto dto, Long providerId) {

		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment", "Id", paymentId));

		if (!providerId.equals(payment.getProviderId())) {
			throw new SecurityException("Unauthorized to update this payment.");
		}

		if (dto.getPaymentStatus() == PaymentStatus.FAILED
				&& (dto.getFailedReason() == null || dto.getFailedReason().isBlank())) {
			throw new IllegalArgumentException("Failure reason is required.");
		}

		switch (dto.getPaymentStatus()) {

		case PAID:
			payment.setPaymentStatus(PaymentStatus.PAID.name());
			payment.setPaidAt(LocalDateTime.now());
			payment.setFailureReason(null);
			break;

		case FAILED:
			payment.setPaymentStatus(PaymentStatus.FAILED.name());
			payment.setFailureReason(dto.getFailedReason());
			payment.setPaidAt(null);
			break;

		default:
			throw new IllegalArgumentException("Unsupported payment status: " + dto.getPaymentStatus());
		}

		paymentRepository.save(payment);
	}

	private PaymentFilterRequest verifyDateFilter(PaymentFilterRequest filter) {
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

}
