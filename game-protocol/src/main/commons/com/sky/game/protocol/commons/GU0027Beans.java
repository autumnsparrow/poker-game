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
public class GU0027Beans {

	/**
	 * 
	 */
	public GU0027Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0027")
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

	@HandlerResponseType(responsecode = "UG0027", transcode = "GU0027")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		long id;
		int state;
		String description;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
	}
}
