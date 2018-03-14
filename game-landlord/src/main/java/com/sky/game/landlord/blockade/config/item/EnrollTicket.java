/**
 * @sparrow
 * @Dec 27, 2014   @1:17:55 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config.item;



/**
 * @author sparrow
 *
 */
public class EnrollTicket {

	/**
	 * 
	 */
	public EnrollTicket() {
		// TODO Auto-generated constructor stub
	}
	int itemId; // item id
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



	private EnrollTicket(int itemId, int itemAmount) {
		super();
		this.itemId = itemId;
		this.itemAmount = itemAmount;
	}

	public static EnrollTicket obtain(int itemId, int itemAmount) {
		return new EnrollTicket(itemId, itemAmount);
	}

}
