package com.homeybites.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.ProviderOrder;

public interface ProviderOrderRepository extends JpaRepository<ProviderOrder, Long> {

}
