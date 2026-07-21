package com.homeybites.services.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.homeybites.payloads.AdminDashboardDTO;
import com.homeybites.payloads.DashboardFilter;
import com.homeybites.payloads.DashboardRevenueProjection;
import com.homeybites.payloads.DateFilter;
import com.homeybites.payloads.DateRange;
import com.homeybites.payloads.ProviderDashboardDTO;
import com.homeybites.payloads.RevenueDashboardDTO;
import com.homeybites.payloads.RevenueGroupBy;
import com.homeybites.payloads.SubscriptionStatus;
import com.homeybites.payloads.UserRoles;
import com.homeybites.repositories.DailyDeliveryRepository;
import com.homeybites.repositories.MenuItemRepository;
import com.homeybites.repositories.PaymentRepository;
import com.homeybites.repositories.ProviderHolidayRepository;
import com.homeybites.repositories.ProviderOrderRepository;
import com.homeybites.repositories.SubscriptionRepository;
import com.homeybites.repositories.TiffinplanRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.DashboardService;
import com.homeybites.services.PaymentService;

@Service
public class DashboardServiceImpl implements DashboardService {

	private final ProviderOrderRepository providerOrderRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final MenuItemRepository menuItemRepository;
	private final TiffinplanRepository tiffinPlanRepository;
	private final UserRepository userRepository;
	private final DailyDeliveryRepository deliveryRepository;
	private final ProviderHolidayRepository holidayRepository;
	private final PaymentRepository paymentRepository;
	private final PaymentService paymentService;

	public DashboardServiceImpl(ProviderOrderRepository providerOrderRepo, SubscriptionRepository subscriptionRepo,
			MenuItemRepository menuItemRepo, TiffinplanRepository tiffinplanRepo, UserRepository userRepo,
			PaymentRepository paymentRepo, PaymentService paymentService, DailyDeliveryRepository deliveryRepo,
			ProviderHolidayRepository holidayRepo) {
		this.providerOrderRepository = providerOrderRepo;
		this.subscriptionRepository = subscriptionRepo;
		this.menuItemRepository = menuItemRepo;
		this.tiffinPlanRepository = tiffinplanRepo;
		this.userRepository = userRepo;
		this.paymentRepository = paymentRepo;
		this.paymentService = paymentService;
		this.deliveryRepository = deliveryRepo;
		this.holidayRepository = holidayRepo;
	}

	@Override
	public ProviderDashboardDTO getProviderDashboard(Long providerId) {
		ProviderDashboardDTO dto = new ProviderDashboardDTO();

		// Dashboard cards
		LocalDate today = LocalDate.now();
		LocalDateTime todayStart = today.atStartOfDay();
		LocalDateTime todayEnd = today.atTime(LocalTime.MAX);

		// Revenue section defaults
		LocalDate revenueStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate revenueEnd = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

//		groupBy = groupBy != null ? groupBy : null;

		// ----------------------------
		// Dashboard Cards
		// ----------------------------

		DashboardRevenueProjection todayRevenue = paymentRepository.getRevenueSummary(providerId, todayStart, todayEnd);

		dto.setTodayRevenue(todayRevenue.getRevenue());

		dto.setTodayOrders(providerOrderRepository.countOrders(providerId, todayStart, todayEnd));

		dto.setPendingOrders(providerOrderRepository.countPendingOrders(providerId));

		dto.setAverageOrderValue(providerOrderRepository.getAverageOrderValue(providerId));

		dto.setActiveSubscriptions(subscriptionRepository.countSubscriptions(providerId, SubscriptionStatus.ACTIVE));

		dto.setTodaySubscriptions(subscriptionRepository.countNewSubscriptions(providerId, today, today));

		dto.setTodayDeliveries(deliveryRepository.countTodayDeliveries(providerId, today, null));

		dto.setTotalMenuItems(menuItemRepository.countMenuItems(providerId));

		dto.setActivePlans(tiffinPlanRepository.countActivePlans(providerId));

		// ----------------------------
		// Revenue
		// ----------------------------

		dto.setRevenueSummary(paymentRepository.getProviderRevenueSummary(providerId, revenueStart.atStartOfDay(),
				revenueEnd.atTime(LocalTime.MAX)));

		dto.setRevenueChart(
				paymentService.getProviderRevenueChart(providerId, revenueStart, revenueEnd, RevenueGroupBy.DAY));

		// ----------------------------
		// Recent Orders
		// ----------------------------

		dto.setRecentOrders(providerOrderRepository.getRecentOrders(providerId, PageRequest.of(0, 5)).getContent());

		// ----------------------------
		// Recent Subscriptions
		// ----------------------------

		dto.setRecentSubscriptions(
				subscriptionRepository.getRecentSubscriptions(providerId, PageRequest.of(0, 5)).getContent());

		// ----------------------------
		// Recent holidays
		// ----------------------------

		dto.setRecentHolidays(holidayRepository.findByProviderIdAndClosedDateGreaterThanEqualOrderByClosedDateAsc(
				providerId, today, PageRequest.of(0, 5)).getContent());

		return dto;
	}

