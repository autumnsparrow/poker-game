/**
 * 
 * @Date:Nov 4, 2014 - 2:45:16 PM
 * @Author: sparrow
 * @Project :game-protocol 
 * 
 *
 */
package com.sky.game.protocol.event.handler;

import com.sky.game.protocol.GameProtocolException;
import com.sky.game.protocol.event.MinaEvent;

/**
 * @author sparrow
 *
 */
public interface IMinaRecieveObserver {
	
	public boolean shouldProcessEvent(MinaEvent evt);

	public void onRecievedMinaEvent(MinaEvent event) throws GameProtocolException;
}
