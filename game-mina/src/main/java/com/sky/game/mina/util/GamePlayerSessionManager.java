/**
 * 
 */
package com.sky.game.mina.util;

import java.util.LinkedHashMap;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sky.game.game.mina.context.SessionContext;
import com.sky.game.mina.protocol.Packet;
import com.sky.game.mina.protocol.PacketTypes;

/**
 * @author Administrator
 *
 */
public class GamePlayerSessionManager {
	
	public final static Logger LOGGER = LoggerFactory.getLogger("Session");
	final static GamePlayerSessionManager instance=new GamePlayerSessionManager();
	public static GamePlayerSessionManager getInstance(){
		return instance;
	}
	public static final String DEVICE_ID="DeviceId";
	public static final String KEY="Key";
	public static final String STATUS="Status";
	public static final String SUCCESS="Success";
	//标志session回话连接的唯一性
	public final static LinkedHashMap<String, IoSession> playerSessionMap=new LinkedHashMap<String, IoSession>();
	
	public String getDeviceId(IoSession session){
		Object obj=session.getAttribute(DEVICE_ID);
		String deviceId=null;
		if(obj!=null){
			deviceId=(String)obj;
		}
		return deviceId;
	}
	
	public String getKey(IoSession session){
		Object obj = session.getAttribute(KEY);
		String key = null;
		if(obj!=null){
			key = (String)obj;
		}
		return key;
	}
	
	public String getStatus(IoSession session){
		Object obj = session.getAttribute(STATUS);
		String status = null;
		if(obj!=null){
			status = (String)obj;
		}
		return status;
	}
	
	public void onBindingSession(IoSession session) throws Exception{
		String deviceId=getDeviceId(session);
		if(deviceId!=null){
			if(playerSessionMap.containsKey(deviceId)){
				//把前一个session断掉
				playerSessionMap.get(deviceId).close(true);
			}
			playerSessionMap.put(deviceId, session);
			//给客户端发送成功的消息
			//String sendStr = SUCCESS;
			
			//Packet.getWritePacket(session, null, type)
			Packet.getWritePacket(session, null,PacketTypes.DeviceBindingPacketType.getType());
			
			SessionContext.bind(session, deviceId);
			SessionContext context=SessionContext.getContext(session);
			LOGGER.info("BIND - "+context.toString());
			//LOGGER.info("DeviceId send success,DeviceId="+deviceId+",packetLenth="+packetLenth);
		}else{
			session.close(true);
			LOGGER.error("DeviceId is null");
		}
	}
	
	
	public void unBindingSession(IoSession session){
		String deviceId=getDeviceId(session);
		if(deviceId!=null&&playerSessionMap.containsKey(deviceId)){
//			playerSessionMap.remove(deviceId);
			SessionContext.unbind(session);
			SessionContext context=SessionContext.getContext(session);
			LOGGER.info("UNBIND - "+context.toString());
			playerSessionMap.remove(deviceId);
			session.close(true);
			//LOGGER.error("session closed,Delete Session:"+session+","+"SessionID="+session.getId());
		}
	}
	
	public IoSession getSession(String deviceId){
		IoSession session=null;
		if(deviceId!=null&&playerSessionMap.containsKey(deviceId)){
			session=playerSessionMap.get(deviceId);
		}
		return session;
	}

}
