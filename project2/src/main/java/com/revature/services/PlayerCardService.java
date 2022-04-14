package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dtos.PlayerCardDTO;
import com.revature.exceptions.CardNotFoundException;
import com.revature.models.PlayerCard;
import com.revature.repositories.PlayerCardRepository;
import com.revature.repositories.UserRepository;

@Service
public class PlayerCardService {

	private PlayerCardRepository pcr;
	private UserRepository ur;
	
	@Autowired
	public PlayerCardService(PlayerCardRepository pcr,UserRepository ur) {
		super();
		this.pcr = pcr;
		this.ur = ur; 
	}
	
	public List<PlayerCardDTO> getAllCards()throws CardNotFoundException{
		List<PlayerCard> cards = pcr.findAll();
		
		List<PlayerCardDTO> cardsDto = new ArrayList<>();
		for(PlayerCard c : cards) {
			if(c.getCardOwner()!=null)
			{
				cardsDto.add(new PlayerCardDTO(c));
			}
			else
			{
				PlayerCardDTO p = new PlayerCardDTO();
				p.setId(c.getId());
				p.setName(c.getName());
				p.setPosition(c.getPosition());
				p.setDraftYear(c.getDraftYear());
				p.setPoints(c.getPoints());
				p.setRebounds(c.getRebounds());
				p.setAssists(c.getAssists());
				p.setUserDto(null);
				
				cardsDto.add(p);
			}
		}
		
		return cardsDto;
	}
	
	public List<PlayerCardDTO> getMyCards(int id)throws CardNotFoundException{
		List<PlayerCard> cards = pcr.findMyCards(ur.findById(id).get()); 
		
		List<PlayerCardDTO> cardsDto = new ArrayList<>();
		for(PlayerCard c : cards) {
			if(c.getCardOwner()!=null)
			{
				cardsDto.add(new PlayerCardDTO(c));
			}
			else
			{
				PlayerCardDTO p = new PlayerCardDTO();
				p.setId(c.getId());
				p.setName(c.getName());
				p.setPosition(c.getPosition());
				p.setDraftYear(c.getDraftYear());
				p.setPoints(c.getPoints());
				p.setRebounds(c.getRebounds());
				p.setAssists(c.getAssists());
				p.setUserDto(null);
				
				cardsDto.add(p);
			}
		}
		
		return cardsDto;
	}
	
	public List<PlayerCard> getAvailableCards(){
		return pcr.findAvailableCards();
	}
	
	public PlayerCardDTO getCardById(int id)throws CardNotFoundException {
		if(pcr.findCardById(id)==null) {
			throw new CardNotFoundException("No card was found of that id.");
		}
		return new PlayerCardDTO(pcr.findCardById(id));
	}
	
	public List<PlayerCardDTO> getCardsByName(String name) {
		if(pcr.findCardsByName(name).isEmpty()) {
			throw new CardNotFoundException("No card was found of that name.");
		}
		List<PlayerCard> cards = pcr.findCardsByName(name);
		List<PlayerCardDTO> cardsDto = new ArrayList<>();
		for(PlayerCard c : cards) {
			if(c.getCardOwner()!=null)
			{
				cardsDto.add(new PlayerCardDTO(c));
			}
			else
			{
				PlayerCardDTO p = new PlayerCardDTO();
				p.setId(c.getId());
				p.setName(c.getName());
				p.setPosition(c.getPosition());
				p.setDraftYear(c.getDraftYear());
				p.setPoints(c.getPoints());
				p.setRebounds(c.getRebounds());
				p.setAssists(c.getAssists());
				p.setUserDto(null);
				
				cardsDto.add(p);
			}
		}
		
		return cardsDto;
	}
	
	public List<PlayerCardDTO> getCardsByPoints(int points) {
		
		List<PlayerCard> cards = pcr.findCardsByPoints(points);
		List<PlayerCardDTO> cardsDto = new ArrayList<>();
		for(PlayerCard c : cards) {
			if(c.getCardOwner()!=null)
			{
				cardsDto.add(new PlayerCardDTO(c));
			}
			else
			{
				PlayerCardDTO p = new PlayerCardDTO();
				p.setId(c.getId());
				p.setName(c.getName());
				p.setPosition(c.getPosition());
				p.setDraftYear(c.getDraftYear());
				p.setPoints(c.getPoints());
				p.setRebounds(c.getRebounds());
				p.setAssists(c.getAssists());
				p.setUserDto(null);
				
				cardsDto.add(p);
			}
		}
		
		return cardsDto;
	}
	
	@Transactional
	public PlayerCard createCard(PlayerCard card) {
		return pcr.save(card);
	}
	
	@Transactional
	public PlayerCard updateCard(int id, PlayerCard card) {
		PlayerCard c = pcr.findById(id).orElseThrow(CardNotFoundException::new);
		card.setId(c.getId());
		return pcr.save(card);
	}
	
	@Transactional
	public boolean deleteCard(int id) throws CardNotFoundException {
		// try to retrieve a card by id, if it doesn't exist, throw an exception
		getCardById(id);

		pcr.deleteById(id);
		return true;
	}
}
