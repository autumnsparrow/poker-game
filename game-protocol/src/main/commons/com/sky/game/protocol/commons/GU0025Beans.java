/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.StoreLog;

/**
 * @author Administrator
 *
 */
public class GU0025Beans {

	/**
	 * 
	 */
	public GU0025Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0025")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}

	@HandlerResponseType(responsecode = "UG0025", transcode = "GU0025")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<StoreLog> list = new ArrayList<StoreLog>();
		public List<StoreLog> getList() {
			return list;
		}
		public void setList(List<StoreLog> list) {
			this.list = list;
		}
		public Response(List<StoreLog> list) {
			super();
			this.list = list;
		}
		@Override
		public String toString() {
			return "Response [list=" + list + "]";
		}
	}
}
