/**
 * 
 */
package com.sky.game.texas.domain.table;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum GameTexasSeatStatesEnum {
	
	Idle((byte)0),
	Used((byte)1);
	
	public byte value;

	private GameTexasSeatStatesEnum(byte value) {
		this.value = value;
	}
	
	
	public  boolean isState(GameTexasSeatStatesEnum state){
		return this.value==state.value;
	}


	/**
	 * @return
	 */
	public State getState() {
		// TODO Auto-generated method stub
		return State.obtain(value, toString());
	}
	
	

}
