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
import com.sky.game.protocol.commons.UC0019Beans;
import com.sky.game.service.logic.UserCenterService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UC0019", enable = true, namespace = "UserCenter")
@Component(value = "UC0019")
public class UC0019Handler extends BaseProtocolHandler<UC0019Beans.Request, UC0019Beans.Response>{

	/**
	 * 
	 */
	public UC0019Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserCenterService userCenterService;
	
	@HandlerMethod(enable = true)
	public boolean handler(UC0019Beans.Request req, UC0019Beans.Response res)
			throws ProtocolException {
		boolean ret = super.handler(req, res);
		String url=req.getUrl();
		long userId=BasePlayer.getPlayer(req).getUserId();
		int state=0;
		state=userCenterService.updateDefaultHead(userId, url);
		   res.setState(state);
		   res.setUrl(url);
		   if(state==-1){
			   res.setDescription("您还没有购买此头像");
		   }else{
			   res.setDescription("头像修改成功");
		   }
		return ret;
	}
}
