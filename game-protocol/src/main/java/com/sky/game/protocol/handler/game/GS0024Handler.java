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
import com.sky.game.protocol.commons.GS0000Beans.GS0024Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0024Response;
import com.sky.game.service.logic.game.GameBlockadeService;

/**
 * 
 * {@link GameBlockadeService#selectBlockadeUserCountByRoomIdAndLevel(Long, Integer)}
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0024",namespace="GameSystem")
@Component("GS0024")
public class GS0024Handler implements IProtocolHandler<GS0024Request, SG0024Response> {

	@Autowired
	GameBlockadeService gameBlockadeService;
	/**
	 * 
	 */
	public GS0024Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0024Request req, SG0024Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		int rows=gameBlockadeService.selectBlockadeUserCountByRoomIdAndLevel(req.getRoomId(), req.getLevel());
		res.setCount(rows);
		
		return false;
	}

}
