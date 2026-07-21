package com.homeybites.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.homeybites.entities.ProviderHoliday;
import com.homeybites.payloads.ProviderHolidayDTO;

public interface ProviderHolidayService {
	ProviderHoliday addHoliday(Long providerId, ProviderHolidayDTO req);

	List<ProviderHoliday> getUpcomingHolidays(Long providerId, Pageable pageable);
	
	List<ProviderHoliday> getAllHolidays(Long providerId);
	
	void updateHoliday(Long providerId, Long holidayId, ProviderHolidayDTO req);
	
	void removeHoliday(Long providerId, Long holidayId);
	
	void toggleHolidayStatus(Long providerId, Long holidayId, boolean isActive);
	
	List<ProviderHoliday> getRecentUpcomingHolidays(Long providerId);
}
