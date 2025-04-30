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

import com.homeybites.entities.Address;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.services.AddressService;

@RestController
@RequestMapping("/api/v1/")
public class AddressController {

	@Autowired
	private AddressService addressService;

	// add address of user
	@PostMapping("/user/{userId}/address")
	public ResponseEntity<ApiResponse> addAddress(@RequestBody Address address, @PathVariable Integer userId) {
		System.out.println(userId);
		Address savedAddress = this.addressService.addAddress(address, userId);
		ApiResponse response = new ApiResponse("Address added successfully..!", true, savedAddress);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// add address of tiffin provider
	@PostMapping("/tiffin/{providerId}/address")
	public ResponseEntity<ApiResponse> addTiffinProviderAddress(@RequestBody Address address,
			@PathVariable Integer providerId) {
		System.out.println(providerId);
		Address savedAddress = this.addressService.addTiffinProviderAddress(address, providerId);
		ApiResponse response = new ApiResponse("Address added successfully..!", true, savedAddress);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.CREATED);
	}

	// get address of specific user
	@GetMapping("/address/user/{userId}")
	public ResponseEntity<List<Address>> getAddressesOfUser(@PathVariable Integer userId) {
		List<Address> allAddress = this.addressService.getAllAddress(userId);
		return new ResponseEntity<List<Address>>(allAddress, HttpStatus.OK);
	}

	// get address
	@GetMapping("/address/{addId}")
	public ResponseEntity<Address> getAddress(@PathVariable Integer addId) {
		Address address = this.addressService.getAddress(addId);
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}

	// get single address of user
	@GetMapping("/user/{userId}/address/{addId}")
	public ResponseEntity<Address> getSingleAddress(@PathVariable Integer userId, @PathVariable Integer addId) {
		Address address = this.addressService.getSingleAddressOfUser(addId, userId);
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}

	// get all address
	@GetMapping("/addresses")
	public ResponseEntity<List<Address>> getAllAddress() {
		List<Address> allAddresses = this.addressService.getAllAddress();
		return new ResponseEntity<List<Address>>(allAddresses, HttpStatus.OK);
	}

	// update address
	@PutMapping("/address/{addId}")
	public ResponseEntity<ApiResponse> updateAddress(@RequestBody Address address, @PathVariable Integer addId) {
		Address updateAddress = this.addressService.updateAddress(address, addId);
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
}
