package com.homeybites.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Subscription;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.SubscriptionDto;
import com.homeybites.repositories.SubscriptionRepository;
import com.homeybites.services.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<SubscriptionDto> getAllSubscriptions() {
		List<Subscription> all = this.subscriptionRepository.findAll();
		List<SubscriptionDto> allPlans = all.stream().map(sub -> this.modelMapper.map(sub, SubscriptionDto.class))
				.collect(Collectors.toList());
		return allPlans;
	}

	@Override
	public SubscriptionDto getSubscription(Integer subId) {
		Subscription subscription = this.subscriptionRepository.findById(subId)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "Id", subId));
		return this.modelMapper.map(subscription, SubscriptionDto.class);
	}

}
