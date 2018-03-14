/**
 * 
 */
package com.sky.game.protocol.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.AC0003Beans;
import com.sky.game.protocol.commons.AC0003Beans.Request;
import com.sky.game.protocol.commons.AC0003Beans.Response;
import com.sky.game.service.domain.MyAuctionGoods;
import com.sky.game.service.logic.AuctionService;

/**
 * @author Administrator
 * 
 *  我的拍买行
 */
@HandlerType(transcode="AC0003",enable=true,namespace="AuctionCompany")
@Component(value="AC0003")
public class AC0003Handler extends BaseProtocolHandler<AC0003Beans.Request, AC0003Beans.Response>{

	/**
	 * 
	 */
	public AC0003Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	AuctionService auctionService;
	
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		List<MyAuctionGoods> myAuctionList=auctionService.selectMyAuctionList(userId);
		res.setMyAuctionList(myAuctionList);
		
	
		
		return ret;
	}
}
