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
import com.sky.game.protocol.commons.GS0000Beans.GS0021Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0021Response;
import com.sky.game.service.logic.game.GameBlockadeMessageService;

/**
 * @author sparrow
 * get the blockade message count(unread) by the game_blockade_message.gbu_to_user_id
 * <p>
 * {@link GameBlockadeMessageService#getUnreadMessageCount(Long)}
 * <p>
 * 
 *
 */
@HandlerType(enable=true,transcode="GS0021",namespace="GameSystem")
@Component("GS0021")
public class GS0021Handler implements IProtocolHandler<GS0021Request, SG0021Response> {
	
	@Autowired
	GameBlockadeMessageService gameBlockadeMessageService;

	/**
	 * 
	 */
	public GS0021Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0021Request req, SG0021Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		Long  count=gameBlockadeMessageService.getUnreadMessageCount(req.getGbuToId());
		res.setTotal((count));
		return true;
	}

}
