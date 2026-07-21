package com.homeybites.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.DailyDelivery;
import com.homeybites.payloads.ProviderDeliveryViewProjection;

public interface DailyDeliveryRepository extends JpaRepository<DailyDelivery, Long> {
	@Query("SELECT d.providerId, COUNT(d) FROM DailyDelivery d WHERE d.status = 'DELIVERED' AND d.deliveryDate >= :startDate GROUP BY d.providerId")
	List<Object[]> countSuccessfulDeliveriesByProvider(@Param("startDate") LocalDate startDate);

	@Query(value = """
			SELECT
			    d.id AS deliveryId,
			    d.subscription_id AS subscriptionId,
			    d.user_id AS userId,
			    d.meal_type AS mealType,
			    d.food_items AS foodItems,
			    d.delivery_date AS deliveryDate,
			    d.receiver_name AS receiverName,
			    d.receiver_contact_no AS receiverContactNo,
			    d.delivery_address AS deliveryAddress,
			    d.status AS status
			FROM daily_deliveries d
			WHERE d.provider_id = :providerId
			  AND d.delivery_date = :targetDate
			  AND (:mealType IS NULL OR d.meal_type = :mealType)
			  AND (
			        :search IS NULL
			        OR TRIM(:search) = ''
			        OR LOWER(d.receiver_name)
			            LIKE LOWER(CONCAT('%', :search, '%'))
			      )
			ORDER BY
			    CASE d.meal_type
			        WHEN 'BREAKFAST' THEN 1
			        WHEN 'LUNCH' THEN 2
			        WHEN 'DINNER' THEN 3
			        ELSE 4
			    END,
			    d.receiver_name
			""", countQuery = """
			SELECT COUNT(*)
			FROM daily_deliveries d
			WHERE d.provider_id = :providerId
			  AND d.delivery_date = :targetDate
			  AND (:mealType IS NULL OR d.meal_type = :mealType)
			  AND (
			        :search IS NULL
			        OR TRIM(:search) = ''
			        OR LOWER(d.receiver_name)
			            LIKE LOWER(CONCAT('%', :search, '%'))
			      )
			""", nativeQuery = true)
	Page<ProviderDeliveryViewProjection> findDeliveriesWithUserDetails(@Param("providerId") Long providerId,
			@Param("targetDate") LocalDate targetDate, @Param("mealType") String mealType,
			@Param("search") String search, Pageable pageable);

	@Query(value = """
			SELECT COUNT(*)
			FROM daily_deliveries d
			WHERE (:providerId IS NULL OR d.provider_id = :providerId)
			  AND d.delivery_date = :targetDate
			  AND (:mealType IS NULL OR d.meal_type = :mealType)
			""", nativeQuery = true)
	Long countTodayDeliveries(@Param("providerId") Long providerId, @Param("targetDate") LocalDate targetDate,
			@Param("mealType") String mealType);
}
