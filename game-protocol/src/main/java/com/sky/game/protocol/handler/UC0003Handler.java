package com.sky.game.protocol.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0003Beans;
import com.sky.game.service.logic.UserCenterService;



/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UC0003", enable = true, namespace = "GameUser")
@Component(value = "UC0003")
public class UC0003Handler extends
		BaseProtocolHandler<UC0003Beans.Request, UC0003Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserCenterService userCenterService;

	public UC0003Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UC0003Beans.Request req, UC0003Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	   long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		String NickName=req.getNewNickName();
		String description="修改昵称成功";
		int state =userCenterService.updateNickName(userId, NickName);
		if(state==-1){
			description="修改失败，金币不足";
		}
		if(state==-2){
			description="修改失败，昵称含敏感词汇";
		}
		res.setState(state);
		res.setDescription(description);
		return ret;

	}
}
