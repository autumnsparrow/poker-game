package com.sky.game.protocol.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UA0001Beans;
import com.sky.game.service.logic.ActivityService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UA0001", enable = true, namespace = "GameUser")
@Component(value = "UA0001")
public class UA0001Handler extends
		BaseProtocolHandler<UA0001Beans.Request, UA0001Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	ActivityService activityService;

	public UA0001Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UA0001Beans.Request req, UA0001Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		List list = activityService.selectActivity(userId);
		res.setList(list);
		return ret;

	}
}
