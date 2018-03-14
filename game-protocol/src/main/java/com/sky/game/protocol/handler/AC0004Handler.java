/**
 * 
 */
package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.AC0004Beans;
import com.sky.game.protocol.commons.AC0004Beans.Request;
import com.sky.game.protocol.commons.AC0004Beans.Response;
import com.sky.game.service.logic.AuctionService;

/**
 * @author Administrator
 * 
 *  我的拍买行--上拍--确定
 */
@HandlerType(transcode="AC0004",enable=true,namespace="AuctionCompany")
@Component(value="AC0004")
public class AC0004Handler extends BaseProtocolHandler<AC0004Beans.Request, AC0004Beans.Response>{

	/**
	 * 
	 */
	public AC0004Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	AuctionService auctionService;
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		long id=req.getId();
		int price=req.getPrice();
		String type=req.getType();
		String chooseCoin=req.getChooseCoin();
		int state=auctionService.arsis(id,type, price,chooseCoin);
		if(state==-1){
			res.setDescription("上拍价格不在规定区间内");
		}
		if(state==-2){
			res.setDescription("时间超出有效期");
		}
		if(state==1){
			res.setDescription("上拍成功");
		}
		res.setState(state);
		return ret;
	}
}
