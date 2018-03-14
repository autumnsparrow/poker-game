/**
 * 
 */
package com.sky.game.protocol.handler.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.commons.GS0000Beans.GS0010Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0010Response;
import com.sky.game.service.domain.GameBlockadeUser;
import com.sky.game.service.logic.game.GameBlockadeService;

/**
 * GS0010 -
 * 	wrap the {@link GameBlockadeService#getGameBlockadeUser(Long, Long)}
 * 
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0010",namespace="GameSystem")
@Component("GS0010")
public class GS0010Handler implements
		IProtocolHandler<GS0010Request, SG0010Response> {
	
	@Autowired
	GameBlockadeService gameBlockadeService;

	/**
	 * 
	 */
	public GS0010Handler() {
		// TODO Auto-generated constructor stub
	}
	
	
	@HandlerMethod(enable=true)
	public boolean handler(GS0010Request req, SG0010Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		GameBlockadeUser user=gameBlockadeService.getGameBlockadeUser(req.getUserId(), req.getRoomId());
		res.wrap(user);
		
		return true;
	}

}
