package com.homeybites.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Address;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address addAddress(Address address, Long userId) {
		address.setUserId(userId);
		return this.addressRepository.save(address);
	}

	@Override
	public Address getSingleAddressOfUser(Long addressId, Integer userId) {
		return this.addressRepository.getAddress(userId, addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
	}

	@Override
	public List<Address> getAllAddress(Integer userId) {
		return this.addressRepository.getAllAddressesOfUser(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
	}

	@Override
	public Address updateAddress(Address add, Long addressId) {
		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));

		address.setAddressLine(add.getAddressLine());
		address.setArea(add.getArea());
		address.setLatitude(add.getLatitude());
		address.setLongitude(add.getLongitude());
		address.setReceiverName(add.getReceiverName());
		address.setReceiverContactNo(add.getReceiverContactNo());
		address.setAddressType(add.getAddressType());
		address.setAddressName(add.getAddressName());
		address.setIsPrimary(add.getIsPrimary());

		return this.addressRepository.save(address);
	}

	@Override
	public void deleteAddress(Long addressId) {
		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));

		Address save = this.addressRepository.save(address);

		this.addressRepository.delete(save);
	}
}
