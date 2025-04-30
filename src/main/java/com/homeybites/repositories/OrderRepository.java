package com.homeybites.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.OrderInfo;

public interface OrderRepository extends JpaRepository<OrderInfo, Integer> {

	List<OrderInfo> findByOrderStatus(String status);

//    // Admin Revenue (Sum of all completed orders)
//    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.status = 'COMPLETED'")
//    Double getAdminRevenue();
//
//    // Provider Revenue (Sum of completed orders for a specific provider)
//    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.status = 'COMPLETED' AND o.providerId = :providerId")
//    Double getProviderRevenue(@Param("providerId") Long providerId);

//	// find menuitem and subscription order of tiffin provider based on order status
//	@Query("SELECT DISTINCT o FROM OrderInfo o " + "LEFT JOIN FETCH o.menuItems m "
//			+ "WHERE o.orderStatus = :status AND (" + "(m.user.userId = :userId) OR "
//			+ "(o.subscription IS NOT NULL AND o.subscription.tiffinPlan.user.userId = :userId))")
//	List<OrderInfo> findOrderOfProvider(@Param("status") String status, @Param("userId") Integer userId);

	// .....................can`t load multiple collection in single JPA
	// query..............................
//

	@Query("SELECT DISTINCT o FROM OrderInfo o "
			+ "WHERE FUNCTION('DATE', o.orderDate) = CURRENT_DATE AND o.orderStatus <> :status")
	List<OrderInfo> findAllTodayOrders(@Param("status") String status);

	@Query("SELECT DISTINCT o FROM OrderInfo o " + "LEFT JOIN o.menuItems m "
			+ "WHERE FUNCTION('DATE', o.orderDate) = CURRENT_DATE AND o.orderStatus <> :status AND "
			+ "(m IS NOT NULL AND m.user.userId = :userId)")
	List<OrderInfo> findTodayOrders(@Param("status") String status, @Param("userId") Integer userId);

	// find subscription order of tiffin provider based on order status
	@Query("SELECT DISTINCT o FROM OrderInfo o " + "WHERE o.orderStatus = :status AND "
			+ "(o.subscription IS NOT NULL AND o.subscription.tiffinPlan.user.userId = :userId)")
	List<OrderInfo> findSubscriptionOrder(@Param("status") String status, @Param("userId") Integer userId);

	// find menuitem order of tiffin provider based on order status
	@Query("SELECT DISTINCT o FROM OrderInfo o " + "LEFT JOIN o.menuItems m " + "WHERE o.orderStatus = :status AND "
			+ "(m IS NOT NULL AND m.user.userId = :userId)")
	List<OrderInfo> findMenuItemOrder(@Param("status") String status, @Param("userId") Integer userId);

	// find menuitem order of tiffin provider based on order status
	@Query("SELECT DISTINCT o FROM OrderInfo o " + "LEFT JOIN o.menuItems m WHERE "
			+ "(FUNCTION('DATE', o.orderDate) BETWEEN :startDate AND :endDate) AND "
			+ "(m IS NOT NULL AND m.user.userId = :userId)")
	List<OrderInfo> findOrderOfProvider(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	@Query("SELECT o FROM OrderInfo o " + "WHERE o.orderStatus = :status AND "
			+ "(o.user IS NOT NULL AND o.user.userId = :userId)")
	List<OrderInfo> findByOrderSatusAndUser(@Param("status") String status, @Param("userId") Integer userId);

	@Query("SELECT COUNT(o) FROM OrderInfo o " + "LEFT JOIN o.menuItems m WHERE "
			+ "(m IS NOT NULL AND m.user.userId = :providerId)")
	Integer getMenuOrderCount(@Param("providerId") Integer providerId);

	@Query("SELECT COUNT(o) FROM OrderInfo o WHERE"
			+ "(o.subscription IS NOT NULL AND o.subscription.tiffinPlan.user.userId = :providerId)")
	Integer getSubOrderCount(@Param("providerId") Integer providerId);

	@Query("SELECT COUNT(o) FROM OrderInfo o")
	Integer getAllMenuOrderCount();

	@Query("SELECT DISTINCT o FROM OrderInfo o WHERE "
			+ "(FUNCTION('DATE', o.orderDate) BETWEEN :startDate AND :endDate)")
	List<OrderInfo> findAllOrdersInrange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
