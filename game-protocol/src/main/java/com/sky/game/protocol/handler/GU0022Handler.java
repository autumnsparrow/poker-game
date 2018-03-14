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
import com.sky.game.protocol.commons.GU0022Beans;
import com.sky.game.protocol.commons.GU0022Beans.Request;
import com.sky.game.protocol.commons.GU0022Beans.Response;
import com.sky.game.service.logic.UserAccountService;
import com.sky.game.service.util.GameServiceUtil;
import com.sky.game.service.util.GameServiceUtil.SexTypes;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0022", enable = true, namespace = "GameUser")
@Component(value = "GU0022")
public class GU0022Handler extends BaseProtocolHandler<GU0022Beans.Request, GU0022Beans.Response>{

	/**
	 * 
	 */
	@Autowired
	UserAccountService userAccountService;
	public GU0022Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable=true)
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		int flag=req.getFlag();
		//XmlDom4J xml=new XmlDom4J();
		//String name=xml.getChinaName(flag);
		String name=null;
		try {
			name = GameServiceUtil.getChinaName(SexTypes.getSexTypes(flag));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    res.setName(name);
		return true;
	}
	
	
	
}
