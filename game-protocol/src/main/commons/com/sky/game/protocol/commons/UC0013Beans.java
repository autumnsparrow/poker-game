/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.UserHead;

/**
 * @author Administrator
 *
 */
public class UC0013Beans {

	/**
	 * 
	 */
	public UC0013Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0013")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HandlerResponseType(responsecode="CU0013",transcode="UC0013")
	public static class Response{

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		UserHead userHead;
		
		public UserHead getUserHead() {
			return userHead;
		}
		public void setUserHead(UserHead userHead) {
			this.userHead = userHead;
		}
	}
}
