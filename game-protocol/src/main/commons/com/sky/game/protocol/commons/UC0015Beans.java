/**
 * 
 */
package com.sky.game.protocol.commons;
import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.Certificate;

/**
 * @author Administrator
 *
 */
public class UC0015Beans {

	/**
	 * 
	 */
	public UC0015Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0015")
	public static class Request extends BaseRequest {

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

	@HandlerResponseType(responsecode = "CU0015", transcode = "UC0015")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<Certificate> list = new ArrayList<Certificate>();

		public List<Certificate> getList() {
			return list;
		}

		public void setList(List<Certificate> list) {
			this.list = list;
		}

		public Response(List<Certificate> list) {
			super();
			this.list = list;
		}

		@Override
		public String toString() {
			return "Response [list=" + list + "]";
		}
  }
}
