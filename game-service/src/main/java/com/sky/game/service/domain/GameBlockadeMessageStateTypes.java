/**
 * sparrow
 * game-service 
 * Jan 27, 2015- 10:59:43 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

/**
 * @author sparrow
 *
 */
public enum GameBlockadeMessageStateTypes {
	
	UNREAD(0),
	READ(1);
	
	
	public int type;

	/**
	 * @param type
	 */
	private GameBlockadeMessageStateTypes(int type) {
		this.type = type;
	}

	public static final int Unread=0;
	public static final int Read=1;
	private static GameBlockadeMessageStateTypes[] types=new GameBlockadeMessageStateTypes[]{UNREAD,READ};
	
	public static GameBlockadeMessageStateTypes getType(int loc){
		return types[loc];
	}
	

}
