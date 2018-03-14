/**
 * @sparrow
 * @1:17:17 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum LLDeckSeatPositionTypes {
	Undefined(0),
	Landlord(1),
	Farmer(2),
	Kicker(3),
	Pull(4);
	public int value;

	private LLDeckSeatPositionTypes(int value) {
		this.value = value;
	}
	
	public boolean equals(LLDeckSeatPositionTypes states){
		return value==states.value;
	}
	
	public State getState(){
		return State.obtain(value, toString());
	}
	
	public static LLDeckSeatPositionTypes[] states={Undefined,Landlord,Farmer,Kicker,Pull};
	public static LLDeckSeatPositionTypes getDeckSeatStates(int state){
		return states[state];
	}
	

}
