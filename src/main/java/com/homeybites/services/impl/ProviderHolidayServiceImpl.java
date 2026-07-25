package com.homeybites.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.homeybites.entities.ProviderHoliday;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.ProviderHolidayDTO;
import com.homeybites.repositories.ProviderHolidayRepository;
import com.homeybites.services.ProviderHolidayService;

import jakarta.transaction.Transactional;

@Service
public class ProviderHolidayServiceImpl implements ProviderHolidayService {

	private final ProviderHolidayRepository holidayRepository;

	public ProviderHolidayServiceImpl(ProviderHolidayRepository holidayRepository) {
		this.holidayRepository = holidayRepository;
	}

	@Override
	@Transactional
	public ProviderHoliday addHoliday(Long providerId, ProviderHolidayDTO req) {
		LocalDate today = LocalDate.now();

		if (req.getClosedDate().isBefore(today)) {
			throw new IllegalArgumentException("Cannot add a holiday in the past.");
		}

		if (holidayRepository.existsByProviderIdAndClosedDate(providerId, req.getClosedDate())) {
			throw new IllegalArgumentException("You have already marked this date as closed.");
		}

		ProviderHoliday holiday = new ProviderHoliday();
		holiday.setProviderId(providerId);
		holiday.setClosedDate(req.getClosedDate());
		holiday.setName(req.getName());
		holiday.setIsActive(req.getIsActive());
		holiday.setDescription(req.getDescription());

		return holidayRepository.save(holiday);
	}

	@Override
	@Transactional
	public void removeHoliday(Long providerId, Long holidayId) {
		ProviderHoliday holiday = this.holidayRepository.findById(holidayId)
				.orElseThrow(() -> new ResourceNotFoundException("Holiday", "id", holidayId));

		// Security Check: Only the owner can delete their holiday
		if (!holiday.getProviderId().equals(providerId)) {
			throw new SecurityException("Unauthorized to remove this holiday.");
		}

		if (holiday.getClosedDate().isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Cannot remove a past holiday.");
		}

		holidayRepository.delete(holiday);
	}

	@Override
	public List<ProviderHoliday> getUpcomingHolidays(Long providerId, Pageable pageable) {
		return holidayRepository.findByProviderIdAndClosedDateGreaterThanEqualOrderByClosedDateAsc(providerId,
				LocalDate.now(), pageable).getContent();
	}

	@Override
	public PageResponse<ProviderHoliday> getAllHolidays(Long providerId, Pageable pageable) {
		return new PageResponse<>(holidayRepository.findByProviderIdOrderByClosedDateAsc(providerId, pageable));
	}

	@Override
	public void updateHoliday(Long providerId, Long holidayId, ProviderHolidayDTO req) {
		ProviderHoliday holiday = this.holidayRepository.findById(holidayId)
				.orElseThrow(() -> new ResourceNotFoundException("Holiday", "id", holidayId));

		// Security Check: Only the owner can delete their holiday
		if (!holiday.getProviderId().equals(providerId)) {
			throw new SecurityException("Unauthorized to remove this holiday.");
		}

//		holiday.setClosedDate(req.getClosedDate());
		holiday.setDescription(req.getDescription());
		holiday.setName(req.getName());
		holiday.setIsActive(req.getIsActive());

		this.holidayRepository.save(holiday);
	}

	@Override
	public void toggleHolidayStatus(Long providerId, Long holidayId, boolean isActive) {
		ProviderHoliday holiday = this.holidayRepository.findById(holidayId)
				.orElseThrow(() -> new ResourceNotFoundException("Holiday", "id", holidayId));

		// Security Check: Only the owner can delete their holiday
		if (!holiday.getProviderId().equals(providerId)) {
			throw new SecurityException("Unauthorized to remove this holiday.");
		}

		holiday.setIsActive(isActive);

		this.holidayRepository.save(holiday);
	}

	@Override
	public List<ProviderHoliday> getRecentUpcomingHolidays(Long providerId) {
		return holidayRepository.findByProviderIdAndClosedDateGreaterThanEqualOrderByClosedDateAsc(providerId,
				LocalDate.now(), PageRequest.of(0, 5)).getContent();
	}

}
