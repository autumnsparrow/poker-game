///**
// * 
// */
//package com.sky.game.protocol.commons;
//import com.sky.game.context.annotation.HandlerRequestType;
//import com.sky.game.context.annotation.HandlerResponseType;
//import com.sky.game.context.domain.BaseRequest;
//import com.sky.game.service.domain.UserBasic;
//
///**
// * @author Administrator
// *
// */
//public class UC0001Beans {
//
//	/**
//	 * 
//	 */
//	public UC0001Beans() {
//		// TODO Auto-generated constructor stub
//	}
//	@HandlerRequestType(transcode = "UC0001")
//	public static class Request extends BaseRequest {
//
//		public Request() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//
//	}
//
//	@HandlerResponseType(responsecode = "CU0001", transcode = "UC0001")
//	public static class Response {
//		public Response() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//	UserBasic userBasic;
//
//	public UserBasic getUserBasic() {
//		return userBasic;
//	}
//
//	public void setUserBasic(UserBasic userBasic) {
//		this.userBasic = userBasic;
//	}
//
//	public Response(UserBasic userBasic) {
//		super();
//		this.userBasic = userBasic;
//	}
//
//	@Override
//	public String toString() {
//		return "Response [userBasic=" + userBasic + "]";
//	}
//  }
//}
