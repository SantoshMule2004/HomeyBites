package com.homeybites.services;

import com.homeybites.payloads.OtpDto;

public interface EmailService {

	// sending email
	void sendEmail(String to, String subject, String message);

	// generate OTP
	OtpDto generateOtp();

	// save OTP
	void saveOtp(String key, OtpDto otpDto);

	// get OTP
	OtpDto getOtp(String key);

	// delete OTP
	void removeOtp(String key);

}
