/**
 * 
 */
package com.sky.game.mina.protocol;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sky.game.context.Message;
import com.sky.game.mina.util.DES;
import com.sky.game.mina.util.GamePlayerSessionManager;
import com.sky.game.mina.util.HexDump;
import com.sky.game.mina.util.MinaContextConfiguration;
import com.sky.game.mina.util.RSAUtils;

/**
 * @author Administrator
 * 
 */
public class Packet {
	// network transferring data format.
	private static final int PACKET_INVALID_FORMAT = 1;
	private static final int PACKET_PARSING_FAILED = 2;
	private static final int PACKET_READ_BUFFER_FAILED = 3;
	private static final int PACKET_WRITE_BUFFER_FAILED = 4;
	private final static Logger LOGGER = LoggerFactory.getLogger(Packet.class);

	private int length;
	private PacketTypes types;
	private byte[] data;

	private static final String CHARSET_ENCODING = "utf-8";

	private String content;

	Message message;

	private String key;

	/**
	 * 
	 * for server recieved request.
	 * 
	 * @param length
	 * @param types
	 * @param data
	 */
	public Packet(int length, PacketTypes types, byte[] data) {
		super();
		this.length = length;
		this.types = types;
		this.data = data;
	}

	private Packet() {
		super();
		// TODO Auto-generated constructor stub
		this.length = 0;
		this.types = PacketTypes.InvalidPacketType;
		this.data = null;
	}

	/**
	 * for server response message
	 * 
	 * @param message
	 */
	public Packet(Message message, int type) {
		super();
		this.message = message;
		this.types = PacketTypes.getInstance(type);
	}

	public Packet(String content) {
		super();
		this.content = content;
		this.types = PacketTypes.SyncPacketType;
	}

	private void log(String msg) {
		LOGGER.info(msg);
	}

