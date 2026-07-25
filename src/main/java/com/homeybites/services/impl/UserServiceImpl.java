package com.homeybites.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homeybites.entities.Address;
import com.homeybites.entities.User;
import com.homeybites.exceptions.ResourceNotFoundException;
import com.homeybites.payloads.UserDetailsProjection;
import com.homeybites.payloads.BusinessDetaisRequest;
import com.homeybites.payloads.OtpDto;
import com.homeybites.payloads.PageResponse;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.payloads.RegisterUserRequest;
import com.homeybites.payloads.UpdateUserDetailsDto;
import com.homeybites.payloads.UserFilterRequest;
import com.homeybites.payloads.UserInfo;
import com.homeybites.payloads.UserRoles;
import com.homeybites.repositories.AddressRepository;
import com.homeybites.repositories.UserRepository;
import com.homeybites.services.AddressService;
import com.homeybites.services.EmailService;
import com.homeybites.services.ProviderMenuService;
import com.homeybites.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	// @Autowired
	// private CartRepository cartRepository;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ProviderMenuService menuService;

	@Autowired
	private EmailService emailService;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	@Override
	@Transactional
	public User registerNewUser(RegisterUserRequest user, String role) {
		User newUser = new User();

		newUser.setFirstName(user.getFirstName());
		newUser.setMiddleName(user.getMiddleName());
		newUser.setLastName(user.getLastName());
		newUser.setEmailId(user.getEmailId());
		newUser.setPhoneNo(user.getPhoneNo());
		newUser.setVerified(user.isVerified());
		newUser.setUserRole(role);
		newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));

		User savedUser = this.userRepository.save(newUser);

		if (role.equals(UserRoles.ROLE_TIFFIN_PROVIDER.name())) {
			menuService.createDefaultMenus(savedUser.getUserId());
		}

		return savedUser;
	}

	@Override
	@Transactional
	public UserDetailsProjection saveBusinessDetails(Long providerId, BusinessDetaisRequest bdRequest) {
		User providerInfo = this.userRepository.findById(providerId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", providerId));

		System.out.println("BusinessDetaisRequest: " + bdRequest);

		providerInfo.setBusinessName(bdRequest.getBusinessName());
		providerInfo.setFoodLicenseNo(bdRequest.getFoodLicenseNo());
		providerInfo.setGSTIN(bdRequest.getGSTIN());
		providerInfo.setLatitude(bdRequest.getLatitude());
		providerInfo.setLongitude(bdRequest.getLongitude());
		providerInfo.setServiceRadius(bdRequest.getServiceRadius() * 1000);

		List<Address> addresses = addressService.getAllAddress(providerId.intValue());

		Address address;
		if (!addresses.isEmpty()) {
			address = addresses.get(0);
			System.out.println("Address already added..");
		} else {
			address = new Address();
			address.setUserId(providerId);
		}

		address.setAddressLine(bdRequest.getAddressLine());
		address.setArea(bdRequest.getArea());
		address.setLatitude(String.valueOf(bdRequest.getLatitude()));
		address.setLongitude(String.valueOf(bdRequest.getLongitude()));

		addressRepository.save(address);

		this.userRepository.save(providerInfo);

		return this.userRepository.getUserDetailsWithAddress(providerId);
	}

	@Override
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	@Override
	public void updateUser(User user, Long userId) {

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

		this.userRepository.save(existingUser);
	}

	@Override
	public void updateBusinessDetails(User user, Long userId, Integer addressId) {
		User existingUser = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		existingUser.setBusinessName(user.getBusinessName());

		// Address address = this.addressRepository.findById(addressId)
		// .orElseThrow(() -> new ResourceNotFoundException("Address", "Id",
		// addressId));
		//
		//// Address bdRequest = user.getAddress().getFirst();
		//
		// address.setAddressLine(bdRequest.getAddressLine());
		// address.setLandmark(bdRequest.getLandmark());
		// address.setCity(bdRequest.getCity());
		// address.setState(bdRequest.getState());
		// address.setCountry(bdRequest.getCountry());
		// address.setLatitude(bdRequest.getLatitude());
		// address.setLongitude(bdRequest.getLongitude());
		// address.setServiceRadius(bdRequest.getServiceRadius());
		//
		// this.addressRepository.save(address);

		this.userRepository.save(existingUser);
	}

	@Override
	public UserInfo getUser(Long userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return new UserInfo(user);
	}

	@Override
	public UserInfo getUserInfoByEmail(String emailId) {
		return this.userRepository.findUserByEmail(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", emailId));
	}

	@Override
	public UserInfo getUserByEmail(String emailId) {
		return this.userRepository.findUserByEmail(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", emailId));
	}

	@Override
	public UserInfo getLoggedInUser(String emailId) {
		return this.userRepository.findUserByEmail(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", emailId));
	}

	@Override
	public UserInfo getLoggedInProvider(String emailId) {
		return this.userRepository.findUserByEmail(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", emailId));
	}

	@Override
	public UserInfo getLoggedInAdmin(String emailId) {
		return this.userRepository.findUserByEmail(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", emailId));
	}

	@Override
	public boolean isUserPresent(String username) {
		return this.userRepository.existsByEmailId(username);
	}

	@Override
	public void deleteUser(Long userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		// List<UserCart> userCart = this.cartRepository.findByUser(user);
		// userCart.stream().forEach(cart -> cart.setMenuItem(null));
		// this.cartRepository.deleteAll(userCart);

		this.userRepository.delete(user);
	}

	@Override
	public void updateContactDetails(String number, Long userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setPhoneNo(number);
		this.userRepository.save(user);
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
	public String resetPassword(PasswordDto passwordDto, String emailId) {
		User user = this.userRepository.findByEmailId(emailId)
				.orElseThrow(() -> new ResourceNotFoundException("Email", "Id", emailId));

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
	public boolean resetPass(PasswordDto passwordDto, String emailId) {
		if (passwordDto.getNewPassword() != null && passwordDto.getcPassword() != null
				&& passwordDto.getNewPassword().equals(passwordDto.getcPassword())) {

			User user = this.userRepository.findByEmailId(emailId)
					.orElseThrow(() -> new ResourceNotFoundException("Email", "Id", emailId));

			user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
			this.userRepository.save(user);

			return true;
		}
		return false;
	}

	@Override
	public Long getAllUserCount() {
		return this.userRepository.countUsersExcludingRole("ROLE_ADMIN");
	}

	@Override
	public Long getUserCountByRole(String role) {
		return this.userRepository.countByUserRole(role);
	}

	@Override
	public User registerAdmin(User user) {
		user.setUserRole("ROLE_ADMIN");
		user.setVerified(true);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		User savedUser = this.userRepository.save(user);
		return savedUser;
	}

	@Override
	@Transactional
	public void updateUserEmail(String emailId, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		user.setEmailId(emailId);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void updateUserPhoneNo(String phoneNo, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		user.setPhoneNo(phoneNo);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public UserInfo updateUserDetails(UpdateUserDetailsDto dto, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());

		if (dto.getDob() != null && !dto.getDob().isBlank()) {
			user.setDob(dto.getDob().trim());
		}

		if (dto.getGender() != null && !dto.getGender().isBlank()) {
			user.setGender(dto.getGender().trim());
		}

		if (dto.getPhoneNo() != null && !dto.getPhoneNo().isBlank()) {
			user.setPhoneNo(dto.getPhoneNo().trim());
		}

		User savedUser = userRepository.save(user);

		return new UserInfo(savedUser);
	}

	@Override
	public PageResponse<UserDetailsProjection> getUsers(UserFilterRequest filter, Pageable pageable) {
		Page<UserDetailsProjection> page = this.userRepository.findUsers(filter.getUserRole(), filter.getSearch(), pageable);
		return new PageResponse<>(page);
	}

	@Override
	public UserDetailsProjection getUserDetails(Long providerId) {
		return this.userRepository.getUserDetailsWithAddress(providerId);
	}
}
