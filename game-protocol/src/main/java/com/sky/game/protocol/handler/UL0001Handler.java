package com.sky.game.protocol.handler;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UL0001Beans;
import com.sky.game.service.logic.LotteryService;





/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UL0001", enable = true, namespace = "UserMessage")
@Component(value = "UL0001")
public class UL0001Handler extends
		BaseProtocolHandler<UL0001Beans.Request, UL0001Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	LotteryService lotteryService;

	public UL0001Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UL0001Beans.Request req, UL0001Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		HashMap<String,Object> map = lotteryService.selectLottery(userId);
		res.setMap(map);
		return ret;
	}
}
