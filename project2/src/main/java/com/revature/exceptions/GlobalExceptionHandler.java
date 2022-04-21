package com.revature.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		LOG.warn("Authentication exception was handled.", e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<String> handleAuthorizationException(AuthorizationException e) {
		LOG.warn("Authorization exception was handled.", e);
		return new ResponseEntity<>("Not authorized.", HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
		LOG.warn("UserNotFound exception was handled.", e);
		return new ResponseEntity<>("User was not found.", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CardNotFoundException.class)
	public ResponseEntity<String> handleCardNotFoundException(CardNotFoundException e) {
		LOG.debug("CardNotFound exception was handled.", e);
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
} 
