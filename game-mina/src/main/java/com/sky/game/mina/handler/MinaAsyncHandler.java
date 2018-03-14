/**
 * 
 */
package com.sky.game.mina.handler;

import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.domain.MinaBeans;
import com.sky.game.context.domain.MinaBeans.Extra;
import com.sky.game.context.domain.MinaBeans.Request;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.mina.protocol.Packet;
import com.sky.game.mina.util.GamePlayerSessionManager;

/**
 * @author sparrow
 *
 */
@HandlerAsyncType(namespace="GameMina",transcode="SM0001",enable=true,enableFilter=true)
@Component("MinaAsyncHandler")
public class MinaAsyncHandler implements IProtocolAsyncHandler<MinaBeans.Request, MinaBeans.Extra> {

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
		IoSession session=GamePlayerSessionManager.getInstance().getSession(deviceId);
		try {
			Packet.getWritePacket(session, request.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}
