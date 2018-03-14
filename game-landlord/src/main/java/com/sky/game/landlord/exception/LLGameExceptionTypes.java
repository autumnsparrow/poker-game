/**
 * 
 */
package com.sky.game.landlord.exception;

/**
 * @author sparrow
 *
 */
public enum LLGameExceptionTypes {
	
	DeckSeatMismatchPlayers(1,"player can't seat the deck!");
	
	
	public int state;
	public String msg;

	private LLGameExceptionTypes(int state,String msg) {
		this.state = state;
		this.msg=msg;
	}
	
	public LLGameExecption exception(){
		return LLGameExecption.obtain(state, msg);
	}
	

}
