/**
 * 
 */
package com.sky.game.protocol.commons;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

/**
 * @author Administrator
 *
 */
public class UM0002Beans {

	/**
	 * 
	 */
	public UM0002Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UM0002")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<Long> list;
		public List<Long> getList() {
			return list;
		}
		public void setList(List<Long> list) {
			this.list = list;
		}
		
		public Request(List<Long> list) {
			super();
			this.list = list;
		}
		@Override
		public String toString() {
			return "Request [list=" + list + "]";
		}
		
		
	}

	@HandlerResponseType(responsecode = "MU0002", transcode = "UM0002")
	public static class Response {
		
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
