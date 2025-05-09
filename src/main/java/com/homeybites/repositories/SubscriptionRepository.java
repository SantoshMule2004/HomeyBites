package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.Subscription;
import com.homeybites.entities.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

	List<Subscription> findByUser(User user);

	Optional<Subscription> findByPlanId(Integer planId);

	List<Subscription> findByTiffinPlan_User(User user);

	List<Subscription> findByTiffinPlanLog_User(User user);

	@Query("SELECT COUNT(s) " + "FROM Subscription s " + "LEFT JOIN s.tiffinPlan tp "
			+ "LEFT JOIN s.tiffinPlanLog tpl "
			+ "WHERE tp.user.userId = :providerId OR tpl.user.userId = :providerId")
	int getSubscriptionCountByProvider(@Param("providerId") Integer providerId);

	@Query("SELECT COUNT(s) FROM Subscription s")
	int getSubscriptionCount();
}
