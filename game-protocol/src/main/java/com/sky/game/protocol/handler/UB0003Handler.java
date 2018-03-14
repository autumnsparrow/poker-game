package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UB0003Beans;
import com.sky.game.service.logic.UserBankService;



/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UB0003", enable = true, namespace = "GameUser")
@Component(value = "UB0003")
public class UB0003Handler extends
		BaseProtocolHandler<UB0003Beans.Request, UB0003Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserBankService userBankService;

	public UB0003Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UB0003Beans.Request req, UB0003Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	   long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		int state = userBankService.takeDeposit(userId, req.getTakeMoney(),req.getBankPw());
		if(state==1){
			res.setState(state);
			res.setTakeMoney(req.getTakeMoney());
		}else{
			res.setState(state);
		}
		return ret;
	}
}
