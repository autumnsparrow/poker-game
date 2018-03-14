/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.GoldBillboard;

/**
 * @author Administrator
 *
 */
public class UR0002Beans {

	/**
	 * 
	 */
	public UR0002Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UR0002")
	public static class Request extends BaseRequest{
		
		public Request() {
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
	}
	
	@HandlerResponseType(responsecode="RU0002",transcode="UR0002")
	public static class Response {
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<GoldBillboard> list = new ArrayList<GoldBillboard>();

		public List<GoldBillboard> getList() {
			return list;
		}

		public void setList(List<GoldBillboard> list) {
			this.list = list;
		}

		public Response(List<GoldBillboard> list) {
			super();
			this.list = list;
		}

		@Override
		public String toString() {
			return "Response []";
		}
	}
		
}
