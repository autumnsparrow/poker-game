/**
 * 
 * @Date:Nov 5, 2014 - 9:49:42 AM
 * @Author: sparrow
 * @Project :game-mina-client 
 * 
 *
 */
package com.sky.game.mina;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoProcessor;
import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.sky.game.mina.packet.Packet;
import com.sky.game.mina.packet.PacketException;

/**
 * @author sparrow
 *
 */
public class MinaConnector {
	
	
	String ip;
	int port;
	
	
	
	NioSocketConnector connector;
	
	
	

	
	public MinaConnector() {
		super();
		
		IoProcessor<NioSession> ioProcessor=new SimpleIoProcessorPool<NioSession>(NioProcessor.class,5);
		MinaIoHandler handler=new MinaIoHandler();
		
		this.connector=new NioSocketConnector(ioProcessor);
		this.connector.setHandler(handler);
		this.connector.getSessionConfig().setUseReadOperation(true);
		
		
	}
	
	
	public IoSession connect(String ip,int port,IoFutureListener<IoFuture> ioListener){
		ConnectFuture future = connector.connect(
				new InetSocketAddress(ip,port));
		
		future.addListener(ioListener);

		//IoSession session = null;
		future.awaitUninterruptibly();
		IoSession session = future.getSession();
		
		
		return session;
	}
	
	
	
	
	
	/**
	 * sync call
	 * 
	 * @param packet
	 * @param session
	 * @return
	 * @throws PacketException 
	 */
	public static Packet request(Packet packet,IoSession session) throws PacketException{
		
		Packet resp=null;
		WriteFuture future = packet.writePacket(session);
		future.awaitUninterruptibly();
		ReadFuture f = session.read();
		f.awaitUninterruptibly();
		resp =Packet.getPacket();
		IoBuffer buffer = (IoBuffer) f.getMessage();
		resp.readPacket( buffer,session);
		
		return packet;

		
	}
	
	
	/**
	 * async call.
	 * @param packet
	 * @param session
	 * @throws PacketException 
	 */
	public static void requestAsync(Packet packet,IoSession session) throws PacketException{
		
		WriteFuture future = packet.writePacket(session);
	}
	
	
	public static Packet onAsyncResponse(IoSession session,Object message) throws PacketException{
		Packet resp=null;
		resp =Packet.getPacket();
		
		IoBuffer buffer = (IoBuffer) message;
		resp.readPacket( buffer,session);
		
		return resp;
		
	}
	
	
	
	
	
	
	
	

}
