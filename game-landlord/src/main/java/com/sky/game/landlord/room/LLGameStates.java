/**
 * 
 */
package com.sky.game.landlord.room;

import com.sky.game.protocol.commons.GT0001Beans.State;

/**
 * 
 * The deck focus the game only.
 * 
 * deck state changed.
 * deck 
 *    id - unique id in the game.
 *    state.
 * 
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */
public enum LLGameStates {
	
	Born(0), // deck created, notify the players the current deck id and players location.
	DealCards(1), // deck shuffles the cards ,and deal to players,notify the player 
	Bid(2),  //  the logical of bid a round
	KittyCards(3), // show the position of the player
	Kicker(4),  //  opponent kick increase the score rate.
	Pull(5),
	Gaming(6), // in gaming.
	Score(7), // score rate change.
	End(8);  // game end.
	
	
	public static final int BORN=0;
	public static final int DEAL_CARDS=1;
	public static final int BID=2;
	public static final int KITTY_CARDS=3;
	public static final int KICKER=4;
	public static final int PULL=5;
	public static final int GAMING=6;
	public static final int SCORE=7;
	public static final int END=8;
	public static final int DESTORY=9;
	
	public int value;

	private LLGameStates(int value) {
		this.value = value;
	}
	
	final static LLGameStates[]  states={
		Born,DealCards,Bid,KittyCards,Kicker,Pull,Gaming,Score,End
	};
	public static  LLGameStates getState(int v){
		return states[v];
	}
	
	public State getState(){
		return State.obtain(value, toString());
	}
	
	
	public boolean eq(LLGameStates s){
		return this.value==s.value;
	}

}
