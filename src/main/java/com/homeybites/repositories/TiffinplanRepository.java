package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.TiffinPlan;
import com.homeybites.entities.User;

public interface TiffinplanRepository extends JpaRepository<TiffinPlan, Integer> {

	// get all tiffin plans of provider
	List<TiffinPlan> findByUser(User user);

	// to check if plan exist with this name
	boolean existsByPlanNameAndUser(String planName, User user);
}
