package com.homeybites.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Subscription;
import com.homeybites.entities.TiffinPlan;
import com.homeybites.entities.User;
import com.homeybites.entities.Log.SubscriptionLog;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.repositories.SubscriptionLogRepository;
import com.homeybites.repositories.SubscriptionRepository;
import com.homeybites.repositories.TiffinplanRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private TiffinplanRepository tiffinplanRepository;

	@Autowired
	private SubscriptionLogRepository subscriptionLogRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Subscription addSubscriptionPlan(Subscription subscription, Integer userId, Integer planId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		List<Subscription> list = this.subscriptionRepository.findByUser(user);

		if (list.size() < 3) {
			subscription.setUser(user);
			subscription.setTiffinPlan(tiffinPlan);
			subscription.setCreatedAt(LocalDateTime.now());
			subscription
					.setPlanDuration(ChronoUnit.DAYS.between(subscription.getStartDate(), subscription.getEndDate()));
			subscription.setTotalPrice(subscription.getTiffinPlan().getPrice());

			return this.subscriptionRepository.save(subscription);
		} else {
			return null;
		}
	}

	@Override
	public Subscription getSubscription(Integer subId) {
		return this.subscriptionRepository.findByPlanId(subId)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "Id", subId));
	}

	@Override
	public List<Subscription> getAllSubscriptionOfUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.subscriptionRepository.findByUser(user);
	}

	@Override
	public List<SubscriptionLog> getAllSubscriptionHistoryOfUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.subscriptionLogRepository.findByUser(user);
	}

	@Override
	public List<Subscription> getAllSubscriptionOfTiffinProvider(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		List<Subscription> list = this.subscriptionRepository.findByTiffinPlan_User(user);

		List<Subscription> list2 = this.subscriptionRepository.findByTiffinPlanLog_User(user);

		if (!list.isEmpty())
			return list;
		else
			return list2;
	}

	@Override
	public List<Subscription> getAllSubscriptions() {
		return this.subscriptionRepository.findAll();
	}

	@Override
	public void deleteSubscriptionPlan(Integer subId) {
		Subscription subscription = this.subscriptionRepository.findByPlanId(subId)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "Id", subId));

		SubscriptionLog log = new SubscriptionLog();

		log.setPlanId(subId);
		log.setStartDate(subscription.getStartDate());
		log.setEndDate(subscription.getEndDate());
		log.setTotalPrice(subscription.getTotalPrice());
		log.setStatus("Deleted");
		log.setPlanDuration(subscription.getPlanDuration());
		log.setArchievedAt(LocalDateTime.now());
		log.setUser(subscription.getUser());
		log.setTiffinPlan(subscription.getTiffinPlan());
		log.setOrder(subscription.getOrder());
		log.setTiffinPlanLog(subscription.getTiffinPlanLog());
		log.setBreakFast(subscription.isBreakFast());
		log.setDinner(subscription.isDinner());
		log.setLunch(subscription.isLunch());
		log.setMonday(subscription.isMonday());
		log.setTuesday(subscription.isTuesday());
		log.setWednesday(subscription.isWednesday());
		log.setThursday(subscription.isThursday());
		log.setFriday(subscription.isFriday());
		log.setSaturday(subscription.isSaturday());
		log.setSunday(subscription.isSunday());

		SubscriptionLog subscriptionLog = this.subscriptionLogRepository.save(log);

		if (subscriptionLog != null) {
			this.subscriptionRepository.delete(subscription);
		}
	}

	@Override
	public void deleteSubscriptionPlanLog(Integer subId) {
		SubscriptionLog subscriptionLog = this.subscriptionLogRepository.findById(subId)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "Id", subId));

		this.subscriptionLogRepository.delete(subscriptionLog);
	}

	// A cron expression consists of 6 fields: second, minute, hour, day-of-month,
	// month, day-of-week
	@Override
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteExpiredPlans() {
		List<Subscription> all = this.subscriptionRepository.findAll();
		for (Subscription sub : all) {
			if (sub.getEndDate().isBefore(LocalDate.now()))
				deleteSubscriptionPlan(sub.getPlanId());
		}

	}

	@Override
	public Integer getSubscriptionCount(Integer providerId) {
		return this.subscriptionRepository.getSubscriptionCountByProvider(providerId);
	}

	@Override
	public Integer getAllSubscriptionCount() {
		return this.subscriptionRepository.getSubscriptionCount();
	}
}
