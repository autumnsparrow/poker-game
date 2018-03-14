/**
 * 
 */
package com.sky.game.landlord.message;

import com.sky.game.landlord.room.LLGamePlayer;

/**
 * @author sparrow
 *
 */
public interface IBrokerObserver {
	
	public void observe(LLGamePlayer player);
	
	public void unObserve(LLGamePlayer player);

}
