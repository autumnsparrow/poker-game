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
import com.sky.game.protocol.commons.GS0000Beans.GS0027Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0027Response;
import com.sky.game.service.domain.UserLogTypes;
import com.sky.game.service.logic.UserCenterService;
import com.sky.game.service.logic.game.GameUserPropertyService;

/**
 * 
 * {@link GameUserPropertyService#updateUserProperty(Long, Long, Integer, UserLogTypes)}
 * @author sparrow
 *
 */
@HandlerType(enable=true,transcode="GS0027",namespace="GameSystem")
@Component("GS0027")
public class GS0027Handler implements IProtocolHandler<GS0027Request, SG0027Response> {

	@Autowired
	UserCenterService userCenterService;
	/**
	 * 
	 */
	public GS0027Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0027Request req, SG0027Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		int state=userCenterService.updateProperty(req.getUserId(),req.getFen(),req.getPropertyId());
		boolean ret=false;
		if(state==1)
			ret=true;
		res.setValid(ret);
		return ret;
	}

}