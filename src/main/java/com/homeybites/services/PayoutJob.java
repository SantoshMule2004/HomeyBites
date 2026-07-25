package com.homeybites.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.homeybites.repositories.DailyDeliveryRepository;

import jakarta.transaction.Transactional;

@Service
public class PayoutJob {
	private final DailyDeliveryRepository deliveryRepository;

	public PayoutJob(DailyDeliveryRepository deliveryRepository) {
		this.deliveryRepository = deliveryRepository;
	}

//	// Edge Case 5: The Escrow Strategy - Runs every Sunday at Midnight
//	@Scheduled(cron = "0 0 0 * * SUN")
//	@Transactional
//	public void processWeeklyPayouts() {
//		LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
//
//		// Find how many successful deliveries each kitchen made this week
//		List<Object[]> payoutData = deliveryRepository.countSuccessfulDeliveriesByProvider(sevenDaysAgo);
//
//		for (Object[] row : payoutData) {
//			Long providerId = (Long) row[0];
//			Long successfulDeliveriesCount = (Long) row[1];
//
//			// Example: Platform pays ₹90 per successful delivery (Assuming ₹10 commission)
//			BigDecimal earnedAmount = new BigDecimal(successfulDeliveriesCount).multiply(new BigDecimal("90.00"));
//
//			// TODO: Trigger Stripe Connect / Razorpay Route API to transfer 'earnedAmount'
//			// to 'providerId' bank account.
//			System.out.println("Initiating transfer of ₹" + earnedAmount + " to Provider " + providerId);
//		}
//	}
}