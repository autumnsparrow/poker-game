/**
 * 
 */
package com.sky.game.websocket;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.game.context.configuration.GameContextConfiguration;
import com.sky.game.context.event.EventHandler;
import com.sky.game.context.event.EventProcessTask;
import com.sky.game.context.message.ProxyMessageInvoker;
import com.sky.game.protocol.commons.MS0001Beans;
import com.sky.game.websocket.util.WebsocketConfiguration;



/**
 * @author Administrator
 *
 */
public class SessionContextManager {
	
	public final static Logger LOGGER = LoggerFactory.getLogger("Session");
	 static SessionContextManager instance;//new SessionContextManager();
	
	private static final Object lock=new Object();
	public static SessionContextManager mgr(){
		if(instance==null){
			synchronized (lock) {
				if(instance==null)
					instance=new SessionContextManager();
			}
		}
		return instance;
	}
	
	public void post(Runnable task){
		try {
			this.taskExceutor.execute(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public SessionContextManager() {
		super();
		// TODO Auto-generated constructor stub
		taskExceutor=Executors.newFixedThreadPool(WebsocketConfiguration.getInstance().getThreadPoolSize());
		
		hanlder=EventHandler.obtain(taskExceutor);
	}



	//标志session回话连接的唯一性
	public final static LinkedHashMap<String, Session> playerSessionMap=new LinkedHashMap<String, Session>();
	
	public String getSessionMap(){
		return Arrays.toString(playerSessionMap.keySet().toArray(new String[]{}));
	}
	
	 void onBindingSession(Session session) throws Exception{
		SessionContext context=SessionContext.getContext(session);
		String deviceId=context.deviceId;
		if(deviceId!=null){
			if(playerSessionMap.containsKey(deviceId)){
				//把前一个session断掉
			
				Session oldSession=playerSessionMap.get(deviceId);
				SessionContext oldSessionContext=SessionContext.getContext(oldSession);
				//LOGGER.info("BIND  - close old "+oldSessionContext.toString());
				oldSessionContext.doRemoveSessionMap=false;
				oldSession.close();
				//
			}
			playerSessionMap.put(deviceId, session);
			//给客户端发送成功的消息
			
			
			LOGGER.info("BIND - "+context.toString());
			//LOGGER.info("DeviceId send success,DeviceId="+deviceId+",packetLenth="+packetLenth);
		}else{
			session.close();
			LOGGER.error("DeviceId is null");
		}
	}
	
	
	 void unBindingSession(Session session){
		SessionContext context=SessionContext.getContext(session);
		//LOGGER.info("UNBIND - "+context.toString());
		String deviceId=context.deviceId;
		if(deviceId!=null&&playerSessionMap.containsKey(deviceId)){
//			playerSessionMap.remove(deviceId);
			MS0001Beans.Extra extra=new MS0001Beans.Extra();
			extra.setDeviceId(deviceId);
			ProxyMessageInvoker.onRecieve( MS0001Beans.Request.getRequest(deviceId), extra);
			
			Session oldSession=playerSessionMap.get(deviceId);
			//SessionContext oldSessionContext=SessionContext.getContext(oldSession);
			//LOGGER.info("BIND  - close old "+oldSessionContext.toString());
			
			LOGGER.info("UNBIND - "+context.toString());
			if(oldSession.getId().equals(session.getId())){
				playerSessionMap.remove(deviceId);
				
			}
//			try {
//				session.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			LOGGER.error("session closed,Delete Session:"+session+","+"SessionID="+session.getId());
		}
	}
	 
	 
	public Session getSession(String deviceId){
		Session session=null;
		if(deviceId!=null&&playerSessionMap.containsKey(deviceId)){
			session=playerSessionMap.get(deviceId);
		}
		return session;
	}
	
	private  ExecutorService taskExceutor=null;
	EventHandler<EventProcessTask<?>> hanlder;
	
	public EventHandler<EventProcessTask<?>> getHandler(){
		return hanlder;
	}
	
	
	

}
