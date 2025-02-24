package com.homeybites.services;

import java.util.List;

import com.homeybites.payloads.OtpDto;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.payloads.UserDto;

public interface UserService {
	
	//register new user
	UserDto registerNewUser(UserDto userDto);
	
	//register tiffin provider
	UserDto registerTiffinProvider(UserDto userDto);
	
	// save user
	UserDto saveUser(UserDto userDto);
	
	//updates user
	UserDto updateUser(UserDto userDto, Integer userId);
	
	//updating contact details
	UserDto updateContactDetails(UserDto userDto, Integer userId);
	
	//get single user 
	UserDto getUser(Integer userId);
	
	//get single user by email id 
	UserDto getUserByEmail(String emailId);
	
	//get single user by email id 
	boolean isUserPresent(String username);
	
	//get all users
	List<UserDto> getAllUser();
	
	// get all user with role
	List<UserDto> getUserByRole(String role);
	
	//delete user
	void deleteUser(Integer userId);
	
	//sending otp for verification
	OtpDto sendOtp(String username);
	
	//verifying email
	boolean VerifyOtp(String enteredOtp, String username);
	
	// reset password
	String resetPassword(PasswordDto passwordDto, UserDto userDto);
	
	//reset pssword after email verification (forget password)
	String resetPass(PasswordDto passwordDto, UserDto userDto);
	
	//forget password
	boolean forgetPassword(String username);
}
