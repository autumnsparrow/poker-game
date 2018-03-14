/**
 * 
 */
package com.sky.game.poker.robot.poker;

import com.sky.game.poker.robot.ai.PokerCubeType;

/**
 * @author sparrow
 *
 */
public interface IPokerCubeMeta {
	
	public PokerCubeType getPokerCubeType() ;
	public void setPokerCubeType(PokerCubeType pokerCubeType);
	
	public boolean isBelongToLandloard();
	public void setBelongToLandloard(boolean belongToLandloard) ;
	
	
	public void setDeckPoistion(int position);
	public int getDeckPosition();
	
	public boolean canWePass();
	public void setWePass(boolean pass);

}
