/**
 * 
 */
package com.sky.game.protocol.commons;
import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.SysActivity;

/**
 * @author Administrator
 *
 */
public class UA0001Beans {

	/**
	 * 
	 */
	public UA0001Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UA0001")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	@HandlerResponseType(responsecode = "AU0001", transcode = "UA0001")
	public static class Response {
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<SysActivity> list = new ArrayList<SysActivity>();
		public List<SysActivity> getList() {
			return list;
		}
		public void setList(List<SysActivity> list) {
			this.list = list;
		}
		public Response(List<SysActivity> list) {
			super();
			this.list = list;
		}
		@Override
		public String toString() {
			return "Response [list=" + list + "]";
		}
		
  }
}
