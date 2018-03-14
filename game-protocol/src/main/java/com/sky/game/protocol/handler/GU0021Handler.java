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
import com.sky.game.protocol.commons.GU0021Beans;
import com.sky.game.protocol.commons.GU0021Beans.Request;
import com.sky.game.protocol.commons.GU0021Beans.Response;
import com.sky.game.service.domain.AwardRecord;
import com.sky.game.service.logic.BagService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0021", enable = true, namespace = "GameUser")
@Component(value = "GU0021")
public class GU0021Handler  extends BaseProtocolHandler<GU0021Beans.Request, GU0021Beans.Response>{

	/**
	 * 
	 */
	public GU0021Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	BagService bagService;
	
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req,Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		long userId=BasePlayer.getPlayer(req).getUserId();
		List<AwardRecord> awardRecord=bagService.selectAllRewardPrice(userId);
		res.setAwardRecord(awardRecord);
		return ret;
	}
	
	
	
}
