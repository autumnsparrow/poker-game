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
import com.sky.game.protocol.commons.GS0000Beans.GS0007Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0007Response;
import com.sky.game.service.logic.game.GameRewardService;

/**
 * GS0007 -
 * 	wrap the {@link GameRewardService#reward(Long, Long, Long, Long, Integer, Integer)}
 * 
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0007",namespace="GameSystem")
@Component("GS0007")
public class GS0007Handler implements IProtocolHandler<GS0000Beans.GS0007Request, GS0000Beans.SG0007Response> {

	
	@Autowired
	GameRewardService gameRewardService;
	/**
	 * 
	 */
	public GS0007Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0007Request req, SG0007Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=false;
		ret=gameRewardService.reward(req.getUserId(), req.getRoomId(),req.getTeamId(), req.getItemId(), req.getAmount(), req.getRank());
		res.setValid(ret);
		return ret;
	}

}
