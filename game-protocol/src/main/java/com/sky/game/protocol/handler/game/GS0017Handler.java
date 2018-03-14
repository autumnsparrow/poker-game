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
import com.sky.game.protocol.commons.GS0000Beans.GS0017Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0017Response;
import com.sky.game.service.domain.GameEnroll;
import com.sky.game.service.logic.game.GameEnrollService;

/**
 * 
 * get game enroll by user id,find the enrolled but not joined the room information
 * {@link GameEnrollService#getGameEnrollByUserId(Long)}
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0017",namespace="GameSystem")
@Component("GS0017")
public class GS0017Handler implements IProtocolHandler<GS0017Request, SG0017Response> {
	
	@Autowired
	GameEnrollService gameEnrollService;

	/**
	 * 
	 */
	public GS0017Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerMethod(enable=true)
	public boolean handler(GS0017Request req, SG0017Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		List<GameEnroll> objects=gameEnrollService.getGameEnrollByUserId(req.getUserId());
		res.setGameEnrolles(objects);
		return true;
	}

}
