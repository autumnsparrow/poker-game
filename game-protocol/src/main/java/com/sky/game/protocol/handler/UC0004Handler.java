package com.sky.game.protocol.handler;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0004Beans;
import com.sky.game.service.logic.SmsMessageService;


/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UC0004", enable = true, namespace = "GameUser")
@Component(value = "UC0004")
public class UC0004Handler extends
		BaseProtocolHandler<UC0004Beans.Request, UC0004Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	SmsMessageService smaMessageService;

	public UC0004Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UC0004Beans.Request req, UC0004Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	    long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		String phone= req.getPhone();
		String  verification=String.valueOf(new Random().nextInt(100000));
		smaMessageService.send(phone, verification);
		return ret;
	}
}
