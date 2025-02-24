package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homeybites.entities.Address;
import com.homeybites.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	//find user by email id
	Optional<User> findByEmailId(String emailId);
	
	// is user present by email id
	boolean existsByEmailId(String username);
	
	// get all users of specific roles
	List<User> findByUserRole(String userRole);
	
	List<User> findByAddressIn(List<Address> address);
}
