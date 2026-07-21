package com.homeybites.services;

import org.springframework.data.domain.Pageable;

import com.homeybites.entities.User;
import com.homeybites.payloads.BusinessDetailsProjection;
import com.homeybites.payloads.BusinessDetaisRequest;
import com.homeybites.payloads.OtpDto;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.payloads.RegisterUserRequest;
import com.homeybites.payloads.UpdateUserDetailsDto;
import com.homeybites.payloads.UserFilterRequest;
import com.homeybites.payloads.UserInfo;

public interface UserService {

	// register new user
	User registerNewUser(RegisterUserRequest user, String role);

	// register admin
	User registerAdmin(User user);

	Long getAllUserCount();
	
	PageResponse<UserInfo> getUsers(UserFilterRequest filter, Pageable pageable);

	Long getUserCountByRole(String role);

	// add business details of tiffin provider
	BusinessDetailsProjection saveBusinessDetails(Long providerId, BusinessDetaisRequest bdRequest);
	
	BusinessDetailsProjection getBusinessDetails(Long providerId);

	// save user
	User saveUser(User user);

	// updates user
	void updateUser(User user, Long userId);

	void updateUserEmail(String emailId, Long userId);
	void updateUserPhoneNo(String phoneNo, Long userId);
	UserInfo updateUserDetails(UpdateUserDetailsDto dto, Long userId);

	// updates business details
	void updateBusinessDetails(User user, Long userId, Integer addressId);

	// updating contact details
	void updateContactDetails(String number, Long userId);

	// get single user
	UserInfo getUser(Long userId);

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

	// delete user
	void deleteUser(Long userId);

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
