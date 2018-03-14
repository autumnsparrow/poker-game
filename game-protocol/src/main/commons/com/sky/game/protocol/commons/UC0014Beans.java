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
public class UC0014Beans {

	/**
	 * 
	 */
	public UC0014Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0014")
	public static class Request extends BaseRequest{
		
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
		public Request(long id) {
			super();
			this.id = id;
		}
		@Override
		public String toString() {
			return "Request [id=" + id + "]";
		}
		
	}
	
	@HandlerResponseType(responsecode="CU0014",transcode="UC0014")
	public static class Response{

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		String description;
		int state;
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
	}
}
