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
import com.sky.game.protocol.commons.GU0018Beans;
import com.sky.game.protocol.commons.GU0018Beans.Request;
import com.sky.game.protocol.commons.GU0018Beans.Response;
import com.sky.game.service.domain.ReceiveInfo;
import com.sky.game.service.logic.BagService;
import com.sky.game.service.persistence.FlagMapper;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0018", enable = true, namespace = "GameUser")
@Component(value = "GU0018")
public class GU0018Handler extends BaseProtocolHandler<GU0018Beans.Request, GU0018Beans.Response>{

	/**
	 * 
	 */
	@Autowired
	BagService bagService;
	@Autowired
	FlagMapper flagMapper;
	public GU0018Handler() {
		// TODO Auto-generated constructor stub
	}
    @HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
    	boolean ret=super.handler(req, res);
    	int state=-1;
    	String description="奖品领取失败";
    	if(flagMapper.selectByUserId(BasePlayer.getPlayer(req).getUserId())!=null){
		ReceiveInfo record=new ReceiveInfo ();
		record.setUserItemsId(req.getId());
		record.setItemId(req.getItemId());
		record.setUserId(BasePlayer.getPlayer(req).getUserId());
		record.setName(req.getName());
		record.setPhone(req.getPhone());
		record.setDistrict(req.getDistrict());
		record.setDetailed(req.getAddress());
		record.setCode(req.getPostCode());
		record.setState(1);
		bagService.userDH(record);
		flagMapper.deleteFlagByUserId(BasePlayer.getPlayer(req).getUserId());
		state=1;
		description="奖品领取成功";
    	}else{
    	description="奖品领取超时，请在20分钟以内领取奖品";	
    	}
    	res.setDescription(description);
		res.setId(req.getId());
		res.setState(state);
		return ret;
	}
 
}
