package com.homeybites.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.UserCart;

public interface CartRepository extends JpaRepository<UserCart, Long> {
	Optional<UserCart> findByUserIdAndIsActive(Long userId, Boolean isActive);
	
	Optional<UserCart> findByUserId(Long userId);
}

