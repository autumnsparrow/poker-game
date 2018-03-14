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
import com.sky.game.service.domain.TaskSet;
import com.sky.game.service.logic.TaskService;




/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UT0001", enable = true, namespace = "GameUser")
@Component(value = "UT0001")
public class UT0001Handler extends
		BaseProtocolHandler<UT0001Beans.Request, UT0001Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	TaskService taskService;

	public UT0001Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UT0001Beans.Request req, UT0001Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);	
		List<TaskSet> list = taskService.selectTaskSet1(userId);
		res.setList(list);
		return ret;
	}
}
