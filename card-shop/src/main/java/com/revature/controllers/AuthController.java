package com.revature.controllers;

import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthService authServ;
	private static Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	public AuthController(AuthService authServ) {
		super();
		this.authServ = authServ;
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam(name="username")String username, @RequestParam(name="password")String password){
		// Generated a request id for new requests to be handled, this id can be attached to logs to show the flow of the request through the application
		MDC.put("requestId", UUID.randomUUID().toString());
		log.debug("Starting login.");
		
		// generates a token if credentials are correct
		String token = authServ.login(username, password);

		// setting headers to be returned to the front end
		HttpHeaders hh = new HttpHeaders();
		hh.set("Authorization", token); 
		
		log.debug("Ending login.");
		log.info(username+"'s login was successful.");
		// constructor for response entity(body, headers, status)
		return new ResponseEntity<>("Login successful.", hh, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestParam(name="username")String username, @RequestParam(name="password")String password){
		MDC.put("requestId", UUID.randomUUID().toString());
		log.debug("Starting register.");
		String token = authServ.register(new User(username,password));
		
		// set authorization header
		HttpHeaders hh = new HttpHeaders();
		hh.set("Authorization", token);
		
		log.debug("Ending register.");
		log.info(username+" successfully registered.");
		
		return new ResponseEntity<>("Registration successful.",hh,HttpStatus.OK);
	}
	
}
