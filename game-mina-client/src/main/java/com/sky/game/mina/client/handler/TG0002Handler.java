/**
 * 
 * @Date:Nov 14, 2014 - 1:48:03 PM
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
import com.sky.game.mina.app.MinaClientApp;
import com.sky.game.mina.client.ProxyMinaMessageInvoker;
import com.sky.game.mina.context.SessionContext;
import com.sky.game.protocol.commons.GT0002Beans;
import com.sky.game.protocol.commons.GT0002Beans.Table;
import com.sky.game.protocol.commons.GT0003Beans;

/**
 * @author sparrow
 *
 */
@HandlerAsyncType(transcode="TG0002",enable=true,namespace="ClientTexas")
@Component("TG0002")
public class TG0002Handler implements IProtocolAsyncHandler<GT0002Beans.Response, SessionContext> {

	/**
	 * 
	 */
	public TG0002Handler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.handler.IProtocolAsyncHandler#onRecieve(java.lang.Object, java.lang.Object)
	 */
	@HandlerMethod(enable=true)
	public void onRecieve(GT0002Beans.Response request, SessionContext extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		
		Table t=request.getTables().get(0);
		
		// here join the talbe.
		for(int j=0;j<1;j++){
			GT0003Beans.Request r=GT0003Beans.Request.obtain();
			r.setId(t.getId());
			r.setToken(extra.mobileUser.token);
			ProxyMinaMessageInvoker.asyncInvoke(extra, "GT0003", r);
			
			
			
		}
		
		
		System.out.println(request.toString());
		
	}

}
