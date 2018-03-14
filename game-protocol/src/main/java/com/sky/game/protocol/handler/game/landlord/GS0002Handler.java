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
import com.sky.game.protocol.commons.GS0000Beans.GS0002Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0002Response;

/**
 * GS0002 - wrap the {@link ProtocolGlobalContextRemote#addOnlinePlayer(
 * {@link BasePlayer)}
 * 
 * protocol ---> landlord
 * 
 * @author sparrow
 *
 */
@HandlerType(enable = true, transcode = "GS0002", namespace = "GameSystem")
@Component("GS0002")
public class GS0002Handler implements
		IProtocolHandler<GS0002Request, SG0002Response> {

	private static final Log logger = LogFactory.getLog(GS0002Handler.class);

	/**
	 * 
	 */
	public GS0002Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(GS0002Request req, SG0002Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub

		try {
			
			
//			BasePlayer p=ProtocolGlobalContextRemote.instance().getOnlinePlayer(req.getPlayer().wrap().getUserId());
//			
//			GameEvent evt=GameUtil.obtain(GameEvent.class);
//			evt.name=GameEvent.NETWORK_EVENT;
//			evt.obj=p;
//			p.setState(PlayerStates.Offline);
//			GameEventHandler.handler.broadcast(evt);
			
			ProtocolGlobalContextRemote.instance().addOnlinePlayer(
					req.getPlayer().wrap());
			
			BasePlayer p=ProtocolGlobalContextRemote.instance().getOnlinePlayer(req.getPlayer().wrap().getUserId());
			
			GameEvent evt=GameUtil.obtain(GameEvent.class);
			evt.name=GameEvent.NETWORK_EVENT;
			evt.obj=p;
			p.setState(PlayerStates.Online);
			GameEventHandler.handler.broadcast(evt);
			
			
		} catch (GameProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

}
