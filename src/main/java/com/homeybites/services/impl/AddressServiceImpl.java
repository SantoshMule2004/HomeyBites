package com.homeybites.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Address;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.AddressDto;
import com.homeybites.payloads.UserDto;
import com.homeybites.payloads.UserRoles;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.services.AddressService;
import com.homeybites.services.UserService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AddressDto addAddress(AddressDto addressDto, Integer userId) {
		UserDto userDto = this.userService.getUser(userId);
		User user = this.modelMapper.map(userDto, User.class);

		Address address = this.modelMapper.map(addressDto, Address.class);
		address.setUser(user);
		address.setUserRoles(UserRoles.NORMAL_USER);
		Address savedAddress = this.addressRepository.save(address);

		return this.modelMapper.map(savedAddress, AddressDto.class);
	}
	
	@Override
	public AddressDto addTiffinProviderAddress(AddressDto addressDto, Integer providerId) {
		UserDto userDto = this.userService.getUser(providerId);
		User user = this.modelMapper.map(userDto, User.class);

		Address address = this.modelMapper.map(addressDto, Address.class);
		address.setUser(user);
		address.setUserRoles(UserRoles.TIFFIN_PROVIDER);
		Address savedAddress = this.addressRepository.save(address);

		return this.modelMapper.map(savedAddress, AddressDto.class);
	}

	@Override
	public AddressDto getAddress(Integer addressId) {
		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
		return this.modelMapper.map(address, AddressDto.class);
	}

	@Override
	public AddressDto getSingleAddressOfUser(Integer addressId, Integer userId) {
		Address address = this.addressRepository.getAddress(userId, addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
		return this.modelMapper.map(address, AddressDto.class);
	}

	@Override
	public List<AddressDto> getAllAddress() {
		List<Address> all = this.addressRepository.findAll();
		List<AddressDto> allAddresses = all.stream().map(address -> this.modelMapper.map(address, AddressDto.class))
				.collect(Collectors.toList());
		return allAddresses;
	}

	@Override
	public List<AddressDto> getAllAddress(Integer userId) {
		UserDto userDto = this.userService.getUser(userId);
		List<Address> addressOfUser = this.addressRepository.findByUser(this.modelMapper.map(userDto, User.class));
		List<AddressDto> allAddresses = addressOfUser.stream().map(address -> this.modelMapper.map(address, AddressDto.class))
				.collect(Collectors.toList());
		return allAddresses;
	}

	@Override
	public AddressDto updateAddress(AddressDto addressDto, Integer addressId) {
		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
		
		address.setAddressLine(addressDto.getAddressLine());
		address.setCity(addressDto.getCity());
		address.setCountry(addressDto.getCountry());
		address.setLandmark(addressDto.getLandmark());
		address.setLatitude(addressDto.getLatitude());
		address.setLongitude(addressDto.getLongitude());
		address.setServiceRadius(addressDto.getServiceRadius());
		address.setState(addressDto.getState());
		
		Address address2 = this.addressRepository.save(address);
		return this.modelMapper.map(address2, AddressDto.class);
	}

	@Override
	public void deleteAddress(Integer addressId) {
		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
		this.addressRepository.delete(address);
	}

	@Override
	public void deleteAddressOfUser(Integer addressId, Integer userId) {
		this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));
		this.userService.getUser(userId);
		this.addressRepository.deleteAddressByUser(userId, addressId);
		
	}
}
