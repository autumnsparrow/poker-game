/**
 * 
 */
package com.sky.game.mina.client.handler;

import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.mina.app.MinaClientApp;
import com.sky.game.mina.client.ProxyMinaMessageInvoker;
import com.sky.game.protocol.commons.GU0006Beans;

import com.sky.game.protocol.commons.GU0006Beans.Response;
import com.sky.game.service.domain.Goods;

/**
 * @author rqz
 *
 */
@HandlerAsyncType(transcode="UG0006",enable=true,namespace="ClientTexas")
@Component("UG0006")
public class UG0006Handler  implements IProtocolAsyncHandler<GU0006Beans.Response, Object>{

	/**
	 * 
	 */
	public UG0006Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public void onRecieve(Response request, Object extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		
		Goods goods=request.getList().get(0);
	
		
		System.out.println(request.toString());
	}

}
