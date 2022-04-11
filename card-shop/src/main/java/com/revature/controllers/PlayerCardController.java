package com.revature.controllers;

import java.util.List;
import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.PlayerCardDTO;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.CardNotFoundException;
import com.revature.models.PlayerCard;
import com.revature.services.AuthService;
import com.revature.services.PlayerCardService;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/cards")
public class PlayerCardController {

	private PlayerCardService pcs;
	private AuthService authService;
	private static Logger log = LoggerFactory.getLogger(PlayerCardController.class); 
	
	@Autowired
	public PlayerCardController(PlayerCardService pcs, AuthService authService) {
		super();
		this.pcs = pcs;
		this.authService = authService; 
	}
	
	@GetMapping
	public ResponseEntity<List<PlayerCardDTO>> getAllCards(@RequestParam(name="name",required=false)String name,
														@RequestParam(name="points",required=false)String points){
		MDC.put("requestId", UUID.randomUUID().toString());
		
		if(name!=null) {
			return new ResponseEntity<>(pcs.getCardsByName(name),HttpStatus.OK);
		}
		if(points!=null ) {
			return new ResponseEntity<>(pcs.getCardsByPoints(Integer.parseInt(points)),HttpStatus.OK);
		}
		log.info("Cards retrieved.");
		return new ResponseEntity<>(pcs.getAllCards(), HttpStatus.OK);
	}
	
	@GetMapping("/my-cards")
	public ResponseEntity<List<PlayerCardDTO>> getMyCards(@RequestHeader(value="Authorization",required=false) String token){
		MDC.put("requestId", UUID.randomUUID().toString());
	
		Claims claims = authService.verify(token);
		return new ResponseEntity<>(pcs.getMyCards(Integer.parseInt(claims.get("id").toString())),HttpStatus.OK);
	}
	 
	@PostMapping 
	public ResponseEntity<String> postPlayerCard(@RequestBody PlayerCard card, 
												@RequestHeader(value="Authorization",required=false) String token) {
		/*-
		 * logic to return appropriate response based on creation success
		 */
		MDC.put("requestId", UUID.randomUUID().toString());

		Claims claims = authService.verify(token);
		if(!claims.get("role").toString().equals("ADMIN")) {
			log.warn("Unauthorized attempt to add a new card."); 
			throw new AuthorizationException();
		}
		
		pcs.createCard(card);
		log.info("New card created.");
		return new ResponseEntity<>("Card created successfully!", HttpStatus.CREATED);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<PlayerCardDTO> getById(@PathVariable("id")int id) {
		MDC.put("requestId", UUID.randomUUID().toString());
		return new ResponseEntity<>(pcs.getCardById(id), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<PlayerCard> updateCard(@PathVariable("id")int id, @RequestBody PlayerCard card,
												@RequestHeader(value="Authorization",required=false)String token) {
		MDC.put("requestId", UUID.randomUUID().toString());
		
		Claims claims = authService.verify(token);
		if(!claims.get("role").toString().equals("ADMIN")) {
			log.warn("Unauthorized attempt to add a new card.");
			throw new AuthorizationException();
		}
				
		log.info("Card of id "+ id + "was updated.");
		return new ResponseEntity<>(pcs.updateCard(id, card), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCardById(@PathVariable("id") int id,
												@RequestHeader(value="Authorization",required=false)String token) {
		MDC.put("requestId", UUID.randomUUID().toString());
			
		Claims claims = authService.verify(token);
		if(!claims.get("role").toString().equals("ADMIN")) {
			log.warn("Unauthorized attempt to add a new card.");
			throw new AuthorizationException();
		}
		
		if(pcs.getCardById(id)==null) {
			throw new CardNotFoundException();
		}
		
		pcs.deleteCard(id);
		log.info("Card of id "+ id + "was deleted.");
		return new ResponseEntity<>("Card was deleted",HttpStatus.OK);
	}
}
