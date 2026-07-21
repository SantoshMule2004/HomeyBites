package com.homeybites.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

import com.homeybites.entities.Subscription;
import com.homeybites.payloads.SubscriptionStatus;
import com.homeybites.repositories.SubscriptionRepository;

import jakarta.transaction.Transactional;

public class SubscriptionStateTransitionJob {
	private final SubscriptionRepository subscriptionRepository;

	public SubscriptionStateTransitionJob(SubscriptionRepository subRepo) {
		this.subscriptionRepository = subRepo;
	}

	// Runs every night at 12:00 AM (Just BEFORE the deliveries are generated)
	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void processStateTransitions() {
		LocalDate today = LocalDate.now();

		// 1. ACTIVATE SCHEDULED PAUSES
		List<Subscription> subscriptionsToPause = subscriptionRepository.findByStatusAndPauseStartDate(SubscriptionStatus.ACTIVE, today);

		for (Subscription sub : subscriptionsToPause) {
			sub.setStatus(SubscriptionStatus.PAUSED);
			subscriptionRepository.save(sub);
		}

		// 2. ACTIVATE SCHEDULED RESUMES
		List<Subscription> subscriptionsToResume = subscriptionRepository
				.findByStatusAndAutoResumeDateLessThanEqual(SubscriptionStatus.PAUSED, today);

		for (Subscription sub : subscriptionsToResume) {
			sub.setStatus(SubscriptionStatus.ACTIVE);
			sub.setPauseStartDate(null);
			sub.setAutoResumeDate(null);
			subscriptionRepository.save(sub);
		}

		System.out.println("Transitioned " + subscriptionsToPause.size() + " to PAUSED and "
				+ subscriptionsToResume.size() + " to ACTIVE.");
	}
}
