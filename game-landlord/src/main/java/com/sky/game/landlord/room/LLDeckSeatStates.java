/**
 * 
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum LLDeckSeatStates {

	Empty(0),
	Seat(1),
	Leave(2),
	Auto(3);
	
	public int value;

	private LLDeckSeatStates(int value) {
		this.value = value;
	}
	
	public boolean equals(LLDeckSeatStates states){
		return value==states.value;
	}
	
	public State getState(){
		return State.obtain(value, toString());
	}
	
	public static LLDeckSeatStates[] states={Empty,Seat,Leave};
	public static LLDeckSeatStates getDeckSeatStates(int state){
		return states[state];
	}
}
