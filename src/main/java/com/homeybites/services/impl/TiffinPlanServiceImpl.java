package com.homeybites.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.MenuItem;
import com.homeybites.entities.Subscription;
import com.homeybites.entities.TiffinDays;
import com.homeybites.entities.TiffinPlan;
import com.homeybites.entities.User;
import com.homeybites.entities.Log.TiffinPlanLog;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.UpdateMenuItemDto;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.repositories.SubscriptionRepository;
import com.homeybites.repositories.TiffinPlanLogRepository;
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
	private TiffinPlanLogRepository tiffinPlanLogRepository;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Override
	public TiffinPlan addTiffinPlan(TiffinPlan tiffinPlan, Integer providerId) {

		User provider = this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));

		tiffinPlan.setUser(provider);
		tiffinPlan.setCreatedAt(LocalDateTime.now());

		List<TiffinDays> tiffinDays = tiffinPlan.getTiffinDays().stream().map(day -> {

			TiffinDays tiffin = new TiffinDays();
			tiffin.setWeekDay(day.getWeekDay());
			tiffin.setTiffinPlan(tiffinPlan);

			List<MenuItem> menuItems = new ArrayList<>();
			day.getMenuIds().forEach(menuId -> {
				MenuItem newMenuItem = this.menuItemRepository.findById(menuId)
						.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));
				menuItems.add(newMenuItem);
			});
