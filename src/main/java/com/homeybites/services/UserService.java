package com.homeybites.services;

import java.util.List;

import com.homeybites.entities.User;
import com.homeybites.payloads.BusinessDetaisRequest;
import com.homeybites.payloads.OtpDto;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.payloads.RegisterUserRequest;
import com.homeybites.payloads.UserInfo;

public interface UserService {

	// register new user
	User registerNewUser(RegisterUserRequest user, String role);

	// register admin
	User registerAdmin(User user);

	Integer getAllUserCount();

	Integer getUserCountByRole(String role);

	// add business details of tiffin provider
	User addBussinessDetails(Integer providerId, BusinessDetaisRequest bdRequest);

	// save user
	User saveUser(User user);

	// updates user
	void updateUser(User user, Integer userId);

	// updates business details
	void updateBusinessDetails(User user, Integer userId, Integer addressId);

	// updating contact details
	void updateContactDetails(String number, Integer userId);

	// get single user
	UserInfo getUser(Integer userId);

	// get single user by email id
	UserInfo getUserInfoByEmail(String emailId);

	// get single user by email id
	UserInfo getUserByEmail(String emailId);

	// get Logged in user
	UserInfo getLoggedInUser(String emailId);

	// get Logged in tiffin provider
	UserInfo getLoggedInProvider(String emailId);

	// get Logged in Admin
	UserInfo getLoggedInAdmin(String emailId);

	// get single user by email id
	boolean isUserPresent(String username);

	// get all users
	List<User> getAllUser();

	// get all user with role
	List<UserInfo> getUserByRole(String role);

	// delete user
	void deleteUser(Integer userId);

	// sending otp for verification
	OtpDto sendOtp(String username);

	// verifying email
	boolean VerifyOtp(String enteredOtp, String username);

	// reset password
	String resetPassword(PasswordDto passwordDto, String emailId);

	// reset pssword after email verification (forget password)
	boolean resetPass(PasswordDto passwordDto, String emailId);

	// forget password
	boolean forgetPassword(String username);
}
