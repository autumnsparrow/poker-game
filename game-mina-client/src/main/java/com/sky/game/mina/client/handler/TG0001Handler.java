/**
 * 
 * @Date:Nov 13, 2014 - 6:46:48 PM
 * @Author: sparrow
 * @Project :game-mina-client 
 * 
 *
 */
package com.sky.game.mina.client.handler;

import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerAsyncType;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.domain.MinaBeans;
import com.sky.game.context.domain.MinaBeans.Extra;
import com.sky.game.context.handler.IProtocolAsyncHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.mina.app.MinaClientApp;
import com.sky.game.mina.client.ProxyMinaMessageInvoker;
import com.sky.game.mina.context.SessionContext;
import com.sky.game.protocol.commons.GT0001Beans;
import com.sky.game.protocol.commons.GT0001Beans.Response;
import com.sky.game.protocol.commons.GT0002Beans;


/**
 * @author sparrow
 *
 */
@HandlerAsyncType(transcode="TG0001",enable=true,namespace="ClientTexas")
@Component("TG0001")
public class TG0001Handler implements IProtocolAsyncHandler<GT0001Beans.Response, SessionContext> {

	/**
	 * 
	 */
	public TG0001Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public void onRecieve(Response request, SessionContext extra)
			throws ProtocolException {
		// TODO Auto-generated method stub
		for(int i=0;i<1;i++){
		GT0001Beans.House h=request.getHouses().get(i);
		//MinaClientApp.token=request.getToken();
		for(int j=0;j<1;j++){
		GT0002Beans.Request r=new GT0002Beans.Request();
		r.setId(Long.valueOf(h.getId()));
		r.setToken(extra.mobileUser.token);
		ProxyMinaMessageInvoker.asyncInvoke(extra, "GT0002", r);
		
		}
		}
		System.out.println(request.toString());
		
	}
	
	

}
