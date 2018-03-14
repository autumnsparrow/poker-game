/**
 * 
 */
/*package com.sky.game.protocol.handler;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0011Beans;
import com.sky.game.protocol.commons.UC0011Beans.Request;
import com.sky.game.protocol.commons.UC0011Beans.Response;
import com.sky.game.service.logic.SmsMessageService;

*//**
 * @author Administrator
 *
 *//*
@HandlerType(transcode="UC0011",enable=true,namespace="UserCenter")
@Component(value="UC0011")
public class UC0011Handler extends BaseProtocolHandler<UC0011Beans.Request, UC0011Beans.Response>{

	*//**
	 * 
	 *//*
	public UC0011Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	SmsMessageService smsMessageService;
	
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		
		 * 处理用户发送验证码
		 
		String phone=req.getPhone();
		Integer i =new Random().nextInt(100000);
		smsMessageService.send(phone, String.valueOf(i));
		return ret;
	}

}
*/