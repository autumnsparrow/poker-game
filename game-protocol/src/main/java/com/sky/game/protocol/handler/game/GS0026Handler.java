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
import com.sky.game.protocol.commons.GS0000Beans.GS0026Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0026Response;
import com.sky.game.service.domain.UserLogTypes;
import com.sky.game.service.logic.game.GameUserPropertyService;

/**
 * 
 * {@link GameUserPropertyService#updateUserProperty(Long, Long, Integer, UserLogTypes)}
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0026",namespace="GameSystem")
@Component("GS0026")
public class GS0026Handler implements IProtocolHandler<GS0026Request, SG0026Response> {

	@Autowired
	GameUserPropertyService gameUserPropertyService;
	/**
	 * 
	 */
	public GS0026Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0026Request req, SG0026Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		Long affectedRows=gameUserPropertyService.updateUserProperty(req.getUserId(), Long.valueOf(req.getItemId().intValue()), req.getItemValue(), UserLogTypes.GameEnd);
		boolean ret=false;
		if(affectedRows!=null)
			ret=true;
		res.setValid(ret);
		return ret;
	}

}