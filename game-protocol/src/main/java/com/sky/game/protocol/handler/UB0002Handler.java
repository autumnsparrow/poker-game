package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UB0002Beans;
import com.sky.game.service.logic.UserBankService;



/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UB0002", enable = true, namespace = "GameUser")
@Component(value = "UB0002")
public class UB0002Handler extends
		BaseProtocolHandler<UB0002Beans.Request, UB0002Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserBankService userBankService;

	public UB0002Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UB0002Beans.Request req, UB0002Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	   long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		int state = userBankService.updateDeposit(userId, req.getSaveMoney());
		if(state!=1){
			res.setState(state);
		}
		else{
			res.setState(state);
			res.setSaveMoney(req.getSaveMoney());
		}
		return ret;
	}
}
