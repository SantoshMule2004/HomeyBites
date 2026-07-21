package com.homeybites.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PaymentDetailsProjection;
import com.homeybites.payloads.PaymentFilterRequest;
import com.homeybites.payloads.PaymentHistoryProjection;
import com.homeybites.payloads.PaymentStatus;
import com.homeybites.payloads.RevenueChartProjection;
import com.homeybites.payloads.RevenueGroupBy;
import com.homeybites.payloads.RevenueSummaryProjection;
import com.homeybites.payloads.UpdatePaymentStatusDto;

public interface PaymentService {

	PageResponse<PaymentHistoryProjection> getCustomerPayments(Long customerId, PaymentFilterRequest filter,
			Pageable pageable);

	PageResponse<PaymentHistoryProjection> getProviderPayments(Long providerId, PaymentFilterRequest filter,
			Pageable pageable);

	PageResponse<PaymentHistoryProjection> getPayments(PaymentFilterRequest filter, Pageable pageable);

	PaymentDetailsProjection getPaymentDetails(Long paymentId);
	
	PaymentDetailsProjection getPaymentDetails(Long paymentId, Long id);

	RevenueSummaryProjection getProviderRevenueSummary(Long providerId, LocalDate startDate, LocalDate endDate);

	RevenueSummaryProjection getPlatformRevenueSummary(LocalDate startDate, LocalDate endDate);

	List<RevenueChartProjection> getProviderRevenueChart(Long providerId, LocalDate startDate, LocalDate endDate,
			RevenueGroupBy groupBy);

	List<RevenueChartProjection> getPlatformRevenueChart(LocalDate startDate, LocalDate endDate,
			RevenueGroupBy groupBy);
	
	void updatePaymentStatus(Long paymentId, UpdatePaymentStatusDto dto, Long providerId);
}
