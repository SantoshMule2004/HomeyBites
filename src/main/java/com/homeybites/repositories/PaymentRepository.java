package com.homeybites.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homeybites.entities.Payment;
import com.homeybites.payloads.DashboardRevenueProjection;
import com.homeybites.payloads.PaymentDetailsProjection;
import com.homeybites.payloads.PaymentHistoryProjection;
import com.homeybites.payloads.RecentPaymentProjection;
import com.homeybites.payloads.RevenueChartProjection;
import com.homeybites.payloads.RevenueSummaryProjection;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	/*-----Payment Lookup-----*/

	Optional<Payment> findFirstBySubscriptionIdAndPaymentStatusOrderByPaidAtDesc(Long subscriptionId,
			String paymentStatus);

	Optional<Payment> findByCustomerOrderIdAndPaymentStatus(Long customerOrderId, String paymentStatus);

	Optional<Payment> findByTransactionId(String transactionId);

	Optional<Payment> findByGatewayPaymentId(String gatewayPaymentId);

	/*-----Payment History-----*/

	@Query(value = """
			SELECT
			    p.id AS paymentId,
			    p.transaction_id AS transactionId,
			    p.amount AS amount,
			    p.refunded_amount AS refundedAmount,
			    p.payment_type AS paymentType,
			    p.payment_method AS paymentMethod,
			    p.payment_status AS paymentStatus,
			    p.paid_at AS paidAt,

			    CONCAT_WS(' ',
			        provider.first_name,
			        provider.middle_name,
			        provider.last_name
			    ) AS providerName,

			    NULL AS customerName

			FROM payments p

			INNER JOIN user provider
			    ON provider.user_id = p.provider_id

			WHERE p.user_id = :customerId

			AND (
			        :search IS NULL
			        OR provider.business_name LIKE CONCAT('%', :search, '%')
			        OR provider.first_name LIKE CONCAT('%', :search, '%')
			        OR provider.last_name LIKE CONCAT('%', :search, '%')
			        OR p.transaction_id LIKE CONCAT('%', :search, '%')
			)

			AND (
			        :paymentMethod IS NULL
			        OR p.payment_method = :paymentMethod
			)

			AND (
			        :paymentStatus IS NULL
			        OR p.payment_status = :paymentStatus
			)

			AND (
			        :startDateTime IS NULL
			        OR p.created_at >= :startDateTime
			)

			AND (
			        :endDateTime IS NULL
			        OR p.created_at <= :endDateTime
			)

			ORDER BY p.created_at DESC
			""", countQuery = """
			SELECT COUNT(*)

			FROM payments p

			INNER JOIN user provider
			ON provider.user_id = p.provider_id

			WHERE p.user_id = :customerId

			AND (
			        :search IS NULL
			        OR provider.business_name LIKE CONCAT('%', :search, '%')
			        OR provider.first_name LIKE CONCAT('%', :search, '%')
			        OR provider.last_name LIKE CONCAT('%', :search, '%')
			        OR p.transaction_id LIKE CONCAT('%', :search, '%')
			)

			AND (
			        :paymentMethod IS NULL
			        OR p.payment_method = :paymentMethod
			)

			AND (
			        :paymentStatus IS NULL
			        OR p.payment_status = :paymentStatus
			)

			AND (
			        :startDateTime IS NULL
			        OR p.created_at >= :startDateTime
			)

			AND (
			        :endDateTime IS NULL
			        OR p.created_at <= :endDateTime
			)
			""", nativeQuery = true)
	Page<PaymentHistoryProjection> findCustomerPayments(Long customerId, String search, String paymentMethod,
			String paymentStatus, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

	@Query(value = """
									SELECT
									    p.id AS paymentId,
									    p.transaction_id AS transactionId,
									    p.amount AS amount,
									    p.refunded_amount AS refundedAmount,
									    p.payment_type AS paymentType,
									    p.payment_method AS paymentMethod,
									    p.payment_status AS paymentStatus,
									    p.paid_at AS paidAt,

									    CONCAT_WS(' ',
			    customer.first_name,
			    customer.middle_name,
			    customer.last_name
			) AS customerName,

			NULL AS providerName

									FROM payments p

									INNER JOIN user customer
						ON customer.user_id = p.user_id

									WHERE p.provider_id = :providerId

									AND (
									        :search IS NULL
									        OR customer.email_id LIKE CONCAT('%', :search, '%')
									        OR customer.first_name LIKE CONCAT('%', :search, '%')
									        OR customer.last_name LIKE CONCAT('%', :search, '%')
									        OR p.transaction_id LIKE CONCAT('%', :search, '%')
									)

									AND (
									        :paymentMethod IS NULL
									        OR p.payment_method = :paymentMethod
									)

									AND (
									        :paymentStatus IS NULL
									        OR p.payment_status = :paymentStatus
									)

									AND (
									        :startDateTime IS NULL
									        OR p.created_at >= :startDateTime
									)

									AND (
									        :endDateTime IS NULL
									        OR p.created_at <= :endDateTime
									)

									ORDER BY p.created_at DESC
									""", countQuery = """
			SELECT COUNT(*)

			FROM payments p

			INNER JOIN user customer
						ON customer.user_id = p.user_id

			WHERE p.provider_id = :providerId

			AND (
			        :search IS NULL
			        OR customer.email_id LIKE CONCAT('%', :search, '%')
									        OR customer.first_name LIKE CONCAT('%', :search, '%')
									        OR customer.last_name LIKE CONCAT('%', :search, '%')
									        OR p.transaction_id LIKE CONCAT('%', :search, '%')
			)

			AND (
			        :paymentMethod IS NULL
			        OR p.payment_method = :paymentMethod
			)

			AND (
			        :paymentStatus IS NULL
			        OR p.payment_status = :paymentStatus
			)

			AND (
			        :startDateTime IS NULL
			        OR p.created_at >= :startDateTime
			)

			AND (
			        :endDateTime IS NULL
			        OR p.created_at <= :endDateTime
			)
			""", nativeQuery = true)
	Page<PaymentHistoryProjection> findProviderPayments(Long providerId, String search, String paymentMethod,
			String paymentStatus, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

	@Query(value = """
									SELECT
									    p.id AS paymentId,
									    p.transaction_id AS transactionId,
									    p.amount AS amount,
									    p.refunded_amount AS refundedAmount,
									    p.payment_type AS paymentType,
									    p.payment_method AS paymentMethod,
									    p.payment_status AS paymentStatus,
									    p.paid_at AS paidAt,

									    CONCAT_WS(' ',
			    customer.first_name,
			    customer.middle_name,
			    customer.last_name
			) AS customerName,

			provider.business_name AS providerName

									FROM payments p

									INNER JOIN user customer
						ON customer.user_id = p.user_id

						INNER JOIN user provider
						ON provider.user_id = p.provider_id

									WHERE (
									        :search IS NULL
									        OR provider.business_name LIKE CONCAT('%', :search, '%')
									        OR customer.first_name LIKE CONCAT('%', :search, '%')
									        OR customer.last_name LIKE CONCAT('%', :search, '%')
									        OR customer.email_id LIKE CONCAT('%', :search, '%')
									        OR p.transaction_id LIKE CONCAT('%', :search, '%')
									)

									AND (
									        :paymentMethod IS NULL
									        OR p.payment_method = :paymentMethod
									)

									AND (
									        :paymentStatus IS NULL
									        OR p.payment_status = :paymentStatus
									)

									AND (
									        :startDateTime IS NULL
									        OR p.created_at >= :startDateTime
									)

									AND (
									        :endDateTime IS NULL
									        OR p.created_at <= :endDateTime
									)

									ORDER BY p.created_at DESC
									""", countQuery = """
			SELECT COUNT(*)

			FROM payments p

			INNER JOIN user customer
					ON customer.user_id = p.user_id

			INNER JOIN user provider
					ON provider.user_id = p.provider_id

			WHERE (
			        :search IS NULL
				    OR provider.business_name LIKE CONCAT('%', :search, '%')
					OR customer.first_name LIKE CONCAT('%', :search, '%')
					OR customer.last_name LIKE CONCAT('%', :search, '%')
					OR customer.email_id LIKE CONCAT('%', :search, '%')
					OR p.transaction_id LIKE CONCAT('%', :search, '%')
			)

			AND (
			        :paymentMethod IS NULL
			        OR p.payment_method = :paymentMethod
			)

			AND (
			        :paymentStatus IS NULL
			        OR p.payment_status = :paymentStatus
			)

			AND (
			        :startDateTime IS NULL
			        OR p.created_at >= :startDateTime
			)

			AND (
			        :endDateTime IS NULL
			        OR p.created_at <= :endDateTime
			)
			""", nativeQuery = true)
	Page<PaymentHistoryProjection> findPayments(String search, String paymentMethod, String paymentStatus,
			LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

	/*-----Payment Details-----*/

	@Query(value = """
			SELECT

			    p.id AS paymentId,

			    p.customer_order_id AS customerOrderId,

			    p.subscription_id AS subscriptionId,

			    p.user_id AS customerId,

			    p.provider_id AS providerId,

			    CONCAT_WS(' ',
			        customer.first_name,
			        customer.middle_name,
			        customer.last_name
			    ) AS customerName,

			    provider.business_name AS providerName,

			    p.amount AS amount,

			    p.tax_amount AS taxAmount,

			    p.discount_amount AS discountAmount,

			    p.refunded_amount AS refundedAmount,

			    p.refund_transaction_id AS refundTransactionId,

			    p.payment_type AS paymentType,

			    p.payment_method AS paymentMethod,

			    p.payment_status AS paymentStatus,

			    p.gateway_name AS gatewayName,

			    p.transaction_id AS transactionId,

			    p.gateway_order_id AS gatewayOrderId,

			    p.gateway_payment_id AS gatewayPaymentId,

			    p.failure_reason AS failureReason,

			    p.paid_at AS paidAt,

			    p.refunded_at AS refundedAt,

			    p.created_at AS createdAt

			FROM payments p

			INNER JOIN user customer
			    ON customer.user_id = p.user_id

			INNER JOIN user provider
			    ON provider.user_id = p.provider_id

			WHERE p.id = :paymentId
			""", nativeQuery = true)
	PaymentDetailsProjection getPaymentDetails(Long paymentId);

	/*-----Revenue-----*/

	@Query(value = """
			SELECT

			COALESCE(SUM(CASE WHEN p.payment_status='SUCCESS' THEN p.amount END),0) AS grossRevenue,

			COALESCE(SUM(p.refunded_amount),0) AS refundedAmount,

			COALESCE(
			SUM(CASE WHEN p.payment_status='SUCCESS' THEN p.amount END)
			-
			SUM(COALESCE(p.refunded_amount,0)), 0) AS netRevenue,

			SUM(CASE WHEN p.payment_status='SUCCESS' THEN 1 ELSE 0 END) AS successfulPayments,

			SUM(CASE WHEN p.payment_status='PENDING' THEN 1 ELSE 0 END) AS pendingPayments,

			SUM(CASE WHEN p.payment_status='FAILED' THEN 1 ELSE 0 END) AS failedPayments,

			SUM(CASE WHEN p.payment_status='REFUNDED' THEN 1 ELSE 0 END) AS refundedPayments

			FROM payments p

			WHERE p.provider_id = :providerId

			AND (
			    :startDateTime IS NULL
			    OR p.created_at >= :startDateTime
			)

			AND (
			    :endDateTime IS NULL
			    OR p.created_at <= :endDateTime
			)
			""", nativeQuery = true)
	RevenueSummaryProjection getProviderRevenueSummary(Long providerId, LocalDateTime startDateTime,
			LocalDateTime endDateTime);

	@Query(value = """
			SELECT

			COALESCE(SUM(CASE WHEN p.payment_status='SUCCESS' THEN p.amount	END),0) AS grossRevenue,

			COALESCE(SUM(p.refunded_amount),0) AS refundedAmount,

			COALESCE(SUM(CASE WHEN p.payment_status='SUCCESS' THEN p.amount END) -
			SUM(COALESCE(p.refunded_amount,0)),0) AS netRevenue,

			SUM(CASE WHEN p.payment_status='SUCCESS' THEN 1 ELSE 0 END) AS successfulPayments,

			SUM(CASE WHEN p.payment_status='PENDING' THEN 1 ELSE 0 END) AS pendingPayments,

			SUM(CASE WHEN p.payment_status='FAILED' THEN 1 ELSE 0 END) AS failedPayments,

			SUM(CASE
			        WHEN p.payment_status='REFUNDED' THEN 1 ELSE 0 END) AS refundedPayments

			FROM payments p

			WHERE (
			    :startDateTime IS NULL
			    OR p.created_at >= :startDateTime
			)

			AND (
			    :endDateTime IS NULL
			    OR p.created_at <= :endDateTime
			)
			""", nativeQuery = true)
	RevenueSummaryProjection getPlatformRevenueSummary(LocalDateTime startDateTime, LocalDateTime endDateTime);

	@Query(value = """
			SELECT
			    DATE(p.created_at) AS period,
			    COALESCE(SUM(CASE
			        WHEN p.payment_status = 'SUCCESS'
			        THEN p.amount
			        ELSE 0
			    END), 0) AS revenue,
			    SUM(CASE
			        WHEN p.payment_status = 'SUCCESS'
			        THEN 1
			        ELSE 0
			    END) AS paymentCount,
			    SUM(CASE
			        WHEN p.payment_status = 'REFUNDED'
			        THEN 1
			        ELSE 0
			    END) AS refundCount
			FROM payments p
			WHERE (:providerId IS NULL OR p.provider_id = :providerId)
			AND (:startDateTime IS NULL OR p.created_at >= :startDateTime)
			AND (:endDateTime IS NULL OR p.created_at <= :endDateTime)
			GROUP BY DATE(p.created_at)
			ORDER BY DATE(p.created_at)
			""", nativeQuery = true)
	List<RevenueChartProjection> getDailyRevenueChart(Long providerId, LocalDateTime startDateTime,
			LocalDateTime endDateTime);

	@Query(value = """
			SELECT
			    DATE_SUB(DATE(p.created_at), INTERVAL WEEKDAY(p.created_at) DAY) AS period,

			    COALESCE(SUM(
			        CASE
			            WHEN p.payment_status = 'SUCCESS'
			            THEN p.amount
			            ELSE 0
			        END
			    ), 0) AS revenue,

			    SUM(
			        CASE
			            WHEN p.payment_status = 'SUCCESS'
			            THEN 1
			            ELSE 0
			        END
			    ) AS paymentCount,

			    SUM(
			        CASE
			            WHEN p.payment_status = 'REFUNDED'
			            THEN 1
			            ELSE 0
			        END
			    ) AS refundCount

			FROM payments p

			WHERE (:providerId IS NULL OR p.provider_id = :providerId)
			  AND (:startDateTime IS NULL OR p.created_at >= :startDateTime)
			  AND (:endDateTime IS NULL OR p.created_at <= :endDateTime)

			GROUP BY DATE_SUB(DATE(p.created_at), INTERVAL WEEKDAY(p.created_at) DAY)

			ORDER BY DATE_SUB(DATE(p.created_at), INTERVAL WEEKDAY(p.created_at) DAY)
			""", nativeQuery = true)
	List<RevenueChartProjection> getWeeklyRevenueChart(Long providerId, LocalDateTime startDateTime,
			LocalDateTime endDateTime);

	@Query(value = """
			SELECT
			    DATE_FORMAT(p.created_at, '%Y-%m') AS period,

			    COALESCE(SUM(
			        CASE
			            WHEN p.payment_status = 'SUCCESS'
			            THEN p.amount
			            ELSE 0
			        END
			    ), 0) AS revenue,

			    SUM(
			        CASE
			            WHEN p.payment_status = 'SUCCESS'
			            THEN 1
			            ELSE 0
			        END
			    ) AS paymentCount,

			    SUM(
			        CASE
			            WHEN p.payment_status = 'REFUNDED'
			            THEN 1
			            ELSE 0
			        END
			    ) AS refundCount

			FROM payments p

			WHERE (:providerId IS NULL OR p.provider_id = :providerId)
			  AND (:startDateTime IS NULL OR p.created_at >= :startDateTime)
			  AND (:endDateTime IS NULL OR p.created_at <= :endDateTime)

			GROUP BY DATE_FORMAT(p.created_at, '%Y-%m')

			ORDER BY DATE_FORMAT(p.created_at, '%Y-%m')
			""", nativeQuery = true)
	List<RevenueChartProjection> getMonthlyRevenueChart(Long providerId, LocalDateTime startDateTime,
			LocalDateTime endDateTime);

	@Query(value = """
			SELECT
			    YEAR(p.created_at) AS period,

			    COALESCE(SUM(
			        CASE
			            WHEN p.payment_status = 'SUCCESS'
			            THEN p.amount
			            ELSE 0
			        END
			    ), 0) AS revenue,

			    SUM(
			        CASE
			            WHEN p.payment_status = 'SUCCESS'
			            THEN 1
			            ELSE 0
			        END
			    ) AS paymentCount,

			    SUM(
			        CASE
			            WHEN p.payment_status = 'REFUNDED'
			            THEN 1
			            ELSE 0
			        END
			    ) AS refundCount

			FROM payments p

			WHERE (:providerId IS NULL OR p.provider_id = :providerId)
			  AND (:startDateTime IS NULL OR p.created_at >= :startDateTime)
			  AND (:endDateTime IS NULL OR p.created_at <= :endDateTime)

			GROUP BY YEAR(p.created_at)

			ORDER BY YEAR(p.created_at)
			""", nativeQuery = true)
	List<RevenueChartProjection> getYearlyRevenueChart(Long providerId, LocalDateTime startDateTime,
			LocalDateTime endDateTime);

	// ==========================
	// Dashboard
	// ==========================

	@Query(value = """
			SELECT

			COALESCE(SUM(
			CASE
			WHEN p.payment_status='SUCCESS'
			THEN p.amount
			ELSE 0
			END),0) AS revenue,

			COUNT(
			CASE
			WHEN p.payment_status='SUCCESS'
			THEN 1
			END) AS successfulPayments,

			COUNT(
			CASE
			WHEN p.payment_status='PENDING'
			THEN 1
			END) AS pendingPayments,

			COUNT(
			CASE
			WHEN p.payment_status='FAILED'
			THEN 1
			END) AS failedPayments,

			COUNT(
			CASE
			WHEN p.payment_status='REFUNDED'
			THEN 1
			END) AS refundedPayments

			FROM payments p

			WHERE p.provider_id=:providerId

			AND (:startDateTime IS NULL
			     OR p.created_at>=:startDateTime)

			AND (:endDateTime IS NULL
			     OR p.created_at<=:endDateTime)
			""", nativeQuery = true)
	DashboardRevenueProjection getRevenueSummary(Long providerId, LocalDateTime startDateTime,
			LocalDateTime endDateTime);

	@Query(value = """
			SELECT

			COALESCE(SUM(
			CASE
			WHEN p.payment_status='SUCCESS'
			THEN p.amount
			ELSE 0
			END),0) AS revenue,

			COUNT(
			CASE
			WHEN p.payment_status='SUCCESS'
			THEN 1
			END) AS successfulPayments,

			COUNT(
			CASE
			WHEN p.payment_status='PENDING'
			THEN 1
			END) AS pendingPayments,

			COUNT(
			CASE
			WHEN p.payment_status='FAILED'
			THEN 1
			END) AS failedPayments,

			COUNT(
			CASE
			WHEN p.payment_status='REFUNDED'
			THEN 1
			END) AS refundedPayments

			FROM payments p

			WHERE (:startDateTime IS NULL
			     OR p.created_at>=:startDateTime)

			AND (:endDateTime IS NULL
			     OR p.created_at<=:endDateTime)
			""", nativeQuery = true)
	DashboardRevenueProjection getRevenueSummary(LocalDateTime startDateTime, LocalDateTime endDateTime);

	@Query(value = """
			SELECT

			p.id AS paymentId,

			CONCAT_WS(' ',
			customer.first_name,
			customer.middle_name,
			customer.last_name
			) AS customerName,

			provider.business_name AS providerName,

			p.amount,

			p.payment_method AS paymentMethod,

			p.payment_status AS paymentStatus,

			p.payment_type AS paymentType,

			p.paid_at AS paidAt

			FROM payments p

			INNER JOIN user customer
			ON customer.user_id=p.user_id

			INNER JOIN user provider
			ON provider.user_id=p.provider_id

			ORDER BY p.created_at DESC
			""", countQuery = """
			SELECT COUNT(*)
			FROM payment
			""", nativeQuery = true)
	Page<RecentPaymentProjection> getRecentPayments(Pageable pageable);

}
