/**
 * @sparrow
 * @Mar 18, 2015   @8:46:36 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * @author sparrow
 *
 */
public enum LLRoomTypes {
	FT(1,"休闲场"),
	ET(2,"夺宝场"),
	ET_VIP(3,"vip场"),
	BT(4,"闯关赛");	
	public int state;
	public String message;
	private LLRoomTypes(int state,String message) {
		//super(name, ordinal);
		// TODO Auto-generated constructor stub
		this.state=state;
		this.message=message;
	}



	public  State wrapState(){
		return State.obtain(state, message);
	}

}
