package com.sky.game.mina.util;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.Message;
import com.sky.game.context.MessageException;
import com.sky.game.context.domain.MinaBeans.Extra;
import com.sky.game.context.message.IceProxyMessageInvoker;


import com.sky.game.mina.protocol.Packet;
import com.sky.game.mina.protocol.PacketException;
import com.sky.game.mina.protocol.PacketTypes;


public class MinaMessageUtil {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MinaMessageUtil.class);
	final static MinaMessageUtil instance = new MinaMessageUtil();

	public static MinaMessageUtil getInstance() {
		return instance;
	}
	
	
	

	public static void sendMessage(Packet packet, IoSession session) {
		// 发送消息

		// mina服务器收到的消息
		Message recieveMess = packet.getMessage();
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
				String deviceId=GamePlayerSessionManager.getInstance().getDeviceId(session);
				com.sky.game.context.domain.MinaBeans.Extra extra=Extra.getExtra(deviceId);
				
				IceProxyMessageInvoker.onRecieve(recieveMess, GameContextGlobals.getJsonConvertor().format(extra));
			}
			
			if(resp!=null){
				if ((resp.transcode != null && !""
						.equals(resp.transcode))) {
					// 返回报文给客户端

					Packet.getWritePacket(session, resp,
							PacketTypes.SyncPacketType.getType());

				} else {
					Packet.getWritePacket(session, null,
							PacketTypes.InvalidPacketType.getType());
				}
			}
			
		} catch (MessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PacketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
