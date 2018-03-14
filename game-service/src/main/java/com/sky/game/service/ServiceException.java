/**
 * 
 */
package com.sky.game.service;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class ServiceException extends Exception {
	
	int state;
	
	

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/**
	 * 
	 */
	public ServiceException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param arg0
	 */
	public ServiceException(int state,String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		this.state=state;
	}


	/**
	 * @param arg0
	 */
	public ServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
