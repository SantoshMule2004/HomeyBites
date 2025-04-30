package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.MenuItem;
import com.homeybites.entities.TiffinDays;
import com.homeybites.entities.TiffinPlan;

public interface TiffindaysRepository extends JpaRepository<TiffinDays, Integer> {

	Optional<TiffinDays> findByTiffinPlanAndWeekDay(TiffinPlan plan, String day);
	
	List<TiffinDays> findByMenuItem(MenuItem menuItem);
}
