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
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0009Beans;
import com.sky.game.protocol.commons.UC0009Beans.Request;
import com.sky.game.protocol.commons.UC0009Beans.Response;
import com.sky.game.service.domain.Detail;
import com.sky.game.service.logic.UserCenterService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="UC0009",enable=true,namespace="UserCenter")
@Component(value="UC0009")
public class UC0009Handler extends BaseProtocolHandler<UC0009Beans.Request, UC0009Beans.Response>{

	/**
	 * 
	 */
	public UC0009Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserCenterService userCenterService;
	
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		List<Detail> detailList=userCenterService.selectUserCheck(BasePlayer.getPlayer(req).getUserId(),req.getPage());
		res.setDetailList(detailList);
		return ret;
	}
}
