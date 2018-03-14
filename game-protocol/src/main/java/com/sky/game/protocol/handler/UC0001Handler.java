//package com.sky.game.protocol.handler;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.sky.game.context.annotation.HandlerMethod;
//import com.sky.game.context.annotation.HandlerType;
//import com.sky.game.context.handler.ProtocolException;
//import com.sky.game.protocol.BasePlayer;
//import com.sky.game.protocol.BaseProtocolHandler;
//import com.sky.game.protocol.commons.UC0001Beans;
//import com.sky.game.service.domain.UserBasic;
//import com.sky.game.service.logic.UserCenterService;
//
//
//
///**
// * @author Administrator
// *
// */
//@HandlerType(transcode = "UC0001", enable = true, namespace = "GameUser")
//@Component(value = "UC0001")
//public class UC0001Handler extends
//		BaseProtocolHandler<UC0001Beans.Request, UC0001Beans.Response> {
//
//	/**
//	 * 
//	 */
//	@Autowired
//	UserCenterService userCenterService;
//
//	public UC0001Handler() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@HandlerMethod(enable = true)
//	public boolean handler(UC0001Beans.Request req, UC0001Beans.Response res)
//			throws ProtocolException {
//		// TODO Auto-generated method stub
//		long userId=BasePlayer.getPlayer(req).getUserId();
//		boolean ret = super.handler(req, res);
//		UserBasic userBasic=userCenterService.selectUserBasic(userId);
//		res.setUserBasic(userBasic);
//		return ret;
//
//	}
//}
