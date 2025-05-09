package com.homeybites.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.Payment;
import com.homeybites.entities.User;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	// get total revenue by menuitems
	@Query("SELECT SUM(p.amount) FROM Payment p " + "WHERE p.order.orderId IN ("
			+ "  SELECT DISTINCT o.orderId FROM OrderInfo o " + "  JOIN o.menuItems m" + ")")
	Double getTotalMenuItemRevenue();

	// get total revenue by subscription
	@Query("SELECT SUM(p.amount) FROM Payment p " + "WHERE p.order.orderId IN ("
			+ "  SELECT DISTINCT o.orderId FROM OrderInfo o " + "  JOIN o.subscription s" + ")")
	Double getTotalSubscriptionRevenue();

	// get total revenue by menuitems for a tiffin provider
	@Query("SELECT SUM(p.amount) FROM Payment p " + "WHERE p.order.orderId IN ("
			+ "  SELECT DISTINCT o.orderId FROM OrderInfo o " + "  JOIN o.menuItems m "
			+ "  WHERE m.user.userId = :providerId" + ")")
	Double getTotalMenuItemRevenueByProvider(@Param("providerId") Integer providerId);

	// get total revenue by subscription for a tiffin provider
	@Query("SELECT SUM(p.amount) FROM Payment p " + "WHERE p.order.orderId IN ("
			+ "  SELECT DISTINCT o.orderId FROM OrderInfo o " + "  JOIN o.subscription s "
			+ "  WHERE s.tiffinPlan.user.userId = :providerId" + ")")
	Double getTotalSubscriptionRevenueByProvider(@Param("providerId") Integer providerId);

	// get total revenue
	@Query("SELECT SUM(p.amount) FROM Payment p")
	Double getTotalRevenue();

	// get total past six months revenue data
	@Query("SELECT FUNCTION('YEAR', p.paymentDate) AS year, FUNCTION('MONTH', p.paymentDate) AS month, SUM(p.amount) "
			+ "FROM Payment p " + "WHERE p.paymentDate >= :sixMonthsAgo "
			+ "GROUP BY FUNCTION('YEAR', p.paymentDate), FUNCTION('MONTH', p.paymentDate) " + "ORDER BY year, month")
	List<Object[]> getMonthlyRevenueLastSixMonths(@Param("sixMonthsAgo") LocalDate sixMonthsAgo);

	// get total past six months revenue data of menuitems for a tiffin provider
	@Query("SELECT FUNCTION('YEAR', p.paymentDate) AS year, FUNCTION('MONTH', p.paymentDate) AS month, SUM(p.amount) "
			+ "FROM Payment p " + "WHERE p.paymentDate >= :sixMonthsAgo AND " + "p.order.orderId IN ("
			+ "  SELECT DISTINCT o.orderId FROM OrderInfo o " + "  JOIN o.subscription s "
			+ "  WHERE s.tiffinPlan.user.userId = :providerId" + ") "
			+ "GROUP BY FUNCTION('YEAR', p.paymentDate), FUNCTION('MONTH', p.paymentDate) " + "ORDER BY year, month")
	List<Object[]> getMonthlySubscriptionRevenueLastSixMonthsByProvider(@Param("providerId") Integer providerId,
			@Param("sixMonthsAgo") LocalDate sixMonthsAgo);

	// get total past six months revenue data of subscription for a tiffin provider
	@Query("SELECT FUNCTION('YEAR', p.paymentDate) AS year, FUNCTION('MONTH', p.paymentDate) AS month, SUM(p.amount) "
			+ "FROM Payment p " + "WHERE p.paymentDate >= :sixMonthsAgo AND " + "p.order.orderId IN ("
			+ "  SELECT DISTINCT o.orderId FROM OrderInfo o " + "  JOIN o.menuItems m "
			+ "  WHERE m.user.userId = :providerId" + ") "
			+ "GROUP BY FUNCTION('YEAR', p.paymentDate), FUNCTION('MONTH', p.paymentDate) " + "ORDER BY year, month")
	List<Object[]> getMonthlyMenuItemRevenueLastSixMonthsByProvider(@Param("providerId") Integer providerId,
			@Param("sixMonthsAgo") LocalDate sixMonthsAgo);

	// get all payments of user
	List<Payment> findByUser(User user);

	@Query("SELECT p FROM Payment p " + "WHERE p.order.orderId IN (" + "  SELECT DISTINCT o.orderId FROM OrderInfo o "
			+ "  JOIN o.menuItems m " + "  WHERE m.user.userId = :providerId" + ")")
	List<Payment> getMenuTransaction(Integer providerId);

	@Query("SELECT p FROM Payment p " + "WHERE p.order.orderId IN (" + "  SELECT DISTINCT o.orderId FROM OrderInfo o "
			+ "  JOIN o.subscription s " + "  WHERE s.tiffinPlan.user.userId = :providerId" + ")")
	List<Payment> getSubTransaction(Integer providerId);
}
