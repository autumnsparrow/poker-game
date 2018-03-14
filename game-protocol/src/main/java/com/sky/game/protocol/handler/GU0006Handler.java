package com.sky.game.protocol.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0006Beans;
import com.sky.game.service.domain.Goods;
import com.sky.game.service.logic.ExchangeService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0006", enable = true, namespace = "GameUser")
@Component(value = "GU0006")
public class GU0006Handler extends
		BaseProtocolHandler<GU0006Beans.Request, GU0006Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	ExchangeService exchangeService;

	public GU0006Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(GU0006Beans.Request req, GU0006Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	   long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		List<Goods> exlist = exchangeService.selectItemGoods(userId);
		res.setList(exlist);
		return ret;

	}
}
