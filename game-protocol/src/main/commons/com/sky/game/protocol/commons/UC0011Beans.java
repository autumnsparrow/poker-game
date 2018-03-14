/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

/**
 * @author Administrator
 *
 */
public class UC0011Beans {

	/**
	 * 
	 */
	public UC0011Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0011")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String phone;
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Request(String phone) {
			super();
			this.phone = phone;
		}
		@Override
		public String toString() {
			return "Request [phone=" + phone + "]";
		}	
		
	}
	
	@HandlerResponseType(responsecode="CU0011",transcode="UC0011")
	public static class Response {

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	}
		
}
