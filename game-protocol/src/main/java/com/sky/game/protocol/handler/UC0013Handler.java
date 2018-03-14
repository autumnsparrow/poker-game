/**
 * 
 */
package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0013Beans;
import com.sky.game.protocol.commons.UC0013Beans.Request;
import com.sky.game.protocol.commons.UC0013Beans.Response;
import com.sky.game.service.domain.UserHead;
import com.sky.game.service.logic.UserCenterService;
import com.sky.game.service.persistence.UserExtraMapper;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="UC0013",enable=true,namespace="UserCenter")
@Component(value="UC0013")
public class UC0013Handler extends BaseProtocolHandler<UC0013Beans.Request, UC0013Beans.Response>{
	/**
	 * 
	 */
	public UC0013Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserCenterService userCenterService;
	@Autowired
	UserExtraMapper userExtraMapper;
	
	public UserCenterService getUserCenterService() {
		return userCenterService;
	}

	public void setUserCenterService(UserCenterService userCenterService) {
		this.userCenterService = userCenterService;
	}
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		UserHead userHead=userCenterService.updaeHead(userId);
		res.setUserHead(userHead);
		return ret;
	}
	
	/*@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		UserHead userHead=userCenterService.updaeHead(BasePlayer.getPlayer(req).getUserId());
		res.setUserHead(userHead);
		return ret;
	}*/
}
