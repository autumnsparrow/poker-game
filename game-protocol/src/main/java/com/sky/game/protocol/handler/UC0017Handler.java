/**
 * 
 */
package com.sky.game.protocol.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.game.context.annotation.HandlerMethod;
import com.sky.game.context.annotation.HandlerType;
import com.sky.game.context.handler.ProtocolException;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.BaseProtocolHandler;
import com.sky.game.protocol.commons.UC0017Beans;
import com.sky.game.protocol.util.ConstantCache;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserAchievement;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.logic.UserCenterService;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserAchievementMapper;
import com.sky.game.service.persistence.UserExtraMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;

/**
 * @author Administrator
 *
 */
@HandlerType(transcode = "UC0017", enable = true, namespace = "UserCenter")
@Component(value = "UC0017")
public class UC0017Handler   extends BaseProtocolHandler<UC0017Beans.Request, UC0017Beans.Response>{

	/**
	 * 
	 */
	@Autowired
	UserCenterService userCenterService; 
	@Autowired
	UserExtraMapper	userExtraMapper;
	@Autowired
	UserItemsMapper userItemsMapper;
	@Autowired
	UserAccountMapper userAccountMapper;
	@Autowired
	UserItemLogMapper userItemLogMapper;
	@Autowired
	UserAchievementMapper userAchievementMapper;
	/*@Autowired
	SequenceService sequenceService;*/
	public UC0017Handler() {
		// TODO Auto-generated constructor stub
	}
	@HandlerMethod(enable = true)
	public boolean handler(UC0017Beans.Request req, UC0017Beans.Response res)
			throws ProtocolException {
		 boolean ret = super.handler(req, res);
		 int state=0;
		 String description=null;
		 Map<String,Boolean> sList=new HashMap<String,Boolean>();//创建一个存放所有用户手机号码的List
		 List<UserExtra> userExtraList =userExtraMapper.selectAllPhone();
		 if(userExtraList!=null){
			 for(UserExtra ua:userExtraList){
				 String p=ua.getPropertyValue();
				 sList.put(p,false);
			 }
		 }
		 UserAccount userAccount=userCenterService.selectUserAccountByUserId(BasePlayer.getPlayer(req).getUserId());
	        Map<PropertyTypes,UserExtra> propertiesMap=userAccount.getPropertiesAsMap();
		 int a=ConstantCache.selectValidate(req.getPhone(), req.getValidate());
		 if(a==1){//验证通过
			 if(userExtraMapper.selectUserExtraByPhone(req.getPhone())==null){//一个账号只能对应一个手机号
			 if(propertiesMap.get(PropertyTypes.Phone)==null){//如果，用户的手机号码为空   插入
				 if(sList.get(req.getPhone())==null){
					 UserItems userItems=userItemsMapper.selectByUserId(userAccount.getId());
						//添加购买头像   日志  start
			    	    	UserItemLog userItemLog=new UserItemLog();
//			    	    	Long id=sequenceService.generateUserItemLogId();
//			    	    	userItemLog.setId(id);
			    	    	userItemLog.setItemId(userItems.getItemId());
			    	    	userItemLog.setValue(5000);
			    	    	userItemLog.setItemValue(userItems.getItemValue());
			    	    	userItemLog.setResumType("手机绑定");
			    	    	userItemLog.setUserAccountId(userAccount.getId());
			    	    	userItemLog.setUpdateTime(new Date());
			    	    	userItemLogMapper.insertSelective(userItemLog);
			    	    	//end
				 HashMap<String,Object> map=new HashMap<String,Object>();
				 map.put("goldAdd",5000);
				 map.put("userId",userAccount.getId());
				 userItemsMapper.updateUserGold(map);//首次绑定手机送5000金币

	    	    //添加成就  手机绑定
	    	    	UserAchievement record=new UserAchievement();
	    	    	record.setState(1);
	    	    	record.setSystemAchievementId(11L);
	    	    	record.setUserAccountId(BasePlayer.getPlayer(req).getUserId());
	    	    	record.setValue(1);
	    	    	record.setType(88);
	    			userAchievementMapper.insertSelective(record);
	    			//end
				 UserExtra u=new UserExtra();
				 u.setPropertyId(12L);
				 u.setPropertyValue(req.getPhone());
				 u.setUserAccountId(BasePlayer.getPlayer(req).getUserId());
				 userExtraMapper.insert(u);
				 state=1;
				 description="手机绑定成功";
				 }else{
					 state=-2;//手机号码已存在
					 description="手机号码已存在";
				 }
			 }
		    else{//修改   
		    	if(sList.get(req.getPhone())==null){
			 UserExtra u=new UserExtra();
			 u.setId(propertiesMap.get(PropertyTypes.Phone).getId());
			 u.setUserAccountId(BasePlayer.getPlayer(req).getUserId());
			 u.setPropertyValue(req.getPhone());
			 u.setPropertyId(propertiesMap.get(PropertyTypes.Phone).getPropertyId());
			 userExtraMapper.updateByPrimaryKey(u);
			 state=1;
			 description="手机绑定成功";
		    	}else{
		    		state=-2;
		    		description="手机号码已存在";
		    	}
		    }
			 }else{
				 state=-3;//手机号码已经绑定别的账号
				 description="手机号码已经绑定到别的账号，请换手机号码";
			 }
		 }else{
			 state=-1;
			 description="验证码输入错误";
		 }
		res.setState(state);
		res.setDescription(description);
	  return ret;	
	}
}
