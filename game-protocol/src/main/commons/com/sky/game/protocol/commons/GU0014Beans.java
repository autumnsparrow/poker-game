/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.ExchangeRecord;

/**
 * @author Administrator
 *
 */
public class GU0014Beans {

	/**
	 * 
	 */
	public GU0014Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0014")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
	}

	@HandlerResponseType(responsecode = "UG0014", transcode = "GU0014")
	public static class Response {

		List<ExchangeRecord> list = new ArrayList<ExchangeRecord>();

		public List<ExchangeRecord> getList() {
			return list;
		}

		public void setList(List<ExchangeRecord> list) {
			this.list = list;
		}

		public Response(List<ExchangeRecord> list) {
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
