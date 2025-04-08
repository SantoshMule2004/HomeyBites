package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.Subscription;
import com.homeybites.entities.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{

	List<Subscription> findByUser(User user);
	
	Optional<Subscription> findByPlanId(Integer planId);
	
	List<Subscription> findByTiffinPlan_User(User user);
	
	List<Subscription> findByTiffinPlanLog_User(User user);
}
