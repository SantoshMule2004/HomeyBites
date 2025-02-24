package com.homeybites.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.homeybites.entities.Address;
import com.homeybites.entities.User;
import com.homeybites.payloads.UserRoles;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	
	//get all address of user
	List<Address> findByUser(User user);
	
	// get single address of a user
	@Query(value = "select * from address where user_id = ? and add_id = ?", nativeQuery = true)
	Optional<Address> getAddress(Integer userId, Integer addId);
	
	//delete address of specific user
	@Query(value = "delete from address where user_id = ? and add_id = ?", nativeQuery = true)
	void deleteAddressByUser(Integer userId, Integer addId);
	
	// find address by user role
	List<Address> findByUserRoles(UserRoles userRoles);
}

