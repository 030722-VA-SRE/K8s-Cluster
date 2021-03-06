package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dtos.PlayerCardDTO;
import com.revature.exceptions.CardNotFoundException;
import com.revature.models.PlayerCard;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.PlayerCardRepository;
import com.revature.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class PlayerCardServiceTest {
	
	static PlayerCardRepository cardRepo;
	static UserRepository userRepo;
	static PlayerCardService cardServ;
	static List<PlayerCard> cards = new ArrayList<>();
	static List<PlayerCardDTO> cardsDto = new ArrayList<>();
	static PlayerCard card;
	static PlayerCard card2;
	static PlayerCardDTO cardDto;
	static PlayerCardDTO cardDto2;
	static User user;
	
	@BeforeAll
	public static void setup() {
		cardRepo = mock(PlayerCardRepository.class);
		userRepo = mock(UserRepository.class);
		cardServ = new PlayerCardService(cardRepo,userRepo);
		user = new User(1,"xxx","yyy",Role.ADMIN);
		card = new PlayerCard(1,"x","x",1999,1,1,1,user);
		card2 = new PlayerCard(2,"y","x",1999,1,1,1,null);
		cardDto = new PlayerCardDTO(card);
		cardDto2 = new PlayerCardDTO();
		cards.add(card);
		cardsDto.add(cardDto);
	}
	
	@Test
	public void getAllCardsTest() {
		cards.add(card2);
		PlayerCardDTO pcdto = new PlayerCardDTO();
		pcdto.setId(card2.getId());
		pcdto.setName(card2.getName());
		pcdto.setPosition(card2.getPosition());
		pcdto.setDraftYear(card2.getDraftYear());
		pcdto.setPoints(card2.getPoints());
		pcdto.setRebounds(card2.getRebounds());
		pcdto.setAssists(card2.getAssists());
		pcdto.setUserDto(null);
		
		cardsDto.add(pcdto);
		when(cardRepo.findAll()).thenReturn(cards);
		assertEquals(cardsDto, cardServ.getAllCards());
	}
	
	@Test
	public void getAvailableCardsTest() {
		when(cardRepo.findAvailableCards()).thenReturn(cards);
		assertEquals(cards, cardServ.getAvailableCards());
	}
	
	@Test
	public void getCardByIdTest() {
		cardDto2.setId(card2.getId());
		cardDto2.setName(card2.getName());
		cardDto2.setPosition(card2.getPosition());
		cardDto2.setDraftYear(card2.getDraftYear());
		cardDto2.setPoints(card2.getPoints());
		cardDto2.setRebounds(card2.getRebounds());
		cardDto2.setAssists(card2.getAssists());
		cardDto2.setUserDto(null);
		when(cardRepo.findCardById(1)).thenReturn(card);
		when(cardRepo.findCardById(2)).thenReturn(card2);
		assertEquals(cardDto, cardServ.getCardById(1));
		assertEquals(cardDto2, cardServ.getCardById(2));
	}
	
	@Test
	public void getCardByIdFailTest() {
		when(cardRepo.findCardById(4)).thenReturn(null);
		assertThrows(CardNotFoundException.class, ()->{
			cardServ.getCardById(4);
		});
	}
	
	@Test
	public void getCardsByNameTest() {
		when(cardRepo.findCardsByName("x")).thenReturn(cards);
		assertEquals(cardsDto, cardServ.getCardsByName("x"));
	}
	
	@Test
	public void getCardsByNameFailTest() {
		when(cardRepo.findCardsByName("ooo")).thenReturn(new ArrayList<PlayerCard>());
		assertThrows(CardNotFoundException.class, ()->{
			cardServ.getCardsByName("ooo");
		});
	}
	
	@Test
	public void getCardsByPointsTest() {
		when(cardRepo.findCardsByPoints(1)).thenReturn(cards);
		assertEquals(cardsDto, cardServ.getCardsByPoints(1));
	}

//	@Test
//	public void getMyCardsTest() {
//		when(cardRepo.findMyCards(user)).thenReturn(cards);
//		assertEquals(cardsDto, cardServ.getMyCards(0));
//	}
	
	@Test
	public void createCardTest() {
		when(cardRepo.save(card)).thenReturn(card);
		assertEquals(card, cardServ.createCard(card));
	}
	
	@Test
	public void updateCardTest() {
		when(cardRepo.findById(1)).thenReturn(Optional.of(card));
		when(cardRepo.save(card)).thenReturn(card);
		assertEquals(card, cardServ.updateCard(1, card));
	}
	
	@Test
	public void deleteCardByIdTest() {
		when(cardRepo.getById(1)).thenReturn(card);
		assertEquals(true,cardServ.deleteCard(1));
	}
	
	@Test
	public void convertToDtoTest() {
		assertEquals(cardsDto,cardServ.convertToDto(cards));
	}
}
