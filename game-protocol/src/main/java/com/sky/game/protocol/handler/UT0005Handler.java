 package com.sky.game.protocol.handler;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UT0005Beans;
import com.sky.game.service.logic.TaskService;




/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UT0005", enable = true, namespace = "GameUser")
@Component(value = "UT0005")
public class UT0005Handler extends
		BaseProtocolHandler<UT0005Beans.Request, UT0005Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	TaskService taskService;

	public UT0005Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UT0005Beans.Request req, UT0005Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);	
		List list=taskService.selectTaskSet3(userId);
		res.setList(list);
		return ret;
	}
}
