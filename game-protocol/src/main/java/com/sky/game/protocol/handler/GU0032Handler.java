package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0005Beans;
import com.sky.game.protocol.commons.GU0032Beans;
import com.sky.game.protocol.commons.GU0032Beans.Request;
import com.sky.game.protocol.commons.GU0032Beans.Response;
import com.sky.game.service.logic.ChannelService;

@HandlerType(transcode="GU0032",enable=true,namespace="GameUser")
@Component(value="GU0032")
public class GU0032Handler  extends BaseProtocolHandler<GU0032Beans.Request, GU0032Beans.Response>{

	public GU0032Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	ChannelService channelService;
	@HandlerMethod(enable=true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		int state=0;
		String description="服务异常";
		if(ret){
		long userId=BasePlayer.getPlayer(req).getUserId();
		long itemId=req.getItemId();
		state=channelService.userChannelGoods(userId, itemId);
		if(state==-1){
			description="用户物品不足";
		}
		if(state==-3){
			description="用户没有该物品";
		}
		}
		res.setState(state);
		res.setDescription(description);
		return ret;
	}

}
