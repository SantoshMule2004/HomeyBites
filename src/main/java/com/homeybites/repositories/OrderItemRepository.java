package com.homeybites.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
