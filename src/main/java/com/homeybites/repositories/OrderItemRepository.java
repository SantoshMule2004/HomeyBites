package com.homeybites.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homeybites.entities.OrderItem;
import com.homeybites.payloads.OrderItemProjection;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	@Query(value = """
			SELECT
			id AS orderItemId,
			provider_order_id AS providerOrderId,
			menu_item_id AS menuItemId,
			item_name AS itemName,
			purchased_price AS purchasedPrice,
			quantity,
			total_price AS totalPrice

			FROM order_item

			WHERE provider_order_id IN (:providerOrderIds)
			""", nativeQuery = true)
	List<OrderItemProjection> findItems(List<Long> providerOrderIds);

	@Query(value = """
			SELECT
			id AS orderItemId,
			provider_order_id AS providerOrderId,
			menu_item_id AS menuItemId,
			item_name AS itemName,
			purchased_price AS purchasedPrice,
			quantity,
			total_price AS totalPrice

			FROM order_item

			WHERE provider_order_id=:providerOrderId
			""", nativeQuery = true)
	List<OrderItemProjection> findItems(Long providerOrderId);
}
