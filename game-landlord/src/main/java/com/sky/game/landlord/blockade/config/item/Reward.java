/**
 * @sparrow
 * @Dec 27, 2014   @1:19:10 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config.item;



/**
 * @author sparrow
 *
 */

public class Reward {

	String ranking;
	int itemId; // item id.
	int itemAmount; // item amount.

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}
	
	

	public Reward() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Reward(String ranking, int itemId, int itemAmount) {
		super();
		this.ranking = ranking;
		this.itemId = itemId;
		this.itemAmount = itemAmount;
	}

	
	public static Reward obtain(String ranking,int itemId, int itemAmount) {
		return new Reward(ranking,itemId, itemAmount);
	}

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

}
