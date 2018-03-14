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
import com.sky.game.protocol.commons.GU0019Beans;
import com.sky.game.protocol.commons.GU0019Beans.Request;
import com.sky.game.protocol.commons.GU0019Beans.Response;
import com.sky.game.protocol.util.ConstantCache;
import com.sky.game.service.domain.Flag;
import com.sky.game.service.domain.ReceiveInfo;
import com.sky.game.service.logic.BagService;
import com.sky.game.service.persistence.FlagMapper;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0019", enable = true, namespace = "GameUser")
@Component(value = "GU0019")
public class GU0019Hander  extends BaseProtocolHandler<GU0019Beans.Request, GU0019Beans.Response>{

	/**
	 * 
	 */
	@Autowired
	BagService bagService;
	@Autowired
	FlagMapper flagMapper;
	
	public GU0019Hander() {
		// TODO Auto-generated constructor stub
	}
	 @HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		 boolean ret = super.handler(req, res);
		 int state=-1;
		 String description="奖品领取失败";
		 long userId=BasePlayer.getPlayer(req).getUserId();
		 /*long id=req.getId();
		 String url="exchange://"+id;
		 GameUtil.gameLife(url,2*3600*1000L,this,"f",id,userId);*/
		 Flag flag=flagMapper.selectByUserId(userId);
		 if(flag!=null){
		 state=ConstantCache.selectValidate(req.getPhone(), req.getValidate());
		 if(state>0){
		 ReceiveInfo record=new ReceiveInfo();
		 record.setPhone(req.getPhone());
		 record.setItemId(req.getItemId());
		 record.setUserId(BasePlayer.getPlayer(req).getUserId());
		 record.setUserItemsId(req.getId());
		 record.setState(1);
		 bagService.userDH(record);
		 flagMapper.deleteFlagByUserId(BasePlayer.getPlayer(req).getUserId());
		 description="奖品领取成功";
		 }else{
			 bagService.addPrice(req.getItemId());
			 description="验证码错误，奖品领取失败";
		 }
		 }else{
			 description="奖品领取超时，请在20分钟以内领取奖品";
		 }
		 res.setDescription(description);
		 res.setState(state);
		 return ret;
	}

}
