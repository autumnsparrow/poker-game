/**
 * 
 */
package com.sky.game.service.logic;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.domain.SysActivity;
import com.sky.game.service.persistence.ActivityMapper;
import com.sky.game.service.persistence.UserActivityMapper;
import com.sky.game.service.persistence.UserItemsMapper;

/**
 * @author Administrator
 *
 */
@Service
public class ActivityService {
	@Autowired
	UserItemsMapper userItemsMapper;
	@Autowired
	UserActivityMapper userActivityMapper;

	private static final Log logger = LogFactory.getLog(ActivityService.class);
	
	/**
	 * 活动展示
	 */
	@Autowired
	ActivityMapper activityMapper;
	
	public ActivityService() {
		// TODO Auto-generated constructor stub
	}
  
	public List<SysActivity> selectActivity(long userId){
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		int s=Integer.parseInt(sdf.format(date));  
		List<SysActivity> list = activityMapper.selectActivity();
		for(int i=0;i<list.size();i++){
			int ss=Integer.parseInt(list.get(i).getStartTime().replaceAll("-",""));
			int sss=Integer.parseInt(list.get(i).getStopTime().replaceAll("-",""));
			if(ss<s && s<sss && userActivityMapper.selectByUserId(userId)==null){
				list.get(i).setState(2);
			}else{
				list.get(i).setState(1);
			}
		}
		
		return list;
		
		
	}
	
    public HashMap<String,Object> insertActivity(long userId){
    	int state=-1;
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	if(userActivityMapper.selectByUserId(userId)==null){
    		userItemsMapper.updateActivity1(userId);
    		if(userItemsMapper.selectActivity2(userId)!=null){
    			userItemsMapper.updateActivity2(userId);
    		}else{
    			userItemsMapper.insertActivity2(userId);
    		}
    		if(userItemsMapper.selectActivity3(userId)!=null){
    			userItemsMapper.updateActivity3(userId);
    		}else{
    			userItemsMapper.insertActivity3(userId);
    		}
    		if(userItemsMapper.selectActivity4(userId)!=null){
    			userItemsMapper.updateActivity4(userId);
    		}else{
    			userItemsMapper.insertActivity4(userId);
    		}
    		if(userItemsMapper.selectActivity5(userId)!=null){
    			userItemsMapper.updateActivity5(userId);
    		}else{
    			userItemsMapper.insertActivity5(userId);
    		}
    		if(userItemsMapper.selectActivity6(userId)!=null){
    			userItemsMapper.updateActivity6(userId);
    		}else{
    			userItemsMapper.insertActivity6(userId);
    		}
    		if(userItemsMapper.selectActivity7(userId)!=null){
    			userItemsMapper.updateActivity7(userId);
    		}else{
    			userItemsMapper.insertActivity7(userId);
    		}
        	userActivityMapper.insertUserActivity(userId);
        	state=1;
        	map.put("state", state);
        	map.put("description", "恭喜您获得了5000金币，绿宝石×10，蓝宝石×2，红宝石×1，水晶×1，蓝水晶×1，紫水晶×1,物品已存入背包，请查收");
    	}else{
    		map.put("state", state);
        	map.put("description", "领取失败");
    	}
    	
    	return map;
    }
	
	
	
}
