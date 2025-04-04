package com.homeybites.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.homeybites.payloads.ApiException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.mail.internet.AddressException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// handles user not found exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiException> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// handles data validation exceptions
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> MethodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException ex) {
		Map<String, String> response = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			response.put(fieldName, message);
		});
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
	}

	// handler for null pointer exception
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ApiException> NullPointerExceptionHandler(NullPointerException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<ApiException>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// handler for database constraint violation
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiException> SQLIntegrityConstraintViolationExceptionHandler(
			SQLIntegrityConstraintViolationException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException("Database constraint violation: " + message, false);
		return new ResponseEntity<ApiException>(response, HttpStatus.BAD_REQUEST);
	}

	// handler for bad request method
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiException> HttpRequestMethodNotSupportedExceptionHandler(
			HttpRequestMethodNotSupportedException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<ApiException>(response, HttpStatus.METHOD_NOT_ALLOWED);
	}

	// handler for resource not found
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiException> NoResourceFoundExceptionHandler(NoResourceFoundException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<ApiException>(response, HttpStatus.NOT_FOUND);
	}

	// handler for wrong credentials
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiException> BadCredentialsExceptionHandler(BadCredentialsException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<ApiException>(response, HttpStatus.UNAUTHORIZED);
	}

	// handler for wrong credentials
	@ExceptionHandler(AddressException.class)
	public ResponseEntity<ApiException> AddressExceptionHandler(AddressException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<ApiException>(response, HttpStatus.UNAUTHORIZED);
	}

	// Exception handlers for JWT token

	// IllegalArgumentException handler
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiException> IllegalArgumentExceptionHandler(IllegalArgumentException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<ApiException>(response, HttpStatus.BAD_REQUEST);
	}

	// handler for expired JWT token
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ApiException> ExpiredJwtExceptionHandler(ExpiredJwtException ex) {
//		String message = ex.getMessage();
		ApiException response = new ApiException("JWT token expired..!", false);
		return new ResponseEntity<ApiException>(response, HttpStatusCode.valueOf(498));
	}

	// handler for malformed data in JWT token
	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<ApiException> MalformedJwtExceptionHandler(MalformedJwtException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<ApiException>(response, HttpStatus.UNAUTHORIZED);
	}

	// handler JWT signature exception
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ApiException> SignatureExceptionHandler(SignatureException ex) {
//		String message = ex.getMessage();
		ApiException response = new ApiException("Invalid JWT token..!", false);
		return new ResponseEntity<ApiException>(response, HttpStatusCode.valueOf(498));
	}

	// handler for email connection timeout
	@ExceptionHandler(MailConnectException.class)
	public ResponseEntity<ApiException> MailConnectExceptionHandler(MailConnectException ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<ApiException>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// handles for any exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiException> anyExceptionHandler(Exception ex) {
		String message = ex.getMessage();
		ApiException response = new ApiException(message, false);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
