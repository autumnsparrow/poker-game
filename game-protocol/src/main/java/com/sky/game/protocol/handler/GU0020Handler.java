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
import com.sky.game.protocol.commons.GU0020Beans;
import com.sky.game.protocol.commons.GU0020Beans.Request;
import com.sky.game.protocol.commons.GU0020Beans.Response;
import com.sky.game.service.domain.ItemCount;
import com.sky.game.service.domain.UserBank;
import com.sky.game.service.logic.BagService;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "GU0020", enable = true, namespace = "GameUser")
@Component(value = "GU0020")
public class GU0020Handler extends BaseProtocolHandler<GU0020Beans.Request, GU0020Beans.Response>{

	/**
	 * 
	 */
	public GU0020Handler() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	BagService bagService;
	@HandlerMethod(enable=true)
	@Override
	public boolean handler(Request req, Response res) throws ProtocolException {
		// TODO Auto-generated method stub
		boolean ret=super.handler(req, res);
		long itemId=req.getItemId();
		long flagId=req.getId();
		long userId=BasePlayer.getPlayer(req).getUserId();
		int state=-2;
		String description="物品剩余数量为0,不能领取";
		UserBank userBank=bagService.selectUserBank(userId);
		if(userBank!=null && userBank.getLoan()>0){
			state=-1;
			description="您现在有贷款，还款后才可以领取奖品";
		}else{
		ItemCount itemCount=bagService.priceSYValidate(itemId);
		if(itemCount!=null && itemCount.getLeftNum()>0){
			int flagByflagId=bagService.countByFlagId(flagId);//根据物品列表id判断此物品是否被锁定
			if(flagByflagId==1){
				description="请领取";
			    state=2;
			}else{
			int flag=bagService.CountByUserId(userId);//一个用户只能锁定一个物品
			if(flag==0){
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("itemId", itemId);
			map.put("userId",BasePlayer.getPlayer(req).getUserId());
			map.put("flagId",flagId);
			bagService.insertFlag(map);
			state=1;
			description="锁定成功";
			}else{
				bagService.f1(userId);
				HashMap<String,Object> map=new HashMap<String,Object>();
				map.put("itemId", itemId);
				map.put("userId",BasePlayer.getPlayer(req).getUserId());
				map.put("flagId",flagId);
				bagService.insertFlag(map);
				state=1;
				description="锁定成功";
				/*state=-1;
				description="你还有未领取的物品，请领取";*/
			}
			}
		}
		}
		res.setDescription(description);
		res.setId(req.getId());
		res.setState(state);
		return ret;
	}
	
}
