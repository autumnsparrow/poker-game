/**
 * sparrow
 * game-service 
 * Jan 23, 2015- 1:22:32 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

/**
 * @author sparrow
 *
 */
public enum UserLandlordStates {
	Unkown(-1,"unkown"),
	Farmer(1,"farmer"),
	Landlord(2,"landlord");
	
	public int state;
	public String message;
	/**
	 * @param state
	 * @param message
	 */
	private UserLandlordStates(int state, String message) {
		this.state = state;
		this.message = message;
	}
	

}
