/**
 * 
 * @Date:Nov 17, 2014 - 1:48:32 PM
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
import com.sky.game.mina.context.SessionContext;
import com.sky.game.protocol.commons.GT0005Beans;
import com.sky.game.protocol.commons.GT0005Beans.Response;

/**
 * @author sparrow
 *
 */
@HandlerAsyncType(transcode="TG0005",enable=true,namespace="ClientTexas")
@Component("TG0005")
public class TG0005Handler implements IProtocolAsyncHandler<GT0005Beans.Response, SessionContext> {

	/**
	 * 
	 */
	public TG0005Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public void onRecieve(Response request, SessionContext extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		System.out.println("+++++++++++++++++++++++++++");
		System.out.println(request.toString());
		
	}
	

}
