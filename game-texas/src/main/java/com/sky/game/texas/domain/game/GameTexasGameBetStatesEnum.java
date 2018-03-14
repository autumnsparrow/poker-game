/**
 * 
 * @Date:Nov 13, 2014 - 3:14:40 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum GameTexasGameBetStatesEnum {
	Open((byte)1),  // bet waiting,
	End((byte)2);  // bet waiting timeout
	
	
	public byte value;
	private GameTexasGameBetStatesEnum(int value){
		this.value=(byte) value;
	}
	/**
	 * @return
	 */
	public State getState() {
		// TODO Auto-generated method stub
		return State.obtain(value, toString());
	}

}
