package com.sky.game.protocol.handler;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UA0001Beans;
import com.sky.game.protocol.commons.UA0002Beans;
import com.sky.game.service.logic.ActivityService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UA0002", enable = true, namespace = "GameUser")
@Component(value = "UA0002")
public class UA0002Handler extends
		BaseProtocolHandler<UA0002Beans.Request, UA0002Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	ActivityService activityService;

	public UA0002Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UA0002Beans.Request req, UA0002Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		HashMap<String,Object> map= activityService.insertActivity(userId);
		int state=(Integer) map.get("state");
		String description=(String) map.get("description");
		res.setState(state);
		res.setDescription(description);
		return ret;

	}
}
