package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0010Beans;
import com.sky.game.service.logic.ExchangeService;

@HandlerType(transcode = "GU0010", enable = true, namespace = "GameUser")
@Component(value = "GU0010")
public class GU0010Handler extends
		BaseProtocolHandler<GU0010Beans.Request, GU0010Beans.Response> {

	/**
	* 
	*/
	public GU0010Handler() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	ExchangeService exchangeService;

	@HandlerMethod(enable = true)
	public boolean handler(GU0010Beans.Request req, GU0010Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		long id = req.getId();
		// String s=GameUtil.decode(tokenStr); token 解密
		// 1011 表示userId
		int state = exchangeService.updateUserGolds(userId,id);
		String description = "";
		if(state==1){
			description="恭喜您成功兑换了该物品";
		}
		if(state==-1){
			description="物品数量不足，不能兑换";
		}
		if(state==-2){
			description="今日发放金币数量不足";
		}
		res.setState(state);
		res.setDescription(description);
		res.setGoldExchangeId(req.getId());
		return ret;
	}
}