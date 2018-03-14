package com.sky.game.protocol.handler;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0015Beans;
import com.sky.game.service.domain.Certificate;
import com.sky.game.service.logic.UserCenterService;



/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UC0015", enable = true, namespace = "UserCenter")
@Component(value = "UC0015")
public class UC0015Handler extends
		BaseProtocolHandler<UC0015Beans.Request, UC0015Beans.Response> {

	/**
	 * 
	 */
	@Autowired
	UserCenterService userCenterService;

	public UC0015Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable = true)
	public boolean handler(UC0015Beans.Request req, UC0015Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret = super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		int page=req.getPage();
		List<Certificate> certificateLis=userCenterService.selectCertificate(userId,page);
		res.setList(certificateLis);
		return ret;
	}
}
