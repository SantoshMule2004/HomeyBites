package com.homeybites.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{

}
