package com.sky.game.protocol.handler.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.commons.GS0000Beans.GS0015Request;
import com.sky.game.protocol.commons.GS0000Beans.SG0015Response;
import com.sky.game.service.logic.AuctionService;

@HandlerType(enable=true,transcode="GS0015",namespace="GameSystem")
@Component("GS0015")
public class GS0015Handler implements IProtocolHandler<GS0015Request, SG0015Response>{
	@Autowired
	AuctionService auctionService;

	/**
	 * 
	 */
	public GS0015Handler(){
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(GS0015Request req, SG0015Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		//int state=auctionService.insertPointCard(req.getUserId());
		//res.setState(state);
		return true;
	}
}
