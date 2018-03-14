/**
 * @sparrow
 * @Jan 19, 2015   @2:38:19 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.protocol;

/**
 * @author sparrow
 *
 */
public enum PlayerStates {
	Offline(0,"offline"),
	Online(1,"not in gaming"),
	Tournament(2,"in tournament"),
	Gaming(3,"in gameing");
	
	public int state;
	public String message;
	/**
	 * @param state
	 * @param message
	 */
	private PlayerStates(int state, String message) {
		this.state = state;
		this.message = message;
	}
	
	public boolean eq(PlayerStates states){
		return state==states.state;
	}
}
