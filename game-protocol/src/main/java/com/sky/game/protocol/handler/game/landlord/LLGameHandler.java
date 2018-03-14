/**
 * 
 */
package com.sky.game.protocol.handler.game.landlord;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.protocol.event.handler.MinaEventHandler;

/**
 * @author sparrow
 *
 */
@HandlerAsyncType(enable=true,enableFilter=true,namespace="LLGame",transcode="GL[0-9][0-9][0-9][0-9]")
@Component("LLGameHandler")
public class LLGameHandler implements IProtocolAsyncHandler<Object,GL0000Beans.Extra > {
	private static final Log logger=LogFactory.getLog(LLGameHandler.class);
	/**
	 * 
	 */
	public LLGameHandler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable=true)
	public void onRecieve(Object request, GL0000Beans.Extra extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		String deviceId=extra==null?"":extra.getDeviceId();
		//logger.info("onReceived:"+GameContextGlobals.getJsonConvertor().format(request));
		
		MinaEvent event=MinaEvent.obtainMinaEvent(request,deviceId);
	//	event.deviceId=;
		MinaEventHandler.addRecieveMinEvent(event);
		
		
	}

}
