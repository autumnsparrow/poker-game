/**
 * @sparrow
 * @Dec 11, 2014   @6:36:07 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum LLRoomStates {
	
	UserJoin(1),
	UserLeave(2),
	UserRead(3);
	
	public int value;

	private LLRoomStates(int value) {
		this.value = value;
	}
	
	public State getState(){
		return State.obtain(value, toString());
	}
}
