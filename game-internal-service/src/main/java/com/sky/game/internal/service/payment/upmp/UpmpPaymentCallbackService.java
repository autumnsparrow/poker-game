/**
 * 
 */
package com.sky.game.internal.service.payment.upmp;

/**
 * @author sparrow
 *
 */
public interface UpmpPaymentCallbackService {
	public void callback(String orderId,int status);
}
