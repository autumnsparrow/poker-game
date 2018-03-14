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
public class UC0010Beans {

	/**
	 * 
	 */
	public UC0010Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0010")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String password;
		String confirm	;
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getConfirm() {
			return confirm;
		}
		public void setConfirm(String confirm) {
			this.confirm = confirm;
		}
		public Request(String password, String confirm) {
			super();
			this.password = password;
			this.confirm = confirm;
		}
		@Override
		public String toString() {
			return "Request [password=" + password + ", confirm=" + confirm
					+ "]";
		}
		
	}
	
	@HandlerResponseType(responsecode="CU0010",transcode="UC0010")
	public static class Response{
		
		int state;

		String description;

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

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

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
	}
}
