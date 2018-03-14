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
import com.sky.game.protocol.commons.GU0013Beans;
import com.sky.game.protocol.commons.GU0013Beans.*;
import com.sky.game.service.logic.ShopService;
import com.sky.game.service.logic.UserAccountService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0013", enable = true, namespace = "GameUser")
@Component(value = "GU0013")
public class GU0013Handler extends
BaseProtocolHandler<GU0013Beans.Request, GU0013Beans.Response>{

	/**
	 * 
	 */
	public GU0013Handler() {
		// TODO Auto-generated constructor stub
	}
    @Autowired
	UserAccountService  userAccountService;
    
    @Autowired
    ShopService shopService;
	
	@Override
	@HandlerMethod(enable=true)
	public boolean handler(Request req,Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	
		boolean ret=super.handler(req, res);
		int state=-1;
		if(req.getOrderId()!=null){
			long userId=BasePlayer.getPlayer(req).getUserId();
			int id=Integer.valueOf(req.getOrderId().substring(15, req.getOrderId().length()));
			//shopService.updateUserGold(id,userId); 暂时注掉
			state=1;
		}
	    res.setState(state);
		return ret;
	}
}
