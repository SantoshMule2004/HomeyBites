package com.homeybites.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.MenuItem;
import com.homeybites.entities.TiffinDays;
import com.homeybites.entities.TiffinPlan;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.TiffinPlanDto;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.repositories.TiffindaysRepository;
import com.homeybites.repositories.TiffinplanRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.TiffinPlanService;

@Service
public class TiffinPlanServiceImpl implements TiffinPlanService {

	@Autowired
	private TiffinplanRepository tiffinplanRepository;

	@Autowired
	private TiffindaysRepository tiffindaysRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TiffinPlanDto addTiffinPlan(TiffinPlanDto tiffinPlanDto, Integer providerId) {

		User provider = this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));

		TiffinPlan tiffinPlan = this.modelMapper.map(tiffinPlanDto, TiffinPlan.class);
		tiffinPlan.setUser(provider);
		tiffinPlan.setCreatedAt(LocalDateTime.now());

		List<TiffinDays> tiffinDays = tiffinPlan.getTiffinDays().stream().map(day -> {

			TiffinDays tiffin = new TiffinDays();
			tiffin.setWeekDay(day.getWeekDay());
			tiffin.setTiffinPlan(tiffinPlan);

			List<MenuItem> menuItems = this.menuItemRepository.findAllById(day.getMenuIds());
			tiffin.setMenuItem(menuItems);

			return tiffin;

		}).collect(Collectors.toList());

		tiffinPlan.setTiffinDays(tiffinDays);
		TiffinPlan save = this.tiffinplanRepository.save(tiffinPlan);

		return this.modelMapper.map(save, TiffinPlanDto.class);
	}

	@Override
	public TiffinPlanDto updateTiffinPlan(TiffinPlanDto tiffinPlanDto, Integer planId) {
		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		tiffinPlan.setPlanName(tiffinPlanDto.getPlanName());
		tiffinPlan.setPrice(tiffinPlanDto.getPrice());
		tiffinPlan.setAddOns(tiffinPlanDto.getAddOns());
		tiffinPlan.setActive(tiffinPlanDto.isActive());
		tiffinPlan.setPlanType(tiffinPlanDto.getPlanType());

		TiffinPlan save = this.tiffinplanRepository.save(tiffinPlan);
		return this.modelMapper.map(save, TiffinPlanDto.class);
	}

	@Override
	public TiffinPlanDto updateMenuItemOnDay(Integer planId, String day, Integer oldMenuId, Integer newMenuId) {
		
		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		TiffinDays tiffinDays = this.tiffindaysRepository.findByTiffinPlanAndWeekDay(tiffinPlan, day)
				.orElseThrow(() -> new ResourceNotFoundException(day, "plan Id", planId));

		List<MenuItem> menuItems = tiffinDays.getMenuItem();
		
		MenuItem oldItem = menuItems.stream().filter(menu -> menu.getMenuId().equals(oldMenuId)).findFirst().get();
		menuItems.remove(oldItem);
		
		MenuItem newMenuItem = this.menuItemRepository.findById(newMenuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", newMenuId));
		
		menuItems.add(newMenuItem);
		tiffinDays.setMenuItem(menuItems);
		
		tiffinPlan.getTiffinDays().add(tiffinDays);
		TiffinPlan save = this.tiffinplanRepository.save(tiffinPlan);
		
		return this.modelMapper.map(save, TiffinPlanDto.class);
	}

	@Override
	public TiffinPlanDto getTiffinPlan(Integer planId) {
		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));
		return this.modelMapper.map(tiffinPlan, TiffinPlanDto.class);
	}

	@Override
	public List<TiffinPlanDto> getAllTiffinPlansOfProvider(Integer providerId) {

		User provider = this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));

		List<TiffinPlan> list = this.tiffinplanRepository.findByUser(provider);

		List<TiffinPlanDto> listOfPlans = list.stream().map(plan -> this.modelMapper.map(plan, TiffinPlanDto.class))
				.collect(Collectors.toList());

		return listOfPlans;
	}

	@Override
	public List<TiffinPlanDto> getAllTiffinPlans() {
		List<TiffinPlan> all = this.tiffinplanRepository.findAll();
		List<TiffinPlanDto> allPlans = all.stream()
				.map(tiffinPlan -> this.modelMapper.map(tiffinPlan, TiffinPlanDto.class)).collect(Collectors.toList());
		return allPlans;
	}

	@Override
	public void deleteTiffinPlan(Integer planId) {
		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));
		this.tiffinplanRepository.delete(tiffinPlan);
	}
}
