/**
 * 
 */
package com.sky.game.internal.service.protocol;

import com.sky.game.internal.service.protocol.domain.BasicUserInfo;

/**
 * @author Administrator
 *
 */
public interface ProtocolService {
	
	/**
	 * 
	 * @param orderId
	 * @param status
	 */
	public void callback(String orderId,int status);
	
	
	public BasicUserInfo getBasicUserInfo(long userId);



}
