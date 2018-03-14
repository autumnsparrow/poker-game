/**
 * 
 * @Date:Nov 5, 2014 - 9:54:51 AM
 * @Author: sparrow
 * @Project :game-mina-client 
 * 
 *
 */
package com.sky.game.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.sky.game.context.Message;
import com.sky.game.context.message.MessageBean;
import com.sky.game.context.util.HexDump;
import com.sky.game.mina.context.SessionContext;
import com.sky.game.mina.packet.Packet;
import com.sky.game.mina.packet.PacketTypes;

/**
 * @author sparrow
 *
 */
public class MinaIoHandler extends IoHandlerAdapter {
	private static final Log logger=LogFactory.getLog(MinaIoHandler.class);

	/**
	 * 
	 */
	public MinaIoHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
	//	SessionContext.getContext(session);
		SessionContext.instance(session);
		logger.info("onCreate - "+session.toString());
		
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
		logger.info("sessionOpened - "+session.toString());
		SessionContext context=SessionContext.getContext(session);
		context.mobileUser.binding();
		
		// when the session opened
//		 Message m=null;
//		 Packet p=new Packet(m, PacketTypes.DeviceBindingPacketType.getType());
//		 
//	     MinaConnector.requestAsync(p, session); 
	     //Packet bindingRespPacket=MinaConnector.request(p, session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
		logger.info("sessionClosed - "+session.toString());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
		logger.info("sessionIdle - "+session.toString());
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
		cause.printStackTrace();
		logger.info("exceptionCaught - "+session.toString()+" - ");
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
		super.messageReceived(session, message);
		//logger.info("messageReceived - "+session.toString());
		
		IoBuffer msg = (IoBuffer) message;
		
		
		session.getConfig().setReadBufferSize(8096);
		//logger.info(HexDump.dumpHexString(msg.array()));
		
		//
		// System reieved the async message.
		//Packet p=MinaConnector.onAsyncResponse(session);
		SessionContext context=SessionContext.getContext(session);
		Packet p=MinaConnector.onAsyncResponse(session,message);
		MessageBean m=new MessageBean(p.getMessage());
		
	
		if(p.getTypes().compare(PacketTypes.SyncPacketType))
				context.getServantHandler().onRecieve(m, context);
		else if(p.getTypes().compare(PacketTypes.PingPacketType.DeviceBindingPacketType)){
			if(p.getContent().equals("Success")){
				context.mobileUser.onBinded();
			}
		}
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}
	
	

}
