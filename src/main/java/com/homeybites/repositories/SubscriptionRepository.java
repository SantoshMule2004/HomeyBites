package com.homeybites.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.Subscription;
import com.homeybites.payloads.RecentSubscriptionProjection;
import com.homeybites.payloads.SubscriptionStatus;
import com.homeybites.payloads.SubscriptionWithUserProjection;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	List<Subscription> findByUserId(Long userId);

	List<Subscription> findByProviderId(Long providerId);

	@Query("""
			    SELECT s
			    FROM Subscription s
			    WHERE s.userId = :userId
			      AND (:status IS NULL OR s.status = :status)
			    ORDER BY s.createdAt DESC
			""")
	Page<Subscription> findSubscriptionsByUser(@Param("userId") Long userId, @Param("status") SubscriptionStatus status,
			Pageable pageable);

	@Query(value = """
			SELECT
			 s.id AS id,
			 s.user_id AS userId,
			 s.plan_id AS planId,
			 s.provider_id AS providerId,

			 s.plan_name AS planName,
			 s.validity_days AS validityDays,

			 s.includes_breakfast AS includesBreakfast,
			 s.includes_lunch AS includesLunch,
			 s.includes_dinner AS includesDinner,

			 s.breakfast_price AS breakfastPrice,
			 s.lunch_price AS lunchPrice,
			 s.dinner_price AS dinnerPrice,

			 s.start_date AS startDate,
			 s.current_end_date AS currentEndDate,
			 s.total_paused_days AS totalPausedDays,

			 s.status AS status,

			 s.pause_start_date AS pauseStartDate,
			 s.auto_resume_date AS autoResumeDate,

			 s.delivery_address_id AS deliveryAddressId,
			 s.created_at AS createdAt,

			 CONCAT_WS(' ',
			     customer.first_name,
			     customer.middle_name,
			     customer.last_name
			 ) AS customerName,

			 customer.email_id AS emailId,
			 customer.phone_no AS phoneNo,
			 customer.is_verified AS isVerified,

			 provider.business_name AS providerName

			FROM subscriptions s
			INNER JOIN user customer
			ON s.user_id = customer.user_id

			INNER JOIN user provider
			 ON s.provider_id = provider.user_id

			WHERE (:providerId IS NULL OR s.provider_id = :providerId)

			AND (:status IS NULL OR s.status = :status)

			AND (
			    :search IS NULL
			    OR LOWER(
			        CONCAT(
			            customer.first_name,
			            ' ',
			            COALESCE(customer.middle_name,''),
			            ' ',
			            customer.last_name
			        )
			    ) LIKE LOWER(CONCAT('%', :search, '%'))
			    OR provider.business_name LIKE CONCAT('%', :search, '%')
			    OR LOWER(customer.email_id) LIKE LOWER(CONCAT('%', :search, '%'))
			)

			ORDER BY s.created_at DESC
			""",

			countQuery = """
					SELECT COUNT(*)
					FROM subscriptions s
					INNER JOIN user customer
					ON s.user_id = customer.user_id

					INNER JOIN user provider
					ON s.provider_id = provider.user_id

					WHERE (:providerId IS NULL OR s.provider_id = :providerId)

					AND (:status IS NULL OR s.status = :status)

					AND (
					    :search IS NULL
					    OR LOWER(
					        CONCAT(
					            customer.first_name,
					            ' ',
					            COALESCE(customer.middle_name,''),
					            ' ',
					            customer.last_name
					        )
					    ) LIKE LOWER(CONCAT('%', :search, '%'))
					    OR provider.business_name LIKE CONCAT('%', :search, '%')
					    OR LOWER(customer.email_id) LIKE LOWER(CONCAT('%', :search, '%'))
					)
					""",

			nativeQuery = true)
	Page<SubscriptionWithUserProjection> findSubscriptions(@Param("providerId") Long providerId,
			@Param("status") String status, @Param("search") String search, Pageable pageable);

	long count();

	long countByProviderId(Long providerId);

	List<Subscription> findByStatusInAndCurrentEndDateLessThan(List<SubscriptionStatus> statuses, LocalDate date);

	List<Subscription> findByStatusAndCurrentEndDateGreaterThanEqual(SubscriptionStatus status, LocalDate date);

	List<Subscription> findByProviderIdAndStatus(Long providerId, SubscriptionStatus status);

	// --- REPLACED: NEW METHODS FOR STATE TRANSITION JOB ---
	List<Subscription> findByStatusAndPauseStartDate(SubscriptionStatus status, LocalDate date);

	List<Subscription> findByStatusAndAutoResumeDateLessThanEqual(SubscriptionStatus status, LocalDate date);

	// dashboard related

	@Query("""
			SELECT COUNT(s)
			FROM Subscription s
			WHERE (:providerId IS NULL OR s.providerId = :providerId)
			AND s.status = :status
			""")
	Long countSubscriptions(Long providerId, SubscriptionStatus status);

	@Query("""
			SELECT COUNT(s)
			FROM Subscription s
			WHERE (:providerId IS NULL OR s.providerId = :providerId)
			AND (:startDate IS NULL OR s.startDate >= :startDate)
			AND (:endDate IS NULL OR s.startDate <= :endDate)
			""")
	Long countNewSubscriptions(Long providerId, LocalDate startDate, LocalDate endDate);

	@Query(value = """
			SELECT

			s.id AS subscriptionId,

			CONCAT_WS(
			' ',
			u.first_name,
			NULLIF(u.middle_name,''),
			u.last_name
			) AS customerName,

			s.plan_name AS planName,

			s.start_date AS startDate,

			s.current_end_date AS currentEndDate,

			s.status AS status

			FROM subscriptions s

			INNER JOIN user u
			ON u.user_id = s.user_id

			WHERE (:providerId IS NULL OR s.provider_id = :providerId)

			ORDER BY s.created_at DESC
			""", countQuery = """
			SELECT COUNT(*)
			FROM subscription
			WHERE (:providerId IS NULL OR s.provider_id = :providerId)
			""", nativeQuery = true)
	Page<RecentSubscriptionProjection> getRecentSubscriptions(Long providerId, Pageable pageable);

}
