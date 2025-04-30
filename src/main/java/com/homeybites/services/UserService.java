package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.User;
import com.homeybites.payloads.OtpDto;
import com.homeybites.payloads.PasswordDto;

public interface UserService {

	// register new user
	User registerNewUser(User user);

	// register tiffin provider
	User registerTiffinProvider(User user);
	
	Integer getAllUserCount();
	
	Integer getUserCountByRole(String role);

	// add business details of tiffin provider
	User addBussinessDetails(Integer providerId, User user);

	// save user
	User saveUser(User user);

	// updates user
	User updateUser(User user, Integer userId);

	// updates business details
	User updateBusinessDetails(User user, Integer userId, Integer addressId);

	// updating contact details
	User updateContactDetails(String number, Integer userId);

	// get single user
	User getUser(Integer userId);

	// get single user by email id
	User getUserByEmail(String emailId);

	// get single user by email id
	boolean isUserPresent(String username);

	// get all users
	List<User> getAllUser();

	// get all user with role
	List<User> getUserByRole(String role);

	// delete user
	void deleteUser(Integer userId);

	// sending otp for verification
	OtpDto sendOtp(String username);

	// verifying email
	boolean VerifyOtp(String enteredOtp, String username);

	// reset password
	String resetPassword(PasswordDto passwordDto, User user);

	// reset pssword after email verification (forget password)
	boolean resetPass(PasswordDto passwordDto, User user);

	// forget password
	boolean forgetPassword(String username);
}
