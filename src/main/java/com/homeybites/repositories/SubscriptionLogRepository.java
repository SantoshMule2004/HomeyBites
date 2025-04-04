package com.homeybites.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.Log.SubscriptionLog;

public interface SubscriptionLogRepository extends JpaRepository<SubscriptionLog, Integer>{

}
