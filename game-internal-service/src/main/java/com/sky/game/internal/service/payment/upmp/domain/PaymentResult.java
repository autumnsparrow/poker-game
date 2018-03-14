/**
 * 
 */
package com.sky.game.internal.service.payment.upmp.domain;

/**
 * @author sparrow
 *
 */
public class PaymentResult {
	
	private int status;
	private String orderId;
	private String tn;
	


	/**
	 * 
	 */
	public PaymentResult() {
		// TODO Auto-generated constructor stub
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getTn() {
		return tn;
	}



	public void setTn(String tn) {
		this.tn = tn;
	}

}
