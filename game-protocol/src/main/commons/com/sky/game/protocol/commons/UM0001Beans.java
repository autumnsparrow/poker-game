/**
 * 
 */
package com.sky.game.protocol.commons;
import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.SysMessage;

/**
 * @author Administrator
 *
 */
public class UM0001Beans {

	/**
	 * 
	 */
	public UM0001Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UM0001")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		long lastId;
		public long getLastId() {
			return lastId;
		}
		public void setLastId(long lastId) {
			this.lastId = lastId;
		}
		public Request(long lastId) {
			super();
			this.lastId = lastId;
		}
		@Override
		public String toString() {
			return "Request [lastId=" + lastId + "]";
		}
		
	}

	@HandlerResponseType(responsecode = "MU0001", transcode = "UM0001")
	public static class Response extends BaseRequest{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<SysMessage> list = new ArrayList<SysMessage>();
		public List<SysMessage> getList() {
			return list;
		}
		public void setList(List<SysMessage> list) {
			this.list = list;
		}
		public Response(List<SysMessage> list) {
			super();
			this.list = list;
		}
		@Override
		public String toString() {
			return "Response [list=" + list + "]";
		}
		
		
  }
}
