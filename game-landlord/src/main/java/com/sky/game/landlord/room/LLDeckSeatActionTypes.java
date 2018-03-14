/**
 * @sparrow
 * @1:18:50 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum LLDeckSeatActionTypes {
	Undefined(-1),
	FirstCall(0),
	Turn(1),
	Bid(2),
	Kick(3),
	ShowHand(4),
	Pass(5),
	Pull(6);
	public int value;

	private LLDeckSeatActionTypes(int value) {
		this.value = value;
	}
	
	public boolean equals(LLDeckSeatActionTypes states){
		return value==states.value;
	}
	
	public State getState(){
		return State.obtain(value, toString());
	}
	
	public static LLDeckSeatActionTypes[] states={FirstCall,Turn,Bid,Kick,ShowHand,Pass};
	public static LLDeckSeatActionTypes getDeckSeatStates(int state){
		return states[state];
	}
	
}
