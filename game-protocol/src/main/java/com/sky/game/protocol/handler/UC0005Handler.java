package com.sky.game.protocol.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0005Beans;
import com.sky.game.service.logic.UserCenterService;



/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UC0005", enable = true, namespace = "GameUser")
@Component(value = "UC0005")
public class UC0005Handler extends
		BaseProtocolHandler<UC0005Beans.Request, UC0005Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserCenterService userCenterService;

	public UC0005Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UC0005Beans.Request req, UC0005Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
	    String Phone=req.getPhone();
		//String Verification=req.getVerification();
		userCenterService.updateUserPhone(userId, Phone);
		return ret;
	}
}
