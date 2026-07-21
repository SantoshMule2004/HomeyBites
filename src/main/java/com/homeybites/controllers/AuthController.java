package com.homeybites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.Security.JwtHelper;
import com.homeybites.entities.User;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.BusinessDetaisRequest;
import com.homeybites.payloads.JwtRequest;
import com.homeybites.payloads.JwtResponse;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.payloads.RegisterUserRequest;
import com.homeybites.payloads.UserInfo;
import com.homeybites.payloads.UserRoles;
import com.homeybites.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	// user login
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> verifyUser(@Valid @RequestBody JwtRequest jwtRequest) {
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

		String username = jwtRequest.getUsername();
		UserInfo user = this.userService.getLoggedInUser(username);
		JwtResponse response = new JwtResponse();

		if (user.getUserRole().equals(UserRoles.ROLE_NORMAL_USER.name())) {
			String token = jwtHelper.generateToken(user.getUserId(), jwtRequest.getUsername());
			response.setMessage("Welocme to HomeyBites..!");
			response.setStatus("success");
			response.setToken(token);
			response.setUser(user);

			return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
		} else {
			response.setMessage("Unable to login, Access denied..!");
			response.setStatus("error");
			return new ResponseEntity<JwtResponse>(response, HttpStatus.FORBIDDEN);
		}
	}

	private void doAuthenticate(String username, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {

			authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid username or password!");
		}
	}

	// new user register
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterUserRequest user) {
		boolean isPresent = this.userService.isUserPresent(user.getEmailId());

		if (isPresent) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("User already exists..", false),
					HttpStatus.CONFLICT);
		}

		if (user.getPassword() != null && user.getcPassword() != null
				&& user.getPassword().equals(user.getcPassword())) {

			this.userService.registerNewUser(user, UserRoles.ROLE_NORMAL_USER.name());

			return new ResponseEntity<ApiResponse>(new ApiResponse("Registeration successfully..!", true, null),
					HttpStatus.CREATED);
		}

		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Password and confirm password does not match..!", false), HttpStatus.BAD_REQUEST);
	}

	// sending OTP
	@PostMapping("/send-otp")
	public ResponseEntity<ApiResponse> sendOtp(@RequestParam String username) {

		boolean isPresent = this.userService.isUserPresent(username);

		if (isPresent)
			return new ResponseEntity<ApiResponse>(new ApiResponse("Email already exists..!", false),
					HttpStatus.CONFLICT);

		this.userService.sendOtp(username);

		return new ResponseEntity<ApiResponse>(
				new ApiResponse("OTP sent to your email-id successfully..! (validte for only 5 minutes.)", true),
				HttpStatus.OK);
	}

	// verifying OTP - at the time of registration
	@PostMapping("/verify-otp")
	public ResponseEntity<ApiResponse> verifyOtp(@RequestParam String otp, @RequestParam String username) {

		if (otp.isEmpty())
			return new ResponseEntity<ApiResponse>(
					new ApiResponse("Please, enter the otp sent to your email Id", false), HttpStatus.BAD_REQUEST);

		boolean verifiedOtp = this.userService.VerifyOtp(otp, username);

		if (verifiedOtp) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("OTP verified successfully..!", true),
					HttpStatus.OK);
		}

		return new ResponseEntity<ApiResponse>(new ApiResponse("OTP does not match..!", false), HttpStatus.BAD_REQUEST);
	}

	// sending OTP - when required updates when user is logged in
	@PostMapping("/update/send-otp")
	public ResponseEntity<ApiResponse> sendOtpForUpdate(@RequestParam String username) {
		this.userService.sendOtp(username);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("OTP sent to your email-id successfully..! (validte for only 5 minutes.)", true),
				HttpStatus.OK);
	}

	// reset password after forget
	@PostMapping("/reset-pass")
	public ResponseEntity<ApiResponse> ResetPasswordAfterVerificationHandler(
			@Valid @RequestBody PasswordDto passwordDto, @RequestParam String emailId) {

		System.out.println("Password" + passwordDto.getNewPassword());
		System.out.println("C-Password" + passwordDto.getcPassword());

		boolean response = this.userService.resetPass(passwordDto, emailId);
		if (response)
			return new ResponseEntity<ApiResponse>(new ApiResponse("Password updated successfully..!"), HttpStatus.OK);
		else
			return new ResponseEntity<ApiResponse>(new ApiResponse("Password and confirm password does not match.."),
					HttpStatus.BAD_REQUEST);
	}

	// login tiffin provider
	@PostMapping("/tiffin-provider/login")
	public ResponseEntity<JwtResponse> verifyTiffinProvider(@Valid @RequestBody JwtRequest jwtRequest) {
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

		String username = jwtRequest.getUsername();
		UserInfo user = this.userService.getLoggedInProvider(username);
		JwtResponse response = new JwtResponse();

		if (user.getUserRole().equals(UserRoles.ROLE_TIFFIN_PROVIDER.name())) {
			String token = jwtHelper.generateToken(user.getUserId(), jwtRequest.getUsername());
			response.setMessage("Welocme to HomeyBites..!");
			response.setStatus("success");
			response.setToken(token);
			response.setUser(user);

			return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
		} else {
			response.setMessage("Unable to login, Access denied..!");
			response.setStatus("error");
			return new ResponseEntity<JwtResponse>(response, HttpStatus.FORBIDDEN);
		}
	}

	// register tiffin provider
	@PostMapping("/tiffin-provider/register")
	public ResponseEntity<ApiResponse> RegisterTiffinProvider(@Valid @RequestBody RegisterUserRequest user) {

		boolean isPresent = this.userService.isUserPresent(user.getEmailId());

		if (isPresent) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Email Id already exists..", false),
					HttpStatus.CONFLICT);
		}

		if (user.getPassword() != null && user.getcPassword() != null
				&& user.getPassword().equals(user.getcPassword())) {

			User newUser = this.userService.registerNewUser(user, UserRoles.ROLE_TIFFIN_PROVIDER.name());

			return new ResponseEntity<ApiResponse>(new ApiResponse("Registeration successfully..!", true, newUser.getUserId()),
					HttpStatus.CREATED);
		}

		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Password and confirm password does not match..!", false), HttpStatus.BAD_REQUEST);
	}

	// add business details of tiffin provider
	@PutMapping("/tiffin-provider/{providerId}/business-details")
	public ResponseEntity<ApiResponse> addBusinnessDetails(@PathVariable Long providerId,
			@RequestBody BusinessDetaisRequest bdRequest) {
		this.userService.saveBusinessDetails(providerId, bdRequest);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Bussiness details added successfully..!", true, null),
				HttpStatus.OK);
	}

	// login admin
	@PostMapping("/admin/login")
	public ResponseEntity<JwtResponse> verifyAdmin(@Valid @RequestBody JwtRequest jwtRequest) {
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

		String username = jwtRequest.getUsername();
		UserInfo user = this.userService.getLoggedInAdmin(username);
		JwtResponse response = new JwtResponse();

		if (user.getUserRole().equals(UserRoles.ROLE_ADMIN.name())) {
			String token = jwtHelper.generateToken(user.getUserId(), jwtRequest.getUsername());
			response.setMessage("Welocme to HomeyBites..!");
			response.setStatus("success");
			response.setToken(token);
			response.setUser(user);

			return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
		} else {
			response.setMessage("Unable to login, Access denied..!");
			response.setStatus("error");
			return new ResponseEntity<JwtResponse>(response, HttpStatus.FORBIDDEN);
		}
	}

	// new admin register
	@PostMapping("/register/admin")
	public ResponseEntity<ApiResponse> registerAdmin(@Valid @RequestBody RegisterUserRequest user) {
		boolean isPresent = this.userService.isUserPresent(user.getEmailId());

		if (isPresent) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("User already exists..", false),
					HttpStatus.CONFLICT);
		}

		if (user.getPassword() != null && user.getcPassword() != null
				&& user.getPassword().equals(user.getcPassword())) {

			User registeredUser = this.userService.registerNewUser(user, UserRoles.ROLE_ADMIN.name());

			return new ResponseEntity<ApiResponse>(
					new ApiResponse("Admin registered successfully..!", true, registeredUser), HttpStatus.CREATED);
		}

		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Password and confirm password does not match..!", false), HttpStatus.BAD_REQUEST);
	}
}
