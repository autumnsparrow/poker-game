/**
 * 
 */
package com.sky.game.websocket;

import java.nio.ByteBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.Message;
import com.sky.game.websocket.packet.EncryptTypes;
import com.sky.game.websocket.packet.PacketTypes;
import com.sky.game.websocket.util.DES;
import com.sky.game.websocket.util.HexDump;
import com.sky.game.websocket.util.RSAUtils;

/**
 * @author sparrow
 *
 */
public class GameWebsocketMessage {
	private static final Log logger=LogFactory.getLog(GameWebsocketMessage.class);

	int type;
	boolean encrypt;
	String content;

	
	ByteBuffer binary;
	
	EncryptTypes encryptType;
	
	PacketTypes packetType;

	
	SessionContext context;

	public GameWebsocketMessage(SessionContext context, ByteBuffer binary) {
		super();
		this.context = context;
		this.binary = binary;

		//

		// unmashall();
	}
	
	private void binding(String c) {
		//String content = RSAUtils.getString(bytes, 0, bytes.length);
		if (!RSAUtils.isEmpty(c) && c.indexOf("~") > 0) {
			String[] values = c.split("~");
			if (values != null && values.length == 2) {
				String deviceId = values[0];
				String desKey = values[1];
				try {
					context.desKey=desKey;
					context.deviceId=deviceId;
					context.encrypt=true;
				
					SessionContextManager.mgr().onBindingSession(context.session);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//SessionContext.bind(context.session, deviceId);
				
				// write back the echo message.
				//
				content="Ok";
				mashall();

			}
		}
	}

	public void unmashall() {
		this.binary.mark();
		byte t = this.binary.get();
		byte e = this.binary.get();

		packetType = PacketTypes.get(t);
		encryptType = EncryptTypes.get(e);
		//
		byte[]  encryptedBytes=binary.array();
		
		if (encryptType.booleanValue()) {

			byte[] bytes = null;
			
			if (packetType.equalsType(PacketTypes.DeviceBindingPacketType)) {
				
				bytes = RSAUtils.rsa().decrypt(encryptedBytes,2,encryptedBytes.length-2);
				// binding the device id.
				//binding(bytes);
				

			} else if (packetType.equalsType(PacketTypes.SyncPacketType)) {
				bytes=DES.des().decrypt(context.desKey, encryptedBytes,2,encryptedBytes.length-2);
			}

			content = RSAUtils.rsa().bytes2String(bytes, 0, bytes.length);
			logger.info("Request - \n"+HexDump.dumpHexString(bytes));
		} else {
			// no encrypt
			//byte[] bytes=this.binary.array();
			logger.info("Request - \n"+HexDump.dumpHexString(encryptedBytes));
			content = RSAUtils.rsa().bytes2String(encryptedBytes, 2,
					encryptedBytes.length-2);

		}

		this.binary.reset();
		
		if(packetType.equalsType(PacketTypes.DeviceBindingPacketType)){
			binding(content);
		}else if(packetType.equalsType(PacketTypes.SyncPacketType)){
			// forward the request.
			
			
		}
	}

	

	public GameWebsocketMessage(SessionContext context, PacketTypes packetType,
			EncryptTypes encryptType, String content) {
		super();
		this.context = context;
		this.packetType = packetType;
		this.encryptType = encryptType;
		this.content = content;
	}

	public void mashall() {
		byte t = (byte) this.packetType.intValue();
		byte e = (byte) this.encryptType.value;
		if (this.encryptType.booleanValue()) {
			// encrypt
			byte[] contentBytes = RSAUtils.getBytes(content);
			if (contentBytes != null) {
				byte[] bytes = DES.des().encrypt(context.desKey, contentBytes);
				if (bytes != null) {
					ByteBuffer byteBuffer = ByteBuffer
							.allocate(2 + bytes.length);
					byteBuffer.put(t);
					byteBuffer.put(e);
					byteBuffer.put(bytes);
					byteBuffer.flip();
					this.binary = byteBuffer;
				}
				logger.info("Response - \n"+HexDump.dumpHexString(contentBytes));
			}

		} else {
			// no- encrypt
			this.binary = RSAUtils.getBytes(t, e, content);
			logger.info("Response - \n"+HexDump.dumpHexString(this.binary.array()));
		}
		
		
		// send the message
		
		context.sendBinary(this.binary);

	}
	
	
	public Message getMessage(){
		Message message=new Message();
		String[]  entries=content.split("&");
		if(entries!=null&&entries.length==3){
			message.transcode=entries[0];
			message.token=entries[1];
			message.content=entries[2];
		}
		return message;
	}
	
	
	
	

}
