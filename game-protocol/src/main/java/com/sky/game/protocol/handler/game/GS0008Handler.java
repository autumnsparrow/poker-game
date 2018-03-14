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
import com.sky.game.protocol.commons.GS0000Beans.GS0008Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0008Response;
import com.sky.game.service.logic.game.GameRewardService;

/**
 * GS0008 -
 * 	wrap the {@link GameRewardService#updateProperty(Long, Long, Long, Integer)}
 * 
 * @author sparrow
 *
 */

@HandlerType(enable=true,transcode="GS0008",namespace="GameSystem")
@Component("GS0008")
public class GS0008Handler implements IProtocolHandler<GS0000Beans.GS0008Request, GS0000Beans.SG0008Response> {

	@Autowired
	GameRewardService gameRewardService;
	/**
	 * 
	 */
	public GS0008Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0008Request req, SG0008Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=false;
		ret=gameRewardService.updateProperty(req.getUserId(), req.getRoomId(), req.getPropertyId(), req.getPropertyValue());
		return ret;
	}

}
