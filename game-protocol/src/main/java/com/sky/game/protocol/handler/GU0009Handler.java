package com.sky.game.protocol.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0009Beans;
import com.sky.game.service.domain.GoldExchange;
import com.sky.game.service.logic.ExchangeService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0009", enable = true, namespace = "GameUser")
@Component(value = "GU0009")
public class GU0009Handler extends
		BaseProtocolHandler<GU0009Beans.Request, GU0009Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	ExchangeService exchangeService ;

	public GU0009Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(GU0009Beans.Request req, GU0009Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		if(ret){
			List<GoldExchange> exlist = exchangeService.selectItemGold();
			res.setGoldExchangeList(exlist);
		}
		return ret;

	}
}