	/**
	 * 
	 * 
	 * @param io
	 * @throws PacketException
	 */
	public void readPacket(IoBuffer io, IoSession session)
			throws PacketException {

		try {
			this.length = io.getInt();
			this.types = PacketTypes.getInstance(io.getInt());

			int debug = MinaContextConfiguration.getInstance().getDebug();// 0加密
																			// 1不加密

			if (this.types.compare(PacketTypes.SyncPacketType)
					|| this.types.compare(PacketTypes.DeviceBindingPacketType)) {

				int remaining = io.remaining();
				LOGGER.info("readPacket:Remaining:" + remaining + ",length:"
						+ (this.length - 4) + "," + this.types.toString());
				if (remaining < this.length - 4) {
					LOGGER.info("[REMAINNING:"
							+ HexDump.dumpHexString(io.array()));
				}
				if (remaining >= (this.length - 4)) {

					IoBuffer encryptBuffer = IoBuffer.allocate(this.length - 4);
					encryptBuffer.put(io.array(), 8, (this.length - 4));
					encryptBuffer.flip();
					// log("ENCRYPT:\n"+HexDump.dumpHexString(encryptBuffer.array()));

					// 不解密
					String notDecryptStr = new String(io.array(), 8,
							(this.length - 4), CHARSET_ENCODING);

					if (this.types.compare(PacketTypes.DeviceBindingPacketType)) {
						String[] decrypyArr;
						// 解密
						if (debug == 0) {
							// RSA解密
							byte[] decryptArr = RSAUtils.rsa().decrypt(
									encryptBuffer.array());
							log("DEENCRYPT:\n"
									+ HexDump.dumpHexString(decryptArr));

							String decryptStr = new String(decryptArr,
									CHARSET_ENCODING);
							decrypyArr = decryptStr.split("~");
						} else {
							decrypyArr = notDecryptStr.split("~");
						}

						if (decrypyArr.length != 2) {
							throw new PacketException(PACKET_INVALID_FORMAT,
									"invalid packet format,deviceId decrypt is wrong!");
						}

						this.content = decrypyArr[0];
						this.key = decrypyArr[1];
						// log("client send,deviceId="+this.content);
						// log("client send,key="+this.key);
					} else if (this.types.compare(PacketTypes.SyncPacketType)) {
						// DES解密
						if (debug == 0) {
							String desKey = GamePlayerSessionManager
									.getInstance().getKey(session);

							byte[] decryptArr = DES.des().decrypt(desKey,
									encryptBuffer.array());
							this.content = new String(decryptArr,
									CHARSET_ENCODING);
						} else {
							// 不解密
							this.content = notDecryptStr;
						}
						// log("GameServer.onReceive-"+this.content);
					}

					if (this.types.compare(PacketTypes.SyncPacketType)) {
						String entries[] = this.content.split("&", 3);
						if (entries != null && entries.length == 3) {
							String transcode = entries[0];
							String token = entries[1];
							String c = entries[2];
							// 防止解析错误
							if (c.equals("")) {
								c = "{}";
							}
							this.message = new Message(transcode, c, token);
						} else {
							throw new PacketException(PACKET_PARSING_FAILED,
									"parsing packet failed!");
						}
					}
				} else {
					throw new PacketException(PACKET_INVALID_FORMAT,
							"invalid packet format!");
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new PacketException(PACKET_READ_BUFFER_FAILED,
					"reading packet from IoBuffer Exception!!");
		} finally {

		}

	}

	/**
	 * 
	 * 
	 * @param session
	 * @throws PacketException
	 */
	public void writePacket(IoSession session) throws PacketException {
		boolean isReady = true;
		IoBufferHolder holder = new IoBufferHolder();
		// response server a error message type.
		if (this.message == null) {
			if (this.types.compare(PacketTypes.InvalidPacketType)) {
				isReady = writeTypeToPacket(holder,
						PacketTypes.InvalidPacketType);
			} else if (this.types.compare(PacketTypes.DeviceBindingPacketType)) {
				isReady = writeStringToPacket(holder, "Success", session);
			} else if (this.types.compare(PacketTypes.PingPacketType)) {
				String status = GamePlayerSessionManager.getInstance()
						.getStatus(session);
				isReady = writeStringToPacket(holder, status, session);

				// ping时不返回数据
				// isReady = writeTypeToPacket(holder,
				// PacketTypes.PingPacketType);

			} else if (this.types.compare(PacketTypes.SyncPacketType)) {
				isReady = writeStringToPacket(holder, this.content, session);
			}

		} else {
			if (this.types.compare(PacketTypes.SyncPacketType)) {
				String m = this.message.transcode + "&" + this.message.token
						+ "&" + this.message.content;
				isReady = writeStringToPacket(holder, m, session);
			}
		}
		if (isReady) {
			if (session != null && session.isConnected()) {
				LOGGER.debug("[WRITE:\r\n"
						+ HexDump.dumpHexString(holder.io.array(), 0, 8)
						+ ", pakcet Length:" + holder.io.array().length
						+ "\r\n]");
				session.write(holder.io);
			} else {
				if (session != null)
					LOGGER.info("Sessioin is not null:"
							+ session.toString()
							+ ",session.isConnected()="
							+ session.isConnected()
							+ ",deviceId"
							+ GamePlayerSessionManager.getInstance()
									.getDeviceId(session));

				throw new PacketException(PACKET_WRITE_BUFFER_FAILED,
						"writing packet failed");
			}
		} else {

			throw new PacketException(PACKET_WRITE_BUFFER_FAILED,
					"packet convert failed ");
		}

	}

	/**
	 * 
	 * 
	 * 
	 * @param io
	 * @param pt
	 * @return
	 */
	private boolean writeTypeToPacket(IoBufferHolder holder, PacketTypes pt) {

		holder.io = IoBuffer.allocate(8);
		holder.io.putInt(4);
		holder.io.putInt(pt.getType());
		holder.io.flip();
		return true;
	}

	/**
	 * 
	 * 
	 * 
	 * @param io
	 * @param p
	 * @return
	 */
	private boolean writeStringToPacket(IoBufferHolder holder, String p,
			IoSession session) {
		boolean isReady = true;
		int debug = MinaContextConfiguration.getInstance().getDebug();// 0加密
																		// 1不加密
		try {
			byte[] buffer = p.getBytes(CHARSET_ENCODING);
			// type==1或者3时需要DES加密
			if (this.types.compare(PacketTypes.SyncPacketType)
					|| this.types.compare(PacketTypes.PingPacketType)) {
				if(debug==0){
				String desKey = GamePlayerSessionManager.getInstance().getKey(
						session);
				// log("desKey="+desKey);
				buffer = DES.des().encrypt(desKey, buffer);
				}
			}

			holder.io = IoBuffer.allocate(buffer.length + 8);
			holder.io.putInt(buffer.length + 4);
			holder.io.putInt(this.types.getType());
			holder.io.put(buffer);
			holder.io.flip();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isReady = false;
		}
		return isReady;
	}

	public static Packet getReadPacket(IoBuffer io, IoSession session)
			throws PacketException {
		Packet packet = new Packet();
		packet.readPacket(io, session);
		return packet;
	}

	public static Packet getWritePacket(IoSession session, Message message,
			int type) throws PacketException {
		Packet packet = new Packet(message, type);
		packet.writePacket(session);
		return packet;
	}

	public static Packet getWritePacket(IoSession session, String message)
			throws PacketException {
		Packet packet = new Packet(message);
		packet.writePacket(session);
		return packet;
	}

	/**
	 * 是否加密：0加密 1不加密
	 * 
	 * @return
	 */
	/*
	 * private int getEncryptStatus(){ return
	 * MyProtocolServer.config.getDebug(); }
	 */

	public PacketTypes getTypes() {
		return types;
	}

	public void setTypes(PacketTypes types) {
		this.types = types;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private static class IoBufferHolder {
		IoBuffer io;

		private IoBufferHolder() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	};

}
