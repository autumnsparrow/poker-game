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
import com.sky.game.protocol.commons.UC0016Beans;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.logic.UserCenterService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode ="UC0016", enable = true, namespace = "UserCenter")
@Component(value = "UC0016")
public class UC0016Handler  extends BaseProtocolHandler<UC0016Beans.Request, UC0016Beans.Response>{

	/**
	 * 
	 */
	public UC0016Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserCenterService userCenterService;
	
	@HandlerMethod(enable = true)
	public boolean handler(UC0016Beans.Request req, UC0016Beans.Response res)
			throws ProtocolException {
		boolean ret = super.handler(req, res);
		String phone=null;
		String mail=null;
	    int userType;
		String bankPassword=null;
		long userId=BasePlayer.getPlayer(req).getUserId();
        UserAccount userAccount=userCenterService.selectUserAccountByUserId(userId);
        Map<PropertyTypes,UserExtra> propertiesMap=userAccount.getPropertiesAsMap();
        String password=userAccount.getUserPassword();
        if(propertiesMap.get(PropertyTypes.Phone)!=null){
        	phone=propertiesMap.get(PropertyTypes.Phone).getPropertyValue();
        }else{
        	phone="首次绑定手机号赠送5000金币";
        }
        if(propertiesMap.get(PropertyTypes.Mail)!=null){
        	mail=propertiesMap.get(PropertyTypes.Mail).getPropertyValue();
        }
        bankPassword=userCenterService.selectUserBankPasswordByUserId(userId);
        res.setBanikPassword(bankPassword);
        res.setMail(mail);
        res.setPassword(password);
        res.setPhone(phone);
        int flag=0;
        if(userAccount.getUserType()==0){
        	flag=2;//表示游客
        	userType=0;
        }else{
        	flag=1;//表示正式
        	userType=1;
        }
        res.setFlag(flag);
       // res.setUserType(userType);
		return ret;
	}
}
