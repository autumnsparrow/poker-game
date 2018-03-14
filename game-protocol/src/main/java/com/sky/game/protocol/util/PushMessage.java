/**
 * 
 */
package com.sky.game.protocol.util;

import java.util.Collection;
import java.util.Iterator;

import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.IGameEventObserver;
import com.sky.game.context.util.GameUtil;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.ProtocolGlobalContext;
import com.sky.game.protocol.commons.GS0000Beans.GS0022Request;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.service.domain.GameSystemMessage;

/**
 * 系统消息推送
 * 
 * @see com.sky.game.protocol.commons.MS0000Beans
 * @author Administrator
 *
 */
public class PushMessage implements IGameEventObserver {

	/**
	 * 
	 */
	public PushMessage() {
		super();
		// TODO Auto-generated constructor stub
		GameEventHandler.handler.registerObserver(
				GameEvent.GAME_SYSTEM_MESSAGE, this);
		GameEventHandler.handler.registerObserver(
				GameEvent.GAME_PROTOCOL_MESSAGE, this);
	}

	
	public String getUri() {
		// TODO Auto-generated method stub
		return PushMessage.class.getSimpleName();
	}

	public void observer(GameEvent evt) {
		// TODO Auto-generated method stub
		if (evt.isEvent(GameEvent.GAME_SYSTEM_MESSAGE)) {
			GameSystemMessage message = GameUtil.evtAsObj(evt);
			onGameSystemMessage(message);
		}else if(evt.isEvent(GameEvent.GAME_PROTOCOL_MESSAGE)){
			onGameProtocolMessage(evt.obj);
		}

	}
	
	
	public static void sendPrivateMessage(long userId,Object obj,boolean request){
		
		MinaEvent minaEvent = MinaEvent.obtainMinaEvent(userId, obj, request);
		minaEvent.sendMessage();
	}
	
	public void onGameProtocolMessage(Object obj) {
		

		Collection<BasePlayer> basePlayerCollection = ProtocolGlobalContext
				.instance().getOnlinePlayers();
		for (Iterator<BasePlayer> i = basePlayerCollection.iterator(); i
				.hasNext();) {
			long userId = i.next().getUserId();
			MinaEvent minaEvent = MinaEvent.obtainMinaEvent(userId, obj, false);
			minaEvent.sendMessage();
		}
	}

	public void onGameSystemMessage(GameSystemMessage message) {
		GS0022Request obj = GS0022Request.wrap(message);

		Collection<BasePlayer> basePlayerCollection = ProtocolGlobalContext
				.instance().getOnlinePlayers();
		for (Iterator<BasePlayer> i = basePlayerCollection.iterator(); i
				.hasNext();) {
			long userId = i.next().getUserId();
			MinaEvent minaEvent = MinaEvent.obtainMinaEvent(userId, obj, true);
			minaEvent.sendMessage();
		}
	}
}
