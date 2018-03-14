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
public class UC0012Beans {

	/**
	 * 
	 */
	public UC0012Beans() {
		// TODO Auto-generated constructor stub
	} 
	@HandlerRequestType(transcode = "UC0012")
	public static class Request extends BaseRequest{
		
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		String phone;	
		String verification	;
		String password	;
		String confirm	;
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
	}
	
	@HandlerResponseType(responsecode="CU0012",transcode="UC0012")
	public static class Response{

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		int state ;
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

	}
	
}
