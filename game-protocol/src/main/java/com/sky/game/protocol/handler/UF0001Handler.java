package com.sky.game.protocol.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UF0001Beans;
import com.sky.game.service.logic.UserFeedbackService;





/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UF0001", enable = true, namespace = "UserMessage")
@Component(value = "UF0001")
public class UF0001Handler extends
		BaseProtocolHandler<UF0001Beans.Request, UF0001Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserFeedbackService userFeedbackService;

	public UF0001Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UF0001Beans.Request req, UF0001Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		int state=0;
		if(req.getToken()!=null){
		userFeedbackService.insertFeedback(userId, req.getTitle(), req.getDescription());
		state=1;
		}
		res.setState(state);
		return ret;
	}
}
