/**
 * 
 */
package com.sky.game.texas.domain.player;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum GameTexasPlayerBetStatesEnum {
	
	Undefined((byte)-1),
	
	Call((byte)1),
	Check((byte)2),
	Bet((byte)3),
	Raise((byte)4),
	Fold((byte)5),
	Allin((byte)6);
	
	public byte value;

	private GameTexasPlayerBetStatesEnum(byte value) {
		this.value = value;
	}
	
	public boolean compare(int value){
		return this.value==value;
	}
	private static final GameTexasPlayerBetStatesEnum[]  states=new GameTexasPlayerBetStatesEnum[]{
		
		Undefined,
		Call,
		Check,
		Bet,
		Raise,
		Fold,
		Allin
	};
	public static GameTexasPlayerBetStatesEnum getBetState(int i){
		return states[i];
	}

	/**
	 * @return
	 */
	public State getState() {
		// TODO Auto-generated method stub
		return State.obtain(value, toString());
	}
	

}
