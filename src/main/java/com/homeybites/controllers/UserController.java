package com.homeybites.controllers;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.entities.User;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.payloads.UpdateEmailDto;
import com.homeybites.payloads.UpdatePhoneDto;
import com.homeybites.payloads.UpdateUserDetailsDto;
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
	public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody User user, @PathVariable Integer userId) {
		this.userService.updateUser(user, userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User updated successfully..!", true, null),
				HttpStatus.OK);
	}

	// update user email id
	@PutMapping("/{userId}/email")
	public ResponseEntity<ApiResponse> updateUserEmail(@Valid @RequestBody UpdateEmailDto dto,
			@PathVariable Integer userId) {
		this.userService.updateUserEmail(dto.getEmail(), userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Email updated successfully..!", true, null),
				HttpStatus.OK);
	}

	// update user phone number
	@PutMapping("/{userId}/phoneno")
	public ResponseEntity<ApiResponse> updateUserPhoneNo(@Valid @RequestBody UpdatePhoneDto dto,
			@PathVariable Integer userId) {
		this.userService.updateUserPhoneNo(dto.getPhoneNo(), userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Phone number updated successfully..!", true, null),
				HttpStatus.OK);
	}

	// update user details (firstname, lastname)
	@PutMapping("/{userId}/user-details")
	public ResponseEntity<ApiResponse> updateUserDetails(@Valid @RequestBody UpdateUserDetailsDto dto,
			@PathVariable Integer userId) {
		this.userService.updateUserDetails(dto.getFirstName(), dto.getLastName(), userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Email updated successfully..!", true, null),
				HttpStatus.OK);
	}

	// update business contact details
	@PutMapping("/contact-details/{userId}")
	public ResponseEntity<ApiResponse> updateContactDetails(@RequestParam String number, @PathVariable Integer userId) {
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
	public ResponseEntity<ApiResponse> getUser(@PathVariable Integer userId) {
		UserInfo user = this.userService.getUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User found..!", true, user), HttpStatus.OK);
	}

	// get user count
	@GetMapping("/all/count")
	public ResponseEntity<Integer> getAllUserCount() {
		Integer count = this.userService.getAllUserCount();
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	// get user count
	@GetMapping("/count")
	public ResponseEntity<Integer> getAllUserCount(@RequestParam String role) {
		Integer count = this.userService.getUserCountByRole(role);
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	// get user by email id
	@GetMapping("/email")
	public ResponseEntity<ApiResponse> getUserByEmail(@RequestParam String emailId) {
		UserInfo user = this.userService.getUserInfoByEmail(emailId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User found..!", true, user), HttpStatus.OK);
	}

	// get all users
	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> allUser = this.userService.getAllUser();
		return new ResponseEntity<List<User>>(allUser, HttpStatus.OK);
	}

	// get all users by role
	@GetMapping("/role")
	public ResponseEntity<List<UserInfo>> getAllUserByRole(@RequestParam String role) {
		List<UserInfo> allUser = this.userService.getUserByRole(role);
		return new ResponseEntity<List<UserInfo>>(allUser, HttpStatus.OK);
	}

	// delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
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
