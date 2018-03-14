package com.sky.game.internal.service.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sky.game.internal.service.payment.domain.PaymentResult;
import com.sky.game.internal.service.payment.domain.WxPayOrder;

public interface PaymentService {

	public PaymentResult payUpmp(String ordreId,int amount,String itemName);
	
	public WxPayOrder payWx(String orderId,int amount,String itemName,String ip);
	/**
	 * 短贷
	 */
	public HashMap<String,Object> productOrder(String orderId,String itemName,int price,int jfid,String description,String name);
	
	/**
	 * 
	 * 
	 * @param orderIds
	 */
	public void resOrder(List<String> orderIds);
}
