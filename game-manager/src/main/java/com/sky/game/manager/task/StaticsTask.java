/**
 * 
 */
package com.sky.game.manager.task;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.UserDataHandler;

import com.sky.game.manager.activerecord.UserActiveDayliyStatics;
import com.sky.game.manager.mybatis.domain.LoginStatics;
import com.sky.game.manager.mybatis.domain.UserAccountExample;
import com.sky.game.manager.mybatis.persistence.UserAccountMapper;
import com.sky.game.manager.types.UserRegisterTypes;

/**
 * @author Li
 *
 */
@Service
public class StaticsTask {
	
	@Autowired
	UserAccountMapper userAccountMapper;
	
	
	
	public void loginUsers(){
//		UserActiveDayliyStatics userActiveDayliyStatics=new UserActiveDayliyStatics();
//		List<LoginStatics> todayLogins=userAccountMapper.countTodayLogin(Integer.valueOf(2));
//		for(LoginStatics s:todayLogins){
//			
//			//visitor
//			if(s.getUserType().intValue()==0){
//				userActiveDayliyStatics.setDay2ActiveV(s.getTimes().intValue());
//			}else{
//				userActiveDayliyStatics.setDay2Active(s.getTimes().intValue());
//			}
//		}
//		
//		List<LoginStatics> todayLogins3=userAccountMapper.countTodayLogin(Integer.valueOf(3));
//		for(LoginStatics s:todayLogins3){
//			
//			//visitor
//			if(s.getUserType().intValue()==0){
//				userActiveDayliyStatics.setDay3ActiveV(s.getTimes().intValue());
//			}else{
//				userActiveDayliyStatics.setDay3Active(s.getTimes().intValue());
//			}
//		}
//		
//		
//		List<LoginStatics> todayLogins7=userAccountMapper.countTodayLogin(Integer.valueOf(7));
//		for(LoginStatics s:todayLogins7){
//			
//			//visitor
//			if(s.getUserType().intValue()==0){
//				userActiveDayliyStatics.setDay7ActiveV(s.getTimes().intValue());
//			}else{
//				userActiveDayliyStatics.setDay7Active(s.getTimes().intValue());
//			}
//		}
//		
//		List<LoginStatics> todayLogins30=userAccountMapper.countTodayLogin(Integer.valueOf(30));
//		for(LoginStatics s:todayLogins30){
//			
//			//visitor
//			if(s.getUserType().intValue()==0){
//				userActiveDayliyStatics.setDay30ActiveV(s.getTimes().intValue());
//			}else{
//				userActiveDayliyStatics.setDay30Active(s.getTimes().intValue());
//			}
//		}
//		
//		userActiveDayliyStatics.persist();
//		userActiveDayliyStatics.flush();
		
	}
	
	
	
	/**
	 * minute, hour, day of month, month and day of week
	 */
	@Scheduled(cron="0 0 * * * *")
	public void task(){
		
		try{
			loginUsers();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
