package com.homeybites.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Address;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.UserRoles;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Address addAddress(Address address, Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		address.setUser(user);
		address.setUserRoles(UserRoles.NORMAL_USER);
		
		return this.addressRepository.save(address);
	}
	
	@Override
	public Address addTiffinProviderAddress(Address address, Integer providerId) {
		User providerInfo = this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));
		
		address.setUser(providerInfo);
		address.setUserRoles(UserRoles.TIFFIN_PROVIDER);
		
		return this.addressRepository.save(address);
	}

	@Override
	public Address getAddress(Integer addressId) {
		return this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
	}

	@Override
	public Address getSingleAddressOfUser(Integer addressId, Integer userId) {
		return this.addressRepository.getAddress(userId, addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
	}

	@Override
	public List<Address> getAllAddress() {
		return this.addressRepository.findAll();
	}

	@Override
	public List<Address> getAllAddress(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return this.addressRepository.findByUser(user);
	}

	@Override
	public Address updateAddress(Address add, Integer addressId) {
		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
		
		address.setAddressLine(add.getAddressLine());
		address.setCity(add.getCity());
		address.setCountry(add.getCountry());
		address.setLandmark(add.getLandmark());
		address.setLatitude(add.getLatitude());
		address.setLongitude(add.getLongitude());
		address.setServiceRadius(add.getServiceRadius());
		address.setState(add.getState());
		
		return this.addressRepository.save(address);
	}

	@Override
	public void deleteAddress(Integer addressId) {
		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
		
		address.setUser(null);
		Address save = this.addressRepository.save(address);
		
		this.addressRepository.delete(save);
	}
}
