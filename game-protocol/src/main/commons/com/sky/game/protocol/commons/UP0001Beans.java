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
public class UP0001Beans {

	/**
	 * 
	 */
	public UP0001Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="UP0001")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HandlerResponseType(responsecode="PU0001",transcode="UP0001")
	public static class Response extends BaseResponse{
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		public HashMap<String, Object> getMap() {
			return map;
		}
		public void setMap(HashMap<String, Object> map) {
			this.map = map;
		}
		public Response(HashMap<String, Object> map) {
			super();
			this.map = map;
		}
		@Override
		public String toString() {
			return "Response [map=" + map + "]";
		}
	}
}
