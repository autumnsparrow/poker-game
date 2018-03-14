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
public class UT0006Beans {

	/**
	 * 
	 */
	public UT0006Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="UT0006")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HandlerResponseType(responsecode="TU0006",transcode="UT0006")
	public static class Response extends BaseResponse{
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
