/**
 * 
 */
package com.sky.game.protocol.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.GU0003Beans;
import com.sky.game.protocol.commons.GU0003Beans.Request;
import com.sky.game.protocol.commons.GU0003Beans.Response;
import com.sky.game.protocol.util.ConstantCache;
import com.sky.game.service.logic.UserAccountService;
import com.sky.game.service.persistence.UserExtraMapper;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="GU0003",enable=true,namespace="GameUser")
@Component(value="GU0003")
public class GU0003Handler extends BaseProtocolHandler<GU0003Beans.Request, GU0003Beans.Response> {

	
	
	
	public GU0003Handler() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	UserAccountService userAccountService;
	@Autowired
	UserExtraMapper userExtraMapper;
	
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret= super.handler(req, res);
		 int state=0;
		 String description="服务异常";
		 String userAccount=null;
		 int a=ConstantCache.selectValidate(req.getPhone(), req.getValidate());
		 if(a==1){//验证码，验证通过
			 userAccount=userExtraMapper.selectUserAccountByPhone(req.getPhone());
			 state=userAccountService.findUserPassWord(req.getPhone(),req.getPassword());
			 if(state==1){
				 description="密码找回成功";
			 }
			 if(state==-2){
				 description="用户手机未绑定";
			 }
		 }else{
			 state=-1;//验证码输入错误 
			 description="验证码输入错误";
		 }
		 res.setAccount(userAccount);
		 res.setPassword(req.getPassword());
		 res.setDescription(description);
		 res.setState(state);
		return ret;
		
	
	}

	
/*	private void testPush(Request req) throws GameProtocolException{
		
    	long chips=200l;
    	String pokerHand="deefbeac";
    	TG0007Beans.Gamebear gamebear=Gamebear.getGamebear(getPlayer(req).getUserId(), chips, pokerHand);
    	MinaEventHandler.getHandler().addPushEvent(new MinaEvent(getPlayer(req).getUserId(),gamebear));
    	
    	//ProtocolGlobalContext.instance().getOnlinePlayer(userId)
    	
    	for(BasePlayer p:ProtocolGlobalContext.instance().getOnlinePlayers()){
    		MinaEventHandler.getHandler().addPushEvent(new MinaEvent(p.getUserId(),gamebear));
    	}
    	
    	//MinaBeans.sendMinaMessage(player.getToken(),player.getDeviceId(), "TG0007", gamebear);
	}*/
	
	
	
	
}
