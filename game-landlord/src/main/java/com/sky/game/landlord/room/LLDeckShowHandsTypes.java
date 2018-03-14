/**
 * @sparrow
 * @2:06:02 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum LLDeckShowHandsTypes {
	Undefined(0),
	Active(1),
	Passive(2);
	
	public int value;
	public int pokerCubeType;

	private LLDeckShowHandsTypes(int value) {
		this.value = value;
	}
	
	public boolean equals(LLDeckShowHandsTypes types){
		return this.value==types.value;
	}
	
	
	
	public State getState(){
		return State.obtain(value, toString());
	}
	
	public static LLDeckShowHandsTypes[] states={Undefined,Active,Passive};
	public static LLDeckShowHandsTypes getDeckSeatStates(int state){
		return states[state];
	}
	
}
