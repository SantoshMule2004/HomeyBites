package com.homeybites.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.homeybites.entities.User;
import com.homeybites.entities.UserCart;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.OtpDto;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.payloads.UserDto;
import com.homeybites.repositories.CartRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.EmailService;
import com.homeybites.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository  cartRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailService emailService;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);
		user.setUserRole("NORMAL_USER");
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User savedUser = this.userRepository.save(user);
		
		// creating cart for every user after registration
		UserCart cart = new UserCart();
		cart.setUser(savedUser);
		this.cartRepository.save(cart);
		
		this.sendOtp(savedUser.getEmailId());

		return this.modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto registerTiffinProvider(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setUserRole("TIFFIN_PROVIDER");
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User savedUser = this.userRepository.save(user);
		// this.sendOtp(savedUser.getEmailId());

		return this.modelMapper.map(savedUser, UserDto.class);
	}
	
	@Override
	public UserDto saveUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);
		User save = this.userRepository.save(user);

		return this.modelMapper.map(save, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto user, Integer userId) {

		User existingUser = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setMiddleName(user.getMiddleName());
		existingUser.setPhoneNo(user.getPhoneNo());
		existingUser.setPassword(user.getPassword());
		existingUser.setGender(user.getGender());
		existingUser.setDob(user.getDob());
		existingUser.setUniversityName(user.getUniversityName());
		existingUser.setCourse(user.getCourse());
		existingUser.setUniversityName(user.getUniversityName());
		existingUser.setCompanyName(user.getCompanyName());
		existingUser.setCourse(user.getCourse());

		User updatedUser = this.userRepository.save(existingUser);

		return this.modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public UserDto getUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto getUserByEmail(String emailId) {
		User user = this.userRepository.findByEmailId(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", emailId));
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public boolean isUserPresent(String username) {
		boolean existsByEmailId = this.userRepository.existsByEmailId(username);
		return existsByEmailId;
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> allUsers = this.userRepository.findAll();
		List<UserDto> all = allUsers.stream().map(user -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return all;
	}

	@Override
	public List<UserDto> getUserByRole(String role) {
		List<User> all = this.userRepository.findByUserRole(role);
		List<UserDto> users = all.stream().map(user -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return users;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		List<UserCart> userCart = this.cartRepository.findByUser(user);
		userCart.stream().forEach(cart -> cart.setMenuItem(null));
		this.cartRepository.deleteAll(userCart);
		
		this.userRepository.delete(user);
	}

	@Override
	public UserDto updateContactDetails(UserDto userDto, Integer userId) {

		return null;
	}

	@Override
	public boolean forgetPassword(String username) {

		return false;
	}

	@Override
	public OtpDto sendOtp(String username) {
		OtpDto otpDto = this.emailService.generateOtp();

		this.emailService.saveOtp(username, otpDto);

		String otp = otpDto.getOtp();

		String subject = "Email verification";
		String message = "Your OTP for email verification for HomeyBites is \n" + otp;

		this.emailService.sendEmail(username, subject, message);

		return otpDto;
	}

	@Override
	public boolean VerifyOtp(String enteredOtp, String username) {

		OtpDto otpDto = this.emailService.getOtp(username);

		// checks if entered OTP is null or not
		if (enteredOtp.isEmpty())
			return false;

		// checks if OTP is expired
		if (otpDto.getExpirationTime().isBefore(LocalDateTime.now())) {
			this.emailService.removeOtp(username);
			return false;
		}
	
		if (enteredOtp.equals(otpDto.getOtp())) {
			this.emailService.removeOtp(username);
			return true;
		}
		return false;
	}

	@Override
	public String resetPassword(PasswordDto passwordDto, UserDto userDto) {
		if (passwordEncoder.matches(passwordDto.getOldPassword(), userDto.getPassword())) {

			if (passwordDto.getNewPassword() != null && passwordDto.getcPassword() != null
					&& passwordDto.getNewPassword().equals(passwordDto.getcPassword())) {
				
				userDto.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
				User user = this.modelMapper.map(userDto, User.class);
				this.userRepository.save(user);
				
				return "Password updated successfully..!";
			}
			return "new password and confirm password does not match";
		}
		return "Wrong password..!";
	}

	@Override
	public String resetPass(PasswordDto passwordDto, UserDto userDto) {
		
			if (passwordDto.getNewPassword() != null && passwordDto.getcPassword() != null
					&& passwordDto.getNewPassword().equals(passwordDto.getcPassword())) {
				
				userDto.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
				User user = this.modelMapper.map(userDto, User.class);
				this.userRepository.save(user);
				
				return "Password updated successfully..!";
			}
			return "new password and confirm password does not match";
	}
}
