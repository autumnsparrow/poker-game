/**
 * 
 */
package com.sky.game.protocol.handler;

import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.ProtocolGlobalContext;
import com.sky.game.protocol.commons.MS0001Beans.Extra;
import com.sky.game.protocol.commons.MS0001Beans.Request;

/**
 * @author Administrator
 *
 */
@HandlerAsyncType(transcode = "MS0001", enable = true, namespace = "MinaServer", enableFilter = false)
@Component("MS0001")
public class MS0001Handler implements IProtocolAsyncHandler<Request, Extra> {

	/**
	 * 
	 */
	public MS0001Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public void onRecieve(Request request, Extra extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		//
		// notify the game server.user offline.
		//
		
		String deviceId = request.getDeviceId();
		ProtocolGlobalContext.instance().removeOnlinePlayer(
				deviceId);
		// should notify the server.

	}

}
