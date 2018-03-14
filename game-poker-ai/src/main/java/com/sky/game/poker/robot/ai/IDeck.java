/**
 * 
 */
package com.sky.game.poker.robot.ai;

import com.sky.game.poker.robot.poker.PokerCube;

/**
 * 
 * 
 * 
 * @author sparrow
 *
 */
public interface IDeck {
	
	
	/**
	 * Checking the Deck states.
	 * 
	 * when the (DeckStates.RoundBegin ,update the PokerCubeTypeStates)
	 * when Other two players pass 
	 * 
	 * @return
	 */
	public DeckStates getDeckState();
	
	
	/**
	 * Ai player join the deck.(DeckStates.WaitingPlayers)
	 * 
	 * 
	 * @param player
	 */
	public void join(IAiPlayer player);
	
	/**
	 * 
	 * Deck shuffles the PokerCube.(DeckStates.ShufflesPoker)
	 */
	public void shuffles();
	
	
	/**
	 * Checking BidStates.(DeckStates.Bid)
	 * @return
	 */
	public BidStates getBidStates();
	
	
	/**
	 * Updating the BidStates(DeckStates.Bid)
	 * @param bidStates
	 */
	public void setBidStates(BidStates bidStates);
	
	
	/**
	 * Checking current PokerCubeTypes
	 * @return
	 */
	public PokerCubeTypes getPokerCubeTypes();
	
	
	/**
	 * Updating the PokerCubeTypes.
	 * @param pokerCubeTypes
	 */
	public void setPokerCubeTypes(PokerCubeTypes pokerCubeTypes);
	
	
	/**
	 * In rounding show hands.
	 * 
	 * @param pokerCube
	 */
	public void showHands(PokerCube pokerCube);
	
	
	/**
	 * notify the other players
	 * @param pokerCube
	 */
	public void notifyHands(PokerCube pokerCube);
	
	
	/**
	 * Checking the current should show hand player.
	 * @return
	 */
	public IAiPlayer shouldShowHandPlayer();
	
	
	/**
	 * Deck prize the AiPlayers.
	 * 
	 */
	public void prizeAiPlayers();
	
	/**
	 * main logic of the deck
	 * 
	 */
	public void run();
	

}
