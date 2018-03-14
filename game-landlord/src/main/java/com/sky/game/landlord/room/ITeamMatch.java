/**
 * @sparrow
 * @Jan 7, 2015   @6:21:31 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

/**
 * @author sparrow
 *
 */
public interface ITeamMatch {
	
	public void startMatch();
	
	public void onGameEnd(LLDeck deck);
	
	public int getUpgradeNumbers();

}
