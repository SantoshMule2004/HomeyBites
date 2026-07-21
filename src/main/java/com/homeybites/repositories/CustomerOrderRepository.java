package com.homeybites.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homeybites.entities.CustomerOrder;
import com.homeybites.payloads.OrderProjection;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

	@Query(value = """
			SELECT

			    co.id AS customerOrderId,
			    co.grand_total AS grandTotal,
			    co.created_at AS createdAt,
			    co.receiver_name AS receiverName,
			    co.receiver_contact_no AS receiverContactNo,
			    co.delivery_address AS deliveryAddress,

			    po.id AS providerOrderId,
			    po.provider_id AS providerId,
			    po.vendor_subtotal AS vendorSubtotal,
			    po.fulfillment_status AS fulfillmentStatus

			FROM customer_order co

			INNER JOIN provider_order po
			    ON po.customer_order_id = co.id

			WHERE co.user_id = :userId

			AND co.created_at >= :startDate
			AND co.created_at < DATE_ADD(:endDate, INTERVAL 1 DAY)

			AND (
			    :status IS NULL
			    OR po.fulfillment_status = :status
			)

			ORDER BY co.created_at DESC
			""", countQuery = """
			SELECT COUNT(*)

			FROM customer_order co

			INNER JOIN provider_order po
			    ON po.customer_order_id = co.id

			WHERE co.user_id = :userId

			AND co.created_at >= :startDate
			AND co.created_at < DATE_ADD(:endDate, INTERVAL 1 DAY)

			AND (
			    :status IS NULL
			    OR po.fulfillment_status = :status
			)
			""", nativeQuery = true)
	Page<OrderProjection> findCustomerOrders(Long userId, LocalDate startDate, LocalDate endDate, String status,
			Pageable pageable);

	@Query(value = """
			SELECT
			    co.id AS customerOrderId,
			    co.grand_total AS grandTotal,
			    co.created_at AS createdAt,
			    co.receiver_name AS receiverName,
			    co.receiver_contact_no AS receiverContactNo,
			    co.delivery_address AS deliveryAddress,

			    po.id AS providerOrderId,
			    po.provider_id AS providerId,
			    po.vendor_subtotal AS vendorSubtotal,
			    po.fulfillment_status AS fulfillmentStatus

			FROM customer_order co
			JOIN provider_order po
			    ON po.customer_order_id = co.id

			WHERE co.id=:orderId
			AND co.user_id=:userId
			""", nativeQuery = true)
	OrderProjection findCustomerOrder(Long orderId, Long userId);
}