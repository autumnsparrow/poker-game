package com.sky.game.protocol.handler;


import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UP0001Beans;
import com.sky.game.service.logic.UserReportService;




/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UP0001", enable = true, namespace = "GameUser")
@Component(value = "UP0001")
public class UP0001Handler extends
		BaseProtocolHandler<UP0001Beans.Request, UP0001Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserReportService userReportService;

	public UP0001Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UP0001Beans.Request req, UP0001Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);	
		long userId=BasePlayer.getPlayer(req).getUserId();
		HashMap<String,Object> map=userReportService.selectReport(userId);
		res.setMap(map);
		return ret;
	}
}
