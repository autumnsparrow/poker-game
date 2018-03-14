/**
 * 
 */
package com.sky.game.protocol.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.internal.service.protocol.ProtocolService;
import com.sky.game.internal.service.protocol.domain.BasicUserInfo;
import com.sky.game.service.domain.UserInfo;
import com.sky.game.service.logic.ShopService;
import com.sky.game.service.logic.UserAccountService;

/**
 * @author sparrow
 *
 */
@Service(value = "ProtocolService")
public class DefaultPaymentCallbackService implements ProtocolService {

	
	private enum OrderType {
		orderFail(-1), orderIng(0), orderSucc(1);
		public int v;

		private OrderType(int i) {
			v = i;
		}
	}

	@Autowired
	ShopService shopService;
	@Autowired
	UserAccountService userAccountService;

	/**
	 * 
	 */
	public DefaultPaymentCallbackService() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.internal.service.payment.upmp.UpmpPaymentCallbackService
	 * #callback(java.lang.String, int)
	 * 
	 */
	@Transactional
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

	public BasicUserInfo getBasicUserInfo(long userId) {
		// TODO Auto-generated method stub
		UserInfo o= userAccountService.selectUserInfo(userId);
		BasicUserInfo bo=null;
		if(o!=null){
			bo=o.wrapper();

//	

		}
		return bo;
	}



}
