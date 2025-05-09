package com.homeybites.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.homeybites.entities.Address;
import com.homeybites.entities.User;
import com.homeybites.entities.UserCart;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.OtpDto;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.repositories.CartRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.EmailService;
import com.homeybites.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private EmailService emailService;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	@Override
	public User registerNewUser(User user) {
		user.setUserRole("ROLE_NORMAL_USER");
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User savedUser = this.userRepository.save(user);

		this.sendOtp(savedUser.getEmailId());

		return savedUser;
	}

	@Override
	public User registerTiffinProvider(User user) {
		user.setUserRole("ROLE_TIFFIN_PROVIDER");
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User savedUser = this.userRepository.save(user);

		this.sendOtp(savedUser.getEmailId());

		return savedUser;
	}

	@Override
	public User addBussinessDetails(Integer providerId, User user) {

		User providerInfo = this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));

		providerInfo.setBusinessName(user.getBusinessName());
		providerInfo.setFoodLicenseNo(user.getFoodLicenseNo());
		providerInfo.setGSTIN(user.getGSTIN());

		for (Address address : user.getAddress()) {
			address.setUser(providerInfo);
			providerInfo.getAddress().add(address);
		}

		return this.userRepository.save(providerInfo);
	}

	@Override
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public User updateUser(User user, Integer userId) {

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

		return this.userRepository.save(existingUser);
	}

	@Override
	public User updateBusinessDetails(User user, Integer userId, Integer addressId) {
		User existingUser = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		existingUser.setBusinessName(user.getBusinessName());

		Address address = this.addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("Address", "Id", addressId));

		Address newAddress = user.getAddress().getFirst();

		address.setAddressLine(newAddress.getAddressLine());
		address.setLandmark(newAddress.getLandmark());
		address.setCity(newAddress.getCity());
		address.setState(newAddress.getState());
		address.setCountry(newAddress.getCountry());
		address.setLatitude(newAddress.getLatitude());
		address.setLongitude(newAddress.getLongitude());
		address.setServiceRadius(newAddress.getServiceRadius());

		this.addressRepository.save(address);

		return this.userRepository.save(existingUser);
	}

	@Override
	public User getUser(Integer userId) {
		return this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
	}

	@Override
	public User getUserByEmail(String emailId) {
		return this.userRepository.findByEmailId(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", emailId));
	}

	@Override
	public boolean isUserPresent(String username) {
		return this.userRepository.existsByEmailId(username);
	}

	@Override
	public List<User> getAllUser() {
		return this.userRepository.findAll();
	}

	@Override
	public List<User> getUserByRole(String role) {
		return this.userRepository.findByUserRole(role);
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
	public User updateContactDetails(String number, Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setPhoneNo(number);
		return this.userRepository.save(user);
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
	public String resetPassword(PasswordDto passwordDto, User user) {
		if (passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {

			if (passwordDto.getNewPassword() != null && passwordDto.getcPassword() != null
					&& passwordDto.getNewPassword().equals(passwordDto.getcPassword())) {

				user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
				this.userRepository.save(user);

				return "Password updated successfully..!";
			}
			return "new password and confirm password does not match";
		}
		return "Wrong password..!";
	}

	@Override
	public boolean resetPass(PasswordDto passwordDto, User user) {

		if (passwordDto.getNewPassword() != null && passwordDto.getcPassword() != null
				&& passwordDto.getNewPassword().equals(passwordDto.getcPassword())) {

			user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
			this.userRepository.save(user);

			return true;
		}
		return false;
	}

	@Override
	public Integer getAllUserCount() {
		return this.userRepository.getAllUserCount("ROLE_ADMIN");
	}

	@Override
	public Integer getUserCountByRole(String role) {
		return this.userRepository.getUserCount(role);
	}

	@Override
	public User registerAdmin(User user) {
		user.setUserRole("ROLE_ADMIN");
		user.setVerified(true);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User savedUser = this.userRepository.save(user);
		return savedUser;
	}
}
