/**
 * 
 */
package com.sky.game.mina.protocol;

import java.util.LinkedList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.game.mina.util.HexDump;


/**
 * @author Administrator
 * 
 */
public class IoBufferSplitter {

	private static final int MAX_PACKET_LENGTH=1024*8;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(IoBufferSplitter.class);

	public static List<IoBuffer> split(IoBuffer buffer) throws PacketException {

		List<IoBuffer> buffers = new LinkedList<IoBuffer>();
		// 1. get the size of the packet.
		buffer.mark();
		int bufferLength = buffer.remaining();
		
		int offsetOfLengthPosition = 0;
		do {

			buffer.position(offsetOfLengthPosition);
			int length = buffer.getInt();
			LOGGER.info("****************,length="+length+",bufferLength="+bufferLength);
			if(length>MAX_PACKET_LENGTH||(length>bufferLength)){
				throw new PacketException(PacketTypes.InvalidPacketType.getType(),PacketTypes.InvalidPacketType.getDescription());
			}
			
			IoBuffer io = IoBuffer.allocate(length + 4);
			io.put(buffer.array(), offsetOfLengthPosition, length + 4);
			io.flip();
			// IoBuffer io=IoBuffer.wrap(buffer.array(), offsetOfLengthPosition,
			// length+4);
			buffers.add(io);

			offsetOfLengthPosition += (length + 4);
			// if(bufferLength>offsetOfLengh)

		} while (bufferLength > offsetOfLengthPosition);

		buffer.reset();

		return buffers;
	}

	/*
	 * public static void main(String args[]){
	 * 
	 * IoBuffer buffer=IoBuffer.allocate(1024*20);
	 * 
	 * for(int i=0;i<512;i++){ buffer.putInt(4+4); buffer.putInt(1);
	 * buffer.putInt(i); } buffer.flip();
	 * 
	 * System.out.println(HexDump.dumpHexString(buffer.array(),0,buffer.remaining()));
	 * 
	 * 
	 * IoBufferSplitter splitter=IoBufferSplitter.getInstance(buffer);
	 * 
	 * splitter.split();
	 * 
	 * List<IoBuffer> buffers=splitter.getBuffers();
	 * System.out.println("----------------------------------");
	 * 
	 * for(IoBuffer io:buffers){
	 * System.out.println(HexDump.dumpHexString(io.array())); } }
	 */
	public static void main(String args[]) throws PacketException{
		IoBuffer buffer=IoBuffer.allocate(1024*20);
		for(int i=0;i<1;i++){ buffer.putInt(4+4); 
		} buffer.flip();
		  
		 System.out.println(HexDump.dumpHexString(buffer.array(),0,buffer.remaining()));
		  
	
		  List<IoBuffer> buffers=IoBufferSplitter.split(buffer);
		  System.out.println("----------------------------------");
		  
		  for(IoBuffer io:buffers){
		  System.out.println(HexDump.dumpHexString(io.array())); } 
	}

}
