package com.sky.game.protocol.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0026Beans;
import com.sky.game.protocol.commons.GU0026Beans.Request;
import com.sky.game.protocol.commons.GU0026Beans.Response;
import com.sky.game.service.domain.AchievementShow;
import com.sky.game.service.logic.AchievementService;

@HandlerType(transcode ="GU0026", enable =true, namespace ="GameUser")
@Component(value ="GU0026")
public class GU0026Handler   extends BaseProtocolHandler<GU0026Beans.Request, GU0026Beans.Response>{


	public GU0026Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	AchievementService achievementService;
	
	
	@HandlerMethod(enable=true)
	public boolean handler(Request req,Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=BasePlayer.getPlayer(req).getUserId();
		List<AchievementShow> achievementShowList=achievementService.achievementShowList(userId);
	    res.setAchementList(achievementShowList);
		return true;
	}
}
