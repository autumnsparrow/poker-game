/**
 * @sparrow
 * @5:27:01 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

/**
 * @author sparrow
 *
 */
public enum LLDeckStates {

	DeckIdle(0),
	DeckGaming(1),
	DeckGameEnd(2);
	
	
	public int state;

	private LLDeckStates(int state) {
		this.state = state;
	}
	
	public boolean equals(LLDeckStates states){
		return state==states.state;
	}
	
}
