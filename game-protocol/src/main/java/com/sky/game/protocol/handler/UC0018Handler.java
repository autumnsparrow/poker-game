/**
 * 
 */
package com.sky.game.protocol.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0018Beans;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.logic.UserCenterService;
import com.sky.game.service.persistence.UserExtraMapper;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UC0018", enable = true, namespace = "UserCenter")
@Component(value = "UC0018")
public class UC0018Handler  extends BaseProtocolHandler<UC0018Beans.Request, UC0018Beans.Response>{

	/**
	 * 
	 */
	public UC0018Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserCenterService userCenterService;
	@Autowired
	UserExtraMapper userExtraMapper;
	@HandlerMethod(enable = true)
	public boolean handler(UC0018Beans.Request req, UC0018Beans.Response res)
			throws ProtocolException {
		boolean ret = super.handler(req, res);
		int state;
		 UserAccount userAccount=userCenterService.selectUserAccountByUserId(BasePlayer.getPlayer(req).getUserId());
	        Map<PropertyTypes,UserExtra> propertiesMap=userAccount.getPropertiesAsMap();
		
			 if(propertiesMap.get(PropertyTypes.Mail)==null){//如果，用户邮箱号码为空   插入
			 UserExtra u=new UserExtra();
			 u.setPropertyId(29L);
			 u.setPropertyValue(req.getMail());
			 u.setUserAccountId(BasePlayer.getPlayer(req).getUserId());
			 userExtraMapper.insert(u);
			 state=1;
			 }
		    else{//修改   
			 UserExtra u=new UserExtra();
			 u.setId(propertiesMap.get(PropertyTypes.Mail).getId());
			 u.setUserAccountId(BasePlayer.getPlayer(req).getUserId());
			 u.setPropertyValue(req.getMail());
			 u.setPropertyId(propertiesMap.get(PropertyTypes.Mail).getPropertyId());
			 userExtraMapper.updateByPrimaryKey(u);
			 state=1;
		    }
		   res.setState(state);
		return ret;
	}
}
