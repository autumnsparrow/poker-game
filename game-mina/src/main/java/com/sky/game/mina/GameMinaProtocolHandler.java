package com.sky.game.mina;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import com.sky.game.game.mina.context.SessionContext;
import com.sky.game.mina.protocol.IoBufferSplitter;
import com.sky.game.mina.protocol.Packet;
import com.sky.game.mina.protocol.PacketException;
import com.sky.game.mina.task.MessageTask;
import com.sky.game.mina.util.GamePlayerSessionManager;
import com.sky.game.mina.util.HexDump;


public class GameMinaProtocolHandler extends IoHandlerAdapter {

	private final static Log
	LOGGER = LogFactory
			.getLog(GameMinaProtocolHandler.class);
	//private static final Log log=LogFactory.getLog(SessionContextFactory.class);

	// 处理当客户端发送的消息到达时
	public void messageReceived(IoSession session, Object message)
			throws Exception {

		IoBuffer msg = (IoBuffer) message;
		session.getConfig().setReadBufferSize(1024);
		

		 LOGGER.info(" ReadBuffer Size"+session.getConfig().getReadBufferSize()+" position:"+msg.position());
		//
		//LOGGER.info("\nIoBuffer :[capcity="+msg.capacity()+",limit="+msg.limit()+"]\n"+HexDump.dumpHexString(msg.array(), 0, msg.limit()));
		// LOGGER.info(session.toString());
		//HexDump.dumpHexString(array)
		LOGGER.info(HexDump.dumpHexString(msg.array()));
		IoBuffer io = getIoBuffer(session, msg);
		
		if(io==null)
			return;

		try {
			List<IoBuffer> buffers = IoBufferSplitter.split(io);

			for (IoBuffer buffer : buffers) {

				SessionContext.receive(session, 1, buffer.limit());

				Packet packet =Packet
						.getReadPacket(buffer, session);

				GameMinaServer.execute(new MessageTask(packet, session));
			}
		} catch (PacketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		GamePlayerSessionManager.getInstance().unBindingSession(session);
		SessionContext.closed(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		LOGGER.info("Create Session:" + session + "," + "SessionID="
				+ session.getId());
		
		SessionContext.instance(session);
		// ctx.setConnectionId(String.valueOf(session.getId()));

	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
		cause.printStackTrace();
		session.close(true);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		SessionContext.sent(session, 1, (message == null ? 0
				: ((IoBuffer) message).limit()));
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
		// when the session idle ,what's the server do?
		// the server should ping.
		
		long idleTime=System.currentTimeMillis()-session.getLastBothIdleTime();
		int idleTimes=session.getIdleCount(status);
		LOGGER.info("  Idle :"+idleTime+" Idle times:"+idleTimes);
		SessionContext.idle(session);

	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		SessionContext.opened(session);
	}

	private static final class IoBufferConcator implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6535956236924607053L;

		List<IoBuffer> ioBuffers;

		private IoBufferConcator() {
			super();
			// TODO Auto-generated constructor stub
			ioBuffers = new LinkedList<IoBuffer>();
		}

		public synchronized void append(IoBuffer buffer) {

			ioBuffers.add(buffer);

		}

		public synchronized boolean finalBuffer(IoBuffer buffer) {
			return buffer.limit() < buffer.capacity();
		}

		public synchronized IoBuffer getBuffer() {
			IoBuffer buf = null;
			
				int length = 0;

				for (int i = 0; i < this.ioBuffers.size(); i++) {
					length = ioBuffers.get(i).capacity();
				}

				buf = IoBuffer.allocate(length);

				// buf.putInt(this.packetLength);
				for (int i = 0; i < this.ioBuffers.size(); i++) {
					buf.put(this.ioBuffers.get(i));
				}
				buf.flip();
				ioBuffers.clear();
			
			return buf;
		}

		public static IoBufferConcator getConcator(IoSession session) {

			Object obj = session.getAttribute(IoBufferConcator.class
					.getSimpleName());

			IoBufferConcator concator = null;
			if (obj != null) {
				concator = (IoBufferConcator) obj;
			} else {
				concator = new IoBufferConcator();
				session.setAttribute(IoBufferConcator.class.getSimpleName(),
						concator);
			}

			return concator;
		}
		
		
		

	}
	
	public static IoBuffer getIoBuffer(IoSession session,IoBuffer buffer){
			// get the session buffer concator,then put the buffer 
			IoBufferConcator concator=IoBufferConcator.getConcator(session);
			concator.append(buffer);
			
			// if the buffer is the last,then release the buffer.
			IoBuffer buf=null;
			if(concator.finalBuffer(buffer)){
				buf=concator.getBuffer();
			}
			
			
			return buf;
		}
	
	
	

	/**
	 * 
	 * 
	 * packet total length |---------------------------------| | trunk | | |
	 * <--- trunks
	 * 
	 * ^ | position
	 * 
	 * should abandon the implements.
	 * 
	 * @author Administrator
	 * 
	 *         private static final class IoBufferConcator implements
	 *         java.io.Serializable{
	 * 
	 *         private static final long serialVersionUID =
	 *         6465131337717128625L; int packetLength; int packetPosition;
	 *         LinkedList<IoBuffer> trunks;
	 * 
	 * 
	 *         public IoBufferConcator(int packetLength) { super();
	 *         this.packetLength = packetLength; this.packetPosition=0;
	 *         this.trunks=new LinkedList<IoBuffer>();
	 *         LOGGER.info("Packet-Length:"+packetLength);
	 * 
	 *         }
	 * 
	 *         public synchronized void append(IoBuffer buffer){ if(!inValid()){
	 *         this.trunks.add(buffer); this.packetPosition+=buffer.remaining();
	 *         LOGGER.info("Packet-Position:"+this.packetPosition); } }
	 * 
	 *         public boolean isFinalTrunk(){ boolean last=
	 *         this.packetLength==this.packetPosition-4; if(last){
	 *         LOGGER.info("Last-Packet!"); } return last; }
	 * 
	 *         public boolean inValid(){ return
	 *         this.packetLength<this.packetPosition-4; }
	 * 
	 *         public void reset(int packetLength){ this.packetLength =
	 *         packetLength; this.packetPosition=0; this.trunks.clear();
	 * 
	 *         }
	 * 
	 *         public boolean isFirstTrunk(){ return this.trunks.size()==0; }
	 * 
	 *         public IoBuffer getIoBuffer(){ IoBuffer
	 *         buf=IoBuffer.allocate(this.packetLength+4); //
	 *         buf.putInt(this.packetLength); for(int
	 *         i=0;i<this.trunks.size();i++){ buf.put(this.trunks.get(i)); }
	 *         buf.flip(); return buf; }
	 * 
	 *         public static IoBufferConcator getConcator(IoSession session){
	 * 
	 *         Object
	 *         obj=session.getAttribute(IoBufferConcator.class.getSimpleName());
	 * 
	 *         IoBufferConcator concator=null; if(obj!=null){
	 *         concator=(IoBufferConcator)obj; }else{ concator=new
	 *         IoBufferConcator(0);
	 *         session.setAttribute(IoBufferConcator.class.getSimpleName(),
	 *         concator); }
	 * 
	 *         return concator; }
	 * 
	 *         }
	 * 
	 * 
	 * 
	 *         private synchronized IoBuffer getIoBuffer(IoSession
	 *         session,IoBuffer io){
	 * 
	 *         IoBufferConcator concator=IoBufferConcator.getConcator(session);
	 * 
	 *         IoBuffer buffer =null; if(concator.isFirstTrunk()){
	 * 
	 *         io.mark(); int ioRemaining=io.remaining(); int
	 *         ioLength=io.getInt(); io.reset(); if(ioLength+4>ioRemaining){
	 * 
	 *         concator.reset(ioLength); concator.append(io); // only when the
	 *         io length > io remaining the buffer is null. }else
	 *         if(ioLength+4>=ioRemaining){ buffer=io; } }else { io.mark(); int
	 *         ioRemaining=io.remaining();
	 * 
	 *         io.reset(); concator.append(io);
	 * 
	 *         }
	 * 
	 *         if(concator.isFinalTrunk()){ // concate the buffers; then reset
	 *         the concator.
	 * 
	 *         buffer=concator.getIoBuffer(); concator.reset(0); }
	 * 
	 *         return buffer;
	 * 
	 * 
	 *         } *
	 */

}
