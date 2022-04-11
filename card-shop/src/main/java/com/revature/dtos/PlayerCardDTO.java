package com.revature.dtos;

import java.util.Objects;

import com.revature.models.PlayerCard;

public class PlayerCardDTO {

	private int id;
	private String name;
	private String position;
	private int draftYear;
	private int points;
	private int rebounds;
	private int assists;
	private UserDTO userDto;
	
	public PlayerCardDTO() {
		super();
	}

	public PlayerCardDTO(PlayerCard card) {
		super();
		this.id = card.getId();
		this.name = card.getName();
		this.position = card.getPosition();
		this.draftYear = card.getDraftYear();
		this.points = card.getPoints();
		this.rebounds = card.getRebounds();
		this.assists = card.getAssists();
		this.userDto = new UserDTO(card.getCardOwner());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getDraftYear() {
		return draftYear;
	}

	public void setDraftYear(int draftYear) {
		this.draftYear = draftYear;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getRebounds() {
		return rebounds;
	}

	public void setRebounds(int rebounds) {
		this.rebounds = rebounds;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}

	@Override
	public String toString() {
		return "PlayerCardDTO [id=" + id + ", name=" + name + ", position=" + position + ", draftYear=" + draftYear
				+ ", points=" + points + ", rebounds=" + rebounds + ", assists=" + assists + ", userDto=" + userDto
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(assists, draftYear, id, name, points, position, rebounds, userDto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerCardDTO other = (PlayerCardDTO) obj;
		return assists == other.assists && draftYear == other.draftYear && id == other.id
				&& Objects.equals(name, other.name) && points == other.points
				&& Objects.equals(position, other.position) && rebounds == other.rebounds
				&& Objects.equals(userDto, other.userDto);
	}
	
	
	
}
