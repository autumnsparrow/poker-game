package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0033Beans;
import com.sky.game.protocol.commons.GU0033Beans.Request;
import com.sky.game.protocol.commons.GU0033Beans.Response;
import com.sky.game.protocol.util.ConstantCache;
import com.sky.game.service.domain.ChannelExchangeInfo;
import com.sky.game.service.logic.ChannelService;

@HandlerType(transcode="GU0033",enable=true,namespace="GameUser")
@Component(value="GU0033")
public class GU0033Handler  extends BaseProtocolHandler<GU0033Beans.Request, GU0033Beans.Response>{

	public GU0033Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	ChannelService channelService;
	@HandlerMethod(enable=true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		String channelAccount=req.getAccount();
		String descripton="服务异常";
		int num=req.getNum();
		int status=0;
		if(ret){
		int state=ConstantCache.selectValidate(req.getPhone(),req.getCode());
		if(state==1){
		long userId=BasePlayer.getPlayer(req).getUserId();
		channelService.insertCEI(userId,num,channelAccount);
		descripton="成功";
		status=1;
		}else{
		descripton="验证码输入错误,请重新获取";
		status=-1;
		}
		}
		res.setDescription(descripton);
		res.setState(status);
		return ret;
	}
}
