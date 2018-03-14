/**
 * 
 */
package com.sky.game.landlord.exception;

/**
 * @author sparrow
 *
 */
public class LLGameExecption extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -95456226866439025L;

	public static LLGameExecption obtain(int state,String message){
		return new LLGameExecption(state, message);
	}
	

	
	public int state;

	/**
	 * 
	 */
	public LLGameExecption() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public LLGameExecption(int state,String message) {
		super(message);
		state=state;
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param cause
	 */
	public LLGameExecption(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LLGameExecption(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	
}
