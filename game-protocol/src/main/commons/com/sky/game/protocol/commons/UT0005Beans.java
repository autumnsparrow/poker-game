/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.ArrayList;
import java.util.List;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;
import com.sky.game.service.domain.TaskSet;

/**
 * @author Administrator
 *
 */
public class UT0005Beans {

	/**
	 * 
	 */
	public UT0005Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="UT0005")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HandlerResponseType(responsecode="TU0005",transcode="UT0005")
	public static class Response extends BaseResponse{
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		List<TaskSet> list = new ArrayList<TaskSet>();
		public List<TaskSet> getList() {
			return list;
		}
		public void setList(List<TaskSet> list) {
			this.list = list;
		}
		public Response(List<TaskSet> list) {
			super();
			this.list = list;
		}
		@Override
		public String toString() {
			return "Response [list=" + list + "]";
		}

	}
}
