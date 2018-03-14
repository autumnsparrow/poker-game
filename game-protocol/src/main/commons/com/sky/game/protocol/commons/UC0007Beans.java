/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.Honor;

/**
 * @author Administrator
 *
 */
public class UC0007Beans {

	/**
	 * 
	 */
	public UC0007Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0007")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HandlerResponseType(responsecode="CU0007",transcode="UC0007")
	public static class Response {
		
		
		Honor honor;

		public Honor getHonor() {
			return honor;
		}

		public void setHonor(Honor honor) {
			this.honor = honor;
		}

		public Response(Honor honor) {
			super();
			this.honor = honor;
		}

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Response [honor=" + honor + "]";
		}
		
	}
		
}
