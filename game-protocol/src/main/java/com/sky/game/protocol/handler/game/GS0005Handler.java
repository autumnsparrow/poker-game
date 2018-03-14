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
import com.sky.game.protocol.commons.GS0000Beans.GS0005Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0005Response;
import com.sky.game.service.logic.game.GameEnrollService;

/**
 * GS0005 -
 * 	wrap the {@link GameEnrollService#validItemValue(long, int, int)}
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0005",namespace="GameSystem")
@Component("GS0005")
public class GS0005Handler implements IProtocolHandler<GS0000Beans.GS0005Request, GS0000Beans.SG0005Response> {

	@Autowired
	GameEnrollService gameEnrollService;
	/**
	 * 
	 */
	public GS0005Handler() {
		// TODO Auto-generated constructor stub
	}
	
	
	@HandlerMethod(enable=true)
	public boolean handler(GS0005Request req, SG0005Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean valid=gameEnrollService.validItemValue(req.getUserId(), req.getItemId(), req.getMin());
		res.setValid(valid);
		return true;
	}

}
