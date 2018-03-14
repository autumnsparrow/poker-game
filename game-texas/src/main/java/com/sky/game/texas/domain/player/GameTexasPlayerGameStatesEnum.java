/**
 * 
 */
package com.sky.game.texas.domain.player;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum GameTexasPlayerGameStatesEnum {
	
	Undefined((byte)-1),
	Inactive((byte)0),
	Active((byte)1)
	;
	
	public byte value;

	private GameTexasPlayerGameStatesEnum(byte value) {
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
