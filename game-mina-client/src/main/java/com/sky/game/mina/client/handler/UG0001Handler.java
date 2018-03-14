/**
 * 
 * @Date:Nov 5, 2014 - 2:56:14 PM
 * @Author: sparrow
 * @Project :game-mina-client 
 * 
 *
 */
package com.sky.game.mina.client.handler;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.mina.context.SessionContext;
import com.sky.game.protocol.commons.GU0001Beans;
import com.sky.game.protocol.commons.GU0001Beans.Response;

/**
 * @author sparrow
 *
 */
@HandlerAsyncType(transcode="UG0001",enable=true,namespace="ClientTexas")
@Component("UG0001")
public class UG0001Handler  implements IProtocolAsyncHandler<GU0001Beans.Response, SessionContext>{

	/**
	 * 
	 */
	public UG0001Handler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.handler.IProtocolAsyncHandler#onRecieve(java.lang.Object, java.lang.Object)
	 */
	@HandlerMethod(enable=true)
	public void onRecieve(Response request, SessionContext extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		//String token=request.getToken();
		
		//extra.closed(extra.getSession());
	//	extra.getSession().close(true);
		
	}

}
