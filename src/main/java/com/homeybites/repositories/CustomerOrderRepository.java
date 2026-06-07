package com.homeybites.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

}