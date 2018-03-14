/**
 * 
 */
package com.sky.game.protocol.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0016Beans;
import com.sky.game.protocol.commons.GU0016Beans.Request;
import com.sky.game.protocol.commons.GU0016Beans.Response;
import com.sky.game.service.domain.MyPrice;
import com.sky.game.service.logic.BagService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0016", enable = true, namespace = "GameUser")
@Component(value = "GU0016")
public class GU0016Handler extends 
BaseProtocolHandler<GU0016Beans.Request, GU0016Beans.Response>{

	/**
	 * 
	 */
	@Autowired
	BagService bagService;
	
	public GU0016Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		List<MyPrice> myPrices=bagService.selectMyPrices(userId);
		res.setMyPrices(myPrices);
		return ret;
	}
  
}
