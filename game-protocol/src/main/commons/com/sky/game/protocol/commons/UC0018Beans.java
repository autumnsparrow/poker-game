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
public class UC0018Beans {

	/**
	 * 
	 */
	public UC0018Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0018")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String mail;
		
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}

	}

	@HandlerResponseType(responsecode = "CU0018", transcode = "UC0018")
	public static class Response {
		
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
		
  }
}
