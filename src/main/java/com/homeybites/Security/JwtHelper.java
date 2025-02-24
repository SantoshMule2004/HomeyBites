package com.homeybites.Security;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtHelper {
	
	private static SecretKey SECRET_KEY;
	
    // directly using secretkey
	public JwtHelper() throws NoSuchAlgorithmException
	{
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		SECRET_KEY = keyGenerator.generateKey();
	}

	// generating JWT token
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)).and().signWith(get_key()).compact();
	}

	//generating secret key
	private SecretKey get_key() {
		return JwtHelper.SECRET_KEY;
	}
	
	
	// validate token

	// extracting username
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject); // extract user name from token
	}

	// extracting claims
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(get_key()).build().parseSignedClaims(token).getPayload();
	}

	// Validating token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// checking expiry of token
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// extracting expiry time
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}