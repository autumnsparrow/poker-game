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
public enum DeckStates {

	DeckIdle(0),
	DeckGaming(1);
	public int state;

	private DeckStates(int state) {
		this.state = state;
	}
	
	public boolean equals(DeckStates states){
		return state==states.state;
	}
	
}
