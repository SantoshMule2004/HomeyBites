package com.homeybites.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.homeybites.entities.UserCart;

public interface CartRepository extends JpaRepository<UserCart, Long> {
	Optional<UserCart> findByUserIdAndIsActive(Long userId, Boolean isActive);
	
	Optional<UserCart> findByUserId(Long userId);

	@Query("SELECT c.grandTotal FROM UserCart c WHERE c.userId = :userId")
    Double getGrandTotal(@Param("userId") Long userId);
}

