package com.homeybites.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Payment;
import com.homeybites.entities.Subscription;
import com.homeybites.exceptions.BadRequestException;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PaymentStatus;
import com.homeybites.payloads.SubscriptionFilterRequest;
import com.homeybites.payloads.SubscriptionStatus;
import com.homeybites.payloads.SubscriptionWithUserProjection;
import com.homeybites.repositories.PaymentRepository;
import com.homeybites.repositories.SubscriptionRepository;
import com.homeybites.repositories.TiffinplanRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.SubscriptionManagementService;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionManagementServiceImpl implements SubscriptionManagementService {

	private final SubscriptionRepository subscriptionRepository;
	private final TiffinplanRepository planRepository;
	private final UserRepository userRepository;
	private final PaymentRepository paymentRepository;
	private static final int MAX_ALLOWED_PAUSES = 5;

	private static final LocalTime CUT_OFF_TIME = LocalTime.of(20, 0);

	public SubscriptionManagementServiceImpl(SubscriptionRepository subRepo, TiffinplanRepository planRepo,
			UserRepository userRepo, PaymentRepository paymentRepo) {
		this.subscriptionRepository = subRepo;
		this.planRepository = planRepo;
		this.userRepository = userRepo;
		this.paymentRepository = paymentRepo;
	}

	// --- LOGISTICS ENGINE: Calculates when an action actually takes effect ---
	private LocalDate calculateEffectiveDate() {
		if (LocalTime.now().isBefore(CUT_OFF_TIME)) {
			return LocalDate.now().plusDays(1); // Before 8 PM? Takes effect tomorrow.
		} else {
			return LocalDate.now().plusDays(2); // After 8 PM? Takes effect the day after tomorrow.
		}
	}

	@Override
	@Transactional
	public void pauseSubscription(Long subscriptionId, int requestedPauseDays) {
		Subscription sub = subscriptionRepository.findById(subscriptionId).orElseThrow();

		if (sub.getTotalPausedDays() + requestedPauseDays > MAX_ALLOWED_PAUSES) {
			throw new IllegalArgumentException("Exceeded maximum allowed pauses.");
		}

		LocalDate effectiveStartDate = calculateEffectiveDate();

		// 1. Schedule the dates (DO NOT change status to PAUSED yet!)
		sub.setPauseStartDate(effectiveStartDate);
		sub.setAutoResumeDate(effectiveStartDate.plusDays(requestedPauseDays));

//		sub.setStatus(SubscriptionStatus.PAUSED);
		sub.setTotalPausedDays(sub.getTotalPausedDays() + requestedPauseDays);
		// Push the end date forward so they don't lose money
		sub.setCurrentEndDate(sub.getCurrentEndDate().plusDays(requestedPauseDays));

		subscriptionRepository.save(sub);
	}

	@Override
	@Transactional
	public void resumeSubscription(Long subscriptionId) {
		Subscription sub = subscriptionRepository.findById(subscriptionId).orElseThrow();

		if (sub.getAutoResumeDate() == null) {
			throw new IllegalStateException("Subscription is not paused or scheduled to pause.");
		}

		// Calculate when they will actually get their first meal
		LocalDate effectiveResumeDate = calculateEffectiveDate();

		// Calculate how many days they DID NOT use from their requested pause
		long unusedDays = ChronoUnit.DAYS.between(effectiveResumeDate, sub.getAutoResumeDate());

		if (unusedDays > 0) {
			// Claw back the unused days from their limits and pull end date backward
			sub.setTotalPausedDays(sub.getTotalPausedDays() - (int) unusedDays);
			sub.setCurrentEndDate(sub.getCurrentEndDate().minusDays(unusedDays));
		}

		// Overwrite the resume date. We DO NOT change status to ACTIVE yet!
		sub.setAutoResumeDate(effectiveResumeDate);

		// Edge Case: If they paused but decided to cancel the pause before it even
		// started
		if (effectiveResumeDate.isBefore(sub.getPauseStartDate())
				|| effectiveResumeDate.isEqual(sub.getPauseStartDate())) {
			sub.setPauseStartDate(null);
			sub.setAutoResumeDate(null);
		}

		subscriptionRepository.save(sub);
	}

	@Override
	@Transactional
	public void cancelSubscription(Long subscriptionId) {

		Subscription subscription = subscriptionRepository.findById(subscriptionId)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "id", subscriptionId));

		if (subscription.getStatus() == SubscriptionStatus.CANCELLED) {
			throw new BadRequestException("Subscription is already cancelled.");
		}

		Payment payment = paymentRepository
				.findFirstBySubscriptionIdAndPaymentStatusOrderByPaidAtDesc(subscriptionId, PaymentStatus.PAID.name())
				.orElseThrow(() -> new ResourceNotFoundException("Payment", "subscriptionId", subscriptionId));

		BigDecimal refundAmount = this.calculateRefundAmount(subscription, payment);

		/*
		 * TODO: Call Razorpay Refund API
		 *
		 * String refundId = razorpayService.refund( payment.getGatewayPaymentId(),
		 * refundAmount);
		 */

		payment.setRefundedAmount(refundAmount);
		payment.setRefundedAt(LocalDateTime.now());

		// payment.setRefundTransactionId(refundId);

		if (refundAmount.compareTo(BigDecimal.ZERO) == 0) {
			payment.setPaymentStatus(PaymentStatus.PAID.name());
		} else if (refundAmount.compareTo(payment.getAmount()) == 0) {
			payment.setPaymentStatus(PaymentStatus.REFUNDED.name());
		} else {
			payment.setPaymentStatus(PaymentStatus.PARTIALLY_REFUNDED.name());
		}

		paymentRepository.save(payment);

		subscription.setStatus(SubscriptionStatus.CANCELLED);
		subscriptionRepository.save(subscription);

		planRepository.decrementSubscribers(subscription.getPlanId());
	}

	@Override
	public Subscription getSubscription(Long subId) {
		return this.subscriptionRepository.findById(subId)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "Id", subId));
	}

	@Override
	public PageResponse<Subscription> getAllSubscriptionOfUser(Long userId, SubscriptionFilterRequest filter,
			Pageable pageable) {
		this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		Page<Subscription> page = this.subscriptionRepository.findSubscriptionsByUser(userId, filter.getStatus(),
				pageable);

		return new PageResponse<>(page);
	}

	@Override
	public Long getSubscriptionCount() {
		return this.subscriptionRepository.count();
	}

	@Override
	public Long getSubscriptionCountByProvider(Long providerId) {
		return this.subscriptionRepository.countByProviderId(providerId);
	}

	@Override
	public PageResponse<SubscriptionWithUserProjection> getSubscriptionsForProvider(Long providerId,
			SubscriptionFilterRequest filter, Pageable pageable) {

		Page<SubscriptionWithUserProjection> page = this.subscriptionRepository.findSubscriptions(providerId,
				filter.getStatus() != null ? filter.getStatus().name() : null, filter.getSearch(), pageable);

		return new PageResponse<>(page);
	}

	@Override
	public PageResponse<SubscriptionWithUserProjection> getSubscriptionsForAdmin(SubscriptionFilterRequest filter,
			Pageable pageable) {
		Page<SubscriptionWithUserProjection> page = this.subscriptionRepository.findSubscriptions(null,
				filter.getStatus() != null ? filter.getStatus().name() : null, filter.getSearch(), pageable);

		return new PageResponse<>(page);
	}

	public List<Subscription> getSubscriptionsByProviderAndStatus(Long providerId, SubscriptionStatus status) {
		return subscriptionRepository.findByProviderIdAndStatus(providerId, status);
	}

	// Historical View: Past customers for accounting/records
	public List<Subscription> getHistoricalSubscriptions(Long providerId) {
		List<Subscription> completed = subscriptionRepository.findByProviderIdAndStatus(providerId,
				SubscriptionStatus.COMPLETED);
		List<Subscription> cancelled = subscriptionRepository.findByProviderIdAndStatus(providerId,
				SubscriptionStatus.CANCELLED);

		List<Subscription> history = new ArrayList<>(completed);
		history.addAll(cancelled);

		// Sort by end date descending (most recently ended first)
		history.sort((s1, s2) -> s2.getCurrentEndDate().compareTo(s1.getCurrentEndDate()));
		return history;
	}

	private BigDecimal calculateRefundAmount(Subscription subscription, Payment payment) {

		long daysConsumed = ChronoUnit.DAYS.between(subscription.getStartDate(), LocalDate.now());

		if (daysConsumed < 0) {
			daysConsumed = 0;
		}

		BigDecimal dailyCost = BigDecimal.ZERO;

		if (subscription.isIncludesBreakfast()) {
			dailyCost = dailyCost.add(subscription.getBreakfastPrice());
		}

		if (subscription.isIncludesLunch()) {
			dailyCost = dailyCost.add(subscription.getLunchPrice());
		}

		if (subscription.isIncludesDinner()) {
			dailyCost = dailyCost.add(subscription.getDinnerPrice());
		}

		BigDecimal consumedAmount = dailyCost.multiply(BigDecimal.valueOf(daysConsumed));

		BigDecimal refundAmount = payment.getAmount().subtract(consumedAmount);

		return refundAmount.max(BigDecimal.ZERO);
	}
}
