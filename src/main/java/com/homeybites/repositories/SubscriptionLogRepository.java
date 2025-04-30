package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.User;
import com.homeybites.entities.Log.SubscriptionLog;

public interface SubscriptionLogRepository extends JpaRepository<SubscriptionLog, Integer>{
	
	List<SubscriptionLog> findByUser(User user);
}
