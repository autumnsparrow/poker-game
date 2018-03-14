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
import com.sky.game.protocol.commons.UC0014Beans;
import com.sky.game.protocol.commons.UC0014Beans.Request;
import com.sky.game.protocol.commons.UC0014Beans.Response;
import com.sky.game.service.logic.UserCenterService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="UC0014",enable=true,namespace="UserCenter")
@Component(value="UC0014")
public class UC0014Handler  extends BaseProtocolHandler<UC0014Beans.Request, UC0014Beans.Response>{

	/**
	 * 
	 */
	public UC0014Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserCenterService userCenterService;
	
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
		long id=req.getId();
		long userId=BasePlayer.getPlayer(req).getUserId();
		
		int state=userCenterService.purchaseHead(userId, id);
		if(state==-1){
			res.setDescription("sql错误");
			res.setState(state);
		}
		if(state==-2){
			res.setDescription("金币不足");
			res.setState(state);
		}
		/*if(state==-3){
			res.setDescription("vip用户过期");
			res.setState(state);
		}*/
		if(state==-4){
			res.setDescription("非VIP 用户不能购买头像");
			res.setState(state);
		}
		if(state==1){
			res.setDescription("购买成功");
			res.setState(state);
		}
		return ret;
	}
	
}
