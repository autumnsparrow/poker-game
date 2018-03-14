/**
 * 
 */
package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0007Beans;
import com.sky.game.protocol.commons.UC0007Beans.Request;
import com.sky.game.protocol.commons.UC0007Beans.Response;
import com.sky.game.service.domain.Honor;
import com.sky.game.service.logic.UserCenterService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="UC0007",enable=true,namespace="UserCenter")
@Component(value="UC0007")
public class UC0007Handler extends BaseProtocolHandler<UC0007Beans.Request, UC0007Beans.Response>{

	/**
	 * 
	 */
	public UC0007Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserCenterService userCenterService;
	
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		Honor honor=userCenterService.userHonor(getPlayer(req).getUserId());
		res.setHonor(honor);
		return ret;
	}

	

}
