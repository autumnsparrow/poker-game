/**
 * 
 */
package com.sky.game.protocol.commons;

import java.util.HashMap;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;

/**
 * @author Administrator
 *
 */
public class UP0002Beans {

	/**
	 * 
	 */
	public UP0002Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="UP0002")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HandlerResponseType(responsecode="PU0002",transcode="UP0002")
	public static class Response extends BaseResponse{
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		int state;
		public HashMap<String, Object> getMap() {
			return map;
		}
		public void setMap(HashMap<String, Object> map) {
			this.map = map;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public Response(HashMap<String, Object> map, int state) {
			super();
			this.map = map;
			this.state = state;
		}
		@Override
		public String toString() {
			return "Response [map=" + map + ", state=" + state + "]";
		}
		
	}
}
