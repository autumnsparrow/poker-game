package com.sky.game.texas.domain.player;

import com.sky.game.protocol.commons.GT0001Beans.State;

public enum GameTexasPlayerOnTurnStatesEnum {
	
	Undefined((byte)-1),
	Waiting((byte)0),
	OnTurn((byte)1),
	OnTurnTimeout((byte)2)
	;
	
	public byte value;
	private static final GameTexasPlayerOnTurnStatesEnum[] states=new GameTexasPlayerOnTurnStatesEnum[]{
		Undefined,
		Waiting,
		OnTurn,
		OnTurnTimeout
		
	};
	
	
	
	public static GameTexasPlayerOnTurnStatesEnum getOnTurnState(int i){
		return states[i];
	}

	private GameTexasPlayerOnTurnStatesEnum(byte value) {
		this.value = value;
	}
	
	public boolean compare(int value){
		return this.value==value;
	}

	/**
	 * @return
	 */
	public State getState() {
		// TODO Auto-generated method stub
		return State.obtain(value, toString());
	}

}
