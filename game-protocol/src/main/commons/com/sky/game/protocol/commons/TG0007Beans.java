/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;

/**
 * @author Administrator
 *
 */

public class TG0007Beans {

	/**
	 * 
	 */
	public TG0007Beans() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * 
	 * 
	 * @author Administrator
	 *
	 */
	@HandlerRequestType(transcode="TG0007")
	public static class Gamebear{
		long userId;
		String pokerHand;
		long chips;
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		public String getPokerHand() {
			return pokerHand;
		}
		public void setPokerHand(String pokerHand) {
			this.pokerHand = pokerHand;
		}
		public long getChips() {
			return chips;
		}
		public void setChips(long chips) {
			this.chips = chips;
		}
		private Gamebear(long userId, long chips, String pokerHand) {
			super();
			this.userId = userId;
			this.chips = chips;
			this.pokerHand = pokerHand;
		}
		
		public Gamebear() {
			super();
			// TODO Auto-generated constructor stub
		}
		public static Gamebear getGamebear(long userId, long chips, String pokerHand){
			return new Gamebear(userId, chips, pokerHand);
		}
	}

}
