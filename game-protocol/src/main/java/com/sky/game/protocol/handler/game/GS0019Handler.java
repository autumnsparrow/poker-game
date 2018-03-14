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
import com.sky.game.protocol.commons.GS0000Beans.GS0019Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0019Response;
import com.sky.game.service.logic.game.GameEnrollService;

/**
 * 
 * update the game enroll state by id
 * <p>
 * {@link GameEnrollService#updateEnrollState(Long)}
 * <p>
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0019",namespace="GameSystem")
@Component("GS0019")
public class GS0019Handler implements IProtocolHandler<GS0019Request, SG0019Response> {
	
	@Autowired
	GameEnrollService gameEnrollService;

	/**
	 * 
	 */
	public GS0019Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0019Request req, SG0019Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean valid=gameEnrollService.updateEnrollState(req.getId());
		
		res.setValid(valid);
		return valid;
	}

}
