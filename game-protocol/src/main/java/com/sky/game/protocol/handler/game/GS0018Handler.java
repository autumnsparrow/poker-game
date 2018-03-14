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
import com.sky.game.protocol.commons.GS0000Beans.GS0018Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0018Response;
import com.sky.game.service.domain.GameEnroll;
import com.sky.game.service.logic.game.GameEnrollService;

/**
 * 
 * get game enroll by room id and user id.
 * {@link GameEnrollService#getGameEnrollByUserIdAndRoomId(Long, Long)}
 * @author sparrow
 * 
 * @see GS0018Request
 *
 */
@HandlerType(enable=true,transcode="GS0018",namespace="GameSystem")
@Component("GS0018")
public class GS0018Handler implements IProtocolHandler<GS0018Request, SG0018Response> {

	
	@Autowired
	GameEnrollService gameEnrollService;
	
	/**
	 * 
	 */
	public GS0018Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0018Request req, SG0018Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		GameEnroll gameEnroll=gameEnrollService.getGameEnrollByUserIdAndRoomId(req.getUserId(), req.getRoomId());
		res.setGameEnroll(gameEnroll);
		return true;
	}

}
