/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseResponse;

/**
 * @author Administrator
 *
 */
public class GU0030Beans {

	/**
	 * 
	 */
	public GU0030Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="GU0030")
	public static class Request{

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}	
		long chanelId;
		String deviceId;
		public long getChanelId() {
			return chanelId;
		}
		public void setChanelId(long chanelId) {
			this.chanelId = chanelId;
		}
		public String getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
	}
	
	@HandlerResponseType(responsecode="UG0030",transcode="GU0030")
	public static class Response extends BaseResponse{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		String account;	
		String password	;
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		
	};
}
