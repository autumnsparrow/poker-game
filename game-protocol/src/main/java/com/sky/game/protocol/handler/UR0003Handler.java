package com.sky.game.protocol.handler;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UR0003Beans;
import com.sky.game.service.domain.GoldBillboard;
import com.sky.game.service.logic.BillboardService;




/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UR0003", enable = true, namespace = "GameUser")
@Component(value = "UR0003")
public class UR0003Handler extends
		BaseProtocolHandler<UR0003Beans.Request, UR0003Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	BillboardService billboardService;

	public UR0003Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(UR0003Beans.Request req, UR0003Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);	
		int type=3;
		List<GoldBillboard> list=billboardService.pagination(type,req.getPage());
		res.setList(list);
		return ret;
	}
}