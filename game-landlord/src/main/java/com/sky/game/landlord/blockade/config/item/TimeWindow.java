/**
 * @sparrow
 * @Dec 27, 2014   @5:55:02 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.blockade.config.item;

/**
 * @author sparrow
 *
 */
public class TimeWindow {

	/**
	 * 
	 */
	public TimeWindow() {
		// TODO Auto-generated constructor stub
	}
	
	String start;
	String end;
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	
	
	
	public TimeWindow(String start, String end) {
		super();
		this.start = start;
		this.end = end;
	}
	public static TimeWindow obtain(String start,String end){
		return new TimeWindow(start,end);
	}

}
