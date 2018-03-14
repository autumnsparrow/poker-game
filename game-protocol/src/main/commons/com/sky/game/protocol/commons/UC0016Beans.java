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
public class UC0016Beans {

	/**
	 * 
	 */
	public UC0016Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode = "UC0016")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}

	}

	@HandlerResponseType(responsecode = "CU0016", transcode = "UC0016")
	public static class Response {
		
		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		int flag;//用户标志
		
		String phone;//电话
		String mail;//邮箱
		String password	;//登录密码
		String banikPassword;//银行密码
		//int userType;//用户类型
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getBanikPassword() {
			return banikPassword;
		}
		public void setBanikPassword(String banikPassword) {
			this.banikPassword = banikPassword;
		}
		public int getFlag() {
			return flag;
		}
		public void setFlag(int flag) {
			this.flag = flag;
		}
//		public int getUserType() {
//			return userType;
//		}
//		public void setUserType(int userType) {
//			this.userType = userType;
//		}
  }
}
