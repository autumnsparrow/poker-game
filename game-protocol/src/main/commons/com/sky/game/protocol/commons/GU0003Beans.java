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
public class GU0003Beans {

	/**
	 * 
	 */
	public GU0003Beans() {
		// TODO Auto-generated constructor stub
	}
	
	@HandlerRequestType(transcode="GU0003")
	public static class Request extends BaseRequest{
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String phone;
		String validate;
		String password;
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getValidate() {
			return validate;
		}
		public void setValidate(String validate) {
			this.validate = validate;
		}
	}
	@HandlerResponseType(responsecode="UG0003",transcode="GU0003")
	public static class Response{
		long id;
		int state;
		String description;
		String account;
		String password;
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
	}

}
