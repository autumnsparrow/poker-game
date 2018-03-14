/**
 * 
 */
package com.sky.game.protocol.handler.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.commons.GS0000Beans.GS0009Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0009Response;
import com.sky.game.service.domain.GameBlockadeUser;
import com.sky.game.service.logic.game.GameBlockadeService;

/**
 * 
 * GS009 -
 * 	wrap {@link GameBlockadeService#getGameBlockadeUserByRoomLevel(Long, Long)}
 * 
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0009",namespace="GameSystem")
@Component("GS0009")
public class GS0009Handler implements IProtocolHandler<GS0009Request, SG0009Response> {
	
	@Autowired
	GameBlockadeService gameBlockadeService;

	/**
	 * 
	 */
	public GS0009Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0009Request req, SG0009Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		List<GameBlockadeUser> users=gameBlockadeService.getGameBlockadeUserByRoomLevel(req.getRoomId(),req.getLevel());
		res.wrap(users);
		
		
		return true;
	}

}
