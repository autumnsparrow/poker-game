/**
 * 
 * @Date:Nov 5, 2014 - 4:06:08 PM
 * @Author: sparrow
 * @Project :game-mina-client 
 * 
 *
 */
package com.sky.game.mina.client.handler;

import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.util.GameUtil;

import com.sky.game.mina.client.ProxyMinaMessageInvoker;
import com.sky.game.mina.context.SessionContext;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GU0002Beans.Response;
import com.sky.game.protocol.commons.GU0002Beans;

/**
 * @author sparrow
 *
 */
@HandlerAsyncType(transcode = "UG0002", enable = true, namespace = "ClientTexas")
@Component("UG0002")
public class UG0002Handler implements
		IProtocolAsyncHandler<GU0002Beans.Response, SessionContext> {

	/**
	 * 
	 */
	public UG0002Handler() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.context.handler.IProtocolAsyncHandler#onRecieve(java.lang
	 * .Object, java.lang.Object)
	 */
	@HandlerMethod(enable = true)
	public void onRecieve(Response request, SessionContext extra)
			throws ProtocolException {
		// TODO Auto-generated method stub

		extra.mobileUser.token = request.getToken();

		/**/

		// GT0001Beans.Request r=new GT0001Beans.Request();
		// r.setId(Long.valueOf(200000000));
		// r.setToken(extra.mobileUser.token);
		// ProxyMinaMessageInvoker.asyncInvoke(extra, "GT0001", r, extra);

		// join the room.
		GL0000Beans.GL4000Request r = GameUtil
				.obtain(GL0000Beans.GL4000Request.class);
		r.setId(Long.valueOf(10000));
		// r.setId(Long.valueOf(2000));
		r.setToken(extra.mobileUser.token);
		//ProxyMinaMessageInvoker.asyncInvoke(extra, "GL4000", r);
		
		// get elimination list
//		GL0000Beans.ETRoomListRequest etReq=GameUtil.obtain(GL0000Beans.ETRoomListRequest.class);
//		etReq.setId(1L);
//		etReq.setToken(extra.mobileUser.token);
//		ProxyMinaMessageInvoker.asyncInvoke(extra, etReq);
		
		
		// basic ai.
		GL0000Beans.GL4005Request req=GameUtil.obtain(GL0000Beans.GL4005Request.class);
		req.setId(2000L);
		req.setItemId(1);
		ProxyMinaMessageInvoker.asyncInvoke(extra, req);

		System.out.println("JoinRoom" + request.toString());
		
		
		/// send the protocol enroll

	}

}
