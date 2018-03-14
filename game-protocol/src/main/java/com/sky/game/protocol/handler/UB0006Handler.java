package com.sky.game.protocol.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UB0006Beans;
import com.sky.game.service.logic.UserBankService;



/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UB0006", enable = true, namespace = "GameUser")
@Component(value = "UB0006")
public class UB0006Handler extends
		BaseProtocolHandler<UB0006Beans.Request, UB0006Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserBankService userBankService;

	public UB0006Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UB0006Beans.Request req, UB0006Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	   long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);	
		int state=userBankService.updateBankRepayment(userId, req.getBackMoney());
		if(state==1){
			res.setState(state);
			res.setBackMoney(req.getBackMoney());
		}else{
			res.setState(state);
		}
		return ret;
	}
}
