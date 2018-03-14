package com.sky.game.protocol.handler;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.internal.service.payment.PaymentService;
import com.sky.game.internal.service.payment.domain.WxPayOrder;
import com.sky.game.internal.service.protocol.ProtocolService;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.PaymentResponse;
import com.sky.game.protocol.commons.WX0001Beans;


import com.sky.game.service.domain.Store;
import com.sky.game.service.domain.StoreOrder;
import com.sky.game.service.logic.ShopService;
import com.sky.game.service.logic.UserAccountService;




/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "WX0001", enable = true, namespace = "GameUser")
@Component(value = "WX0001")
public class WX0001Handler extends
		BaseProtocolHandler<WX0001Beans.Request, WX0001Beans.Response> {

	/**
	 * 
	 */
	
	@Autowired
	ShopService shopService;
	
	

	public WX0001Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(WX0001Beans.Request req, WX0001Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=false;
		try {
			long userId=BasePlayer.getPlayer(req).getUserId();
			ret = super.handler(req, res);	
//		String url="http://pay.ipagat.com:8080/payment_upmp/wxpay/payment?totalFee={totalFee}&amount={ip}";
//		ResponseEntity<PaymentResponse> paymentResponse=restTemplate.postForEntity(url,requestEntity,PaymentResponse.class,params);
//		PaymentResponse payment =paymentResponse.getBody();
			String tradeNum="WX"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+req.getId();
			int price=shopService.getPrice(req.getId());
			String itemName = shopService.getItemName(req.getId());
			String description = shopService.selectStoreById(req.getId()).getDescription();
			//long storeId=shopService.selectStoreById(req.getId()).getId();
			shopService.insertStoreOrder(tradeNum,req.getId(),userId);
			
			WxPayOrder order=new WxPayOrder();//paymentService.payWx(tradeNum, price*100, itemName, req.getIp());
			order.setAppid("appid");
			order.setExpand("expand");
			order.setNoncestr("noneStrs");
			order.setPrepayid(tradeNum);
			order.setStamp("stamp");
			order.setSign("sing");

			res.setWxPayOrder(order);
			
			callback(tradeNum, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ProtocolException(e.getMessage());
		}
		return ret;
	}
	
	
	
	private enum OrderType {
		orderFail(-1), orderIng(0), orderSucc(1);
		public int v;

		private OrderType(int i) {
			v = i;
		}
	}

	
	
	public void callback(String tradeNum, int state) {

		int ret = shopService.updateTradeStatus(tradeNum, state ,OrderType.orderIng.v);
		if(ret == 1){
			if(state == 1){
				
				long userId = shopService.selectByTradeNum(tradeNum)//根据order_id查询store_order表中的用户ID
						.getUserAccountId();
				long storeId = shopService.selectByTradeNum(tradeNum).getStoreId();//
				shopService.updateUserGold(storeId, userId);
			}
		}
		

	}

	    
	
	
}
