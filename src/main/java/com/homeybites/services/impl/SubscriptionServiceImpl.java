package com.homeybites.services.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Subscription;
import com.homeybites.entities.TiffinPlan;
import com.homeybites.entities.User;
import com.homeybites.entities.Log.SubscriptionLog;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.SubscriptionDto;
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

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public SubscriptionDto addSubscriptionPlan(SubscriptionDto subscriptionDto, Integer userId, Integer planId) {

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		List<Subscription> list = this.subscriptionRepository.findByUser(user);

		if (list.size() < 3) {
			Subscription subscription = this.modelMapper.map(subscriptionDto, Subscription.class);
			subscription.setUser(user);
			subscription.setTiffinPlan(tiffinPlan);
			subscription.setCreatedAt(LocalDateTime.now());
			subscription
					.setPlanDuration(ChronoUnit.DAYS.between(subscription.getStartDate(), subscription.getEndDate()));
			subscription.setTotalPrice(subscription.getTiffinPlan().getPrice() * subscription.getPlanDuration());

			Subscription save = this.subscriptionRepository.save(subscription);
			return this.modelMapper.map(save, SubscriptionDto.class);
		} else {
			return null;
		}
	}

	@Override
	public SubscriptionDto getSubscription(Integer subId) {
		Subscription subscription = this.subscriptionRepository.findByPlanId(subId)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "Id", subId));
		return this.modelMapper.map(subscription, SubscriptionDto.class);
	}

	@Override
	public List<SubscriptionDto> getAllSubscriptionOfUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		List<Subscription> list = this.subscriptionRepository.findByUser(user);

		List<SubscriptionDto> userPlans = list.stream().map(plan -> this.modelMapper.map(plan, SubscriptionDto.class))
				.collect(Collectors.toList());
		return userPlans;
	}

	@Override
	public List<SubscriptionDto> getAllSubscriptionOfTiffinProvider(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		List<Subscription> list = this.subscriptionRepository.findByTiffinPlan_User(user);
		List<SubscriptionDto> userPlans = list.stream().map(plan -> this.modelMapper.map(plan, SubscriptionDto.class))
				.collect(Collectors.toList());
		return userPlans;
	}

	@Override
	public List<SubscriptionDto> getAllSubscriptions() {
		List<Subscription> all = this.subscriptionRepository.findAll();
		List<SubscriptionDto> allPlans = all.stream().map(sub -> this.modelMapper.map(sub, SubscriptionDto.class))
				.collect(Collectors.toList());
		return allPlans;
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
}
