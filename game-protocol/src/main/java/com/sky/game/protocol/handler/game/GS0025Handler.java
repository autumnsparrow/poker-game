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
import com.sky.game.protocol.commons.GS0000Beans.GS0025Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0025Response;
import com.sky.game.service.logic.game.GameEnrollService;

/**
 * 
 * {@link GameEnrollService#unenroll(Long, Long)}
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0025",namespace="GameSystem")
@Component("GS0025")
public class GS0025Handler implements IProtocolHandler<GS0025Request, SG0025Response> {

	@Autowired
	GameEnrollService gameEnrollService;
	/**
	 * 
	 */
	public GS0025Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0025Request req, SG0025Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=gameEnrollService.unenroll(req.getUserId(), req.getRoomId());
		res.setValid(ret);
		return ret;
	}

}