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
public class UC0006Beans {

	/**
	 * 
	 */
	public UC0006Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0006")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String UserSign;
		public String getUserSign() {
			return UserSign;
		}
		public void setUserSign(String userSign) {
			UserSign = userSign;
		}
		public Request(String userSign) {
			super();
			UserSign = userSign;
		}
		@Override
		public String toString() {
			return "Request [UserSign=" + UserSign + "]";
		}
		
		
	}

	@HandlerResponseType(responsecode = "CU0006", transcode = "UC0006")
	public static class Response {
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	int state;

	@Override
	public String toString() {
		return "Response [state=" + state + "]";
	}

	public Response(int state) {
		super();
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
  }
}
