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
import com.sky.game.protocol.commons.GU0027Beans.Request;
import com.sky.game.protocol.commons.GU0027Beans.Response;
import com.sky.game.service.logic.AchievementService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode ="GU0027", enable =true, namespace ="GameUser")
@Component(value ="GU0027")
public class GU0027Handler {

	/**
	 * 
	 */
	public GU0027Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	AchievementService achievementService;
	
	
	@HandlerMethod(enable=true)
	public boolean handler(Request req,Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
		long userId=BasePlayer.getPlayer(req).getUserId();
		String description=null;
		//List<AchievementShow> achievementShowList=achievementService.achievementShowList(userId);
		int state=achievementService.userAchievementGet(userId,req.getId());
		if(state==-1){
			description="领取失败";
		}
		if(state==1){
			description="领取成功";
		}
		res.setDescription(description);
		res.setState(state);
		res.setId(req.getId());
		return true;
	}
}
