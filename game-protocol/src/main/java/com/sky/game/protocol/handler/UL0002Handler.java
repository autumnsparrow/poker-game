package com.sky.game.protocol.handler;



import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UL0002Beans;
import com.sky.game.service.logic.LotteryService;





/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UL0002", enable = true, namespace = "UserMessage")
@Component(value = "UL0002")
public class UL0002Handler extends
		BaseProtocolHandler<UL0002Beans.Request, UL0002Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	LotteryService lotteryService;

	public UL0002Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable = true)
	public boolean handler(UL0002Beans.Request req, UL0002Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);	
		long userId=BasePlayer.getPlayer(req).getUserId();
		HashMap<String,Object> map= lotteryService.randomLottery(userId);
		res.setMap(map);
		return ret;
	}
}