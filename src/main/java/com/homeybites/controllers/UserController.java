package com.homeybites.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.User;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.UserDetailsProjection;
import com.homeybites.payloads.BusinessDetaisRequest;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.payloads.UpdateEmailDto;
import com.homeybites.payloads.UpdatePhoneDto;
import com.homeybites.payloads.UpdateUserDetailsDto;
import com.homeybites.payloads.UserFilterRequest;
import com.homeybites.payloads.UserInfo;
import com.homeybites.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	// update user
	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody User user, @PathVariable Long userId) {
		this.userService.updateUser(user, userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User updated successfully..!", true, null),
				HttpStatus.OK);
	}

	// checking working of @RequestAttribute
	@GetMapping("/check")
	public ResponseEntity<ApiResponse> getUserId(@RequestAttribute Long userId) {
		UserInfo user = this.userService.getUser(userId);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("User fetched from @RequestAttribute - " + userId, true, user), HttpStatus.OK);
	}

	// update user email id
	@PutMapping("/{userId}/email")
	public ResponseEntity<ApiResponse> updateUserEmail(@Valid @RequestBody UpdateEmailDto dto,
			@PathVariable Long userId) {
		this.userService.updateUserEmail(dto.getEmail(), userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Email updated successfully..!", true, null),
				HttpStatus.OK);
	}

	// update user phone number
	@PutMapping("/{userId}/phoneno")
	public ResponseEntity<ApiResponse> updateUserPhoneNo(@Valid @RequestBody UpdatePhoneDto dto,
			@PathVariable Long userId) {
		this.userService.updateUserPhoneNo(dto.getPhoneNo(), userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Phone number updated successfully..!", true, null),
				HttpStatus.OK);
	}

	// update user details (firstname, lastname)
	@PutMapping("/{userId}/user-details")
	public ResponseEntity<ApiResponse> updateUserDetails(@Valid @RequestBody UpdateUserDetailsDto dto,
			@PathVariable Long userId) {
		UserInfo userDetails = this.userService.updateUserDetails(dto, userId);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("User details updated successfully..!", true, userDetails), HttpStatus.OK);
	}

	// update business details
	@PutMapping("/business-details/{userId}")
	public ResponseEntity<ApiResponse> updateBusinessDetails(@RequestBody BusinessDetaisRequest request, @PathVariable Long userId) {
		UserDetailsProjection details = this.userService.saveBusinessDetails(userId, request);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Business details updated successfully..!", true, details),
				HttpStatus.OK);
	}

	// update business contact details
	@PutMapping("/contact-details/{userId}")
	public ResponseEntity<ApiResponse> updateContactDetails(@RequestParam String number, @PathVariable Long userId) {
		this.userService.updateContactDetails(number, userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Contact details updated successfully..!", true, null),
				HttpStatus.OK);
	}

	// getting logged in user
	@GetMapping("/current-user")
	public ResponseEntity<ApiResponse> getLoggedInUser(Principal principal) {
		String name = principal.getName();
		UserInfo user = this.userService.getLoggedInUser(name);
		return new ResponseEntity<ApiResponse>(new ApiResponse("current user", true, user), HttpStatus.OK);
	}

	// get user by id
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId) {
		UserInfo user = this.userService.getUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User found..!", true, user), HttpStatus.OK);
	}

	// get user count
	@GetMapping("/all/count")
	public ResponseEntity<Long> getAllUserCount() {
		Long count = this.userService.getAllUserCount();
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	// get user count
	@GetMapping("/count")
	public ResponseEntity<Long> getAllUserCount(@RequestParam String role) {
		Long count = this.userService.getUserCountByRole(role);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

	// get user by email id
	@GetMapping("/email")
	public ResponseEntity<ApiResponse> getUserByEmail(@RequestParam String emailId) {
		UserInfo user = this.userService.getUserInfoByEmail(emailId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User found..!", true, user), HttpStatus.OK);
	}

	// get all users
	@GetMapping("/")
	public ResponseEntity<PageResponse<UserDetailsProjection>> getAllUser(@ModelAttribute UserFilterRequest filter,
			Pageable pageable) {
		PageResponse<UserDetailsProjection> allUser = this.userService.getUsers(filter, pageable);
		return new ResponseEntity<>(allUser, HttpStatus.OK);
	}

	// get business details
	@GetMapping("/user-details")
	public ResponseEntity<UserDetailsProjection> getUserDetails(@RequestParam Long providerId) {
		UserDetailsProjection bp = this.userService.getUserDetails(providerId);
		return new ResponseEntity<>(bp, HttpStatus.OK);
	}

	// delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully..!", true),
				HttpStatus.NO_CONTENT);
	}

	// reset password
	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponse> ResetPasswordHandler(@Valid @RequestBody PasswordDto passwordDto,
			Principal principal) {
		String name = principal.getName();
		String response = this.userService.resetPassword(passwordDto, name);
		return new ResponseEntity<ApiResponse>(new ApiResponse(response), HttpStatus.OK);
	}
}
