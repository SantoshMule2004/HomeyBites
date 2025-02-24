package com.homeybites.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.User;
import com.homeybites.entities.UserCart;

public interface CartRepository extends JpaRepository<UserCart, Integer> {

	UserCart findByUser(User user);
}
