package com.homeybites.services;

import java.util.List;

import com.homeybites.payloads.AddressDto;

public interface AddressService {

	// add address of user
	AddressDto addAddress(AddressDto addressDto, Integer userId);

	// add address of tiffin provider
	AddressDto addTiffinProviderAddress(AddressDto addressDto, Integer providerId);

	// get address
	AddressDto getAddress(Integer addressId);

	// get single address of a user
	AddressDto getSingleAddressOfUser(Integer addressId, Integer userId);

	// get all addresses
	List<AddressDto> getAllAddress();

	// get all addresses of a specific user
	List<AddressDto> getAllAddress(Integer userId);

	// update address
	AddressDto updateAddress(AddressDto addressDto, Integer addressId);

	// delete address
	void deleteAddress(Integer addressId);

	// delete address of specific user
	void deleteAddressOfUser(Integer addressId, Integer userId);
}
