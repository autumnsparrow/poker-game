/**
 * @sparrow
 * @Jan 7, 2015   @3:50:11 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

/**
 * @author sparrow
 *
 */
public enum LLGamePlayerStates {

	Offline(-2,"offline"), 
	Leave(-1," leaving"),
	
	Idle(0,"idle"),
	Room(1,"join room"),
	Team(2,"join team"),
	Deck(3,"join deck"),
	Game(4," in game"), 
	Level(5,"in level"),
	Reward(8,"reward "),
	Resume(9,"should resume");
	
	public int state;
	public String message;
	/**
	 * @param state
	 * @param m
	 */
	private LLGamePlayerStates(int state, String m) {
		this.state = state;
		this.message = m;
	};
	
	public boolean eq(LLGamePlayerStates s){
		return state==s.state;
	}
	
}
