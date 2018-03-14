/**
 * 
 * @Date:Nov 5, 2014 - 10:43:59 AM
 * @Author: sparrow
 * @Project :game-mina-client 
 * 
 *
 */
package com.sky.game.mina.client;

import com.sky.game.context.Message;

import com.sky.game.context.message.IMessageHandler;
import com.sky.game.context.message.MessageBean;
import com.sky.game.context.message.MessageException;
import com.sky.game.mina.MinaConnector;
import com.sky.game.mina.context.SessionContext;
import com.sky.game.mina.packet.Packet;
import com.sky.game.mina.packet.PacketException;
import com.sky.game.mina.packet.PacketTypes;

/**
 * @author sparrow
 *
 */
public class MinaMessageHandler implements IClientMessage{
	
	
	SessionContext context;
	

	public MinaMessageHandler(SessionContext context) {
		super();
		this.context = context;
		
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 */
	public MinaMessageHandler() {
		// TODO Auto-generated constructor stub
	}

	/* 
	 * 
	 * 
	 * (non-Javadoc)
	 * @see com.sky.game.context.message.IMessageHandler#invoke(com.sky.game.context.message.MessageBean)
	 */
	public MessageBean invoke(MessageBean message) throws MessageException {
		// TODO Auto-generated method stub
		Message m=new Message();
		m.content=message.content;
		m.transcode=message.transcode;
		m.token=message.token;
		
		MessageBean mb=new MessageBean();
		Packet p=new Packet(m, PacketTypes.SyncPacketType.getType());
		
		try {
			Packet resp=MinaConnector.request(p, this.context.getSession());
			
			if(resp!=null){
				Message msg=resp.getMessage();
				mb.transcode=msg.transcode;
				mb.token=msg.token;
				mb.content=msg.content;
			}
			
		} catch (PacketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.context.session
		
		return mb;
	}

	/* 
	 * invoke async request to server.
	 * 
	 * 
	 * (non-Javadoc)
	 * @see com.sky.game.context.message.IMessageHandler#onRecieve(com.sky.game.context.message.MessageBean, java.lang.String)
	 */
	public void onRecieve(MessageBean message, Object extra)
			throws MessageException {
		// TODO Auto-generated method stub
		
		Message m=new Message();
		m.content=message.content;
		m.transcode=message.transcode;
		m.token=message.token;
		
		
		Packet p=new Packet(m, PacketTypes.SyncPacketType.getType());
		
		try {
			MinaConnector.requestAsync(p, this.context.getSession());
		} catch (PacketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
