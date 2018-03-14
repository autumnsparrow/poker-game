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
public class GU0017Beans {

	/**
	 * 
	 */
	public GU0017Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "GU0017")
	public static class Request extends BaseRequest {
 
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String phone;
		int    isFlag;//判断是否是启用银行密码验证
		
		public int getIsFlag() {
			return isFlag;
		}
		public void setIsFlag(int isFlag) {
			this.isFlag = isFlag;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Request(String phone) {
			super();
			this.phone = phone;
		}
		@Override
		public String toString() {
			return "Request [phone=" + phone + "]";
		}
	}

	@HandlerResponseType(responsecode = "UG0017", transcode = "GU0017")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		int State;
		String description;
		public int getState() {
			return State;
		}
		public void setState(int state) {
			State = state;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	};
}
