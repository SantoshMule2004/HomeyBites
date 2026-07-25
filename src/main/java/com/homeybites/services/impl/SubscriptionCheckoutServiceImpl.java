package com.homeybites.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.homeybites.entities.Address;
import com.homeybites.entities.Payment;
import com.homeybites.entities.Subscription;
import com.homeybites.entities.TiffinPlan;
import com.homeybites.exceptions.BadRequestException;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.PaymentStatus;
import com.homeybites.payloads.PaymentType;
import com.homeybites.payloads.SubscriptionRequestDTO;
import com.homeybites.payloads.SubscriptionStatus;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.repositories.PaymentRepository;
import com.homeybites.repositories.ProviderHolidayRepository;
import com.homeybites.repositories.SubscriptionRepository;
import com.homeybites.repositories.TiffinplanRepository;
import com.homeybites.services.SubscriptionCheckoutService;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionCheckoutServiceImpl implements SubscriptionCheckoutService {
	private final TiffinplanRepository planRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final AddressRepository addressRepository;
	private final PaymentRepository paymentRepository;
	private final ProviderHolidayRepository holidayRepository;

	public SubscriptionCheckoutServiceImpl(TiffinplanRepository planRepo, SubscriptionRepository subRepo,
			AddressRepository addressRepo, PaymentRepository paymentRepo, ProviderHolidayRepository holidayRepo) {
		this.planRepository = planRepo;
		this.subscriptionRepository = subRepo;
		this.addressRepository = addressRepo;
		this.paymentRepository = paymentRepo;
		this.holidayRepository = holidayRepo;
	}

	@Override
	@Transactional
	public boolean processSubscriptionCheckout(Long userId, Long planId, SubscriptionRequestDTO req) {
		TiffinPlan plan = planRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "id", planId));

		LocalDate tomorrow = LocalDate.now().plusDays(1);

		if (req.getStartDate().isBefore(tomorrow)) {
			throw new BadRequestException("Subscription start date must be tomorrow or later.");
		}

		if (holidayRepository.existsByProviderIdAndClosedDateAndIsActiveTrue(plan.getProviderId(),
				req.getStartDate())) {

			throw new BadRequestException(
					"The provider is unavailable on the selected start date. Please choose another date.");
		}

		// Atomic Capacity Check using the custom SQL update
		int rowsUpdated = planRepository.incrementSubscribersIfCapacityAllows(planId);
		if (rowsUpdated == 0) {
			return false;
		}

		Address address = this.addressRepository.findByAddId(req.getDeliveryAddressId())
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", req.getDeliveryAddressId()));

		String deliveryAddress = address.getAddressLine() + ", " + address.getArea();

		// Calculate Pricing
		BigDecimal dailyCost = BigDecimal.ZERO;
		if (req.isWantBreakfast())
			dailyCost = dailyCost.add(plan.getPricePerBreakfast());
		if (req.isWantLunch())
			dailyCost = dailyCost.add(plan.getPricePerLunch());
		if (req.isWantDinner())
			dailyCost = dailyCost.add(plan.getPricePerDinner());

		BigDecimal totalAmount = dailyCost.multiply(new BigDecimal(plan.getValidityDays()));

		// TODO: Call Payment Gateway (Stripe/Razorpay) here. Assume Success for now.
		// If payment fails, decrease the subscriber count

		// Create the Living Contract
		Subscription sub = new Subscription();
		sub.setUserId(userId);
		sub.setPlanId(planId);
		sub.setProviderId(plan.getProviderId());
		sub.setPlanName(plan.getPlanName());
		sub.setValidityDays(plan.getValidityDays());
		sub.setIncludesBreakfast(req.isWantBreakfast());
		sub.setIncludesLunch(req.isWantLunch());
		sub.setIncludesDinner(req.isWantDinner());
		sub.setBreakfastPrice(plan.getPricePerBreakfast());
		sub.setLunchPrice(plan.getPricePerLunch());
		sub.setDinnerPrice(plan.getPricePerDinner());
//		sub.setAmountPaid(totalAmount);
		sub.setStartDate(req.getStartDate());
		sub.setCurrentEndDate(req.getStartDate().plusDays(plan.getValidityDays()));
		sub.setTotalPausedDays(0);
		sub.setStatus(SubscriptionStatus.ACTIVE);
		sub.setDeliveryAddress(deliveryAddress);
		sub.setReceiverName(address.getReceiverName());
		sub.setReceiverContactNo(address.getReceiverContactNo());

		Subscription savedSub = subscriptionRepository.save(sub);

		// create payment
		Payment payment = new Payment();

		payment.setSubscriptionId(savedSub.getId());
		payment.setPaymentType(PaymentType.SUBSCRIPTION.name());

		payment.setUserId(userId);
		payment.setProviderId(plan.getProviderId());

		payment.setAmount(totalAmount);
		payment.setTaxAmount(BigDecimal.ZERO);
		payment.setDiscountAmount(BigDecimal.ZERO);

		payment.setPaymentMethod("UPI");

		payment.setPaymentStatus(PaymentStatus.PAID.name());

		payment.setTransactionId(UUID.randomUUID().toString());

//		payment.setTransactionId(transactionId);

//		payment.setGatewayOrderId(gatewayOrderId);
//		payment.setGatewayPaymentId(gatewayPaymentId);

		payment.setPaidAt(LocalDateTime.now());

		paymentRepository.save(payment);
		return true;
	}

}
