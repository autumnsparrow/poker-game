/**
 * 
 */
package com.sky.game.protocol.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.IProtocolHandler;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.commons.GU0030Beans;
import com.sky.game.protocol.commons.GU0030Beans.Request;
import com.sky.game.protocol.commons.GU0030Beans.Response;
import com.sky.game.service.ServiceException;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.logic.SequenceService;
import com.sky.game.service.logic.UserAccountService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode="GU0030",enable=true,namespace="GameUser")
@Component(value="GU0030")
public class GU0030Handler  implements IProtocolHandler<GU0030Beans.Request, GU0030Beans.Response>{

	/**
	 * 
	 */
	public GU0030Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	SequenceService sequenceService;
	@Autowired
	UserAccountService userAccountService;
	
	@HandlerMethod(enable=true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		String account=null;
		String password=null;
		String deviceId=req.getDeviceId();
		UserAccount userAccount=userAccountService.findUserByDeviceId(deviceId);
		if(userAccount!=null){
			account=userAccount.getName();
			password=userAccount.getUserPassword();
		}else{
		try {
			long sequence=sequenceService.generateYouKeId();
			account="youke"+sequence;
			password="96e79218965eb72c92a549dd5a330112";
			long channelId=req.getChanelId();
			userAccountService.register(account, password,channelId,deviceId,0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		res.setAccount(account);
		res.setPassword(password);
		return true;
	}
public static void main(String[] args) {
	System.out.println(new Date().getTime());
}
}