	@Override
	public AdminDashboardDTO getAdminDashboard() {

		AdminDashboardDTO dto = new AdminDashboardDTO();

		// Dashboard cards
		LocalDate today = LocalDate.now();
		LocalDateTime todayStart = today.atStartOfDay();
		LocalDateTime todayEnd = today.atTime(LocalTime.MAX);

		// Revenue section defaults
		LocalDate revenueStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate revenueEnd = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

		DashboardRevenueProjection todayRevenue = paymentRepository.getRevenueSummary(null, todayStart, todayEnd);

		dto.setTodayRevenue(todayRevenue.getRevenue());

		dto.setTotalUsers(userRepository.countByUserRole(UserRoles.ROLE_NORMAL_USER.name()));

		dto.setTotalProviders(userRepository.countByUserRole(UserRoles.ROLE_TIFFIN_PROVIDER.name()));

		dto.setActiveProviders(userRepository.countByUserRoleAndActiveTrue(UserRoles.ROLE_TIFFIN_PROVIDER.name()));

		dto.setTodayOrders(providerOrderRepository.countOrders(null, todayStart, todayEnd));

		dto.setPendingOrders(providerOrderRepository.countPendingOrders(null));

		dto.setTodayDeliveries(deliveryRepository.countTodayDeliveries(null, today, null));

		dto.setActiveSubscriptions(subscriptionRepository.countSubscriptions(null, SubscriptionStatus.ACTIVE));

		dto.setAverageOrderValue(providerOrderRepository.getAverageOrderValue(null));

		// revenue
		dto.setRevenueSummary(paymentRepository.getPlatformRevenueSummary(revenueStart.atStartOfDay(),
				revenueEnd.atTime(LocalTime.MAX)));

		dto.setRevenueChart(paymentService.getPlatformRevenueChart(revenueStart, revenueEnd, RevenueGroupBy.DAY));

		// recent
		dto.setRecentOrders(providerOrderRepository.getRecentOrders(null, PageRequest.of(0, 5)).getContent());

		dto.setRecentPayments(paymentRepository.getRecentPayments(PageRequest.of(0, 5)).getContent());

		dto.setRecentUsers(userRepository.getRecentUsers(PageRequest.of(0, 5)).getContent());

		dto.setRecentProviders(userRepository.getRecentProviders(PageRequest.of(0, 5)).getContent());

		return dto;
	}

	@Override
	public RevenueDashboardDTO getProviderRevenueDashboard(Long providerId, DashboardFilter filter) {

//		filter = this.verifyDashboardFilter(filter);

		RevenueGroupBy groupBy = filter.getGroupBy();
		DateFilter dateFilter = filter.getDateFilter();

		// Validate combination
		groupBy.validate(dateFilter);

		// Resolve dates
		DateRange range = this.resolve(dateFilter);

		LocalDateTime start = range.startDate().atStartOfDay();
		LocalDateTime end = range.endDate().atTime(LocalTime.MAX);

		RevenueDashboardDTO dto = new RevenueDashboardDTO();

		dto.setRevenueSummary(paymentRepository.getProviderRevenueSummary(providerId, start, end));

		dto.setRevenueChart(paymentService.getProviderRevenueChart(providerId, range.startDate(), range.endDate(),
				filter.getGroupBy()));

		return dto;
	}

