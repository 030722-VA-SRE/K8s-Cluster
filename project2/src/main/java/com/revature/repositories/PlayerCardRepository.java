package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.models.PlayerCard;
import com.revature.models.User;

@Repository
public interface PlayerCardRepository extends JpaRepository<PlayerCard,Integer>{
	
	public PlayerCard findCardById(int id);
	public List<PlayerCard> findCardsByName(String name);
	@Query("select p from PlayerCard p where p.points >= :points")
	public List<PlayerCard> findCardsByPoints(int points);
	@Query("select p from PlayerCard p where p.cardOwner = null")
	public List<PlayerCard> findAvailableCards();
	@Query("select p from PlayerCard p where p.cardOwner = :user")
	public List<PlayerCard> findMyCards(User user);
}
