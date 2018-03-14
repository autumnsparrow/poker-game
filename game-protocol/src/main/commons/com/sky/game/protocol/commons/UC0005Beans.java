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
public class UC0005Beans {

	/**
	 * 
	 */
	public UC0005Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0005")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String phone;
		String verification;
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getVerification() {
			return verification;
		}
		public void setVerification(String verification) {
			this.verification = verification;
		}
		public Request(String phone, String verification) {
			super();
			this.phone = phone;
			this.verification = verification;
		}
		@Override
		public String toString() {
			return "Request [phone=" + phone + ", verification=" + verification
					+ "]";
		}
	}

	@HandlerResponseType(responsecode = "CU0005", transcode = "UC0005")
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
