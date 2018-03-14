package com.sky.game.protocol.handler;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UP0002Beans;
import com.sky.game.service.logic.UserReportService;




/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UP0002", enable = true, namespace = "GameUser")
@Component(value = "UP0002")
public class UP0002Handler extends
		BaseProtocolHandler<UP0002Beans.Request, UP0002Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserReportService userReportService;

	public UP0002Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UP0002Beans.Request req,UP0002Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		int state = 0;
		if(req.getToken()!=null){
		state=1;
		}
		else{
			state=-1;
		}
		HashMap<String,Object> map=userReportService.updateReportGold(userId);
		res.setMap(map);
		res.setState(state);
		return ret;
	}
}
