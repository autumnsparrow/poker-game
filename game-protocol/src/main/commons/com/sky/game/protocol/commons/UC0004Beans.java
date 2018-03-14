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
public class UC0004Beans {

	/**
	 * 
	 */
	public UC0004Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0004")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String phone;
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		@Override
		public String toString() {
			return "Request [phone=" + phone + "]";
		}
		public Request(String phone) {
			super();
			this.phone = phone;
		}
		
	}

	@HandlerResponseType(responsecode = "CU0004", transcode = "UC0004")
	public static class Response {
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
	String verification;

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	@Override
	public String toString() {
		return "Response [verification=" + verification + "]";
	}

	public Response(String verification) {
		super();
		this.verification = verification;
	}
	
  }
}
