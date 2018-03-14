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
import com.sky.game.protocol.commons.GS0000Beans.GS0006Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0006Response;
import com.sky.game.service.logic.game.GameEnrollService;

/**
 * GS0006 -
 * 	wrap the {@link GameEnrollService#enroll(Long, Long, Long, Long, Integer)}
 * 
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0006",namespace="GameSystem")
@Component("GS0006")
public class GS0006Handler implements IProtocolHandler<GS0000Beans.GS0006Request, GS0000Beans.SG0006Response> {
	
	@Autowired
	GameEnrollService gameEnrollService;
	/**
	 * 
	 */
	public GS0006Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerMethod(enable=true)
	public boolean handler(GS0006Request req, SG0006Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=false;
		ret=gameEnrollService.enroll(req.getUserId(), req.getRoomId(),req.getTeamId(), req.getItemId(), req.getAmount());
		res.setValid(ret);
		return ret;
	}

}
