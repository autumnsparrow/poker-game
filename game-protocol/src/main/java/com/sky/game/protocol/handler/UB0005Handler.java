package com.sky.game.protocol.handler;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UB0005Beans;
import com.sky.game.service.logic.UserBankService;



/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UB0005", enable = true, namespace = "GameUser")
@Component(value = "UB0005")
public class UB0005Handler extends
		BaseProtocolHandler<UB0005Beans.Request, UB0005Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserBankService userBankService;

	public UB0005Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UB0005Beans.Request req, UB0005Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
	   long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);	
		HashMap map=userBankService.updateBankLoan(userId, req.getLend());
		int state = (Integer) map.get("state");
		if(state==1){
			res.setState(state);
			res.setLend(req.getLend());
			res.setLoanDate((String) map.get("loanDate1"));
			res.setRefundDate((String) map.get("refundDate1"));
		}
		else{
		res.setState(state);
		}
		return ret;
	}
}
