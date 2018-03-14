/**
 * 
 */
package com.sky.game.protocol.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.IS0001Beans;
import com.sky.game.protocol.commons.IS0001Beans.Request;
import com.sky.game.protocol.commons.IS0001Beans.Response;
import com.sky.game.service.domain.IndianaStar;
import com.sky.game.service.logic.IndianaStarService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="IS0001",enable=true,namespace="IndianaStar")
@Component(value="IS0001")
public class IS0001Handler   extends BaseProtocolHandler<IS0001Beans.Request, IS0001Beans.Response>{

	/**
	 * 
	 */
	
	public IS0001Handler() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	IndianaStarService indianaStarService;
	@HandlerMethod(enable=true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		List<IndianaStar> indianaStarList=indianaStarService.indianaStarList();
		res.setIndianaStarList(indianaStarList);
		return ret;
	}

}
