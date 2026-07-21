package com.homeybites.services.impl;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.homeybites.entities.TiffinPlan;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.CreateTiffinPlanDTO;
import com.homeybites.payloads.NearbyTiffinPlanProjection;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.TiffinPlanFilterRequest;
import com.homeybites.repositories.TiffinplanRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.TiffinPlanService;

import jakarta.transaction.Transactional;

@Service
public class TiffinPlanServiceImpl implements TiffinPlanService {

	private final TiffinplanRepository planRepository;
	private final UserRepository userRepository;

	public TiffinPlanServiceImpl(TiffinplanRepository planRepository, UserRepository userRepository) {
		this.planRepository = planRepository;
		this.userRepository = userRepository;
	}

	// 1. CREATE A NEW PLAN
	@Override
	@Transactional
	public TiffinPlan createPlan(Long providerId, CreateTiffinPlanDTO req) {
		TiffinPlan plan = new TiffinPlan();
		plan.setProviderId(providerId);
		plan.setPlanName(req.getPlanName());
		plan.setValidityDays(req.getValidityDays());

		// Meal Offerings & Dynamic Pricing (Nullify price if not offered)
		plan.setOffersBreakfast(req.isOffersBreakfast());
		plan.setPricePerBreakfast(req.isOffersBreakfast() ? req.getPricePerBreakfast() : null);

		plan.setOffersLunch(req.isOffersLunch());
		plan.setPricePerLunch(req.isOffersLunch() ? req.getPricePerLunch() : null);

		plan.setOffersDinner(req.isOffersDinner());
		plan.setPricePerDinner(req.isOffersDinner() ? req.getPricePerDinner() : null);

		// System controlled fields
		plan.setMaxCapacity(req.getMaxCapacity());
		plan.setActiveSubscribers(0); // Hardcoded for fresh plans
		plan.setActive(true); // Active by default

		return planRepository.save(plan);
	}

	// 2. UPDATE AN EXISTING PLAN
	@Override
	@Transactional
	public TiffinPlan updatePlan(Long providerId, Long planId, CreateTiffinPlanDTO req) {
		TiffinPlan plan = planRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "id", planId));

		// Security Check: Does this kitchen own this plan?
		if (!plan.getProviderId().equals(providerId)) {
			throw new SecurityException("Unauthorized to edit this plan.");
		}

		// Capacity Safety Check: You cannot lower capacity below current active users
		if (req.getMaxCapacity() < plan.getActiveSubscribers()) {
			throw new IllegalArgumentException("Cannot reduce capacity to " + req.getMaxCapacity()
					+ ". You already have " + plan.getActiveSubscribers() + " active subscribers.");
		}

		plan.setPlanName(req.getPlanName());
		plan.setValidityDays(req.getValidityDays());

		plan.setOffersBreakfast(req.isOffersBreakfast());
		plan.setPricePerBreakfast(req.isOffersBreakfast() ? req.getPricePerBreakfast() : null);

		plan.setOffersLunch(req.isOffersLunch());
		plan.setPricePerLunch(req.isOffersLunch() ? req.getPricePerLunch() : null);

		plan.setOffersDinner(req.isOffersDinner());
		plan.setPricePerDinner(req.isOffersDinner() ? req.getPricePerDinner() : null);

		plan.setMaxCapacity(req.getMaxCapacity());

		return planRepository.save(plan);
	}

	// 3. SOFT DELETE / TOGGLE STATUS
	@Override
	@Transactional
	public void togglePlanStatus(Long providerId, Long planId, boolean isActive) {
		TiffinPlan plan = planRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "id", planId));

		if (!plan.getProviderId().equals(providerId)) {
			throw new SecurityException("Unauthorized access.");
		}

		plan.setActive(isActive);
		planRepository.save(plan);
	}

	// 4. FETCH PLANS FOR PROVIDER DASHBOARD (Shows both Active and Inactive)
	@Override
	public PageResponse<TiffinPlan> getAllProviderPlans(Long providerId, TiffinPlanFilterRequest filter,
			Pageable pageable) {
		Page<TiffinPlan> page = this.planRepository.getTiffinPlansByProvider(providerId, filter.getOffersBreakfast(),
				filter.getOffersLunch(), filter.getOffersDinner(), filter.getIsActive(), filter.getSearch(), pageable);

		return new PageResponse<>(page);
	}

	@Override
	public boolean isPlanPresent(String planName, Long providerId) {
		this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("Provider", "Id", providerId));
		return this.planRepository.existsByPlanNameAndProviderId(planName, providerId);
	}

	@Override
	public PageResponse<NearbyTiffinPlanProjection> findNearbyTiffinPlans(double userLat, double userLng,
			double maxAbsolutePlatformRadiusInMeters, Boolean wantsBreakfast, Boolean wantsLunch, Boolean wantsDinner,
			Pageable pageable) {
		Page<NearbyTiffinPlanProjection> page = this.planRepository.findNearbyTiffinPlans(userLat, userLng,
				maxAbsolutePlatformRadiusInMeters, wantsBreakfast, wantsLunch, wantsDinner, pageable);

		return new PageResponse<>(page);
	}

	@Override
	public TiffinPlan getTiffinPlanId(Long planId) {
		return this.planRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));
	}

	@Override
	public void deleteTiffinPlan(Long planId, Long providerId) {
		TiffinPlan plan = planRepository.findById(planId)
				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "id", planId));

		if (!plan.getProviderId().equals(providerId)) {
			throw new SecurityException("Unauthorized access.");
		}

		this.planRepository.delete(plan);
	}

