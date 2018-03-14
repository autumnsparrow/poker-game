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
import com.sky.game.protocol.commons.GU0028Beans;
import com.sky.game.service.logic.BagService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode ="GU0028", enable =true, namespace ="GameUser")
@Component(value ="GU0028")
public class GU0028Handler extends BaseProtocolHandler<GU0028Beans.Request, GU0028Beans.Response>{

	/**
	 * 
	 */
	public GU0028Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	BagService bagService;
	
	@HandlerMethod(enable = true)
	public boolean handler(GU0028Beans.Request req, GU0028Beans.Response res)
			throws ProtocolException {
		// TODO Auto-generated method stub
			boolean ret = super.handler(req, res);
			long userId=BasePlayer.getPlayer(req).getUserId();
			String cardId=req.getCardId();
			int state=bagService.giftBagGot(cardId, userId);
			String description=null;
			if(state==1){
				description="领取成功";
			}
			if(state==-1){
				description="领取失败";
			}
			if(state==-2){
				description="卡号已使用";
			}
			if(state==-3){
				description="卡号不存在";
			}
            res.setDescription(description);
			res.setState(state);
			return ret;
	}
}
