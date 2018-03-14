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
import com.sky.game.protocol.commons.GM0000Beans.GM0003Request;
import com.sky.game.protocol.commons.GM0000Beans.MG0003Response;
import com.sky.game.service.logic.game.GameBlockadeMessageService;

/**
 * @author sparrow
 *
 *{@link GameBlockadeMessageService#updateMessageReadState(Long[])}
 *@see GM0003Request
 *@see MG0003Response
 */
@HandlerType(enable=true,transcode="GM0003",namespace="GameMessage")
@Component("GM0003")
public class GM0003Handler implements IProtocolHandler<GM0003Request, MG0003Response> {
	
	@Autowired
	GameBlockadeMessageService blockadeMessageService;

	/**
	 * 
	 */
	public GM0003Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GM0003Request req, MG0003Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		Long[] ids=req.getMessageMapIds()==null?null:req.getMessageMapIds().toArray(new Long[]{});
		boolean valid=blockadeMessageService.updateMessageReadState(ids);
		res.setValid(valid);
		return valid;
	}

}
