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
import com.sky.game.protocol.commons.GS0000Beans.GS0016Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0016Response;
import com.sky.game.service.domain.GameEnroll;
import com.sky.game.service.logic.game.GameEnrollService;

/**
 * get game enroll by room id and state not enrolled.
 * @author sparrow
 *
 *{@link GameEnrollService#getGameEnrollByState(Long)}
 */
@HandlerType(enable=true,transcode="GS0016",namespace="GameSystem")
@Component("GS0016")
public class GS0016Handler implements IProtocolHandler<GS0016Request, SG0016Response> {
	@Autowired
	GameEnrollService gameEnrollService;
	
	/**
	 * 
	 */
	public GS0016Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0016Request req, SG0016Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		List<GameEnroll> objects=gameEnrollService.getGameEnrollByState(req.getRoomId());
		res.setGameEnrolles(objects);
		return true;
	}

}
