/**
 * @sparrow
 * @Dec 28, 2014   @10:07:10 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config.item;

/**
 * @author sparrow
 *
 */
public class PlayerNumberController {
	
	int minPlayerNumber;
	int maxPlayerNumber;
	

	/**
	 * 
	 */
	public PlayerNumberController() {
		// TODO Auto-generated constructor stub
	}


	public int getMinPlayerNumber() {
		return minPlayerNumber;
	}


	public void setMinPlayerNumber(int minPlayerNumber) {
		this.minPlayerNumber = minPlayerNumber;
	}


	public int getMaxPlayerNumber() {
		return maxPlayerNumber;
	}


	public void setMaxPlayerNumber(int maxPlayerNumber) {
		this.maxPlayerNumber = maxPlayerNumber;
	}


	public PlayerNumberController(int minPlayerNumber, int maxPlayerNumber) {
		super();
		this.minPlayerNumber = minPlayerNumber;
		this.maxPlayerNumber = maxPlayerNumber;
	}
	
	public static PlayerNumberController obtain(){
		return new PlayerNumberController(6, 12);
	}

}
