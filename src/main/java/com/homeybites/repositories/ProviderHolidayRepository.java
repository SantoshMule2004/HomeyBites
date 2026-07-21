package com.homeybites.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.ProviderHoliday;

public interface ProviderHolidayRepository extends JpaRepository<ProviderHoliday, Long> {

//	long countByProviderIdAndClosedDate(Long providerId, LocalDate date);

	// Check if a specific holiday already exists to prevent duplicates
	boolean existsByProviderIdAndClosedDate(Long providerId, LocalDate closedDate);

	long countByProviderIdAndClosedDateAndIsActiveTrue(Long providerId, LocalDate closedDate);

	// Fetch upcoming holidays for the dashboard
	Page<ProviderHoliday> findByProviderIdAndClosedDateGreaterThanEqualOrderByClosedDateAsc(Long providerId,
			LocalDate today, Pageable pageable);

	// Fetch all holidays for the dashboard
	List<ProviderHoliday> findByProviderIdOrderByClosedDateAsc(Long providerId);
}
