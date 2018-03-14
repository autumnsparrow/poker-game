/**
 * 
 */
package com.sky.game.protocol.handler.game;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.ProtocolGlobalContext;
import com.sky.game.protocol.commons.GS0000Beans.GS0022Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0022Response;
import com.sky.game.protocol.event.MinaEvent;

/**
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0022",namespace="GameSystem")
@Component("GS0022")
public class GS0022Handler implements IProtocolHandler<GS0022Request, SG0022Response> {

	/**
	 * 
	 */
	public GS0022Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerMethod(enable=true)
	public boolean handler(GS0022Request req, SG0022Response res)
			throws ProtocolException {
		
		
		// TODO Auto-generated method stub
		Collection<BasePlayer> basePlayerCollection=ProtocolGlobalContext.instance().getOnlinePlayers();
		for (Iterator<BasePlayer> i = basePlayerCollection.iterator(); i.hasNext(); ){
   		  long userId=i.next().getUserId();
   		  MinaEvent minaEvent=MinaEvent.obtainMinaEvent(userId,req,true);
   		  minaEvent.sendMessage();
   	 	}
		res.setSent(true);
		return true;
	}

}
