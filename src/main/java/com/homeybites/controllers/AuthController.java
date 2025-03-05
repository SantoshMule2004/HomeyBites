
package com.homeybites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeybites.Security.JwtHelper;
import com.homeybites.payloads.ApiResponse;
import com.homeybites.payloads.JwtRequest;
import com.homeybites.payloads.JwtResponse;
import com.homeybites.payloads.PasswordDto;
import com.homeybites.payloads.UserDto;
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

	// login
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> verifyUser(@Valid @RequestBody JwtRequest jwtRequest) {
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

		String username = jwtRequest.getUsername();
		UserDto userDto = this.userService.getUserByEmail(username);
		JwtResponse response = new JwtResponse();

		if (userDto.isVerified()) {
			String token = jwtHelper.generateToken(jwtRequest.getUsername());
			response.setMessage("User logged in successfully..!");
			response.setStatus("success");
			response.setToken(token);
			response.setUser(userDto);

			return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
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
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserDto userDto) {
		boolean isPresent = this.userService.isUserPresent(userDto.getEmailId());

		if (isPresent) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("User already exists..", false),
					HttpStatus.CONFLICT);
		}

		System.out.println("Password" + userDto.getPassword());
		System.out.println("C Password" + userDto.getCpassword());

		if (userDto.getPassword() != null && userDto.getCpassword() != null
				&& userDto.getPassword().equals(userDto.getCpassword())) {

			UserDto registeredUser = this.userService.registerNewUser(userDto);

			return new ResponseEntity<ApiResponse>(
					new ApiResponse("email-verification OTP has sent to your email id (valid only for 5 minutes)", true,
							registeredUser),
					HttpStatus.OK);
		}

		return new ResponseEntity<ApiResponse>(
				new ApiResponse("Password and confirm password does not match..!", false), HttpStatus.BAD_REQUEST);
	}

	// register tiffin provider
	@PostMapping("/tiffin-provider")
	public ResponseEntity<ApiResponse> RegisterTiffinProvider(@RequestBody UserDto userDto) {

		boolean isPresent = this.userService.isUserPresent(userDto.getEmailId());

		if (isPresent) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Email Id already exists..", false),
					HttpStatus.CONFLICT);
		}

		// session.setAttribute("userDto", userDto);
		// this.userService.sendOtp(userDto.getEmailId());

		UserDto registerTiffinProvider = this.userService.registerTiffinProvider(userDto);

		return new ResponseEntity<ApiResponse>(new ApiResponse("registered success", true, registerTiffinProvider),
				HttpStatus.OK);
	}

	// verifying email through OTP
	@PostMapping("/verify-email")
	public ResponseEntity<ApiResponse> verifyEmail(@RequestParam String otp, @RequestParam String username) {

		UserDto userDto = this.userService.getUserByEmail(username);

		if (otp.isEmpty())
			return new ResponseEntity<ApiResponse>(
					new ApiResponse("Please, enter the otp sent to your email Id", false), HttpStatus.BAD_REQUEST);

		boolean verifiedOtp = this.userService.VerifyOtp(otp, username);

		if (verifiedOtp) {
			userDto.setVerified(true);
			UserDto savedUser = this.userService.saveUser(userDto);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Registeration successfully..!", true, savedUser),
					HttpStatus.CREATED);
		}

		return new ResponseEntity<ApiResponse>(new ApiResponse("OTP does not match..!", false), HttpStatus.BAD_REQUEST);
	}

	// Re-sending OTP
	@PostMapping("/resend-otp")
	public ResponseEntity<ApiResponse> resendOtp(@RequestParam String username) {

		UserDto userDto = this.userService.getUserByEmail(username);
		if (userDto.isVerified())
			return new ResponseEntity<ApiResponse>(new ApiResponse("Email already verified..!", true),
					HttpStatus.CONFLICT);

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

		UserDto userDto = this.userService.getUserByEmail(emailId);

		String response = this.userService.resetPass(passwordDto, userDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse(response), HttpStatus.OK);
	}
}
