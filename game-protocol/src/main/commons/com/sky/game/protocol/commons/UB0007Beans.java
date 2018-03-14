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
public class UB0007Beans {

	/**
	 * 
	 */
	public UB0007Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UB0007")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String bankPw;
		public String getBankPw() {
			return bankPw;
		}
		public void setBankPw(String bankPw) {
			this.bankPw = bankPw;
		}
		public Request(String bankPw) {
			super();
			this.bankPw = bankPw;
		}
		@Override
		public String toString() {
			return "Request [bankPw=" + bankPw + "]";
		}
	
	}

	@HandlerResponseType(responsecode = "BU0007", transcode = "UB0007")
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
