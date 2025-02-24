package com.homeybites.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.payloads.AddressDto;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.services.AddressService;

@RestController
@RequestMapping("/api/v1/")
public class AddressController {

	@Autowired
	private AddressService addressService;

	// add address of user
	@PostMapping("/user/{userId}/address")
	public ResponseEntity<ApiResponse> addAddress(@RequestBody AddressDto addressDto, @PathVariable Integer userId) {
		System.out.println(userId);
		AddressDto address = this.addressService.addAddress(addressDto, userId);
		ApiResponse response = new ApiResponse("Address added successfully..!", true, address);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// add address of tiffin provider
	@PostMapping("/tiffin/{providerId}/address")
	public ResponseEntity<ApiResponse> addTiffinProviderAddress(@RequestBody AddressDto addressDto,
			@PathVariable Integer providerId) {
		System.out.println(providerId);
		AddressDto address = this.addressService.addTiffinProviderAddress(addressDto, providerId);
		ApiResponse response = new ApiResponse("Address added successfully..!", true, address);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// get address of specific user
	@GetMapping("/address/user/{userId}")
	public ResponseEntity<List<AddressDto>> getAddressesOfUser(@PathVariable Integer userId) {
		List<AddressDto> allAddress = this.addressService.getAllAddress(userId);
		return new ResponseEntity<List<AddressDto>>(allAddress, HttpStatus.OK);
	}

	// get address
	@GetMapping("/address/{addId}")
	public ResponseEntity<AddressDto> getAddress(@PathVariable Integer addId) {
		AddressDto address = this.addressService.getAddress(addId);
		return new ResponseEntity<AddressDto>(address, HttpStatus.OK);
	}

	// get single address of user
	@GetMapping("/user/{userId}/address/{addId}")
	public ResponseEntity<AddressDto> getSingleAddress(@PathVariable Integer userId, @PathVariable Integer addId) {
		AddressDto addressDto = this.addressService.getSingleAddressOfUser(addId, userId);
		return new ResponseEntity<AddressDto>(addressDto, HttpStatus.OK);
	}

	// get all address
	@GetMapping("/addresses")
	public ResponseEntity<List<AddressDto>> getAllAddress() {
		List<AddressDto> allAddresses = this.addressService.getAllAddress();
		return new ResponseEntity<List<AddressDto>>(allAddresses, HttpStatus.OK);
	}

	// update address
	@PutMapping("/address/{addId}")
	public ResponseEntity<ApiResponse> updateAddress(@RequestBody AddressDto addressDto, @PathVariable Integer addId) {
		AddressDto updateAddress = this.addressService.updateAddress(addressDto, addId);
		ApiResponse response = new ApiResponse("Address updated successfully..!", true, updateAddress);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// delete address
	@DeleteMapping("/address/{addId}")
	public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer addId) {
		this.addressService.deleteAddress(addId);
		ApiResponse response = new ApiResponse("Address deleted successfully..!", true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// delete address of specific user
	@DeleteMapping("/user/{userId}/address/{addId}")
	public ResponseEntity<ApiResponse> deleteAddressOfUser(@PathVariable Integer addId, @PathVariable Integer userId) {
		this.addressService.deleteAddressOfUser(addId, userId);
		ApiResponse response = new ApiResponse("Address deleted successfully..!", true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
}
