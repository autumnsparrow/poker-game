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
import com.sky.game.protocol.commons.GS0000Beans.GS0020Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0020Response;
import com.sky.game.service.logic.game.GameEnrollService;

/**
 * @author sparrow
 * <p>
 * {@link GameEnrollService#updateTeamId(Long, Long)}
 * <p>
 *
 */
@HandlerType(enable=true,transcode="GS0020",namespace="GameSystem")
@Component("GS0020")
public class GS0020Handler implements IProtocolHandler<GS0020Request, SG0020Response> {
	
	@Autowired
	GameEnrollService gameEnrollService;

	/**
	 * 
	 */
	public GS0020Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0020Request req, SG0020Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean valid=gameEnrollService.updateTeamId(req.getId(), req.getTeamId());
		return valid;
	}

}
