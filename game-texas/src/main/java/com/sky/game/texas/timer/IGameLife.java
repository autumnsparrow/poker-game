/**
 * 
 * @Date:Nov 13, 2014 - 2:00:08 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.timer;

/**
 * 
 * GameLife :
 * 
 * 	born
 *  life
 * 	die.
 * 
 * @author sparrow
 *
 */
public interface IGameLife {
	
	/**
	 * 
	 * @return
	 */
	public String getName();
	
	
	/**
	 * 
	 * @param life
	 */
	
	
	
	/**
	 * 
	 * 
	 */
	public void timeout();
	
	/**
	 * 
	 * @return if the life end!
	 */
	public boolean isTimeout();
	
	
	public void destory();
	
	
	public GameLifeEnum getStage();
	

}
