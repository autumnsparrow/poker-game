/**
 * 
 */
package com.sky.game.protocol.handler;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.GameProtocolException;
import com.sky.game.protocol.ProtocolGlobalContext;
import com.sky.game.protocol.commons.GU0002Beans;
import com.sky.game.protocol.commons.GU0002Beans.Request;
import com.sky.game.protocol.commons.GU0002Beans.Response;

import com.sky.game.service.ServiceException;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.logic.UserAccountService;
import com.sky.game.service.logic.UserTokenService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode ="GU0002", enable =true, namespace ="GameUser")
@Component(value = "GU0002")
public class GU0002Handler implements
		IProtocolHandler<GU0002Beans.Request, GU0002Beans.Response> {
	@Autowired
	UserAccountService userAccountService;

	@Autowired
	UserTokenService userTokenService;

	
	/**
	 * 无参构造方法，必须加
	 */
	public GU0002Handler() {
		// TODO Auto-generated constructor stub
	}

	
	@HandlerMethod(enable = true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		long userId = 0L;
		String token = null;
		int state=0;
		int flag=0;
		int userType=0;
		String description="账号不存在";
		String deviceId=req.getDeviceId();
		state=userAccountService.selectUserAccountByName(req.getAccount());
		
		if(state==1){
		try {
 		  UserAccount useraccount = userAccountService.login(req.getAccount(),req.getPassword());
		  userType=useraccount.getUserType();
		    userId=useraccount.getId();
		   BasePlayer basePalyer=ProtocolGlobalContext.instance().getOnlinePlayer(userId);
		    if(basePalyer!=null){
		    	if(useraccount.getDeviceId().equalsIgnoreCase(deviceId)){
		    	if(userType==1){
					HashMap<String,Object> hashmap=new HashMap<String,Object>();
					hashmap.put("id",userId);
					hashmap.put("deviceId",deviceId);
					userAccountService.updateDeviceId(hashmap);
					try {
						UserExtra ue=userAccountService.login(req.getAccount(),req.getPassword()).getPropertiesAsMap().get(PropertyTypes.Vip);
					    if(ue==null){
					    	userType=1;
					    }else{
					    	if(ue.getPropertyValue().equals("1")){
					    		userType=2;
					    	}else{
					    		userType=1;
					    	}
					    }
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		    	token = userTokenService.generateToken(userId);
		    	BasePlayer player=new BasePlayer(userId, token, req.getDeviceId());
		    	ProtocolGlobalContext.instance().addOnlinePlayer(player);
		    	flag=userAccountService.DefaultFlag(req.getAccount(),req.getPassword());
		    	
		    	state=1;
		    	}else{
			    	state=-3;
			    	description="此账号已在别的设备上登录";
			    }
		    }else{
		    	HashMap<String,Object> hashmap=new HashMap<String,Object>();
				hashmap.put("id",userId);
				hashmap.put("deviceId",deviceId);
				userAccountService.updateDeviceId(hashmap);
				try {
					UserExtra ue=userAccountService.login(req.getAccount(),req.getPassword()).getPropertiesAsMap().get(PropertyTypes.Vip);
				    if(ue==null){
				    	userType=0;
				    }else{
				    	if(ue.getPropertyValue().equals("1")){
				    		userType=2;
				    	}else{
				    		userType=1;
				    	}
				    }
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				token = userTokenService.generateToken(userId);
				BasePlayer player=new BasePlayer(userId, token, req.getDeviceId());
		    	ProtocolGlobalContext.instance().addOnlinePlayer(player);
		    	flag=userAccountService.DefaultFlag(req.getAccount(),req.getPassword());
		    	description="登录成功";
		    	state=1;
		    }
		} catch (ServiceException e) {
			state=-2;
			description="密码错误";
			new ProtocolException(e.getState(), "password error");
		} catch (GameProtocolException e) {
			e.printStackTrace();
		}
		}
		
	    res.setFlag(flag);
		res.setId(userId);
		res.setUserType(userType);
		res.setState(state);
		res.setToken(token);
		res.setDescription(description);
		return true;
	}
}
