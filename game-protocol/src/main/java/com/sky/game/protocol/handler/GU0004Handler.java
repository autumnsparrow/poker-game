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
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0004Beans;
import com.sky.game.service.domain.StoreShow;
import com.sky.game.protocol.commons.GU0004Beans.Request;
import com.sky.game.protocol.commons.GU0004Beans.Response;
import com.sky.game.service.logic.ShopService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0004", enable = true, namespace = "GameUser")
@Component(value = "GU0004")
public class GU0004Handler extends
		BaseProtocolHandler<GU0004Beans.Request, GU0004Beans.Response> {

	/**
	 * 
	 */
	
	@Autowired
	ShopService shopService;
	
	
	public GU0004Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerMethod(enable=true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
			List<StoreShow> exList = shopService.selectStore();
			res.setChargeRateList(exList);
		return ret;
	}

}
