/**
 * 
 */
package com.sky.game.protocol.handler;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0010Beans;
import com.sky.game.protocol.commons.UC0010Beans.Request;
import com.sky.game.protocol.commons.UC0010Beans.Response;
import com.sky.game.service.logic.UserCenterService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="UC0010",enable=true,namespace="UserCenter")
@Component(value="UC0010")
public class UC0010Handler  extends BaseProtocolHandler<UC0010Beans.Request, UC0010Beans.Response>{

	/**
	 * 
	 */
	public UC0010Handler() {
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
		String password=req.getPassword();
		//String confirm=req.getConfirm();
		String description=null;
		int state=-1;
		if(!"".equals(password)&& password!=null){
			//if(password.equals(confirm)){
				HashMap<String,Object> hashmap=new HashMap<String,Object>();
				hashmap.put("password", password);
				hashmap.put("userId", BasePlayer.getPlayer(req).getUserId());
				state=userCenterService.userUpdatePassword(hashmap);
				description="修改成功";
			/*	
			}else{
				description="Sql修改失败";
			}*/
		}else{
			description="Sql修改失败";
		}
		res.setDescription(description);
		res.setState(state);
		return ret;
	}

}
