/**
 * 
 */
package com.sky.game.poker.robot.poker;

import com.sky.game.poker.robot.ai.PokerCubeType;


/**
 * @author sparrow
 *
 */
public class PokerCubeMeta  implements IPokerCubeMeta{
	
	
	
	

	/**
	 //////////////////////////////////////////////////////////
	 * 
	 * Property for the IAiPlayer.
	 * 
	 * 
	 */
	PokerCubeType  pokerCubeType;

	public PokerCubeType getPokerCubeType() {
		return pokerCubeType;
	}

	public void setPokerCubeType(PokerCubeType pokerCubeType) {
		this.pokerCubeType = pokerCubeType;
	}

	boolean belongToLandloard;
	



	public boolean isBelongToLandloard() {
		return belongToLandloard;
	}

	public void setBelongToLandloard(boolean belongToLandloard) {
		this.belongToLandloard = belongToLandloard;
	}
	
	/**
	 * 
	 * End of Property for the IAiPlayer.
	 * 
	 * 
	 //////////////////////////////////////////////////////////
	 */

	/**
	 * 
	 */
	
	
	int deckPosition;

	public PokerCubeMeta(PokerCubeType pokerCubeType,
			boolean belongToLandloard, int deckPosition, boolean shouldPass) {
		super();
		this.pokerCubeType = pokerCubeType;
		this.belongToLandloard = belongToLandloard;
		this.deckPosition = deckPosition;
		this.shouldPass = shouldPass;
	}

	public PokerCubeMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setDeckPoistion(int position) {
		// TODO Auto-generated method stub
		this.deckPosition=position;
	}

	public int getDeckPosition() {
		// TODO Auto-generated method stub
		return deckPosition;
	}

	
	boolean shouldPass;
	
	
	public boolean canWePass(){
		return this.shouldPass;
	}

	public void setWePass(boolean pass) {
		// TODO Auto-generated method stub
		this.shouldPass=pass;
	}
	
	boolean shouldActive;
	

	
	public boolean isShouldActive() {
		return shouldActive;
	}

	public void setShouldActive(boolean shouldActive) {
		this.shouldActive = shouldActive;
	}
	
	
	

	

	
	protected void dump(PokerCubeMeta obj ) {
		// TODO Auto-generated method stub
		pokerCubeType = obj.pokerCubeType;
		belongToLandloard = obj.belongToLandloard;
		deckPosition = obj.deckPosition;
		shouldPass = obj.shouldPass;
		shouldActive=obj.shouldActive;
		
	}

	
	
	


}
