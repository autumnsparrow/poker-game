/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseResponse;

/**
 * @author Administrator
 *
 */
public class GU0002Beans {

	/**
	 * 
	 */
	public GU0002Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="GU0002")
	public static class Request{
		int flag;
		String account;
		String password;
		String deviceId;
		public Request() {
			super();
			// TODO Auto-generated constructor stub
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
		
		public String getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		public int getFlag() {
			return flag;
		}
		public void setFlag(int flag) {
			this.flag = flag;
		}
		
		
	}
	@HandlerResponseType(responsecode="UG0002",transcode="GU0002")
	public static class Response extends BaseResponse{
		long id;
		int state;
		int flag;
		int userType;
        String description;
		
		public int getFlag() {
			return flag;
		}
		public void setFlag(int flag) {
			this.flag = flag;
		}
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
		public int getUserType() {
			return userType;
		}
		public void setUserType(int userType) {
			this.userType = userType;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
}
