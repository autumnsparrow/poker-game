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
import com.sky.game.protocol.commons.AC0002Beans;
import com.sky.game.protocol.commons.AC0002Beans.Request;
import com.sky.game.protocol.commons.AC0002Beans.Response;
import com.sky.game.service.logic.AuctionService;

/**
 * @author Administrator
 * 
 *  拍买行--竞拍
 */
@HandlerType(transcode="AC0002",enable=true,namespace="AuctionCompany")
@Component(value="AC0002")
public class AC0002Handler extends BaseProtocolHandler<AC0002Beans.Request, AC0002Beans.Response>{

	/**
	 * 
	 */
	public AC0002Handler() {
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
		long id=req.getId();
		String bankPassword=req.getBankPassword();
		int count=req.getCount();
		int state=auctionService.auction(userId,id,bankPassword,count);
		if(state==-1){
			res.setDescription("没有积分卡");
		}
		if(state==-2){
			res.setDescription("你还没有开通银行");
		}
		if(state==-3){
			res.setDescription("银行密码错误");
		}
		if(state==-4){
			res.setDescription("用户账户余额不足");
		}
		if(state==-5){
			res.setDescription("已有本关积分卡");
		}
		if(state==-9){
			res.setDescription("用户没有相应物品");
		}
		if(state==1){
			res.setDescription("购买成功");
		}
		if(state==2){
			res.setDescription("加价成功");
		}
		res.setState(state);
		return ret;
	}
}
