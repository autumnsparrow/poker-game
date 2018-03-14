package com.sky.game.protocol.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UT0002Beans;
import com.sky.game.service.logic.TaskService;




/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UT0002", enable = true, namespace = "GameUser")
@Component(value = "UT0002")
public class UT0002Handler extends
		BaseProtocolHandler<UT0002Beans.Request, UT0002Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	TaskService taskService;

	public UT0002Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UT0002Beans.Request req, UT0002Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		int state=0;
		if(req.getId()!=0){
		taskService.updateNeckType(userId, req.getId());
		state=1;
		}
		else{
			state=-1;
		}
		res.setState(state);
		return ret;
	}
}