//			List<MenuItem> menuItems = this.menuItemRepository.findAllById(day.getMenuIds());
			tiffin.setMenuItem(menuItems);

			return tiffin;

		}).collect(Collectors.toList());

		tiffinPlan.setTiffinDays(tiffinDays);
		return this.tiffinplanRepository.save(tiffinPlan);
	}

	@Override
	public TiffinPlan updateTiffinPlan(TiffinPlan planDto, Integer planId) {
		TiffinPlan existingTiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		// deleting plan and saving in log table
		deleteTiffinPlan(planId);

		// creating new plan for updated data and saving to db
		TiffinPlan tiffinPlan = new TiffinPlan();

		tiffinPlan.setPlanName(planDto.getPlanName());
		tiffinPlan.setPrice(planDto.getPrice());
		tiffinPlan.setAddOns(planDto.getAddOns());
		tiffinPlan.setActive(planDto.isActive());
		tiffinPlan.setPlanType(planDto.getPlanType());
		tiffinPlan.setUser(existingTiffinPlan.getUser());
		tiffinPlan.setCreatedAt(LocalDateTime.now());

		TiffinPlan plan = this.tiffinplanRepository.save(tiffinPlan);

		// copying tiffin plan from existing plan
		List<TiffinDays> newTiffinDays = new ArrayList<>();
		for (TiffinDays oldDay : existingTiffinPlan.getTiffinDays()) {
			TiffinDays newDay = new TiffinDays();

			newDay.setWeekDay(oldDay.getWeekDay());
			newDay.setTiffinPlan(plan);

			if (newDay.getMenuItem() == null)
				newDay.setMenuItem(new ArrayList<>());

			newDay.getMenuItem().addAll(oldDay.getMenuItem());

			newTiffinDays.add(newDay);
		}

		tiffindaysRepository.saveAll(newTiffinDays);

		return plan;
	}

	@Override
	public TiffinPlan updateMenuItemOnDay(Integer planId, String day, UpdateMenuItemDto updateMenuItemDto) {
		System.out.println("finding plan");
		TiffinPlan existingTiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		// creating new plan for updated data
		System.out.println("creating new plan");
		TiffinPlan tiffinPlan = new TiffinPlan();
		tiffinPlan.setPlanName(existingTiffinPlan.getPlanName());
		tiffinPlan.setPrice(existingTiffinPlan.getPrice());
		tiffinPlan.setAddOns(existingTiffinPlan.getAddOns());
		tiffinPlan.setActive(true);
		tiffinPlan.setPlanType(existingTiffinPlan.getPlanType());
		tiffinPlan.setUser(existingTiffinPlan.getUser());
		tiffinPlan.setCreatedAt(LocalDateTime.now());

		System.out.println("saving new plan");
		TiffinPlan plan = this.tiffinplanRepository.save(tiffinPlan);
		this.tiffinplanRepository.flush();

		List<TiffinDays> newTiffinDays = new ArrayList<>();
		for (TiffinDays oldDay : existingTiffinPlan.getTiffinDays()) {
			TiffinDays newDay = new TiffinDays();
			System.out.println("creating weekdays");
			newDay.setWeekDay(oldDay.getWeekDay());
			newDay.setTiffinPlan(plan);

			if (newDay.getMenuItem() == null)
				newDay.setMenuItem(new ArrayList<>());

			newDay.getMenuItem().addAll(oldDay.getMenuItem());

			newTiffinDays.add(newDay);
		}
		tiffindaysRepository.saveAll(newTiffinDays);
		System.out.println("saving weekdays of plan");
		// deleting plan and saving in log table
		deleteTiffinPlan(planId);
		System.out.println("deleting plan");

		TiffinDays tiffinDays = this.tiffindaysRepository.findByTiffinPlanAndWeekDay(plan, day)
				.orElseThrow(() -> new ResourceNotFoundException(day, "plan Id", planId));

		// getting all menu items of a day
		List<MenuItem> menuItems = tiffinDays.getMenuItem();
		System.out.println("getting menuitems of tifin day");

		// finding old menu item and removing it
//		MenuItem oldItem = menuItems.stream().filter(menu -> menu.getMenuId().equals(oldId)).findFirst().get();
//		menuItems.remove(oldItem);

		// trying accessing old menuItems array
		for (Integer oldId : updateMenuItemDto.getOldIds()) {
			System.out.println("removing old menuitems");
			MenuItem oldItem = menuItems.stream().filter(menu -> menu.getMenuId().equals(oldId)).findFirst().get();
			menuItems.remove(oldItem);
		}

		// finding new menu item
		for (Integer newId : updateMenuItemDto.getNewIds()) {
			MenuItem newMenuItem = this.menuItemRepository.findById(newId)
					.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", newId));
			System.out.println("adding new menuitems");
			// adding new menu item to day
			menuItems.add(newMenuItem);
		}

		// adding new menu item to day
		tiffinDays.setMenuItem(menuItems);
		System.out.println("saving menuitems");

		// adding day to tiffin plan and saving tiffin plan
		plan.getTiffinDays().add(tiffinDays);
		System.out.println("saving plan");
		return this.tiffinplanRepository.save(plan);
	}

	@Override
	public TiffinPlan getTiffinPlan(Integer planId) {
		return this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));
	}

	@Override
	public List<TiffinPlan> getAllTiffinPlansOfProvider(Integer providerId) {

		User provider = this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));

		return this.tiffinplanRepository.findByUser(provider);
	}

	@Override
	public List<TiffinPlan> getAllTiffinPlans() {
		return this.tiffinplanRepository.findAll();
	}

	@Override
	public void deleteTiffinPlan(Integer planId) {
		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		// inserting tiffin plan info into log
		TiffinPlanLog log = new TiffinPlanLog();
		log.setTiffinPlanId(planId);
		log.setPlanName(tiffinPlan.getPlanName());
		log.setPrice(tiffinPlan.getPrice());
		log.setAddOns(tiffinPlan.getAddOns());
		log.setActive(true);
		log.setPlanType(tiffinPlan.getPlanType());
		log.setUser(tiffinPlan.getUser());
		log.setCreatedAt(tiffinPlan.getCreatedAt());
		log.setArchievedAt(LocalDateTime.now());

		// saving tiffin plan log
		TiffinPlanLog planLog = this.tiffinPlanLogRepository.save(log);

		// setting tiffin days
		List<TiffinDays> newTiffinDays = new ArrayList<>();
		for (TiffinDays oldDay : tiffinPlan.getTiffinDays()) {
			TiffinDays newDay = new TiffinDays();

			newDay.setWeekDay(oldDay.getWeekDay());
			newDay.setTiffinPlanLog(planLog);

			if (newDay.getMenuItem() == null)
				newDay.setMenuItem(new ArrayList<>());

			newDay.getMenuItem().addAll(oldDay.getMenuItem());

			newTiffinDays.add(newDay);
		}
//		newTiffinDays.forEach(day -> {
//			day.setTiffinPlanLog(planLog);
//		});

		// saving tiffin days
		tiffindaysRepository.saveAll(newTiffinDays);

//		tiffinPlan.getTiffinDays().clear();

		// setting subscription plan
		List<Subscription> list = tiffinPlan.getSubscription();
		list.forEach(sub -> {
			sub.setTiffinPlanLog(planLog);
			sub.setTiffinPlan(null);
		});

		// saving subscription
		this.subscriptionRepository.saveAll(list);

		// clearing subscription
		tiffinPlan.getSubscription().clear();
		tiffinPlan.getSubscriptionLog().clear();

		TiffinPlan save = this.tiffinplanRepository.save(tiffinPlan);

		// deleting tiffin plan
		this.tiffinplanRepository.delete(save);
	}

	@Override
	public void deleteTiffinPlanLog(Integer planId) {
		TiffinPlanLog planLog = this.tiffinPlanLogRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));

		this.tiffinPlanLogRepository.delete(planLog);
	}

	@Override
	public boolean isPlanPresent(String planName, Integer userId) {
		User provider = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return this.tiffinplanRepository.existsByPlanNameAndUser(planName, provider);
	}

	@Override
	public List<TiffinDays> getAllTiffinDaysByMenuItem(Integer menuId) {
		MenuItem item = this.menuItemRepository.findById(menuId).get();
		return this.tiffindaysRepository.findByMenuItem(item);
	}

	@Override
	public TiffinPlanLog getTiffinPlanLog(Integer planId) {
		return this.tiffinPlanLogRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlanLog", "Id", planId));
	}
}
