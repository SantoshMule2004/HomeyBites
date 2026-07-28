package com.homeybites.services;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Subscription;
import com.homeybites.payloads.SubscriptionStatus;
import com.homeybites.repositories.SubscriptionRepository;
import com.homeybites.repositories.TiffinplanRepository;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionExpirationJob {
	private final SubscriptionRepository subscriptionRepository;
    private final TiffinplanRepository planRepository;

    public SubscriptionExpirationJob(SubscriptionRepository subRepo, TiffinplanRepository planRepo) {
        this.subscriptionRepository = subRepo;
        this.planRepository = planRepo;
    }

    // Runs every night at 12:10 AM (Just after the deliveries are generated)
    @Scheduled(cron = "0 10 0 * * ?")
    @Transactional
    public void processExpiredSubscriptions() {
        LocalDate today = LocalDate.now();
        
        System.out.println("process Expired Subscriptions");
        
        // Find all ACTIVE or PAUSED subscriptions whose end date was yesterday or earlier
        List<Subscription> expiredSubscriptions = subscriptionRepository
            .findByStatusInAndCurrentEndDateLessThan(Arrays.asList(SubscriptionStatus.ACTIVE), today);

        for (Subscription sub : expiredSubscriptions) {
            // 1. Mark as completed
            sub.setStatus(SubscriptionStatus.COMPLETED);
            subscriptionRepository.save(sub);
            
            // 2. Free up the inventory capacity for the kitchen!
            planRepository.decrementSubscribers(sub.getPlanId());
        }
        
        if (!expiredSubscriptions.isEmpty()) {
            System.out.println("Cleaned up " + expiredSubscriptions.size() + " expired subscriptions.");
        }
    }
}
