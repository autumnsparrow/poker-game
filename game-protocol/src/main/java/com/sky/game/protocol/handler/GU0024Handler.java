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
import com.sky.game.protocol.commons.GU0024Beans;
import com.sky.game.protocol.commons.GU0024Beans.Request;
import com.sky.game.protocol.commons.GU0024Beans.Response;
import com.sky.game.service.domain.DefaultHead;
import com.sky.game.service.logic.UserAccountService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0024", enable = true, namespace = "GameUser")
@Component(value = "GU0024")
public class GU0024Handler  extends BaseProtocolHandler<GU0024Beans.Request, GU0024Beans.Response>{

	/**
	 * 
	 */
	@Autowired
	UserAccountService userAccountService;
	public GU0024Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(Request req,Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		List<DefaultHead> iconList=userAccountService.userDefaultValue();
		res.setIconList(iconList);
		return true;
	}
}
