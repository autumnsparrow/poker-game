package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;

public class GU0033Beans {

	public GU0033Beans() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerRequestType(transcode="GU0033")
	public static class Request  extends BaseRequest{

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}	
		int num;
		String account;
		String phone;
		String code;
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	}
	@HandlerResponseType(responsecode="UG0033",transcode="GU0033")
	public static class Response{
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		int state;
		String description;
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	};

}
