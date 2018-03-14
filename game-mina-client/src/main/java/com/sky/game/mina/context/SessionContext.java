/**
 * 
 */
package com.sky.game.mina.context;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

import com.sky.game.context.SpringContext;
import com.sky.game.context.message.IMessageHandler;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.mina.app.MobileUser;
import com.sky.game.mina.client.IClientMessage;
import com.sky.game.mina.client.MinaMessageHandler;
import com.sky.game.mina.client.MinaServantMessageHandler;




/**
 * 
 *    connectionId  connection established.   ( openedTimestamp)
 *    
 *    deviceId      device binding connection.
 *    
 *    				requestRecieved  (bytesReceived) 
 *    				responsePushed   (bytesSent)
 *    
 *                  connection idle
 *                  connection exception.      (should close the connection)
 *    
 *                  device un-binding connection/ 
 *                  connection closed                (closedTimestamp)
 *    
 *    
 * 
 * 
 * @author sparrow
 *
 */


public class SessionContext implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7011960836362363693L;
	
	String deviceId;
	String connectionId;
	long bindingTimestamp;
	long unbindingTimestamp;
	
	long createdTimestamp;
	long openedTimestamp;
	long closedTimestamp;
	long lastIdleTimestamp;
	 long requestReceived;
	 long responsePushed;
	
	long bytesReceived;
	long bytesSent;
	
	int idle;
	
	
	public MobileUser mobileUser;
	
	
	
	
	
	
	public IoSession getSession() {
		return session;
	}


	public void setSession(IoSession session) {
		this.session = session;
	}


	IoSession  session;
	IClientMessage handler;
	
	IClientMessage servantHandler;
	
	
	
	
	
	
	
	public IClientMessage getServantHandler() {
		return servantHandler;
	}


	public void setServantHandler(IClientMessage servantHandler) {
		this.servantHandler = servantHandler;
	}


	public IClientMessage getHandler() {
		return handler;
	}


	public void setHandler(IClientMessage handler) {
		this.handler = handler;
	}


	private SessionContext(IoSession session) {
		super();
		this.session = session;
		session.setAttribute(SessionContext.class.getSimpleName(), this);
		this.connectionId=String.valueOf(session.getId());
		this.createdTimestamp=System.currentTimeMillis();
		
		this.handler=new MinaMessageHandler(this);
		this.servantHandler=new MinaServantMessageHandler();
		
		
	}
	
	
	public static SessionContext getContext(IoSession session){
		Object obj=session.getAttribute(SessionContext.class.getSimpleName());
		return (obj!=null)?(SessionContext)obj:null;
	}
	



	public static SessionContext instance(IoSession session){
		return new SessionContext(session);
	}
	
	
	






	@Override
	public String toString() {
		return "SessionContext [deviceId=" + deviceId + ", connectionId="
				+ connectionId +",active="+(((unbindingTimestamp>0?(unbindingTimestamp-bindingTimestamp):0))/1000)+ " sec , bindingTimestamp=" + bindingTimestamp
				+ ", unbindingTimestamp=" + unbindingTimestamp
				+ ", createdTimestamp=" + createdTimestamp
				+ ", openedTimestamp=" + openedTimestamp + ", closedTimestamp="
				+ closedTimestamp + ", lastIdleTimestamp=" + lastIdleTimestamp
				+ ", requestReceived=" + requestReceived + ", responsePushed="
				+ responsePushed + ", bytesReceived=" + bytesReceived
				+ ", bytesSent=" + bytesSent + ", idle=" + idle + ", session="
				+ session.toString() + "]";
	}


	public static void closed(IoSession session){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.closedTimestamp=System.currentTimeMillis();
			
			
		}
		
		
	}
	
	
	
	public static void receive(IoSession session,int numbers,int bytes){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.bytesReceived+=bytes;
			ctx.requestReceived+=numbers;
			
		}
			
		
	}
	
	public static void sent(IoSession session,int numbers,int bytes){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.bytesSent+=bytes;
			ctx.responsePushed+=numbers;
			
		}
	}
	
	
	public static void idle(IoSession session ){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.idle++;
			ctx.lastIdleTimestamp=System.currentTimeMillis();
			
		}
	}
	
	public static void bind(IoSession session,String deviceId){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.bindingTimestamp=System.currentTimeMillis();
			ctx.deviceId=deviceId;
			
			
		}
	}
	
	public static void unbind(IoSession session){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.unbindingTimestamp=System.currentTimeMillis();
			//ctx.deviceId=null;
			
		}
	}
	
	
	public static void opened(IoSession session){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.openedTimestamp=System.currentTimeMillis();
			
			
		}
	}


	
	
	
	
	

}
