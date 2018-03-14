/**
 * 
 */
package com.sky.game.protocol.handler;


import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.internal.service.payment.PaymentService;
import com.sky.game.internal.service.payment.domain.PaymentResult;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0005Beans;
import com.sky.game.protocol.commons.GU0005Beans.Request;
import com.sky.game.protocol.commons.GU0005Beans.Response;
import com.sky.game.protocol.commons.PaymentResponse;
import com.sky.game.service.domain.Store;
import com.sky.game.service.domain.StoreOrder;
import com.sky.game.service.logic.ShopService;
/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0005", enable = true, namespace = "GameUser")
@Component(value = "GU0005")
public class GU0005Handler extends BaseProtocolHandler<GU0005Beans.Request, GU0005Beans.Response> {

	/**
	 * 
	 */
	public GU0005Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	ShopService shopService;
	
	@Autowired
	PaymentService paymentService;
	
	@HandlerMethod(enable=true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		
		boolean ret = super.handler(req,res);
		MultiValueMap<String, String> headers =
			    new LinkedMultiValueMap<String, String>();
			headers.add("Accept","application/json;charset=utf-8");
			headers.add("Content-Type","application/json;charset=utf-8");
			HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
			RestTemplate restTemplate =new RestTemplate();
		StoreOrder order =new StoreOrder();
		if (req.getId()!=0) {
			Store store=shopService.selectStoreById(req.getId());
			String orderId="Y"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+req.getId();
			String itemName=null;
			// 封装    订单（用处待考虑）
			order.setOrderId(orderId);
			order.setUserAccountId(BasePlayer.getPlayer(req).getUserId());
			if(store.getItemId()==1001){
				itemName="金币";
			}
			else if(store.getItemId()==1003){
				itemName="钻石";
			}
			order.setDescription(store.getTotalCount()+itemName);
			order.setMenoy(store.getPrice());
			order.setType(store.getPayType());
		    order.setStoreId(req.getId());
			shopService.insertStroeOrder(order);
			//
			Map<String,String> extra = new HashMap<String,String>();
			extra.put("111", "111");
			//HashMap<String,Object>  map=(HashMap<String, Object>) paymentService.pay(1,orderId, Integer.valueOf(store.getPrice())*100, store.getName(),extra);
			PaymentResult reslt =paymentService.payUpmp(orderId, Integer.valueOf(store.getPrice())*100, itemName);
			res.setPaymentResponse(reslt);
			/*Map<String,Object> params=new HashMap<String,Object>();
			params.put("orderId",orderId);
			params.put("amount",Integer.valueOf(store.getPrice()*100));
			try {
				params.put("description",URLEncoder.encode(store.getTotalCount()+store.getName(),"utf-8"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//String url="http://game.sky-game.com:8089/payment_upmp/upmp/payment?orderId={orderId}&amount={amount}&description={description}";
			//online
			//String url="http://pay.ipagat.com:88/payment_upmp/upmp/payment?orderId={orderId}&amount={amount}&description={description}";
			//test
			String url="http://pokergame.xicp.net:8080/payment_upmp/upmp/payment?orderId={orderId}&amount={amount}&description={description}";
			ResponseEntity<PaymentResponse> paymentResponse=restTemplate.postForEntity(url,requestEntity,PaymentResponse.class,params);
			PaymentResponse payment =paymentResponse.getBody();
			res.setPaymentResponse(payment);*/
		} 
		return ret;
	}
	
	
}
