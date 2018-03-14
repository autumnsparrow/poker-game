 package com.sky.game.protocol.handler;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UT0001Beans;
import com.sky.game.protocol.commons.UT0003Beans;
import com.sky.game.protocol.commons.UT0005Beans;
import com.sky.game.protocol.commons.UT0006Beans;
import com.sky.game.service.domain.TaskSet;
import com.sky.game.service.logic.TaskService;




/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UT0006", enable = true, namespace = "GameUser")
@Component(value = "UT0006")
public class UT0006Handler extends
		BaseProtocolHandler<UT0006Beans.Request, UT0006Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	TaskService taskService;

	public UT0006Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UT0006Beans.Request req, UT0006Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);	
		int state=taskService.selectRewardState(userId);
		res.setState(state);
		return ret;
	}
}