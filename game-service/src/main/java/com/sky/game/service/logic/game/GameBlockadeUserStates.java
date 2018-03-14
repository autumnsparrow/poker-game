/**
 * sparrow
 * game-service 
 * Mar 11, 2015- 10:16:51 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic.game;

/**
 * @author sparrow
 *
 */
public enum GameBlockadeUserStates {
	
	Win(1),
	Process(0),
	Lose(-1);
	
	public int status;

	/**
	 * @param status
	 */
	private GameBlockadeUserStates(int status) {
		this.status = status;
	}
	

}
