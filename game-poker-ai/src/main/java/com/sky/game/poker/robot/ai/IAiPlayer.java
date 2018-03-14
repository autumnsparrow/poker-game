/**
 * 
 */
package com.sky.game.poker.robot.ai;

import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public interface IAiPlayer {
	
	/**
	 * Deck set the Ai player initialize poker cube.
	 * @param pokerCube
	 */
	public void setInitializePoker(PokerCube pokerCube);
	
	/**
	 * 
	 * Deck set the bottom poker,when the player is landloader,
	 *  then add the initialize poker and bottom poker.
	 * 
	 * @param pokerCube
	 */
	public void setBottomPoker(PokerCube pokerCube);
	
	
	/**
	 * show hands.
	 * 
	 * @return
	 */
	public PokerCube showHands();
	
	
	
	
	
	/**
	 * notify the current bid states.
	 * @param bidStates
	 */
	public void notifyBidStates(BidStates bidStates);
	
	/**
	 * Current Ai offers the bid states.
	 * @return
	 */
	public BidStates offerBidStates();
	
	/**
	 * the player must bid
	 * 
	 * @return
	 */
	public BidStates offerLowestBidStates();
	
	/**
	 * notify the current pokercube
	 * @param pokerCube
	 */
	public void notifyHands(PokerCube pokerCube);
	
	/**
	 * modify the aiplayer score
	 * @param score
	 */
	public void setScore(int score);
	
	/**
	 * the number of pokers remain in hand
	 * 
	 * @return
	 */
	public int pokerRemains();
	
	/**
	 * mark as land lorder
	 * @param landLorder
	 */
	public void setLandLordFlag(boolean landLorder);
	
	
	/**
	 * checking is the landlorder
	 * @return
	 */
	public boolean isLandLorder();
	
	/**
	 * 
	 * 
	 * @return
	 */
	public boolean offerKick();
	
	/**
	 * 
	 * update the deck position.
	 * @param position
	 */
	public void setDeckPosition(int position);
	
	/**
	 * 
	 * @return
	 */
	public int getDeckPosition();
	
	/**
	 * 
	 * the land lord combinate the poker cube
	 */
	public void combinatePokerCube();
	
	

}
