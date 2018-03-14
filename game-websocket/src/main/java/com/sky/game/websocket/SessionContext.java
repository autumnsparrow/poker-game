/**
 * 
 */
package com.sky.game.websocket;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.Session;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.domain.MinaBeans.Extra;
import com.sky.game.context.event.EventProcessTask;
import com.sky.game.context.message.IceProxyMessageInvoker;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.protocol.commons.MS0001Beans;
import com.sky.game.websocket.context.GameConnectionStates;
import com.sky.game.websocket.context.IGameConnectionStateChanged;
import com.sky.game.websocket.packet.EncryptTypes;
import com.sky.game.websocket.packet.PacketTypes;

/**
 * 
 * connectionId connection established. ( openedTimestamp)
 * 
 * deviceId device binding connection.
 * 
 * requestRecieved (bytesReceived) responsePushed (bytesSent)
 * 
 * connection idle connection exception. (should close the connection)
 * 
 * device un-binding connection/ connection closed (closedTimestamp)
 * 
 * 
 * 
 * 
 * @author sparrow
 *
 */

public class SessionContext implements Serializable,
		IGameConnectionStateChanged {

	public boolean isEncrypt() {
		return encrypt;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7011960836362363693L;
	private static final Future<Void> COMPLETED = new CompletedFuture();
	Future<Void> f = COMPLETED;
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
	Session session;
	String desKey;
	boolean encrypt;
	
	
	boolean doRemoveSessionMap;

	private static final AtomicInteger snakeIds = new AtomicInteger(0);

	public GameConnectionStateChangedObserver getFactory() {
//		GameConnectionStateChangedObserver factory = SpringContext
//				.getBean("sessionContextFactory");
//		return factory;
		return null;
	}

	private SessionContext(Session session) {
		super();
		this.session = session;

		session.getUserProperties().put(SessionContext.class.getSimpleName(),
				this);
		this.doRemoveSessionMap=true;
		this.connectionId = String.valueOf(session.getId());
		this.createdTimestamp = System.currentTimeMillis();
		this.states = GameConnectionStates.ConnectionOpened;
//		onGameConnectionStateChanged(connectionId, states);
//		if (getFactory() != null)
//			getFactory().addSessionContext(this);

	}

	public static SessionContext getContext(Session session) {
		Object obj = session.getUserProperties().get(
				SessionContext.class.getSimpleName());
		return (obj != null) ? (SessionContext) obj : null;
	}

	public static SessionContext obtain(Session session) {
		return new SessionContext(session);
	}

	@Override
	public String toString() {
		return "SessionContext [deviceId="
				+ deviceId
				+ ", connectionId="
				+ connectionId
				+ ",active="
				+ (((unbindingTimestamp > 0 ? (unbindingTimestamp - bindingTimestamp)
						: 0)) / 1000) + " sec , bindingTimestamp="
				+ bindingTimestamp + ", unbindingTimestamp="
				+ unbindingTimestamp + ", createdTimestamp=" + createdTimestamp
				+ ", openedTimestamp=" + openedTimestamp + ", closedTimestamp="
				+ closedTimestamp + ", lastIdleTimestamp=" + lastIdleTimestamp
				+ ", requestReceived=" + requestReceived + ", responsePushed="
				+ responsePushed + ", bytesReceived=" + bytesReceived
				+ ", bytesSent=" + bytesSent + ", idle=" + idle + ", session="
				+ session.toString() + "]";
	}

	public void closed() {

		closedTimestamp = System.currentTimeMillis();
		states = GameConnectionStates.ConnectionClosed;
		onGameConnectionStateChanged(connectionId, states);
		// send protocol a message online user offline or broken

		ProxyMessageInvoker.onRecieve(MS0001Beans.Request.getRequest(deviceId),
				new MS0001Beans.Extra());
		// should send to the game server 

		if (getFactory() != null)
			getFactory().removeSession(this);

	}

	public void receive( int numbers, int bytes) {

		bytesReceived += bytes;
		requestReceived += numbers;
		states = GameConnectionStates.ConnectionDeviceRecieving;
		onGameConnectionStateChanged(connectionId, states);

	}

	public  void sent( int numbers, int bytes) {
		
		
			bytesSent += bytes;
			responsePushed += numbers;
			states = GameConnectionStates.ConnectionDeviceSending;
			onGameConnectionStateChanged(connectionId, states);
		
	}

	public  void idle() {
			idle++;
			lastIdleTimestamp = System.currentTimeMillis();
			states = GameConnectionStates.ConnectionIdled;
			onGameConnectionStateChanged(connectionId, states);
		
	}

	public  void bind( String deviceId) {
			bindingTimestamp = System.currentTimeMillis();
			states = GameConnectionStates.ConnectionDeviceBinding;
			onGameConnectionStateChanged(connectionId, states);
			onGameConnectionStateBinding(connectionId, deviceId);
			try {
				SessionContextManager.mgr().onBindingSession(session);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public  void unbind() {
		
			unbindingTimestamp = System.currentTimeMillis();
			// ctx.deviceId=null;
			states = GameConnectionStates.ConnectionDeviceUnbind;
			onGameConnectionStateChanged(connectionId, states);
			SessionContextManager.mgr().unBindingSession(session);
		
	}

	public  void opened(Session session) {
		
		
			openedTimestamp = System.currentTimeMillis();
			states = GameConnectionStates.ConnectionOpened;
			onGameConnectionStateChanged(connectionId, states);

		
	}

	public void onGameConnectionStateChanged(String connectionId,
			GameConnectionStates state) {
		// TODO Auto-generated method stub

		if (getFactory() != null)
			getFactory().onGameConnectionStateChanged(connectionId, state);

	}

	public void onGameConnectionStateBinding(String connectionId,
			String deviceId) {
		// TODO Auto-generated method stub
		if (getFactory() != null)
			getFactory().onGameConnectionStateBinding(connectionId, deviceId);

	}

	public void onGameConnectionStateUnBind(String connectionId) {
		// TODO Auto-generated method stub
		if (getFactory() != null)
			getFactory().onGameConnectionStateUnBind(connectionId);
	}

	public void clearup() {
		// TODO Auto-generated method stub
		if (getFactory() != null)
			getFactory().clearup();

	}
	
	
	/**
	 * 
	 * 
	 * 
	 */

	/**
	 * 
	 * 
	 * @param buffer
	 * @return
	 */
	GameWebsocketMessage obtainMessage(ByteBuffer buffer){
		return new GameWebsocketMessage(this, buffer);
	}
	
	
	
	
	void request(ByteBuffer buffer){
		GameWebsocketMessage msg=obtainMessage(buffer);
		
		
		SessionContextManager.mgr().getHandler().addEvent(new EventProcessTask<GameWebsocketMessage>(msg){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					t.unmashall();
					if(t.packetType.equalsType(PacketTypes.SyncPacketType)){
						// forward the request.
						handleMessage(t);
					}
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		});
		
	}
	
	public GameWebsocketMessage obtainMessage( PacketTypes packetType,
			EncryptTypes encryptType, String content){
		return new GameWebsocketMessage(this, packetType, encryptType, content);
	}
	
	public void response(GameWebsocketMessage msg){
		SessionContextManager.mgr().getHandler().addEvent(new EventProcessTask<GameWebsocketMessage>(msg){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					t.mashall();
					
					//session.getAsyncRemote().sendBinary(t.binary);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	
	public void handleMessage(GameWebsocketMessage msg){
		Message recieveMess = msg.getMessage();
		encrypt=msg.encryptType.booleanValue();
		boolean sync=IceProxyMessageInvoker.sync(recieveMess);
		
		try {
			Message resp=null;
			if(sync){
				// sync invoke
				// 
				resp=IceProxyMessageInvoker.invoke(recieveMess);
			}
			else{
				// async invoke
				//String deviceId=null;//
				com.sky.game.context.domain.MinaBeans.Extra extra=Extra.getExtra(deviceId);
				
				IceProxyMessageInvoker.onRecieve(recieveMess, GameContextGlobals.getJsonConvertor().format(extra));
			}
			
			if(resp!=null){
				if ((resp.transcode != null && !""
						.equals(resp.transcode))) {
					// 返回报文给客户端
					String content=resp.transcode+"&"+resp.token+"&"+resp.content;
					GameWebsocketMessage respMsg=obtainMessage( PacketTypes.SyncPacketType, encrypt?EncryptTypes.True:EncryptTypes.False, content);
					response(respMsg);

					

				} else {

					GameWebsocketMessage respMsg=obtainMessage( PacketTypes.InvalidPacketType, encrypt?EncryptTypes.True:EncryptTypes.False, null);
					response(respMsg);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public   void sendBinary(ByteBuffer byteBuffer){
		
			synchronized (session) {
				try {
					session.getBasicRemote().sendBinary(byteBuffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		
		
	}
	
	
	private static class CompletedFuture implements Future<Void> {

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isCancelled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isDone() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Void get() throws InterruptedException, ExecutionException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Void get(long timeout, TimeUnit unit)
				throws InterruptedException, ExecutionException,
				TimeoutException {
			// TODO Auto-generated method stub
			return null;
		}
	
	}
}
