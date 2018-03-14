/**
 * 
 */
package com.sky.game.texas;

/**
 * @author sparrow
 *
 */
public interface IGameWorld {

	public long getSerialId(long timeout);
	
	
	public void onTimeout();
}
