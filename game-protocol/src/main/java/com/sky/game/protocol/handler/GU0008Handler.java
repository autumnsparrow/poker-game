package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0008Beans;
import com.sky.game.service.domain.UserInfo;
import com.sky.game.service.logic.UserAccountService;
@HandlerType(transcode = "GU0008", enable = true, namespace = "GameUser")
@Component(value = "GU0008")
public class GU0008Handler extends
		BaseProtocolHandler<GU0008Beans.Request, GU0008Beans.Response> {

	/**
	* 
	*/
	
	public GU0008Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserAccountService userAccountService;
	
	@HandlerMethod(enable = true)
	public boolean handler(GU0008Beans.Request req, GU0008Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
			boolean ret = super.handler(req, res);
			if(ret){
				BasePlayer basePlayer=BasePlayer.getPlayer(req);
				long userId=basePlayer.getUserId();
				UserInfo userInfo=userAccountService.selectUserInfo(userId);
				res.setUserInfo(userInfo);
			}
			return ret;
	}
}