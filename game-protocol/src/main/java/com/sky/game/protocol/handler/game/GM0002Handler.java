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
import com.sky.game.protocol.commons.GM0000Beans.GM0002Request;
import com.sky.game.protocol.commons.GM0000Beans.MG0002Response;
import com.sky.game.service.domain.GameBlockadeMessage;
import com.sky.game.service.logic.game.GameBlockadeMessageService;

/**
 * @author sparrow
 * {@link GameBlockadeMessageService#getGameBlockadeMessages(Long, Long)}
 * @see GM0002Request
 * @see MG0002Response
 * 
 *
 */
@HandlerType(enable=true,transcode="GM0002",namespace="GameMessage")
@Component("GM0002")
public class GM0002Handler implements IProtocolHandler<GM0002Request, MG0002Response> {

	@Autowired
	GameBlockadeMessageService blockadeMessageService;
	
	/**
	 * 
	 */
	public GM0002Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable=true)
	public boolean handler(GM0002Request req, MG0002Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		List<GameBlockadeMessage > messages= blockadeMessageService.getGameBlockadeMessages(req.getUserId(), req.getRoomId());
		res.setMessages(messages);
		
		return true;
	}

}
