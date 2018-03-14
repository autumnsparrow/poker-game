/**
 * 
 */
package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.commons.GU0031Beans;
import com.sky.game.protocol.commons.GU0031Beans.Request;
import com.sky.game.protocol.commons.GU0031Beans.Response;
import com.sky.game.service.logic.SystemChargeCardService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="GU0031",enable=true,namespace="GameUser")
@Component(value="GU0031")
public class GU0031Handler implements IProtocolHandler<GU0031Beans.Request, GU0031Beans.Response>{

	/**
	 * 
	 */
	public GU0031Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	SystemChargeCardService systemChargeCardService; 
	
	@HandlerMethod(enable=true) 
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		systemChargeCardService.produceChargeCard(req.getType(),req.getCount());
		return true;
	}
	
}
