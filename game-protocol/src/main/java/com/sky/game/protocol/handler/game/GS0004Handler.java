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
import com.sky.game.protocol.commons.GS0000Beans;
import com.sky.game.protocol.commons.GS0000Beans.GS0004Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0004Response;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.logic.game.GameUserService;

/**
 * GS0004 -
 * 	wrap the {@link GameUserService#getGameUser(Long)}
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0004",namespace="GameSystem")
@Component("GS0004")
public class GS0004Handler implements IProtocolHandler<GS0000Beans.GS0004Request, GS0000Beans.SG0004Response> {

	/**
	 * 
	 */
	public GS0004Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	GameUserService gameUserService;
	
	@HandlerMethod(enable=true)
	public boolean handler(GS0004Request req, SG0004Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=req.getUserId();
		GameUser user=gameUserService.getGameUser(userId);
		res.setUser(user);
		
		return true;
	}

}
