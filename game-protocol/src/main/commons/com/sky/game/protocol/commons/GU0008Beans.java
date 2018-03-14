package com.sky.game.protocol.commons;
//
//import com.sky.game.context.annotation.HandlerRequestType;
//import com.sky.game.context.annotation.HandlerResponseType;
//import com.sky.game.context.domain.BaseRequest;
//import com.sky.game.service.domain.UserInfo;
//
//public class GU0008Beans {
//
//	/**
//	 * 
//	 */
//	public GU0008Beans() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@HandlerRequestType(transcode = "GU0008")
//	public static class Request extends BaseRequest {
//
//		public Request() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//		
//	}
//
//	@HandlerResponseType(responsecode = "UG0008", transcode = "GU0008")
//	public static class Response {
//
//		public Response() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//		UserInfo userInfo= new UserInfo();
//		public UserInfo getUserInfo() {
//			return userInfo;
//		}
//		public void setUserInfo(UserInfo userInfo) {
//			this.userInfo = userInfo;
//		}
//		public Response(UserInfo userInfo) {
//			super();
//			this.userInfo = userInfo;
//		}
//		@Override
//		public String toString() {
//			return "Response [userInfo=" + userInfo + "]";
//		}
//	}
//
//}

import com.sky.game.context.annotation.HandlerRequestType;
import com.sky.game.context.annotation.HandlerResponseType;
import com.sky.game.context.domain.BaseRequest;
import com.sky.game.service.domain.UserInfo;

public class GU0008Beans {

	/**
	 * 
	 */
	public GU0008Beans() {
		// TODO Auto-generated constructor stub
	}

	@HandlerRequestType(transcode = "GU0008")
	public static class Request extends BaseRequest {

		public Request() {
			super();
			// TODO Auto-generated constructor stub
		}
	}

	@HandlerResponseType(responsecode = "UG0008", transcode = "GU0008")
	public static class Response {

		public Response() {
			super();
			// TODO Auto-generated constructor stub
		}
		UserInfo userInfo= new UserInfo();
		public UserInfo getUserInfo() {
			return userInfo;
		}
		public void setUserInfo(UserInfo userInfo) {
			this.userInfo = userInfo;
		}
		public Response(UserInfo userInfo) {
			super();
			this.userInfo = userInfo;
		}
		@Override
		public String toString() {
			return "Response [userInfo=" + userInfo + "]";
		}
	}

}

