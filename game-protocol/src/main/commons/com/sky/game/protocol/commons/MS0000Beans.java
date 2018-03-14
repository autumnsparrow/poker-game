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
public class MS0000Beans {

	/**
	 * 
	 */
	public MS0000Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="MS0000")
	public static class Request extends BaseRequest{
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		long userId;
		String name;
		String message;
		public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
	
	@HandlerResponseType(responsecode="SM0000",transcode="MS0000")
	public static class Response{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		String ms;

		public String getMs() {
			return ms;
		}

		public void setMs(String ms) {
			this.ms = ms;
		}
        
	
	};
}
