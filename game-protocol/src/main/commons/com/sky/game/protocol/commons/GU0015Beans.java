/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.MyGood;

/**
 * @author Administrator
 *
 */
public class GU0015Beans {

	/**
	 * 
	 */
	public GU0015Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerRequestType(transcode = "GU0015")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
	}

	@HandlerResponseType(responsecode = "UG0015", transcode = "GU0015")
	public static class Response {

		List<MyGood> list = new ArrayList<MyGood>();

		public List<MyGood> getList() {
			return list;
		}

		public void setList(List<MyGood> list) {
			this.list = list;
		}

		public Response(List<MyGood> list) {
			super();
			this.list = list;
		}
		
		

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Response [list=" + list + "]";
		}
		
	}	
}
