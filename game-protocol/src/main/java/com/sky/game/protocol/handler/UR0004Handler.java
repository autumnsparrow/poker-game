//package com.sky.game.protocol.handler;
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.sky.game.context.annotation.HandlerMethod;
//import com.sky.game.context.annotation.HandlerType;
//import com.sky.game.context.handler.ProtocolException;
//import com.sky.game.protocol.BaseProtocolHandler;
//import com.sky.game.protocol.commons.UR0001Beans;
//import com.sky.game.protocol.commons.UR0004Beans;
//import com.sky.game.service.domain.GoldBillboard;
//import com.sky.game.service.logic.BillboardService;
//
//
//
//
///**
// * @author Administrator
// *
// */
//@HandlerType(transcode = "UR0004", enable = true, namespace = "GameUser")
//@Component(value = "UR0004")
//public class UR0004Handler extends
//		BaseProtocolHandler<UR0004Beans.Request, UR0004Beans.Response> {
//
//	/**
//	 * 
//	 */
//	@Autowired
//	BillboardService billboardService;
//
//	public UR0004Handler() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@HandlerMethod(enable = true)
//	public boolean handler(UR0004Beans.Request req, UR0004Beans.Response res)
//			throws ProtocolException {
//		// TODO Auto-generated method stub
//		boolean ret = super.handler(req, res);	
//		res.setPage(page);
//		return ret;
//	}
//}