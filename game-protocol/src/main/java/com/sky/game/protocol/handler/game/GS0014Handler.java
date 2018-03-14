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
import com.sky.game.protocol.commons.GS0000Beans.GS0014Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0014Response;
import com.sky.game.service.logic.AchievementService;

/**
 * update the achievement of user.
 * <p>
 * {@link AchievementService#inserAchievement(long, int, int)}
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0014",namespace="GameSystem")
@Component("GS0014")
public class GS0014Handler implements IProtocolHandler<GS0014Request, SG0014Response> {
	
	@Autowired
	AchievementService achievementService;

	/**
	 * 
	 */
	public GS0014Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0014Request req, SG0014Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		achievementService.inserAchievement(req.getUserId(), req.getState().getState(), req.getLandlord().getState());
		
		return true;
	}

}
