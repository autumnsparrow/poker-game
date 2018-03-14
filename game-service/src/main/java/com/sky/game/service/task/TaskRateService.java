package com.sky.game.service.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.domain.Task;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserTask;
import com.sky.game.service.persistence.TaskMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserRewardMapper;
import com.sky.game.service.persistence.UserTaskMapper;
@Service
public class TaskRateService {

@Autowired
TaskMapper taskMapper;
@Autowired
UserTaskMapper userTaskMapper;
@Autowired
UserRewardMapper userRewardMapper;
@Autowired
UserAccountMapper userAccountMapper;

	
	public TaskRateService() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void updateTaskRate(long userId,int t,int rate){
		
		Date fDate = new Date();
		UserAccount userAccount = userAccountMapper.selectByPrimaryId(userId);
		Date oDate = userAccount.getCreateTime();
		long channelId = userAccount.getChannelId();
		 Calendar aCalendar = Calendar.getInstance();
	       aCalendar.setTime(fDate);
	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       aCalendar.setTime(oDate);
	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);  
        int lll=day1-day2;
		if(t==41 && lll>7 && lll<8){
			updateTask(userId,t,rate);
		}else if(t==40 && lll>6 && lll<7 ){
			updateTask(userId,t,rate);
		}else if(t==39 && lll>5 && lll<6 ){
			updateTask(userId,t,rate);
		}else if(t==38 && lll>4 && lll<3 ){
			updateTask(userId,t,rate);
		}else if(t==37 && lll>3 && lll<2 ){
			updateTask(userId,t,rate);
		}else if(t==36 && lll>2 && lll<1 ){
			updateTask(userId,t,rate);
		}else if(t==32 && lll<1 ){
			updateTask(userId,t,rate);
		}
		
		if(t==12 || t==15){
			Date date = userAccountMapper.selectByPrimaryId(userId).getCreateTime();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");  
			String str=sdf.format(date);  
			Date date1 = new Date();
			String str1=sdf.format(date1);
			long time1 =Long.parseLong(str)+7*24*24*60*60;
			long time2 = Long.parseLong(str1);
			if(time2<time1){
				updateTask(userId,t,rate);
			}
		}
		else if(channelId!=2000){
			updateTask(userId,t,rate);
		}
	}
	
	public void updateTask(long userId,int t,int rate){
		long type=(long)t;
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("type", type);
		map.put("rate", rate);
		UserTask userTask = null;
		int count=0;
		if(userTaskMapper.selectByUserId(map)!=null){
			userTask=userTaskMapper.selectByUserId(map);
			count=rate+userTask.getState();
		}
		Task task=taskMapper.selectByPrimaryKey(type);
		if(userTask!=null ){
			if(task.getRate()>userTask.getState()){
			userTaskMapper.updateTaskRate(map);
			}
		}
		else{
		userTaskMapper.insertTaskRate(map);
		}
		long rewardId = taskMapper.selectRewardId(type);
		HashMap<String,Object> map1 = new HashMap<String,Object>();
		map1.put("userId", userId);
		map1.put("rewardId",rewardId);
		if(userRewardMapper.selectByUserId(map1)==null){
			userRewardMapper.insertUserReward(map1);
		}
		
	}
	
	
	
	
	
}




//2000%E9%87%91%E5%B8%81
