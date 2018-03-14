/**
 * 
 */
package com.sky.game.protocol.commons;
import java.util.HashMap;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

/**
 * @author Administrator
 *
 */
public class UL0001Beans {

	/**
	 * 
	 */
	public UL0001Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UL0001")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	@HandlerResponseType(responsecode = "LU0001", transcode = "UL0001")
	public static class Response extends BaseRequest{
		
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
