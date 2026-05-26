package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.Address;

public interface AddressService {

	// add address of user
	Address addAddress(Address address, Long userId);

	// get single address of a user
	Address getSingleAddressOfUser(Long addressId, Integer userId);

	// get all addresses of a specific user
	List<Address> getAllAddress(Integer userId);

	// update address
	Address updateAddress(Address address, Long addressId);

	// delete address
	void deleteAddress(Long addressId);
}
