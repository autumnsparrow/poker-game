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
public class UC0017Beans {

	/**
	 * 
	 */
	public UC0017Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0017")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String phone	; //手机号码
		String validate	;//验证码
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getValidate() {
			return validate;
		}
		public void setValidate(String validate) {
			this.validate = validate;
		}
		
	}

	@HandlerResponseType(responsecode = "CU0017", transcode = "UC0017")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		int state;
		String description;
		
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		
  }
}
