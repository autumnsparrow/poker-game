/**
 * @sparrow
 * @Dec 28, 2014   @11:33:19 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config.item;



/**
 * @author sparrow
 *
 */
public class TimePattern {
	TimeWindow clientShow;
	TimeWindow enroll;
	TimeWindow match;
	public TimeWindow getClientShow() {
		return clientShow;
	}
	public void setClientShow(TimeWindow clientShow) {
		this.clientShow = clientShow;
	}
	public TimeWindow getEnroll() {
		return enroll;
	}
	public void setEnroll(TimeWindow enroll) {
		this.enroll = enroll;
	}
	public TimeWindow getMatch() {
		return match;
	}
	public void setMatch(TimeWindow match) {
		this.match = match;
	}
	public TimePattern(TimeWindow clientShow, TimeWindow enroll,
			TimeWindow match) {
		super();
		this.clientShow = clientShow;
		this.enroll = enroll;
		this.match = match;
	}
	
	public static TimePattern obtain(TimeWindow clientShow, TimeWindow enroll,
			TimeWindow match){
		return new TimePattern(clientShow, enroll, match);
	}
	public TimePattern() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
