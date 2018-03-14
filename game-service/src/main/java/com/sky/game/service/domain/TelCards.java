package com.sky.game.service.domain;

import java.util.List;

public class TelCards {

	public TelCards() {
		// TODO Auto-generated constructor stub
	}
	List<TelCard> telCard;
	public List<TelCard> getTelCard() {
		return telCard;
	}
	public void setTelCard(List<TelCard> telCard) {
		this.telCard = telCard;
	}
	public TelCards(List<TelCard> telCard) {
		super();
		this.telCard = telCard;
	}
	@Override
	public String toString() {
		return "TelCards [telCard=" + telCard + "]";
	}
	
}
