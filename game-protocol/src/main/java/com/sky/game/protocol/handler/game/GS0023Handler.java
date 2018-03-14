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
import com.sky.game.protocol.commons.GS0000Beans.GS0023Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0023Response;
import com.sky.game.service.logic.game.GameBlockadeService;

/**
 * 
 * {@link GameBlockadeService#updateBlockadeUserStatus(Long, Integer)}
 *  {@link GS0023Request}
 *  {@link SG0023Response}
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0023",namespace="GameSystem")
@Component("GS0023")
public class GS0023Handler implements IProtocolHandler<GS0023Request, SG0023Response> {

	@Autowired
	GameBlockadeService gameBlockadeService;
	/**
	 * 
	 */
	public GS0023Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0023Request req, SG0023Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=false;
		ret=gameBlockadeService.updateBlockadeUserStatus(req.getId(), req.getStatus());
		return ret;
	}

}
