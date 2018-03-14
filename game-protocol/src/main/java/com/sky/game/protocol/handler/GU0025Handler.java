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
import com.sky.game.protocol.commons.GU0025Beans;
import com.sky.game.protocol.commons.GU0025Beans.Request;
import com.sky.game.protocol.commons.GU0025Beans.Response;
import com.sky.game.service.domain.StoreLog;
import com.sky.game.service.logic.ShopService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0025", enable = true, namespace = "GameUser")
@Component(value = "GU0025")
public class GU0025Handler  extends BaseProtocolHandler<GU0025Beans.Request, GU0025Beans.Response>{

	/**
	 * 
	 */
	public GU0025Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	ShopService shopService;
	
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req,Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		List<StoreLog> list=shopService.selectStoreLog(userId);
		res.setList(list);
		return ret;
	}
	
}
