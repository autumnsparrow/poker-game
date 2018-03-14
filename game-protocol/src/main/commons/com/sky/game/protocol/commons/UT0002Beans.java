/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;

/**
 * @author Administrator
 *
 */
public class UT0002Beans {

	/**
	 * 
	 */
	public UT0002Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="UT0002")
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
	
	@HandlerResponseType(responsecode="TU0002",transcode="UT0002")
	public static class Response extends BaseResponse{
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		int state;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public Response(int state) {
			super();
			this.state = state;
		}
		@Override
		public String toString() {
			return "Response [state=" + state + "]";
		}

	}
}
