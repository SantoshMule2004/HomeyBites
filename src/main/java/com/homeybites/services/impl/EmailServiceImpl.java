package com.homeybites.services.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.homeybites.payloads.OtpDto;
import com.homeybites.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	private final ConcurrentHashMap<String, OtpDto> otpStorage = new ConcurrentHashMap<>();

	@Autowired
	private JavaMailSender mailSender;
	
	private static final String NUMERIC_STRING = "0123456789";
    private static final SecureRandom random = new SecureRandom();

	@Override
	public void sendEmail(String to, String subject, String message) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setCc(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);

		mailSender.send(mailMessage);
	}

	@Override
	public OtpDto generateOtp() {
	    	
		StringBuilder otp = new StringBuilder();
	        
	    for (int i = 0; i < 4; i++) {
	    	int index = random.nextInt(NUMERIC_STRING.length());
	        otp.append(NUMERIC_STRING.charAt(index));
	    }
	    
	    String OTP = otp.toString();
	    
	    OtpDto otpDto = new OtpDto();
	    otpDto.setOtp(OTP);
	    otpDto.setExpirationTime(LocalDateTime.now().plusMinutes(5));
	        
	    return otpDto;
	}

	@Override
	public void saveOtp(String key, OtpDto otpDto) {
		this.otpStorage.put(key, otpDto);
	}

	@Override
	public OtpDto getOtp(String key) {
		OtpDto otpDto = this.otpStorage.get(key);
		return otpDto;
	}

	@Override
	public void removeOtp(String key) {
		this.otpStorage.remove(key);
	}
}
