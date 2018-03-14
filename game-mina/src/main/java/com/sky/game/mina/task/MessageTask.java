package com.sky.game.mina.task;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.game.mina.protocol.Packet;
import com.sky.game.mina.protocol.PacketException;
import com.sky.game.mina.protocol.PacketTypes;
import com.sky.game.mina.util.GamePlayerSessionManager;
import com.sky.game.mina.util.MinaMessageUtil;


public class MessageTask implements Runnable {
	
	private Packet packet;
	private IoSession session;
	private final static Logger LOGGER = LoggerFactory.getLogger(MessageTask.class);
	
	
	

	public MessageTask(Packet packet, IoSession session) {
		super();
		this.packet = packet;
		this.session = session;
	}




	public void run() {
		// TODO Auto-generated method stub

		if(packet.getTypes().compare(PacketTypes.SyncPacketType)){
			MinaMessageUtil.sendMessage(this.packet, session);
		}else if(packet.getTypes().compare(PacketTypes.DeviceBindingPacketType)){
			session.setAttribute(GamePlayerSessionManager.DEVICE_ID, this.packet.getContent());
			session.setAttribute(GamePlayerSessionManager.KEY, this.packet.getKey());
			try {
				GamePlayerSessionManager.getInstance().onBindingSession(session);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOGGER.info("OnBinding Game Server Excepiton:"+e.getMessage());
			}
		}else if(packet.getTypes().compare(PacketTypes.PingPacketType)){
			try {
				
				String deviceId=GamePlayerSessionManager.getInstance().getDeviceId(session);
				
				// status 0: not in game  , 1: in game ,2 :offline
				int status=0;
				if(deviceId==null){
					status=2;
				}
				session.setAttribute(GamePlayerSessionManager.STATUS, String.valueOf(status));
				
				Packet.getWritePacket(session, null, PacketTypes.PingPacketType.getType());
			} catch (PacketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.info("client pings server,type="+packet.getTypes().toString());
		}else{
			LOGGER.error("MyServerHandler.messageReceived type is wrong"+packet.getTypes().toString());
		}
		
		
	
	}

}
