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
import com.sky.game.protocol.commons.GM0000Beans.GM0001Request;
import com.sky.game.protocol.commons.GM0000Beans.MG0001Response;
import com.sky.game.service.logic.game.GameBlockadeMessageService;

/**
 * 
 * 
 *@author sparrow
 *
 *{@link GameBlockadeMessageService#inviteSameLevelPlayers(Long, Long[], String)}
 *@see GM0001Request
 *@see MG0001Response
 *
 */
@HandlerType(enable=true,transcode="GM0001",namespace="GameMessage")
@Component("GM0001")
public class GM0001Handler implements IProtocolHandler<GM0001Request, MG0001Response> {

	/**
	 * 
	 */
	public GM0001Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	GameBlockadeMessageService gameBlockadeMessageService;
	
	
	@HandlerMethod(enable=true)
	public boolean handler(GM0001Request req, MG0001Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		Long gameBlockadeUserId=req.getGameBlockadeUserId();
		Long[] toGameBlockadeUserIds=req.getToGameBlockadeUserId()==null?null:req.getToGameBlockadeUserId().toArray(new Long[]{});
		boolean valid=gameBlockadeMessageService.inviteSameLevelPlayers(gameBlockadeUserId,toGameBlockadeUserIds, req.getMessage());
		res.setValid(valid);
		
		return valid;
	}

}
