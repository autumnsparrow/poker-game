/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
/**
 * @author Administrator
 *
 */
public class GU0031Beans {

	/**
	 * 
	 */
	public GU0031Beans() {
		// TODO Auto-generated constructor stub
	}
	
		@HandlerRequestType(transcode="GU0031")
		public static class Request{

			public Request() {
				super();
				// TODO Auto-generated constructor stub
			}	
			int type;
			int count;
			public int getType() {
				return type;
			}
			public void setType(int type) {
				this.type = type;
			}
			public int getCount() {
				return count;
			}
			public void setCount(int count) {
				this.count = count;
			}
		}
		
		@HandlerResponseType(responsecode="UG0031",transcode="GU0031")
		public static class Response{
			
			public Response() {
				super();
				// TODO Auto-generated constructor stub
			}
		};

}
