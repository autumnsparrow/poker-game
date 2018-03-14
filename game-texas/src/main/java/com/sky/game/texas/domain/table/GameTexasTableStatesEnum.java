/**
 * 
 */
package com.sky.game.texas.domain.table;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum GameTexasTableStatesEnum  {
	
	Idle((byte)0),
	Waiting((byte)1),
	Gaming((byte)2),
	PlayerJoin((byte)3),
	PlayerLeave((byte)4),
	NoEmptySeat((byte)5);
	
	
	public byte value;

	private GameTexasTableStatesEnum(byte value) {
		this.value = value;
	}
	
	
	public boolean isState(int value){
		return this.value==(byte)value;
	}
	
	public  State getState(){
		return State.obtain(value, toString());
	}
	 

}
