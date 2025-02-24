package com.homeybites.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.MenuItem;
import com.homeybites.entities.TiffinPlan;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.MenuItemDto;
import com.homeybites.payloads.TiffinPlanDto;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.repositories.TiffinplanRepository;
import com.homeybites.services.TiffinPlanService;

@Service
public class TiffinPlanServiceImpl implements TiffinPlanService {

	@Autowired
	private TiffinplanRepository tiffinplanRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TiffinPlanDto addTiffinPlan(TiffinPlanDto tiffinPlanDto) {
		TiffinPlan tiffinPlan = this.modelMapper.map(tiffinPlanDto, TiffinPlan.class);
		tiffinPlan.setCreatedAt(LocalDateTime.now());
		TiffinPlan save = this.tiffinplanRepository.save(tiffinPlan);
		return this.modelMapper.map(save, TiffinPlanDto.class);
	}

	@Override
	public TiffinPlanDto addMenuItems(Integer planId, List<Integer> menuIds) {

		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		List<MenuItem> allMenuitems = this.menuItemRepository.findAllById(menuIds);
		tiffinPlan.getMenuItems().addAll(allMenuitems);

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

		List<MenuItemDto> menuItems = tiffinPlanDto.getMenuItems();
		List<MenuItem> allMenuitems = menuItems.stream().map(menuItem -> this.modelMapper.map(menuItem, MenuItem.class))
				.collect(Collectors.toList());

		tiffinPlan.getMenuItems().addAll(allMenuitems);

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

	@Override
	public TiffinPlanDto deleteMenuItemFromPlan(Integer planId, Integer menuId) {
		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		MenuItem menuItem = this.menuItemRepository.findById(menuId)
				.orElseThrow(() -> new ResourceNotFoundException("Menuitem", "Id", menuId));

		tiffinPlan.getMenuItems().remove(menuItem);
		TiffinPlan save = this.tiffinplanRepository.save(tiffinPlan);

		return this.modelMapper.map(save, TiffinPlanDto.class);

	}
}
