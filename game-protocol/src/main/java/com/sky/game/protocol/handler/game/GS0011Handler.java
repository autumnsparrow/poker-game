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
import com.sky.game.protocol.commons.GS0000Beans.GS0011Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0011Response;
import com.sky.game.service.logic.game.GameBlockadeService;

/**
 * 
 * GS0011 -
 * 	wrap the {@link GameBlockadeService#updateBlockade(Long, Long, Long, Integer)} 
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0011",namespace="GameSystem")
@Component("GS0011")
public class GS0011Handler implements IProtocolHandler<GS0011Request, SG0011Response> {

	
	@Autowired
	GameBlockadeService gameBlockadeService;
	/**
	 * 
	 */
	public GS0011Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerMethod
	public boolean handler(GS0011Request req, SG0011Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=gameBlockadeService.updateBlockade(req.getUserId(), req.getRoomId(), req.getIntegral(), req.getLevel());
		res.setValid(ret);
		return true;
	}

}
