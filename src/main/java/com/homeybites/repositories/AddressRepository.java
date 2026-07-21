package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homeybites.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	// get all address of user
	List<Address> findByUserId(Long userId);
	
	Optional<Address> findByAddId(Long addId);

	// get single address of a user
	@Query(value = "select * from address where user_id = ? and add_id = ?", nativeQuery = true)
	Optional<Address> getAddress(Integer userId, Long addId);

	// get all address of a user
	@Query(value = "select * from address where user_id = ?", nativeQuery = true)
	Optional<List<Address>> getAllAddressesOfUser(Integer userId);
}
