/**
 * 
 */
package com.sky.game.mina.client.handler;

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
@HandlerAsyncType(enable=true,enableFilter=true,namespace="LLGame",transcode="LG[0-9][0-9][0-9][0-9]")
@Component("LLGameClientHandler")
public class LLGameClientHandler implements
		IProtocolAsyncHandler<Object, Object> {

	/**
	 * 
	 */
	public LLGameClientHandler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public void onRecieve(Object request, Object extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
			MinaEvent event=MinaEvent.obtainMinaEvent(request);
			MinaEventHandler.addRecieveMinEvent(event);
		
	}

}
