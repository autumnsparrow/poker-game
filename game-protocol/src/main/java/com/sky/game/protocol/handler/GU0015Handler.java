package com.sky.game.protocol.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0015Beans;
import com.sky.game.service.domain.MyGood;
import com.sky.game.service.logic.BagService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0015", enable = true, namespace = "GameUser")
@Component(value = "GU0015")
public class GU0015Handler extends
		BaseProtocolHandler<GU0015Beans.Request, GU0015Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	BagService bagService;

	public GU0015Handler() {
		// TODO Auto-generated constructor stub
	}

	@HandlerMethod(enable = true)
	public boolean handler(GU0015Beans.Request req, GU0015Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=BasePlayer.getPlayer(req).getUserId();
		boolean ret = super.handler(req, res);
		List<MyGood> exlist = bagService.selectMyGood(userId);
		res.setList(exlist);
		return ret;

//	}
	}
}
