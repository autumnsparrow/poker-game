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
import com.sky.game.protocol.commons.GU0012Beans;
import com.sky.game.protocol.commons.GU0012Beans.Request;
import com.sky.game.protocol.commons.GU0012Beans.Response;
import com.sky.game.service.logic.ExchangeService;

/**
 * @author Administrator
 *
 */

@HandlerType(transcode = "GU0012", enable = true, namespace = "GameUser")
@Component(value = "GU0012")
public class GU0012Hander extends
BaseProtocolHandler<GU0012Beans.Request, GU0012Beans.Response>{

	/**
	 * 
	 */
	public GU0012Hander() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	ExchangeService exchangeService;

	@Override
	@HandlerMethod(enable=true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
	  boolean ret=super.handler(req, res);
	  long userId=BasePlayer.getPlayer(req).getUserId();
		long id = req.getId();
		int state = 0;
		String description="物品投放量为0";
		state=exchangeService.priceRewardExchange(userId,id);
		if (state==1){
			description="奖品兑换成功";
		} 
		if(state==-1){
            description="您的物品数量不足";
		}
		if(state==-2){
			description="今日发放的该物品已经被抢光啦，明天再来吧";
		}
		if(state==-3){
			description="用户没有该物品";
		}
		res.setState(state);
		res.setDescription(description);
		res.setPirceExchangeId(req.getId());
		return ret;
	}
}
