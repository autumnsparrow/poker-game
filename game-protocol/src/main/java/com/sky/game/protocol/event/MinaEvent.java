/**
 * 
 */
package com.sky.game.protocol.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.annotation.introspector.AnnotationIntrospector.ProtocolMapEntry;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.MinaBeans;
import com.sky.game.context.event.DefaultGameSession;
import com.sky.game.context.event.IGameSession;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.GameProtocolException;
import com.sky.game.protocol.ProtocolGlobalContextRemote;
import com.sky.game.protocol.commons.GL0000Beans.Channel;
import com.sky.game.protocol.commons.GL0000Beans.LG0010Response;
import com.sky.game.protocol.commons.GL0000Beans.PlayerObject;

/**
 * @author Administrator
 *
 */
public class MinaEvent implements IBornTime {

	public String deviceId;
	public String transcode;
	public Object obj;
	public String token;
	public long userId;
	public long born;
	private static final Log logger = LogFactory.getLog(MinaEvent.class);
	
	

	/**
	 * 
	 */
	public MinaEvent() {
		// TODO Auto-generated constructor stub
	}

	private void invalidToken() {

		LG0010Response resp = GameUtil.obtain(LG0010Response.class);
		ProtocolMapEntry entry = GameContextGlobals.getAnnotationIntrospector()
				.getProtocolMapEntryByResponse(resp);
		if (entry != null) {
			transcode = entry.transcode;
		}
		MinaBeans.sendMinaMessage(token, deviceId, transcode, resp);
	}

	public MinaEvent(Object obj) {
		super();
		this.born = System.currentTimeMillis();
		this.obj = obj;

		if (obj instanceof BaseRequest) {
			this.token = ((BaseRequest) obj).getToken();
		}

		BasePlayer player = null;
		// valid token.
		try {

			player = ProtocolGlobalContextRemote.instance().getOnlinePlayer(
					userId);
		} catch (GameProtocolException e) {
			// TODO Auto-generated catch block
			invalidToken();
			// e.printStackTrace();

		}

		if (player != null) {
			this.deviceId = player.getDeviceId();
			this.userId = player.getUserId();
			ProtocolMapEntry entry = GameContextGlobals
					.getAnnotationIntrospector()
					.getProtocolMapEntryByRequestClass(obj);
			if (entry != null) {
				transcode = entry.transcode;
			}

			if (obj instanceof PlayerObject) {
				IIdentifiedObject entry1 = (IIdentifiedObject) obj;
				entry1.setId(Long.valueOf(this.userId));
			}
		}

	}

	/**
	 * 
	 * NOTICE: must login with token
	 * 
	 * @param obj
	 */
	public MinaEvent(Object obj, String deviceId) {
		super();
		this.born = System.currentTimeMillis();
		this.obj = obj;
		this.deviceId = deviceId;
		if (obj instanceof BaseRequest) {
			this.token = ((BaseRequest) obj).getToken();
			// logger.info("minaevent.token:" + this.token);
		}

		BasePlayer player = null;
		// valid token.
		// /player=BasePlayer.getPlayer(this.token);

		player = BasePlayer.getPlayer(this);

		if (player != null) {
			this.userId = player.getUserId();

			// this.deviceId=player.getDeviceId();
			// this.userId=player.getUserId();
			ProtocolMapEntry entry = GameContextGlobals
					.getAnnotationIntrospector()
					.getProtocolMapEntryByRequestClass(obj);
			if (entry != null) {
				transcode = entry.transcode;
			}

			if (obj instanceof PlayerObject) {
				IIdentifiedObject entry1 = (IIdentifiedObject) obj;
				entry1.setId(Long.valueOf(this.userId));
			}
			
			if(obj instanceof Channel){
				IIdentifiedObject entry1 = (IIdentifiedObject) obj;
				entry1.setId(Long.valueOf(1));
			}
			
		} else {
			invalidToken();
		}

		log();
	}

	private void log() {
		// logger.info("Born - "+born+" current - "+System.currentTimeMillis()+" duration:"+(System.currentTimeMillis()-born));
	}

	/**
	 * 
	 * NOTICE: must login with token don't need the object is {@link BasePlayer}
	 * 
	 * @param obj
	 */
	public MinaEvent(long userId, Object obj) {
		super();
		this.born = System.currentTimeMillis();
		this.obj = obj;
		this.userId = userId;
		BasePlayer player = null;
		try {
			player = ProtocolGlobalContextRemote.instance().getOnlinePlayer(
					userId);
			deviceId = player.getDeviceId();
			ProtocolMapEntry entry = GameContextGlobals
					.getAnnotationIntrospector()
					.getProtocolMapEntryByRequestClass(obj);
			if (entry != null) {
				transcode = entry.transcode;
			}
			token = player.getToken();
		} catch (GameProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		log();
	}

	public MinaEvent(long userId, Object obj, boolean request) {
		super();
		this.born = System.currentTimeMillis();
		this.obj = obj;
		this.userId = userId;
		BasePlayer player = null;
		try {
			player = ProtocolGlobalContextRemote.instance().getOnlinePlayer(
					userId);
			deviceId = player.getDeviceId();

			if (request) {
				ProtocolMapEntry entry = GameContextGlobals
						.getAnnotationIntrospector()
						.getProtocolMapEntryByRequestClass(obj);
				if (entry != null) {
					transcode = entry.transcode;
				}
			} else {
				ProtocolMapEntry entry = GameContextGlobals
						.getAnnotationIntrospector()
						.getProtocolMapEntryByResponse(obj);
				if (entry != null) {
					transcode = entry.responsecode;
				}
			}
			token = player.getToken();
		} catch (GameProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		log();

	}

	@Override
	public String toString() {
		return "MinaEvent [deviceId=" + deviceId + ", transcode=" + transcode
				+ ", obj=" + obj + ", token=" + token + "]";
	}

	public static MinaEvent obtainMinaEvent(Object obj) {
		return new MinaEvent(obj);
	}

	public static MinaEvent obtainMinaEvent(Object obj, String deviceId) {
		return new MinaEvent(obj, deviceId);
	}

	public static MinaEvent obtainMinaEvent(long userId, Object obj,
			boolean request) {
		return new MinaEvent(userId, obj, request);
	}

	public static MinaEvent obtainMinaEvent(long userId, Object obj) {
		return new MinaEvent(userId, obj);
	}

	public static boolean filterMinaEvent(MinaEvent event,
			IIdentifiedObject identiedObject) {
		boolean ret = false;
		Object obj = event.obj;
		if (obj instanceof IIdentifiedObject) {
			IIdentifiedObject request = (IIdentifiedObject) obj;
			ret = (request.getId().longValue() == identiedObject.getId()
					.longValue());
		}

		return ret;

	}

	public Long getId() throws ProtocolException {
		Long id = null;

		if (obj instanceof IIdentifiedObject) {
			IIdentifiedObject request = (IIdentifiedObject) obj;
			id = request.getId();
		} else {
			throw new ProtocolException(-1, obj.getClass().getName()
					+ " must implements the IIdentifiedObject Interface");
		}
		return id;

	}

	public long getBorn() {
		// TODO Auto-generated method stub
		return born;
	}

	public void sendMessage() {

		boolean valid = true;

		if (deviceId == null
				|| (deviceId != null && deviceId.startsWith("robot"))) {
			valid = false;
			deviceId = deviceId == null ? "" : deviceId;
			logger.info("Won't send mina message --" + deviceId);
		}

		if (valid) {

			MinaBeans.sendMinaMessage(token, deviceId, transcode, obj);
		}
	}

}
