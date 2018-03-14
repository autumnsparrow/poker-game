//package com.sky.game.protocol.handler;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.sky.game.context.annotation.HandlerMethod;
//import com.sky.game.context.annotation.HandlerType;
//import com.sky.game.context.handler.ProtocolException;
//import com.sky.game.protocol.BaseProtocolHandler;
//import com.sky.game.protocol.commons.UM0002Beans;
//import com.sky.game.service.logic.AnnounceService;
//
//
//
//
///**
// * @author Administrator
// *
// */
//@HandlerType(transcode = "UM0002", enable = true, namespace = "UserMessage")
//@Component(value = "UM0002")
//public class UM0002Handler extends
//		BaseProtocolHandler<UM0002Beans.Request, UM0002Beans.Response> {
//
//	/**
//	 * 
//	 */
//	@Autowired
//	AnnounceService announceService;
//
//	public UM0002Handler() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@HandlerMethod(enable = true)
//	public boolean handler(UM0002Beans.Request req, UM0002Beans.Response res)
//			throws ProtocolException {
//		// TODO Auto-generated method stub
//		boolean ret = super.handler(req, res);
//		
//		List<Long> ids =req.getList();
//		int state;
//		if(req.getList()!=null){
//			announceService.deleteMessage(ids);
//			state = 1;
//		}
//		else{
//			state=-1;
//		}
//		res.setState(state);
//	return ret;
//	}
//}
