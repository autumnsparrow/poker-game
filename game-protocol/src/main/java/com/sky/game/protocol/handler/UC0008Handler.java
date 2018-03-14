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
import com.sky.game.protocol.commons.UC0008Beans;
import com.sky.game.protocol.commons.UC0008Beans.Request;
import com.sky.game.protocol.commons.UC0008Beans.Response;
import com.sky.game.service.domain.PrivilegeShow;
import com.sky.game.service.logic.UserCenterService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="UC0008",enable=true,namespace="UserCenter")
@Component(value="UC0008")
public class UC0008Handler extends BaseProtocolHandler<UC0008Beans.Request, UC0008Beans.Response>{

	/**
	 * 
	 */
	@Autowired
	UserCenterService userCenterService;
	
	public UserCenterService getUserCenterService() {
		return userCenterService;
	}


	public void setUserCenterService(UserCenterService userCenterService) {
		this.userCenterService = userCenterService;
	}


	public UC0008Handler() {
		// TODO Auto-generated constructor stub
	}
	
	
		@HandlerMethod(enable=true)
		@Override
		public boolean handler(Request req, Response res) throws ProtocolException {
			// TODO Auto-generated method stub
			Boolean ret=super.handler(req, res);
			long userId=BasePlayer.getPlayer(req).getUserId();
			PrivilegeShow privilegeShow=userCenterService.userPrivilege(userId);
			res.setPrivilegeShow(privilegeShow);
			return ret;
		}
}
