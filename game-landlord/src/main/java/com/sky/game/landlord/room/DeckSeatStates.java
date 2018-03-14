/**
 * 
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * 
 * 
 * @author sparrow
 *
 */
public enum DeckSeatStates {

	Empty(0),
	Seat(1),
	Leave(2),
	Landlord(3),
	Farmer(4),
	FirstCall(5),
	PointZero(6),
	PointOne(7),
	PointTwo(8),
	PointThree(9),
	Turn(10),
	Kicker(11);
	
	public int value;

	private DeckSeatStates(int value) {
		this.value = value;
	}

	
	public boolean equals(DeckSeatStates states){
		return value==states.value;
	}
	
	public State getState(){
		return State.obtain(value, toString());
	}
	
	public static DeckSeatStates[] states={Empty,Seat,Leave,Landlord,Farmer,FirstCall,PointZero,PointOne,PointTwo,PointThree,Turn,Kicker};
	public static DeckSeatStates getDeckSeatStates(int state){
		return states[state];
	}
}
