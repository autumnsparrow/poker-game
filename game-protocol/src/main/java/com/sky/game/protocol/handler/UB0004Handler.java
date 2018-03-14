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
//import com.sky.game.protocol.commons.UB0004Beans;
//import com.sky.game.service.domain.LoanShow;
//import com.sky.game.service.logic.UserBankService;
//
//
///**
// * @author Administrator
// *
// */
//@HandlerType(transcode = "UB0004", enable = true, namespace = "GameUser")
//@Component(value = "UB0004")
//public class UB0004Handler extends
//		BaseProtocolHandler<UB0004Beans.Request, UB0004Beans.Response> {
//
//	/**
//	 * 
//	 */
//	@Autowired
//	UserBankService userBankService;
//
//	public UB0004Handler() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@HandlerMethod(enable = true)
//	public boolean handler(UB0004Beans.Request req, UB0004Beans.Response res)
//			throws ProtocolException {
//		// TODO Auto-generated method stub
//	   long userId=BasePlayer.getPlayer(req).getUserId();
//		boolean ret = super.handler(req, res);
//		LoanShow loanShow=userBankService.selectBankLoan(userId);
//		res.setLoanShow(loanShow);
//		return ret;
//	}
//}
