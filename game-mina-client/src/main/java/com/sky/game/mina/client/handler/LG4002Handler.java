/**
 * 
 */
package com.sky.game.mina.client.handler;

import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.context.util.GameUtil;
import com.sky.game.mina.client.ProxyMinaMessageInvoker;
import com.sky.game.mina.context.SessionContext;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.LG4002Response;

/**
 * @author sparrow
 *
 */
@HandlerAsyncType(transcode="LG4002",enable=true,namespace="LLGame")
@Component("LG4002")
public class LG4002Handler implements IProtocolAsyncHandler<GL0000Beans.LG4002Response, SessionContext> {

	/**
	 * 
	 */
	public LG4002Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable=true)
	public void onRecieve(LG4002Response request, SessionContext extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		if(request.getState().getState()==1){
		GL0000Beans.GL4004Request req=GameUtil.obtain(GL0000Beans.GL4004Request.class);
		req.setId(request.getId());
		req.setToken(extra.mobileUser.token);
		ProxyMinaMessageInvoker.asyncInvoke(extra, req);
		}
		
	}

}
