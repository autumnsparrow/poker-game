package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0006Beans;
import com.sky.game.service.logic.UserCenterService;



/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UC0006", enable = true, namespace = "GameUser")
@Component(value = "UC0006")
public class UC0006Handler extends
		BaseProtocolHandler<UC0006Beans.Request, UC0006Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserCenterService userCenterService;

	public UC0006Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UC0006Beans.Request req, UC0006Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	   long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		String UserSign=req.getUserSign();
		int state=0;
		if(UserSign!=null){
			state = 1;
			userCenterService.updateUserSign(userId, UserSign);
		}
		else
		{
			state =-1;
		}
		res.setState(state);
		return ret;

	}
}
