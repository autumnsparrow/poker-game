/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.internal.service.payment.domain.PaymentResult;

/**
 * @author Administrator
 *
 */
public class GU0005Beans {

	/**
	 * 
	 */
	public GU0005Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerRequestType(transcode = "GU0005")
	public static class Request extends BaseRequest {
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		long id;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	}

	@HandlerResponseType(responsecode = "UG0005", transcode = "GU0005")
	public static class Response {
		//PaymentResponse paymentResponse;
		
		PaymentResult paymentResponse;

		public PaymentResult getPaymentResponse() {
			return paymentResponse;
		}

		public void setPaymentResponse(PaymentResult paymentResponse) {
			this.paymentResponse = paymentResponse;
		}
		
	};
    
	/*public static class Order{
		
		public Order() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		String orderId;
		long   account;
		int    menoy;
		String descript;
		Timestamp orderTime;
		int type;
		
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public long getAccount() {
			return account;
		}
		public void setAccount(long account) {
			this.account = account;
		}
		public int getMenoy() {
			return menoy;
		}
		public void setMenoy(int menoy) {
			this.menoy = menoy;
		}
		public String getDescript() {
			return descript;
		}
		public void setDescript(String descript) {
			this.descript = descript;
		}
		public Timestamp getOrderTime() {
			return orderTime;
		}
		public void setOrderTime(Timestamp orderTime) {
			this.orderTime = orderTime;
		}
		
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
	} ;*/
	
}
