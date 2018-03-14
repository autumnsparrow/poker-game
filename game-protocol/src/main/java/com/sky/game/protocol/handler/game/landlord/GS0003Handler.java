/**
 * 
 */
package com.sky.game.protocol.handler.game.landlord;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.GameProtocolException;
import com.sky.game.protocol.PlayerStates;
import com.sky.game.protocol.ProtocolGlobalContextRemote;
import com.sky.game.protocol.commons.GS0000Beans;
import com.sky.game.protocol.commons.GS0000Beans.GS0003Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0003Response;

/**
 * GS0003 -
 * 	wrap the {@link ProtocolGlobalContextRemote#removeOnlinePlayer(Long)}
 * 
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0003",namespace="GameSystem")
@Component("GS0003")
public class GS0003Handler implements IProtocolHandler<GS0000Beans.GS0003Request, GS0000Beans.SG0003Response> {
	private static final Log logger=LogFactory.getLog(GS0003Handler.class);
	/**
	 * 
	 */
	public GS0003Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0003Request req, SG0003Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		Long userId=req.getUserId();
		
		try {
			ProtocolGlobalContextRemote.instance().removeOnlinePlayer(userId);
			// real time broken online.
			BasePlayer p=ProtocolGlobalContextRemote.instance().getOnlinePlayer(userId);
			GameEvent evt=GameUtil.obtain(GameEvent.class);
			evt.name=GameEvent.NETWORK_EVENT;
			evt.obj=p;
			p.setState(PlayerStates.Offline);
			GameEventHandler.handler.broadcast(evt);
		} catch (GameProtocolException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
