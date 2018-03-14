/**
 * sparrow
 * game-service 
 * Feb 11, 2015- 9:40:30 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

/**
 * @author sparrow
 *
 */
public class GameSystemMessage {
	
	
	long userId;
	String message;

	/**
	 * 
	 */
	public GameSystemMessage() {
		// TODO Auto-generated constructor stub
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
