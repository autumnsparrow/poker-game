/**
 * 
 */
package com.sky.game.protocol.commons;
import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
/**
 * @author Administrator
 *
 */
public class GU0022Beans {

	/**
	 * 
	 */
	public GU0022Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0022")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		int flag;
		public int getFlag() {
			return flag;
		}
		public void setFlag(int flag) {
			this.flag = flag;
		}
		
		
	}

	@HandlerResponseType(responsecode = "UG0022", transcode = "GU0022")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		String name;
	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
		
	};
}
