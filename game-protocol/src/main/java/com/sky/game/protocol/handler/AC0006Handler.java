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
import com.sky.game.protocol.commons.AC0006Beans;
import com.sky.game.protocol.commons.AC0006Beans.Request;
import com.sky.game.protocol.commons.AC0006Beans.Response;
import com.sky.game.service.logic.AuctionService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="AC0006",enable=true,namespace="AuctionCompany")
@Component(value="AC0006")
public class AC0006Handler  extends BaseProtocolHandler<AC0006Beans.Request, AC0006Beans.Response>{
	@Autowired
	AuctionService auctionService;
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		int state=auctionService.userUsePoint(BasePlayer.getPlayer(req).getUserId(),req.getRoomId());
		res.setState(state);
		return ret;
	}
}
