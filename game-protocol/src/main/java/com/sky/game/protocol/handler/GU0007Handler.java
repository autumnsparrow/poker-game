package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0007Beans;
import com.sky.game.service.logic.ExchangeService;

@HandlerType(transcode = "GU0007", enable = true, namespace = "GameUser")
@Component(value = "GU0007")
public class GU0007Handler extends
		BaseProtocolHandler<GU0007Beans.Request, GU0007Beans.Response> {

	/**
	* 
	*/
	public GU0007Handler() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	ExchangeService exchangeService;

	@HandlerMethod(enable = true)
	public boolean handler(GU0007Beans.Request req, GU0007Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		long id = req.getId();
		// String s=GameUtil.decode(tokenStr); token 解密
		// 1011 表示userId
		int state=exchangeService.updateUserGoods(userId,id);
		String description="";
		if(state==1){
			description="恭喜您成功兑换了该物品，进入背包进行查看。";
		}
		if(state==-1){
			description="您的金币数量不足，不能兑换。";
		}
		if(state==-2){
			description="今日发放的已经被抢光啦，明天再来吧。";
		}
		if(state==-3){
			description="非VIP用户无法购买该物品。";
		}
		res.setState(state);
		res.setDescription(description);
		res.setGoodsExchangeId(id);
		return ret;
	}
}