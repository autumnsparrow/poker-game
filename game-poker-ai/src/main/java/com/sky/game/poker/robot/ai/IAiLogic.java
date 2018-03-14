/**
 * 
 */
package com.sky.game.poker.robot.ai;

import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public interface IAiLogic {
	
	public static final int CUR_POS = 0;
	public static final int RIGHT_POS = 1;
	public static final int LEFT_POS = 2;
	
	public static final int LANDLORD_CARDS_SUMS=20;
	public static final int FARMER_CARDS_SUM=17;
	
	public void think(IPokerCubeAnalyzer analyzer,PokerCube[][] cubes);
	
	

}
