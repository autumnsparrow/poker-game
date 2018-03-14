/**
 * 
 */
package com.sky.game.poker.robot.ai;

/**
 * Deck States:
 * 
 *   waiting players join game 
 *   |
 *   players already.
 *   |
 *   shuffles
 *   |
 *   assign
 *   |
 *   bid
 *   |
 *   round  | same type.
 *   ...
 *   |
 *   end
 *   |
 *   prize
 * 
 *   
 * 
 * 
 * 
 * @author sparrow
 *
 */
public enum DeckStates {
	New(0),
	WaitingPlayers(1),
	AssignPoker(2),
	Bid(3),
	Kick(7),
	Hands(4),
	Prize(5),
	End(6);
	
	public static final int NEW=0;
	public static final int WAITING_PLAYER=1;
	public static final int ASSIGN_POKER=2;
	public static final int BID=3;
	public static final int HAND=4;
	public static final int PRIZE=5;
	public static final int END=6;
	public static final int KICK=7;
	
	
	public int state;

	private DeckStates(int state) {
		this.state = state;
	}
	

}
