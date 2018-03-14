package com.sky.game.texas.domain.game;

import com.sky.game.protocol.commons.GT0001Beans.State;

public enum GameTexasGamePositionStatesEnum {
	Normal(0),
	Dealer(1),
	SmallBlind(2),
	BigBlind(4);
	
	
	public int value;

	private GameTexasGamePositionStatesEnum(int value) {
		this.value = value;
	}
	
	
	public boolean compare(int state){
		return this.value==state;
	}


	/**
	 * @return
	 */
	public State getState() {
		// TODO Auto-generated method stub
		return State.obtain(value, toString());
	}
	
	

}
