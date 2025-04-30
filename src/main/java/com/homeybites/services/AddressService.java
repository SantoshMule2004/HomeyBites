package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.Address;

public interface AddressService {

	// add address of user
	Address addAddress(Address address, Integer userId);

	// add address of tiffin provider
	Address addTiffinProviderAddress(Address address, Integer providerId);

	// get address
	Address getAddress(Integer addressId);

	// get single address of a user
	Address getSingleAddressOfUser(Integer addressId, Integer userId);

	// get all addresses
	List<Address> getAllAddress();

	// get all addresses of a specific user
	List<Address> getAllAddress(Integer userId);

	// update address
	Address updateAddress(Address address, Integer addressId);

	// delete address
	void deleteAddress(Integer addressId);
}
