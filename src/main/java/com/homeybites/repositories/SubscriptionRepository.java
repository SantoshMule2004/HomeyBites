package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.Subscription;
import com.homeybites.entities.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{

	List<Subscription> findByUser(User user);
}