	@Override
	public RevenueDashboardDTO getAdminRevenueDashboard(DashboardFilter filter) {

//		filter = this.verifyDashboardFilter(filter);
		RevenueGroupBy groupBy = filter.getGroupBy();
		DateFilter dateFilter = filter.getDateFilter();

		// Validate combination
		groupBy.validate(dateFilter);

		// Resolve dates
		DateRange range = this.resolve(dateFilter);

		LocalDateTime start = range.startDate().atStartOfDay();
		LocalDateTime end = range.endDate().atTime(LocalTime.MAX);

		RevenueDashboardDTO dto = new RevenueDashboardDTO();

		dto.setRevenueSummary(paymentRepository.getPlatformRevenueSummary(start, end));

		dto.setRevenueChart(
				paymentService.getPlatformRevenueChart(range.startDate(), range.endDate(), filter.getGroupBy()));

		return dto;
	}

//	private DashboardFilter verifyDashboardFilter(DashboardFilter filter) {
//
//		if (filter.getStartDate() == null && filter.getEndDate() == null) {
//			filter.setStartDate(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));
//			filter.setEndDate(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
//		} else if (filter.getStartDate() == null) {
//			filter.setStartDate(filter.getEndDate());
//		} else if (filter.getEndDate() == null) {
//			filter.setEndDate(filter.getStartDate());
//		}
//
//		filter.setStart(filter.getStartDate().atStartOfDay());
//		filter.setEnd(filter.getEndDate().atTime(LocalTime.MAX));
//
//		filter.setGroupBy(filter.getGroupBy() != null ? filter.getGroupBy() : RevenueGroupBy.DAY);
//
//		return filter;
//	}

	private DateRange resolve(DateFilter filter) {

		LocalDate today = LocalDate.now();

		return switch (filter) {

		case TODAY -> new DateRange(today, today);

		case YESTERDAY -> {
			LocalDate yesterday = today.minusDays(1);
			yield new DateRange(yesterday, yesterday);
		}

		case THIS_WEEK -> new DateRange(today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
				today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));

		case LAST_WEEK -> {

			LocalDate lastWeek = today.minusWeeks(1);

			yield new DateRange(lastWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
					lastWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
		}

		case LAST_7_DAYS -> new DateRange(today.minusDays(6), today);

		case LAST_15_DAYS -> new DateRange(today.minusDays(14), today);

		case LAST_30_DAYS -> new DateRange(today.minusDays(29), today);

		case THIS_MONTH -> new DateRange(today.withDayOfMonth(1), today.with(TemporalAdjusters.lastDayOfMonth()));

		case LAST_MONTH -> {

			LocalDate lastMonth = today.minusMonths(1);

			yield new DateRange(lastMonth.withDayOfMonth(1), lastMonth.with(TemporalAdjusters.lastDayOfMonth()));
		}

		case LAST_3_MONTHS ->
			new DateRange(today.minusMonths(2).withDayOfMonth(1), today.with(TemporalAdjusters.lastDayOfMonth()));

		case LAST_6_MONTHS ->
			new DateRange(today.minusMonths(5).withDayOfMonth(1), today.with(TemporalAdjusters.lastDayOfMonth()));

		case THIS_YEAR -> new DateRange(today.withDayOfYear(1), today.with(TemporalAdjusters.lastDayOfYear()));

		case LAST_YEAR -> {

			LocalDate lastYear = today.minusYears(1);

			yield new DateRange(lastYear.withDayOfYear(1), lastYear.with(TemporalAdjusters.lastDayOfYear()));
		}

		case LAST_2_YEARS ->
			new DateRange(today.minusYears(1).withDayOfYear(1), today.with(TemporalAdjusters.lastDayOfYear()));

		case LAST_5_YEARS ->
			new DateRange(today.minusYears(4).withDayOfYear(1), today.with(TemporalAdjusters.lastDayOfYear()));

		case ALL_TIME -> new DateRange(LocalDate.of(2000, 1, 1), today);
		};
	}
}
