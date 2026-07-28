package com.homeybites.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.homeybites.entities.DailyDelivery;
import com.homeybites.entities.ProviderMenuItem;
import com.homeybites.entities.Subscription;
import com.homeybites.payloads.DailyDeliveryStatus;
import com.homeybites.payloads.MealType;
import com.homeybites.payloads.SubscriptionStatus;
import com.homeybites.repositories.DailyDeliveryRepository;
import com.homeybites.repositories.ProviderHolidayRepository;
import com.homeybites.repositories.ProviderMenuItemRepository;
import com.homeybites.repositories.SubscriptionRepository;

import jakarta.transaction.Transactional;

@Service
public class DeliveryGenerationJob {
	private final SubscriptionRepository subscriptionRepository;
	private final DailyDeliveryRepository deliveryRepository;
	private final ProviderHolidayRepository holidayRepository;
	private final ProviderMenuItemRepository providerMenuItemRepository;

	public DeliveryGenerationJob(SubscriptionRepository subRepo, DailyDeliveryRepository delRepo,
			ProviderHolidayRepository holRepo, ProviderMenuItemRepository providerMenuItemRepo) {
		this.subscriptionRepository = subRepo;
		this.deliveryRepository = delRepo;
		this.holidayRepository = holRepo;
		this.providerMenuItemRepository = providerMenuItemRepo;
	}

	// Runs every night at 12:05 AM
	@Scheduled(cron = "0 5 0 * * ?")
	@Transactional
	public void generateDailyDeliveries() {
		LocalDate today = LocalDate.now();

		List<Subscription> activeSubscriptions = subscriptionRepository
				.findByStatusAndCurrentEndDateGreaterThanEqual(SubscriptionStatus.ACTIVE, today);

		System.out.println("Subscriptions: " + activeSubscriptions);

		for (Subscription sub : activeSubscriptions) {

			System.out.println("Current Processing Subscription: " + sub);

			// Edge Case 2: Skip generation if the Provider is on Holiday today
			long isHoliday = holidayRepository.countByProviderIdAndClosedDateAndIsActiveTrue(sub.getProviderId(),
					today);
			if (isHoliday > 0) {
				// Silently push the user's end date forward to compensate for the kitchen
				// closure
				sub.setCurrentEndDate(sub.getCurrentEndDate().plusDays(1));
				subscriptionRepository.save(sub);
				continue; // Skip creating deliveries today
			}

			// SIMPLIFIED: Just blindly dispatch to the one address!
			if (sub.isIncludesBreakfast())
				saveDeliveryRow(sub, MealType.BREAKFAST, today);
			if (sub.isIncludesLunch())
				saveDeliveryRow(sub, MealType.LUNCH, today);
			if (sub.isIncludesDinner())
				saveDeliveryRow(sub, MealType.DINNER, today);
		}
	}

	private void saveDeliveryRow(Subscription sub, MealType mealType, LocalDate date) {

		System.out.println("Saving delivery for: " + sub.getReceiverName());

		String dayOfWeek = date.getDayOfWeek().name(); // MONDAY

		Optional<ProviderMenuItem> itemOpt = this.providerMenuItemRepository.findMenuForDelivery(sub.getProviderId(),
				dayOfWeek, mealType.name());

		if (itemOpt.isEmpty()) {
			throw new RuntimeException(
					"No menu found for provider=" + sub.getProviderId() + ", day=" + dayOfWeek + ", meal=" + mealType);
		}

		ProviderMenuItem item = itemOpt.get();

		DailyDelivery delivery = new DailyDelivery();
		delivery.setSubscriptionId(sub.getId());
		delivery.setProviderId(sub.getProviderId());
		delivery.setUserId(sub.getUserId());
		delivery.setMealType(mealType);
		delivery.setFoodItems(item.getFoodItems());
		delivery.setDeliveryDate(date);
		delivery.setDeliveryAddress(sub.getDeliveryAddress());
		delivery.setReceiverName(sub.getReceiverName());
		delivery.setReceiverContactNo(sub.getReceiverContactNo());
		delivery.setStatus(DailyDeliveryStatus.PENDING);

		deliveryRepository.save(delivery);
	}
}
