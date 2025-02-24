package com.homeybites.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper jwtHelper;

	private HandlerExceptionResolver handlerExceptionResolver;

	@Autowired
	ApplicationContext context;

	public JwtFilter(HandlerExceptionResolver handlerExceptionResolver) {
		super();
		this.handlerExceptionResolver = handlerExceptionResolver;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		try {

			if ((authHeader != null) && (authHeader.startsWith("Bearer "))) {

				token = authHeader.substring(7);
				
				username = jwtHelper.extractUsername(token);
				
//				try {
//
//					username = jwtHelper.extractUsername(token);
//
//				} catch (IllegalArgumentException e) {
//					logger.info("Illegal Argument while fetching the username !!");
//					// handlerExceptionResolver.resolveException(request, response, null, e);
//					e.printStackTrace();
//				} catch (ExpiredJwtException e) {
//					logger.info("Given jwt token is expired !!");
//					// handlerExceptionResolver.resolveException(request, response, null, e);
//					e.printStackTrace();
//				} catch (MalformedJwtException e) {
//					logger.info("Some changed has done in token !! Invalid Token");
//					// handlerExceptionResolver.resolveException(request, response, null, e);
//					e.printStackTrace();
//				} catch (SignatureException e) {
//					logger.info("JWT signature does not match locally computed signature");
//					// handlerExceptionResolver.resolveException(request, response, null, e);
//					e.printStackTrace();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails userDetails = context.getBean(CustomUserDetailService.class).loadUserByUsername(username);

				if (jwtHelper.validateToken(token, userDetails)) {

					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails.getUsername(), null, userDetails.getAuthorities());

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				} else {
					System.out.println("Validation failed.");
				}
			}
			filterChain.doFilter(request, response);
		} catch (IllegalArgumentException | ExpiredJwtException | MalformedJwtException | SignatureException e) {
			handlerExceptionResolver.resolveException(request, response, null, e);
		}
	}
}
