/**
 * 
 */
package com.sky.game.websocket.handler;


import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.domain.MinaBeans;
import com.sky.game.context.domain.MinaBeans.Extra;
import com.sky.game.context.domain.MinaBeans.Request;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.websocket.SessionContext;
import com.sky.game.websocket.SessionContextManager;
import com.sky.game.websocket.packet.EncryptTypes;
import com.sky.game.websocket.packet.PacketTypes;


/**
 * @author sparrow
 *
 */
@HandlerAsyncType(namespace="GameMina",transcode="SM0001",enable=true,enableFilter=true)
@Component("MinaAsyncHandler")
public class MinaAsyncHandler implements IProtocolAsyncHandler<MinaBeans.Request, MinaBeans.Extra> {

	private static final Log logger=LogFactory.getLog(MinaAsyncHandler.class);
	/**
	 * 
	 */
	public MinaAsyncHandler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable=true)
	public void onRecieve(Request request, Extra extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		String deviceId=extra.getDeviceId();
		if(deviceId.startsWith("R0123456")){
			return;
		}
		Session session=SessionContextManager.mgr().getSession(deviceId);
		if(session==null){
			logger.info("can't find the conneciton by device id :"+deviceId+" message :"+request+" \n  "+SessionContextManager.mgr().getSessionMap());
			return;
		}
		logger.info("deviceId:"+deviceId+" session:"+session);
		SessionContext context=SessionContext.getContext(session);
		context.response(context.obtainMessage(PacketTypes.SyncPacketType, context.isEncrypt()?EncryptTypes.True:EncryptTypes.False, request.getContent()));
//		SessionM
		//SessionContextManager.mgr().get
	}

	

}
