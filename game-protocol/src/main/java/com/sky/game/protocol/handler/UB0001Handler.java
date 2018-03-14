package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UB0001Beans;
import com.sky.game.service.domain.BankShow;
import com.sky.game.service.logic.UserBankService;


/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UB0001", enable = true, namespace = "GameUser")
@Component(value = "UB0001")
public class UB0001Handler extends
		BaseProtocolHandler<UB0001Beans.Request, UB0001Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserBankService userBankService;

	public UB0001Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UB0001Beans.Request req, UB0001Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	    long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		BankShow bankShow=userBankService.selectBankShow(userId);
		res.setBankShow(bankShow);
		return ret;
	}
}
