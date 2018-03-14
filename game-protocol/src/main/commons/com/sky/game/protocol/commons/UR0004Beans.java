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
public class UR0004Beans {

	/**
	 * 
	 */
	public UR0004Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UR0004")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HandlerResponseType(responsecode="RU0004",transcode="UR0004")
	public static class Response {
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		int page;
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public Response(int page) {
			super();
			this.page = page;
		}
		@Override
		public String toString() {
			return "Response [page=" + page + "]";
		}
		
	}
		
}
