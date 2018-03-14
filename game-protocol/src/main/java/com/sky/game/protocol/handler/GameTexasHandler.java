/**
 * 
 * @Date:Nov 13, 2014 - 6:07:23 PM
 * @Author: sparrow
 * @Project :game-protocol 
 * 
 *
 */
package com.sky.game.protocol.handler;

import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.protocol.event.handler.MinaEventHandler;

/**
 * @author sparrow
 *
 */
@HandlerAsyncType(enable=true,enableFilter=true,namespace="GameTexas",transcode="GT[0-9][0-9][0-9][0-9]")
@Component("GameTexasHandler")
public class GameTexasHandler implements IProtocolAsyncHandler<Object, Object> {

	/**
	 * 
	 */
	public GameTexasHandler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.handler.IProtocolAsyncHandler#onRecieve(java.lang.Object, java.lang.Object)
	 */
	@HandlerMethod(enable=true)
	public void onRecieve(Object request, Object extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		MinaEvent event=MinaEvent.obtainMinaEvent(request);
		MinaEventHandler.addRecieveMinEvent(event);
		
	}

}
