/**
 * 
 */
package com.sky.game.texas.domain;

/**
 * @author sparrow
 *
 */
public class GameTexasException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5606717323882284833L;

	public static final int TABLE_NO_EMPY_SEAT=10000;
	
	private int state;

	/**
	 * 
	 */
	public GameTexasException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public GameTexasException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	
	public GameTexasException(int state,String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		this.state=state;
	}
	
	

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @param arg0
	 */
	public GameTexasException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public GameTexasException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	

}
