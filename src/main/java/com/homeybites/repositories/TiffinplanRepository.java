package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.homeybites.entities.TiffinPlan;
import com.homeybites.payloads.NearbyTiffinPlanProjection;

public interface TiffinplanRepository extends JpaRepository<TiffinPlan, Long> {

	List<TiffinPlan> findByProviderIdAndIsActiveTrue(Long providerId);

	// to check if plan exist with this name
	boolean existsByPlanNameAndProviderId(String planName, Long providerId);

	@Modifying
	@Query("UPDATE TiffinPlan p SET p.activeSubscribers = p.activeSubscribers + 1 "
			+ "WHERE p.id = :planId AND p.activeSubscribers < p.maxCapacity")
	int incrementSubscribersIfCapacityAllows(@Param("planId") Long planId);

	@Modifying
	@Query("UPDATE TiffinPlan p SET p.activeSubscribers = p.activeSubscribers - 1 WHERE p.id = :planId")
	void decrementSubscribers(@Param("planId") Long planId);

	@Query(value = """
			SELECT
			    tp.id AS planId,
			    tp.plan_name AS planName,
			    tp.validity_days AS validityDays,
			    tp.offers_breakfast AS offersBreakfast,
			    tp.offers_lunch AS offersLunch,
			    tp.offers_dinner AS offersDinner,
			    tp.price_per_breakfast AS pricePerBreakfast,
			    tp.price_per_lunch AS pricePerLunch,
			    tp.price_per_dinner AS pricePerDinner,
			    u.user_id AS providerId,
			    u.business_name AS businessName,
			    u.latitude AS latitude,
			    u.longitude AS longitude,
			    u.service_radius AS serviceRadius,
			    ST_Distance_Sphere(
			        u.provider_location_point,
			        ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)
			    ) AS distanceInMeters
			FROM tiffin_plans tp
			JOIN `user` u ON tp.provider_id = u.user_id
			WHERE
			    MBRContains(
			        ST_Buffer(
			            ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326),
			            :maxLimit
			        ),
			        u.provider_location_point
			    )
			    AND ST_Distance_Sphere(
			            u.provider_location_point,
			            ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)
			        ) <= u.service_radius
			    AND tp.is_active = true
			    AND tp.active_subscribers < tp.max_capacity
			    AND (:wantsBreakfast IS NULL OR tp.offers_breakfast = :wantsBreakfast)
			    AND (:wantsLunch IS NULL OR tp.offers_lunch = :wantsLunch)
			    AND (:wantsDinner IS NULL OR tp.offers_dinner = :wantsDinner)
			ORDER BY distanceInMeters ASC
			""",

			countQuery = """
					SELECT COUNT(*)
					FROM tiffin_plans tp
					JOIN `user` u ON tp.provider_id = u.user_id
					WHERE
					    MBRContains(
					        ST_Buffer(
					            ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326),
					            :maxLimit
					        ),
					        u.provider_location_point
					    )
					    AND ST_Distance_Sphere(
					            u.provider_location_point,
					            ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)
					        ) <= u.service_radius
					    AND tp.is_active = true
					    AND tp.active_subscribers < tp.max_capacity
					    AND (:wantsBreakfast IS NULL OR tp.offers_breakfast = :wantsBreakfast)
					    AND (:wantsLunch IS NULL OR tp.offers_lunch = :wantsLunch)
					    AND (:wantsDinner IS NULL OR tp.offers_dinner = :wantsDinner)
					""",

			nativeQuery = true)
	Page<NearbyTiffinPlanProjection> findNearbyTiffinPlans(@Param("lat") double userLat, @Param("lng") double userLng,
			@Param("maxLimit") double maxAbsolutePlatformRadiusInMeters,
			@Param("wantsBreakfast") Boolean wantsBreakfast, @Param("wantsLunch") Boolean wantsLunch,
			@Param("wantsDinner") Boolean wantsDinner, Pageable pageable);

	@Query(value = """
			SELECT
			    tp.*
			FROM tiffin_plans tp
			WHERE tp.provider_id = :providerId
			  AND (:offersBreakfast IS NULL OR tp.offers_breakfast = :offersBreakfast)
			  AND (:offersLunch IS NULL OR tp.offers_lunch = :offersLunch)
			  AND (:offersDinner IS NULL OR tp.offers_dinner = :offersDinner)
			  AND (:isActive IS NULL OR tp.is_active = :isActive)
			  AND (:search IS NULL OR LOWER(tp.plan_name) LIKE LOWER(CONCAT('%', :search, '%')))
			ORDER BY tp.created_at DESC
			""",

			countQuery = """
					SELECT COUNT(*)
					FROM tiffin_plans tp
					WHERE tp.provider_id = :providerId
					  AND (:offersBreakfast IS NULL OR tp.offers_breakfast = :offersBreakfast)
					  AND (:offersLunch IS NULL OR tp.offers_lunch = :offersLunch)
					  AND (:offersDinner IS NULL OR tp.offers_dinner = :offersDinner)
					  AND (:isActive IS NULL OR tp.is_active = :isActive)
					  AND (:search IS NULL OR LOWER(tp.plan_name) LIKE LOWER(CONCAT('%', :search, '%')))
					""",

			nativeQuery = true)
	Page<TiffinPlan> getTiffinPlansByProvider(@Param("providerId") Long providerId,
			@Param("offersBreakfast") Boolean offersBreakfast, @Param("offersLunch") Boolean offersLunch,
			@Param("offersDinner") Boolean offersDinner, @Param("isActive") Boolean isActive,
			@Param("search") String search, Pageable pageable);

	@Query("""
			SELECT COUNT(tp)
			FROM TiffinPlan tp
			WHERE tp.providerId = :providerId
			""")
	Long countPlans(Long providerId);

	@Query("""
			SELECT COUNT(tp)
			FROM TiffinPlan tp
			WHERE tp.providerId = :providerId
			AND tp.isActive = true
			""")
	Long countActivePlans(Long providerId);
}
