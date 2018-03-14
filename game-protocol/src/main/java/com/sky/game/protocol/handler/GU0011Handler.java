/**
 * 
 */
package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0011Beans;
import com.sky.game.protocol.commons.GU0011Beans.Request;
import com.sky.game.protocol.commons.GU0011Beans.Response;
import com.sky.game.service.logic.ExchangeService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0011", enable = true, namespace = "GameUser")
@Component(value = "GU0011")
public class GU0011Handler extends
BaseProtocolHandler<GU0011Beans.Request, GU0011Beans.Response>{

	/**
	 * 
	 */
	@Autowired
	ExchangeService exchangeService;
	
	public GU0011Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		res.setPriceExchangeList(exchangeService.selectPriceExchange(userId));
		return ret;
	}
	
	
}
