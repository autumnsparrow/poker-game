/**
 * 
 */
package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0014Beans;
import com.sky.game.protocol.commons.GU0014Beans.Request;
import com.sky.game.protocol.commons.GU0014Beans.Response;
import com.sky.game.service.logic.ExchangeService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0014", enable = true, namespace = "GameUser")
@Component(value = "GU0014")
public class GU0014Handler extends
BaseProtocolHandler<GU0014Beans.Request, GU0014Beans.Response>{

	/**
	 * 
	 */
	public GU0014Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	ExchangeService exchangeService;
	
	@HandlerMethod(enable = true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		res.setList(exchangeService.selectExchangeRecord());
		return  ret;
	}

}