//	@Override
//	public void addTiffinPlan(CreateTiffinPlanDTO tiffinPlan, Long providerId) {
//
//		User provider = this.userRepository.findById(providerId.intValue())
//				.orElseThrow(() -> new ResourceNotFoundException("Provider", "Id", providerId));
//
//		tiffinPlan.setUser(provider);
//		tiffinPlan.setCreatedAt(LocalDateTime.now());
//
//		List<TiffinDays> tiffinDays = tiffinPlan.getTiffinDays().stream().map(day -> {
//
//			TiffinDays tiffin = new TiffinDays();
//			tiffin.setWeekDay(day.getWeekDay());
//			tiffin.setTiffinPlan(tiffinPlan);
//
//			List<MenuItem> menuItems = new ArrayList<>();
//			day.getMenuIds().forEach(menuId -> {
//				MenuItem newMenuItem = this.menuItemRepository.findById(menuId)
//						.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", menuId));
//				menuItems.add(newMenuItem);
//			});
////			List<MenuItem> menuItems = this.menuItemRepository.findAllById(day.getMenuIds());
//			tiffin.setMenuItem(menuItems);
//
//			return tiffin;
//
//		}).collect(Collectors.toList());
//
//		tiffinPlan.setTiffinDays(tiffinDays);
//		return this.tiffinplanRepository.save(tiffinPlan);
//	}
//
//	@Override
//	public TiffinPlan updateTiffinPlan(TiffinPlan planDto, Integer planId) {
//		TiffinPlan existingTiffinPlan = this.tiffinplanRepository.findById(planId)
//				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));
//
//		// deleting plan and saving in log table
//		deleteTiffinPlan(planId);
//
//		// creating new plan for updated data and saving to db
//		TiffinPlan tiffinPlan = new TiffinPlan();
//
//		tiffinPlan.setPlanName(planDto.getPlanName());
//		tiffinPlan.setPrice(planDto.getPrice());
//		tiffinPlan.setAddOns(planDto.getAddOns());
//		tiffinPlan.setActive(planDto.isActive());
//		tiffinPlan.setPlanType(planDto.getPlanType());
//		tiffinPlan.setUser(existingTiffinPlan.getUser());
//		tiffinPlan.setCreatedAt(LocalDateTime.now());
//
//		TiffinPlan plan = this.tiffinplanRepository.save(tiffinPlan);
//
//		// copying tiffin plan from existing plan
//		List<TiffinDays> newTiffinDays = new ArrayList<>();
//		for (TiffinDays oldDay : existingTiffinPlan.getTiffinDays()) {
//			TiffinDays newDay = new TiffinDays();
//
//			newDay.setWeekDay(oldDay.getWeekDay());
//			newDay.setTiffinPlan(plan);
//
//			if (newDay.getMenuItem() == null)
//				newDay.setMenuItem(new ArrayList<>());
//
//			newDay.getMenuItem().addAll(oldDay.getMenuItem());
//
//			newTiffinDays.add(newDay);
//		}
//
//		tiffindaysRepository.saveAll(newTiffinDays);
//
//		return plan;
//	}
//
//	@Override
//	public TiffinPlan updateMenuItemOnDay(Integer planId, String day, UpdateMenuItemDto updateMenuItemDto) {
//		System.out.println("finding plan");
//		TiffinPlan existingTiffinPlan = this.tiffinplanRepository.findById(planId)
//				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));
//
//		// creating new plan for updated data
//		System.out.println("creating new plan");
//		TiffinPlan tiffinPlan = new TiffinPlan();
//		tiffinPlan.setPlanName(existingTiffinPlan.getPlanName());
//		tiffinPlan.setPrice(existingTiffinPlan.getPrice());
//		tiffinPlan.setAddOns(existingTiffinPlan.getAddOns());
//		tiffinPlan.setActive(true);
//		tiffinPlan.setPlanType(existingTiffinPlan.getPlanType());
//		tiffinPlan.setUser(existingTiffinPlan.getUser());
//		tiffinPlan.setCreatedAt(LocalDateTime.now());
//
//		System.out.println("saving new plan");
//		TiffinPlan plan = this.tiffinplanRepository.save(tiffinPlan);
//		this.tiffinplanRepository.flush();
//
//		List<TiffinDays> newTiffinDays = new ArrayList<>();
//		for (TiffinDays oldDay : existingTiffinPlan.getTiffinDays()) {
//			TiffinDays newDay = new TiffinDays();
//			System.out.println("creating weekdays");
//			newDay.setWeekDay(oldDay.getWeekDay());
//			newDay.setTiffinPlan(plan);
//
//			if (newDay.getMenuItem() == null)
//				newDay.setMenuItem(new ArrayList<>());
//
//			newDay.getMenuItem().addAll(oldDay.getMenuItem());
//
//			newTiffinDays.add(newDay);
//		}
//		tiffindaysRepository.saveAll(newTiffinDays);
//		System.out.println("saving weekdays of plan");
//		// deleting plan and saving in log table
//		deleteTiffinPlan(planId);
//		System.out.println("deleting plan");
//
//		TiffinDays tiffinDays = this.tiffindaysRepository.findByTiffinPlanAndWeekDay(plan, day)
//				.orElseThrow(() -> new ResourceNotFoundException(day, "plan Id", planId));
//
//		// getting all menu items of a day
//		List<MenuItem> menuItems = tiffinDays.getMenuItem();
//		System.out.println("getting menuitems of tifin day");
//
//		// finding old menu item and removing it
////		MenuItem oldItem = menuItems.stream().filter(menu -> menu.getMenuId().equals(oldId)).findFirst().get();
////		menuItems.remove(oldItem);
//
//		// trying accessing old menuItems array
//		for (Integer oldId : updateMenuItemDto.getOldIds()) {
//			System.out.println("removing old menuitems");
//			MenuItem oldItem = menuItems.stream().filter(menu -> menu.getMenuId().equals(oldId)).findFirst().get();
//			menuItems.remove(oldItem);
//		}
//
//		// finding new menu item
//		for (Integer newId : updateMenuItemDto.getNewIds()) {
//			MenuItem newMenuItem = this.menuItemRepository.findById(newId)
//					.orElseThrow(() -> new ResourceNotFoundException("Menu item", "id", newId));
//			System.out.println("adding new menuitems");
//			// adding new menu item to day
//			menuItems.add(newMenuItem);
//		}
//
//		// adding new menu item to day
//		tiffinDays.setMenuItem(menuItems);
//		System.out.println("saving menuitems");
//
//		// adding day to tiffin plan and saving tiffin plan
//		plan.getTiffinDays().add(tiffinDays);
//		System.out.println("saving plan");
//		return this.tiffinplanRepository.save(plan);
//	}
//
//	@Override
//	public List<TiffinPlan> getAllTiffinPlansOfProvider(Integer providerId) {
//
//		User provider = this.userRepository.findById(providerId)
//				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));
//
//		return this.tiffinplanRepository.findByUser(provider);
//	}
//
//	@Override
//	public List<TiffinPlan> getAllTiffinPlans() {
//		return this.tiffinplanRepository.findAll();
//	}
//
//	@Override
//	public void deleteTiffinPlan(Integer planId) {
//		TiffinPlan tiffinPlan = this.tiffinplanRepository.findById(planId)
//				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));
//
//		// inserting tiffin plan info into log
//		TiffinPlanLog log = new TiffinPlanLog();
//		log.setTiffinPlanId(planId);
//		log.setPlanName(tiffinPlan.getPlanName());
//		log.setPrice(tiffinPlan.getPrice());
//		log.setAddOns(tiffinPlan.getAddOns());
//		log.setActive(true);
//		log.setPlanType(tiffinPlan.getPlanType());
//		log.setUser(tiffinPlan.getUser());
//		log.setCreatedAt(tiffinPlan.getCreatedAt());
//		log.setArchievedAt(LocalDateTime.now());
//
//		// saving tiffin plan log
//		TiffinPlanLog planLog = this.tiffinPlanLogRepository.save(log);
//
//		// setting tiffin days
//		List<TiffinDays> newTiffinDays = new ArrayList<>();
//		for (TiffinDays oldDay : tiffinPlan.getTiffinDays()) {
//			TiffinDays newDay = new TiffinDays();
//
//			newDay.setWeekDay(oldDay.getWeekDay());
//			newDay.setTiffinPlanLog(planLog);
//
//			if (newDay.getMenuItem() == null)
//				newDay.setMenuItem(new ArrayList<>());
//
//			newDay.getMenuItem().addAll(oldDay.getMenuItem());
//
//			newTiffinDays.add(newDay);
//		}
////		newTiffinDays.forEach(day -> {
////			day.setTiffinPlanLog(planLog);
////		});
//
//		// saving tiffin days
//		tiffindaysRepository.saveAll(newTiffinDays);
//
////		tiffinPlan.getTiffinDays().clear();
//
//		// setting subscription plan
//		List<Subscription> list = tiffinPlan.getSubscription();
//		list.forEach(sub -> {
//			sub.setTiffinPlanLog(planLog);
//			sub.setTiffinPlan(null);
//		});
//
//		// saving subscription
//		this.subscriptionRepository.saveAll(list);
//
//		// clearing subscription
//		tiffinPlan.getSubscription().clear();
//		tiffinPlan.getSubscriptionLog().clear();
//
//		TiffinPlan save = this.tiffinplanRepository.save(tiffinPlan);
//
//		// deleting tiffin plan
//		this.tiffinplanRepository.delete(save);
//	}
//
//	@Override
//	public void deleteTiffinPlanLog(Integer planId) {
//		TiffinPlanLog planLog = this.tiffinPlanLogRepository.findById(planId)
//				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlan", "Id", planId));
//
//		this.tiffinPlanLogRepository.delete(planLog);
//	}
//

//
//	@Override
//	public List<TiffinDays> getAllTiffinDaysByMenuItem(Integer menuId) {
//		MenuItem item = this.menuItemRepository.findById(menuId).get();
//		return this.tiffindaysRepository.findByMenuItem(item);
//	}
//
//	@Override
//	public TiffinPlanLog getTiffinPlanLog(Integer planId) {
//		return this.tiffinPlanLogRepository.findById(planId)
//				.orElseThrow(() -> new ResourceNotFoundException("TiffinPlanLog", "Id", planId));
//	}
}
