package com.sky.game.service.logic;



import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.Reward;
import com.sky.game.service.domain.Task;
import com.sky.game.service.domain.TaskSet;
import com.sky.game.service.domain.TaskState;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserReward;
import com.sky.game.service.domain.UserTask;
import com.sky.game.service.persistence.RewardMapper;
import com.sky.game.service.persistence.TaskMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;
import com.sky.game.service.persistence.UserRewardMapper;
import com.sky.game.service.persistence.UserTaskMapper;

//import com.sky.game.service.domain.MyGood;


/**
 * @author Administrator
 *
 */
@Service
public class TaskService {
	
	/**
	 *  任务、新手送大礼模块
	 */
@Autowired
TaskMapper taskMapper;

@Autowired
UserRewardMapper userRewardMapper;

@Autowired
RewardMapper rewardmapper;

@Autowired
UserItemsMapper userItemsMapper;

@Autowired
UserTaskMapper userTaskMapper;

@Autowired
UserItemLogMapper userItemLogMapper;

@Autowired
UserAccountMapper userAccountMapper;
	public TaskService() {
		// TODO Auto-generated constructor stub
		String url ="task:"+1;
		GameUtil.gameLife(url, "0 59 23 * * ?",this,"updateTimming");  //每日任务更新
	}
	
	/**
	 *  任务模块展示
	 */
	public List<TaskSet> selectTaskSet1(long userId){
		UserAccount userAccount  = userAccountMapper.selectByPrimaryId(userId);
		long channelId=userAccount.getChannelId();
		if(channelId==1){
			channelId=0;
		}
		List<TaskSet> list = taskMapper.selectTaskSet1(channelId);
		List<TaskState> tr = userTaskMapper.selectUserRate1(userId);
		List<TaskState> tn = userRewardMapper.selectNeckType1(userId);
		for(int i = 0;i<list.size();i++){
			if(tr.get(i)!=null && list.get(i).getId()==tr.get(i).getId()){
				list.get(i).setUserRate(tr.get(i).getUserRate());
			}
			else{
				list.get(i).setUserRate(0);
			}
		}
		for(int i = 0;i<list.size();i++){
			if(tn.get(i)!=null && list.get(i).getId()==tn.get(i).getId()){
				if(tn.get(i).getNeckType()==0){
					list.get(i).setNeckType(2);
				}else{
				list.get(i).setNeckType(tn.get(i).getNeckType());
				}
			}
			else{
				list.get(i).setUserRate(1);
			}
		}
		return list;
	}
	
	/**
	 *  新手送大礼模块展示
	 */
	public List<TaskSet> selectTaskSet2(long userId){
		List<TaskSet> list = taskMapper.selectTaskSet2();
		List<TaskState> tr = userTaskMapper.selectUserRate2(userId);
		List<TaskState> tn = userRewardMapper.selectNeckType2(userId);
		for(int i = 0;i<list.size();i++){
			if(tr.get(i)!=null && list.get(i).getId()==tr.get(i).getId()){
				list.get(i).setUserRate(tr.get(i).getUserRate());
			}
			else{
				list.get(i).setUserRate(0);
			}
		}
		for(int i = 0;i<list.size();i++){
			if(tn.get(i)!=null && list.get(i).getId()==tn.get(i).getId()){
				if(tn.get(i).getNeckType()==0){
					list.get(i).setNeckType(2);
				}else{
				list.get(i).setNeckType(tn.get(i).getNeckType());
				}
			}
			else{
				list.get(i).setUserRate(1);
			}
		}
		return list;
	}
	
	
	public List<TaskSet> selectTaskSet3(long userId){
		List<TaskSet> list = taskMapper.selectTaskSet3();
		List<TaskState> tr = userTaskMapper.selectUserRate3(userId);
		List<TaskState> tn = userRewardMapper.selectNeckType3(userId);
		for(int i = 0;i<list.size();i++){
			if(tr.get(i)!=null && list.get(i).getId()==tr.get(i).getId()){
				list.get(i).setUserRate(tr.get(i).getUserRate());
			}
			else{
				list.get(i).setUserRate(0);
			}
		}
		for(int i = 0;i<list.size();i++){
			if(tn.get(i)!=null && list.get(i).getId()==tn.get(i).getId()){
				if(tn.get(i).getNeckType()==0){
					list.get(i).setNeckType(2);
				}else{
				list.get(i).setNeckType(tn.get(i).getNeckType());
				}
			}
			else{
				list.get(i).setUserRate(1);
			}
		}
		return list;
	}
	
	
	
	/**
	 *  领取奖励
	 */
	@Transactional
	public void updateNeckType(long userId,long id){
		Reward reward = rewardmapper.selectTaskReward(id);
		long itemId = reward.getItemId();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("userId", userId);
		userRewardMapper.updateNeckType(map);
		int goldAdd=rewardmapper.selectRewardValue(id);
		HashMap<String,Object> addMap = new HashMap<String,Object>();
		addMap.put("userId", userId);
		addMap.put("itemId", itemId);
		addMap.put("goldAdd", goldAdd);
		int coin = userItemsMapper.selectPlayerCoin(userId);
		if(userItemsMapper.selectByItemsId(addMap)==null){  
    		userItemsMapper.insertRewardItem(addMap);
		}
    		else{
    			userItemsMapper.updateRewardItem(addMap);
    		}
		insertUserItemLog(userId,itemId,goldAdd,coin);
	}	
	
	public void insertUserItemLog(long userId,long itemId,int value,int itemValue){
    	UserItemLog userItemLog=new UserItemLog();
    	userItemLog.setItemId(itemId);
    	userItemLog.setValue(value);
    	userItemLog.setItemValue(itemValue);
    	userItemLog.setResumType("任务");
    	userItemLog.setUserAccountId(userId);
    	userItemLog.setUpdateTime(new Date());
    	userItemLogMapper.insertSelective(userItemLog);
	}
	
	public void updateTimming(){
		userTaskMapper.updateTimming();
		
	}
	public int selectRewardState(long userId){
		List<UserTask> list = userTaskMapper.selectUserTask(userId);
		List<Task> list1 = taskMapper.selectTask();
		int state =0;
		HashMap<String,Object> map  = new HashMap<String,Object>();
		for(int i=0;i<list.size();i++){
			for(int j=0;j<list1.size();j++){
				if(list.get(i).getTaskId()==list1.get(j).getId()){
				long id =list1.get(j).getId();
				map.put("userId", userId);
				map.put("id", id);
				UserReward userReward = userRewardMapper.selectRewardState(map);
				if(list.get(i).getState()==list1.get(j).getRate() && userReward!=null && userReward.getState()!=1){
					state=1;
				}
				}
					
			}
		}
		return state;
		
	}
	
}
