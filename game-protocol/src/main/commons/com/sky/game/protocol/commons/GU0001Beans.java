/**
 * 
 */
package com.sky.game.protocol.commons;

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.context.domain.BaseResponse;

/**
 * @author Administrator
 *
 */
public class GU0001Beans {

	/**
	 * 
	 */
	public GU0001Beans() {
		// TODO Auto-generated constructor stub
	}
	@HandlerRequestType(transcode="GU0001")
	public static class Request  extends BaseRequest{
		//long userId;   //账号中心  启用密码
		long chnalID;
		String account;
		String password;
		String deviceId;
		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
		/*public long getUserId() {
			return userId;
		}
		public void setUserId(long userId) {
			this.userId = userId;
		}*/

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
		
		public long getChnalID() {
			return chnalID;
		}
		public void setChnalID(long chnalID) {
			this.chnalID = chnalID;
		}
		@Override
		public String toString() {
			return "Request [account=" + account + ", password=" + password
					+ "]";
		}

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		
		
	}
	
	@HandlerResponseType(responsecode="UG0001",transcode="GU0001")
	public static class Response extends BaseResponse{
		
		long userId;
        String description;

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}

		public long getUserId() {
			return userId;
		}

		public void setUserId(long userId) {
			this.userId = userId;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	};

}
