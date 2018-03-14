package com.sky.game.protocol.handler;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UT0003Beans;
import com.sky.game.service.logic.TaskService;




/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UT0003", enable = true, namespace = "GameUser")
@Component(value = "UT0003")
public class UT0003Handler extends
		BaseProtocolHandler<UT0003Beans.Request, UT0003Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	TaskService taskService;

	public UT0003Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UT0003Beans.Request req, UT0003Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);	
		List list=taskService.selectTaskSet2(userId);
		res.setList(list);
		return ret;
	}
}
