/**
 * 
 */
package com.sky.game.game.mina.context;

import java.io.Serializable;
import org.apache.mina.core.session.IoSession;
import com.sky.game.context.SpringContext;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.protocol.commons.MS0001Beans;



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


public class SessionContext implements Serializable ,IGameConnectionStateChanged{
	
	
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
	
	
	GameConnectionStates states;
	
	
	IoSession  session;
	
	
	
	
	
	
	public SessionContextFactory getFactory(){
		SessionContextFactory factory=SpringContext.getBean("sessionContextFactory");
		return factory;
	}
	
	
	private SessionContext(IoSession session) {
		super();
		this.session = session;
		session.setAttribute(SessionContext.class.getSimpleName(), this);
		this.connectionId=String.valueOf(session.getId());
		this.createdTimestamp=System.currentTimeMillis();
		this.states=GameConnectionStates.ConnectionOpened;
		onGameConnectionStateChanged(connectionId, states);
		if(getFactory()!=null)
			getFactory().addSessionContext(this);
		
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
			ctx.states=GameConnectionStates.ConnectionClosed;
			ctx.onGameConnectionStateChanged(ctx.connectionId, ctx.states);
			// send protocol a message online user offline or  broken
			
			ProxyMessageInvoker.onRecieve( MS0001Beans.Request.getRequest(ctx.deviceId), new MS0001Beans.Extra());
			
			if(ctx.getFactory()!=null)
				ctx.getFactory().removeSession(ctx);
			
		}
		
		
	}
	
	
	
	public static void receive(IoSession session,int numbers,int bytes){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.bytesReceived+=bytes;
			ctx.requestReceived+=numbers;
			ctx.states=GameConnectionStates.ConnectionDeviceRecieving;
			ctx.onGameConnectionStateChanged(ctx.connectionId, ctx.states);
		}
			
		
	}
	
	public static void sent(IoSession session,int numbers,int bytes){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.bytesSent+=bytes;
			ctx.responsePushed+=numbers;
			ctx.states=GameConnectionStates.ConnectionDeviceSending;
			ctx.onGameConnectionStateChanged(ctx.connectionId, ctx.states);
		}
	}
	
	
	public static void idle(IoSession session ){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.idle++;
			ctx.lastIdleTimestamp=System.currentTimeMillis();
			ctx.states=GameConnectionStates.ConnectionIdled;
			ctx.onGameConnectionStateChanged(ctx.connectionId, ctx.states);
		}
	}
	
	public static void bind(IoSession session,String deviceId){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.bindingTimestamp=System.currentTimeMillis();
			ctx.deviceId=deviceId;
			ctx.states=GameConnectionStates.ConnectionDeviceBinding;
			ctx.onGameConnectionStateChanged(ctx.connectionId, ctx.states);
			ctx.onGameConnectionStateBinding(ctx.connectionId, ctx.deviceId);
		}
	}
	
	public static void unbind(IoSession session){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.unbindingTimestamp=System.currentTimeMillis();
			//ctx.deviceId=null;
			ctx.states=GameConnectionStates.ConnectionDeviceUnbind;
			ctx.onGameConnectionStateChanged(ctx.connectionId, ctx.states);
		}
	}
	
	
	public static void opened(IoSession session){
		SessionContext ctx=getContext(session);
		if(ctx!=null){
			ctx.openedTimestamp=System.currentTimeMillis();
			ctx.states=GameConnectionStates.ConnectionOpened;
			ctx.onGameConnectionStateChanged(ctx.connectionId, ctx.states);
			
		}
	}


	
	public void onGameConnectionStateChanged(String connectionId,
			GameConnectionStates state) {
		// TODO Auto-generated method stub
		
		if(getFactory()!=null)
			getFactory().onGameConnectionStateChanged(connectionId, state);
			
	}


	
	public void onGameConnectionStateBinding(String connectionId, String deviceId) {
		// TODO Auto-generated method stub
		if(getFactory()!=null)
			getFactory().onGameConnectionStateBinding(connectionId, deviceId);
		
	}


	
	public void onGameConnectionStateUnBind(String connectionId) {
		// TODO Auto-generated method stub
		if(getFactory()!=null)
			getFactory().onGameConnectionStateUnBind(connectionId);
	}


	
	public void clearup() {
		// TODO Auto-generated method stub
		if(getFactory()!=null)
			getFactory().clearup();
		
	}
	
	
	
	
	
	

}
