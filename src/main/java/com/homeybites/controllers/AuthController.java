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
import com.homeybites.payloads.JwtRequest;
import com.homeybites.payloads.JwtResponse;
import com.homeybites.payloads.PasswordDto;
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
		User user = this.userService.getUserByEmail(username);
		JwtResponse response = new JwtResponse();

		if (user.isVerified()) {
			if (user.getUserRole().equals("ROLE_NORMAL_USER")) {
				String token = jwtHelper.generateToken(jwtRequest.getUsername());
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
		} else {
			response.setMessage("Unable to login, email not verified..!");
			response.setStatus("error");
			return new ResponseEntity<JwtResponse>(response, HttpStatus.FORBIDDEN);

		}
	}

	// login tiffin provider
	@PostMapping("/tiffin-provider/login")
	public ResponseEntity<JwtResponse> verifyTiffinProvider(@Valid @RequestBody JwtRequest jwtRequest) {
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

		String username = jwtRequest.getUsername();
		User user = this.userService.getUserByEmail(username);
		JwtResponse response = new JwtResponse();

		if (user.isVerified()) {
			if (user.getUserRole().equals("ROLE_TIFFIN_PROVIDER")) {
				String token = jwtHelper.generateToken(jwtRequest.getUsername());
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
		} else {
			response.setMessage("Unable to login, email not verified..!");
			response.setStatus("error");
			return new ResponseEntity<JwtResponse>(response, HttpStatus.FORBIDDEN);
		}
	}

	// login tiffin provider
	@PostMapping("/admin/login")
	public ResponseEntity<JwtResponse> verifyAdmin(@Valid @RequestBody JwtRequest jwtRequest) {
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

		String username = jwtRequest.getUsername();
		User user = this.userService.getUserByEmail(username);
		JwtResponse response = new JwtResponse();

		if (user.isVerified()) {
			if (user.getUserRole().equals("ROLE_ADMIN")) {
				String token = jwtHelper.generateToken(jwtRequest.getUsername());
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
		} else {
			response.setMessage("Unable to login, email not verified..!");
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
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody User user) {
		boolean isPresent = this.userService.isUserPresent(user.getEmailId());

		if (isPresent) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("User already exists..", false),
					HttpStatus.CONFLICT);
		}

		if (user.getPassword() != null && user.getcPassword() != null
				&& user.getPassword().equals(user.getcPassword())) {

			User registeredUser = this.userService.registerNewUser(user);

			return new ResponseEntity<ApiResponse>(
					new ApiResponse("email-verification OTP has sent to your email id (valid only for 5 minutes)", true,
							registeredUser),
					HttpStatus.OK);
		}

		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Password and confirm password does not match..!", false), HttpStatus.BAD_REQUEST);
	}

	// register tiffin provider
	@PostMapping("/tiffin-provider/register")
	public ResponseEntity<ApiResponse> RegisterTiffinProvider(@Valid @RequestBody User user) {

		boolean isPresent = this.userService.isUserPresent(user.getEmailId());

		if (isPresent) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Email Id already exists..", false),
					HttpStatus.CONFLICT);
		}

		if (user.getPassword() != null && user.getcPassword() != null
				&& user.getPassword().equals(user.getcPassword())) {

			User registerTiffinProvider = this.userService.registerTiffinProvider(user);

			return new ResponseEntity<ApiResponse>(
					new ApiResponse("email-verification OTP has sent to your email id (valid only for 5 minutes)", true,
							registerTiffinProvider),
					HttpStatus.OK);
		}

		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Password and confirm password does not match..!", false), HttpStatus.BAD_REQUEST);
	}

	// add business details of tiffin provider
	@PutMapping("/tiffin-provider/{providerId}/business-details")
	public ResponseEntity<ApiResponse> addBusinnessDetails(@PathVariable Integer providerId, @RequestBody User user) {

		User providerInfo = this.userService.addBussinessDetails(providerId, user);

		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Bussiness details added successfully..!", true, providerInfo), HttpStatus.OK);
	}

	// verifying email through OTP
	@PostMapping("/verify-email")
	public ResponseEntity<ApiResponse> verifyEmail(@RequestParam String otp, @RequestParam String username) {

		User user = this.userService.getUserByEmail(username);

		if (otp.isEmpty())
			return new ResponseEntity<ApiResponse>(
					new ApiResponse("Please, enter the otp sent to your email Id", false), HttpStatus.BAD_REQUEST);

		boolean verifiedOtp = this.userService.VerifyOtp(otp, username);

		if (verifiedOtp) {
			user.setVerified(true);
			User savedUser = this.userService.saveUser(user);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Registeration successfully..!", true, savedUser),
					HttpStatus.CREATED);
		}

		return new ResponseEntity<ApiResponse>(new ApiResponse("OTP does not match..!", false), HttpStatus.BAD_REQUEST);
	}

	// Re-sending OTP
	@PostMapping("/resend-otp")
	public ResponseEntity<ApiResponse> resendOtp(@RequestParam String username) {

		User user = this.userService.getUserByEmail(username);
		if (user.isVerified())
			return new ResponseEntity<ApiResponse>(new ApiResponse("Email already verified..!", true),
					HttpStatus.CONFLICT);

		this.userService.sendOtp(username);

		return new ResponseEntity<ApiResponse>(
				new ApiResponse("OTP sent to your email-id successfully..! (validte for only 5 minutes.)", true),
				HttpStatus.OK);
	}

	// Re-sending OTP
	@PostMapping("/update/resend-otp")
	public ResponseEntity<ApiResponse> resendOtpForUpdate(@RequestParam String username) {
		this.userService.sendOtp(username);
		return new ResponseEntity<ApiResponse>(
				new ApiResponse("OTP sent to your email-id successfully..! (validte for only 5 minutes.)", true),
				HttpStatus.OK);
	}

	// verifying OTP
	@PostMapping("/verify-otp")
	public ResponseEntity<ApiResponse> verifyOtp(@RequestParam String otp, @RequestParam String username) {

		if (otp.isEmpty())
			return new ResponseEntity<ApiResponse>(
					new ApiResponse("Please, enter the otp sent to your email Id", false), HttpStatus.BAD_REQUEST);

		boolean verifiedOtp = this.userService.VerifyOtp(otp, username);

		if (verifiedOtp) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("OTP verified successfully..!", true),
					HttpStatus.CREATED);
		}

		return new ResponseEntity<ApiResponse>(new ApiResponse("OTP does not match..!", false), HttpStatus.BAD_REQUEST);
	}

	// forget-password
	@PostMapping("/forget-password")
	public ResponseEntity<ApiResponse> forgetPassword(@RequestParam String username) {

		System.out.println(username);
		boolean userPresent = this.userService.isUserPresent(username);

		if (!userPresent)
			return new ResponseEntity<ApiResponse>(
					new ApiResponse("User does not exists with email id: " + username, false), HttpStatus.NOT_FOUND);

		this.userService.sendOtp(username);
		ApiResponse response = new ApiResponse("OTP sent to your email id (validate for 5 minutes)", true);

		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}

	// reset password after forget
	@PostMapping("/reset-pass")
	public ResponseEntity<ApiResponse> ResetPasswordAfterVerificationHandler(
			@Valid @RequestBody PasswordDto passwordDto, @RequestParam String emailId) {

		System.out.println("Password" + passwordDto.getNewPassword());
		System.out.println("C-Password" + passwordDto.getcPassword());

		User user = this.userService.getUserByEmail(emailId);

		boolean response = this.userService.resetPass(passwordDto, user);
		if (response)
			return new ResponseEntity<ApiResponse>(new ApiResponse("Password updated successfully..!"), HttpStatus.OK);
		else
			return new ResponseEntity<ApiResponse>(new ApiResponse("Password and confirm password does nto match.."),
					HttpStatus.BAD_REQUEST);
	}
}
