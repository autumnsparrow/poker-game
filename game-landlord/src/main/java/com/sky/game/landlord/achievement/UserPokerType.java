/**
 * @sparrow
 * @Jan 23, 2015   @1:27:46 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.achievement;

import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class UserPokerType {
	
	long userId;
	PokerCube pokerCube;
	boolean landlord;
	
	boolean firstHand;
	
	boolean previosBombOrRocket;
	
	

	/**
	 * 
	 */
	public UserPokerType() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userId
	 * @param pokerCube
	 */
	public UserPokerType(long userId, PokerCube pokerCube,boolean landlord,boolean firstHand) {
		super();
		this.userId = userId;
		this.pokerCube = pokerCube;
		this.landlord=landlord;
		this.firstHand=firstHand;
		
	}
	
	public static UserPokerType obtain(long userId, PokerCube pokerCube,boolean landlord,boolean firstHand){
		return new UserPokerType(userId, pokerCube,landlord,firstHand);
	}

	public boolean isPreviosBombOrRocket() {
		return previosBombOrRocket;
	}

	public void setPreviosBombOrRocket(boolean previosBombOrRocket) {
		this.previosBombOrRocket = previosBombOrRocket;
	}

}
