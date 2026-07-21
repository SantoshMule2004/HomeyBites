package com.homeybites.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.homeybites.entities.ProviderOrder;
import com.homeybites.payloads.CancelOrderProjection;
import com.homeybites.payloads.OrderProjection;
import com.homeybites.payloads.RecentOrderProjection;

import jakarta.transaction.Transactional;

public interface ProviderOrderRepository extends JpaRepository<ProviderOrder, Long> {
	@Query(value = """
			SELECT

			    po.id AS providerOrderId,
			    po.customer_order_id AS customerOrderId,
			    po.provider_id AS providerId,
			    po.vendor_subtotal AS vendorSubtotal,
			    po.fulfillment_status AS fulfillmentStatus,
			    po.created_at AS createdAt,
			    p.payment_status AS paymentStatus,
			    p.payment_method AS paymentMethod,

			    co.receiver_name AS receiverName,
			    co.receiver_contact_no AS receiverContactNo,
			    co.delivery_address AS deliveryAddress,

			    CONCAT_WS(' ',
			        u.first_name,
			        u.last_name
			    ) AS providerName,
			    u.business_name AS businessName

			FROM provider_order po

			INNER JOIN customer_order co
			    ON co.id = po.customer_order_id

			LEFT JOIN payments p
			    ON p.customer_order_id = po.customer_order_id

			LEFT JOIN user u
			    ON u.user_id = po.provider_id

			WHERE (:providerId IS NULL OR po.provider_id = :providerId)

			AND po.created_at >= :startDate
			AND po.created_at < DATE_ADD(:endDate, INTERVAL 1 DAY)

			AND (
			    :status IS NULL
			    OR po.fulfillment_status = :status
			)

			AND (
				:search IS NULL
				OR :search = ''
				OR LOWER(co.receiver_name) LIKE LOWER(CONCAT('%', :search, '%'))
				OR LOWER(u.business_name) LIKE LOWER(CONCAT('%', :search, '%'))
			)

			ORDER BY po.created_at DESC
			""", countQuery = """
			SELECT COUNT(*)

			FROM provider_order po

			INNER JOIN customer_order co
			    ON co.id = po.customer_order_id

			LEFT JOIN payments p
			    ON p.customer_order_id = po.customer_order_id

			LEFT JOIN user u
			    ON u.user_id = po.provider_id

			WHERE (:providerId IS NULL OR po.provider_id = :providerId)

			AND po.created_at >= :startDate
			AND po.created_at < DATE_ADD(:endDate, INTERVAL 1 DAY)

			AND (
			    :status IS NULL
			    OR po.fulfillment_status = :status
			)

			AND (
					 :search IS NULL
					 OR :search = ''
					 OR LOWER(co.receiver_name) LIKE LOWER(CONCAT('%', :search, '%'))
					 OR LOWER(u.business_name) LIKE LOWER(CONCAT('%', :search, '%'))
			)
			""", nativeQuery = true)
	Page<OrderProjection> findOrders(Long providerId, LocalDate startDate, LocalDate endDate, String status,
			String paymentStatus, String search, Pageable pageable);

	@Query(value = """
			SELECT

			po.id AS providerOrderId,
			po.customer_order_id AS customerOrderId,
			po.provider_id AS providerId,
			po.vendor_subtotal AS vendorSubtotal,
			po.fulfillment_status AS fulfillmentStatus,
			po.created_at AS createdAt,

			p.payment_status AS paymentStatus,
			p.payment_method AS paymentMethod,

			co.receiver_name AS receiverName,
			co.receiver_contact_no AS receiverContactNo,
			co.delivery_address AS deliveryAddress,

			CONCAT_WS(' ',
			        u.first_name,
			        u.last_name
			    ) AS providerName,
			u.business_name AS businessName

			FROM provider_order po

			JOIN customer_order co
			    ON co.id = po.customer_order_id

			LEFT JOIN payments p
			    ON p.customer_order_id = po.customer_order_id

			LEFT JOIN user u
			    ON u.user_id = po.provider_id

			WHERE (:providerId IS NULL OR po.provider_id = :providerId)
			AND po.id = :providerOrderId
			""", nativeQuery = true)
	Optional<OrderProjection> findOrder(Long providerOrderId, Long providerId);

	// Update status
	@Modifying
	@Transactional
	@Query(value = """
			UPDATE provider_order

			SET fulfillment_status=:status

			WHERE id=:providerOrderId
			AND provider_id=:providerId
			""", nativeQuery = true)
	int updateStatus(Long providerOrderId, Long providerId, String status);

	// Cancel Order
	@Query(value = """
			SELECT
			    po.id AS providerOrderId,
			    po.fulfillment_status AS fulfillmentStatus,
			    po.customer_order_id AS customerOrderId
			FROM provider_order po
			JOIN customer_order co
			    ON co.id = po.customer_order_id
			WHERE po.id = :providerOrderId
			  AND co.user_id = :customerId
			""", nativeQuery = true)
	Optional<CancelOrderProjection> findOrderForCancellation(Long providerOrderId, Long customerId);

	@Modifying
	@Transactional
	@Query(value = """
			UPDATE provider_order
			SET fulfillment_status = 'CANCELLED'
			WHERE id = :providerOrderId
			""", nativeQuery = true)
	int cancelOrder(Long providerOrderId);

	// dashboard related

	@Query("""
			SELECT COUNT(po)
			FROM ProviderOrder po
			WHERE (:providerId IS NULL OR po.providerId = :providerId)
			AND (:startDateTime IS NULL OR po.createdAt >= :startDateTime)
			AND (:endDateTime IS NULL OR po.createdAt <= :endDateTime)
			""")
	Long countOrders(Long providerId, LocalDateTime startDateTime, LocalDateTime endDateTime);

	@Query("""
			SELECT COUNT(po)
			FROM ProviderOrder po
			WHERE (:providerId IS NULL OR po.providerId = :providerId)
			AND po.fulfillmentStatus = 'PENDING'
			""")
	Long countPendingOrders(Long providerId);

	@Query("""
			SELECT COALESCE(AVG(po.vendorSubtotal),0)
			FROM ProviderOrder po
			WHERE (:providerId IS NULL OR po.providerId = :providerId)
			""")
	BigDecimal getAverageOrderValue(Long providerId);

	@Query(value = """
			SELECT

			po.id AS providerOrderId,

			po.customer_order_id AS customerOrderId,

			CONCAT_WS(' ',
			u.first_name,
			u.middle_name,
			u.last_name
			) AS customerName,

			po.vendor_subtotal AS amount,

			po.fulfillment_status AS fulfillmentStatus,

			co.created_at AS createdAt

			FROM provider_order po

			INNER JOIN customer_order co
			ON co.id = po.customer_order_id

			INNER JOIN user u
			ON u.user_id = co.user_id

			WHERE (:providerId IS NULL OR po.provider_id = :providerId)

			ORDER BY co.created_at DESC
			""", countQuery = """
			SELECT COUNT(*)
			FROM provider_order
			WHERE (:providerId IS NULL OR provider_id = :providerId)
			""", nativeQuery = true)
	Page<RecentOrderProjection> getRecentOrders(Long providerId, Pageable pageable);
}
