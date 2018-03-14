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
import com.sky.game.protocol.commons.GS0000Beans.GS0013Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0013Response;
import com.sky.game.service.domain.GameDataCategoryTypes;
import com.sky.game.service.domain.GameDataStaticsTypes;
import com.sky.game.service.logic.game.GameDataStaticsService;

/**
 * GS0013 -
 *  wrap the {@link GameDataStaticsService#updateGameData(long, com.sky.game.service.domain.GameDataStaticsTypes)}
 * <p>
 * @author sparrow
 * @see GameDataStaticsTypes
 */

@HandlerType(enable=true,transcode="GS0013",namespace="GameSystem")
@Component("GS0013")
public class GS0013Handler implements
		IProtocolHandler<GS0013Request, SG0013Response> {
	
	@Autowired
	GameDataStaticsService gameDataStaticsService;

	/**
	 * 
	 */
	public GS0013Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0013Request req, SG0013Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		
		GameDataStaticsTypes types=GameDataStaticsTypes.getType(req.getState().getState());
		GameDataCategoryTypes category=GameDataCategoryTypes.getType(req.getCategory().getState());
		gameDataStaticsService.updateGameData(req.getUserId(),category, types);
		
		
		return true;
	}

}
