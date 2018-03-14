/**
 * 
 */
package com.sky.game.protocol.handler;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.AC0001Beans;
import com.sky.game.protocol.commons.AC0001Beans.Request;
import com.sky.game.protocol.commons.AC0001Beans.Response;
import com.sky.game.service.domain.AuctionGoods;
import com.sky.game.service.logic.AuctionService;

/**
 * @author Administrator
 *
 * 拍卖市场
 */
@HandlerType(transcode="AC0001",enable=true,namespace="AuctionCompany")
@Component(value="AC0001")
public class AC0001Handler extends BaseProtocolHandler<AC0001Beans.Request, AC0001Beans.Response>{

	/**
	 * 
	 */
	public AC0001Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	AuctionService auctionService;

	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		 List<AuctionGoods> auctionGoodsList= auctionService.selectAuctionList();
		 res.setAuctionGoodsList(auctionGoodsList);
		return ret;
	}
	

	
	public static void main(String[] args) throws IOException {
		long nowTime=System.currentTimeMillis();
		long mss=nowTime-4;
		long days = mss / (1000 * 60 * 60 * 24);
		   long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		   long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		   long seconds = (mss % (1000 * 60)) / 1000;
		   String a= days + "日" + hours + "小时" + minutes + "分"
		     + seconds + "秒";
		System.out.println(a);
		
		String d=String.format("%02d:%02d:%02d", hours,minutes,seconds);
		System.out.println(d);
		
	}
}
