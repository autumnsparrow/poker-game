package com.sky.game.protocol.handler;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UM0001Beans;
import com.sky.game.service.domain.SysMessage;
import com.sky.game.service.logic.AnnounceService;





/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UM0001", enable = true, namespace = "UserMessage")
@Component(value = "UM0001")
public class UM0001Handler extends
		BaseProtocolHandler<UM0001Beans.Request, UM0001Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	AnnounceService announceService;

	public UM0001Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UM0001Beans.Request req, UM0001Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);	
		long userId=BasePlayer.getPlayer(req).getUserId();
		List<SysMessage> list=announceService.selectMessage(userId,req.getLastId());
		res.setList(list);
		return ret;
	}
}
