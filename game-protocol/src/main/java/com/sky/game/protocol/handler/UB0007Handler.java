//package com.sky.game.protocol.handler;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import com.sky.game.context.annotation.HandlerMethod;
//import com.sky.game.context.annotation.HandlerType;
//import com.sky.game.context.handler.ProtocolException;
//import com.sky.game.protocol.BasePlayer;
//import com.sky.game.protocol.BaseProtocolHandler;
//import com.sky.game.protocol.commons.UB0007Beans;
//import com.sky.game.service.logic.UserBankService;
//
//
//
///**
// * @author Administrator
// *
// */
//@HandlerType(transcode = "UB0007", enable = true, namespace = "GameUser")
//@Component(value = "UB0007")
//public class UB0007Handler extends
//		BaseProtocolHandler<UB0007Beans.Request, UB0007Beans.Response> {
//
//	/**
//	 * 
//	 */
//	@Autowired
//	UserBankService userBankService;
//
//	public UB0007Handler() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@HandlerMethod(enable = true)
//	public boolean handler(UB0007Beans.Request req, UB0007Beans.Response res)
//			throws ProtocolException {
//		// TODO Auto-generated method stub
//	   long userId=BasePlayer.getPlayer(req).getUserId();
//		boolean ret = super.handler(req, res);	
//		int state=userBankService.setBankPw(userId, req.getBankPw());
//		res.setState(state);
//		return ret;
//	}
//}
