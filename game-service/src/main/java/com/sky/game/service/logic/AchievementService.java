package com.sky.game.service.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.game.service.domain.Achievement;
import com.sky.game.service.domain.AchievementShow;
import com.sky.game.service.domain.Achievements;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserAchievement;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserAchievementMapper;
import com.sky.game.service.persistence.UserExtraMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;
@Service
public class AchievementService {
     
	@Autowired
	UserAchievementMapper userAchievementMapper;
	@Autowired
	UserItemsMapper userItemsMapper;
	@Autowired
	UserAccountMapper userAccountMapper;
	@Autowired
	UserExtraMapper userExtraMapper;
	@Autowired
	UserItemLogMapper userItemLogMapper;
	/**
	 * 用户成就
	 */
	public List<AchievementShow> achievementShowList(long userId){ 
    	InputStream is=AchievementService.class.getResourceAsStream("/META-INF/achievement.json");
       	Achievements o=GameContxtConfigurationLoader.loadConfiguration(is,Achievements.class);	
    	
    	Achievement[] achievementArray = o.getAchievements().toArray(new Achievement[]{});//GameContxtConfigurationLoader.loadConfiguration(is, Achievement[].class);
    	List<UserAchievement> userAchievementList=userAchievementMapper.selectAll(userId);
    	List<AchievementShow> AchievementShowList=new ArrayList<AchievementShow>();
    		for(int i=0;i<achievementArray.length;i++){
    			AchievementShow as=new AchievementShow();
    			String schedule="0/"+achievementArray[i].getValue();
    			int state=0;
    			as.setId(achievementArray[i].getId());
				as.setDescription(achievementArray[i].getAchievementDis());
				as.setName(achievementArray[i].getAchievementName());
				String exp;
				if(achievementArray[i].getAchievementAwardExp()!=0){
					exp=achievementArray[i].getAchievementAwardCoin()+"金币"+achievementArray[i].getAchievementAwardExp()+"经验";
				}else{
					exp=achievementArray[i].getAchievementAwardCoin()+"金币";
				}
				as.setRewardDesc(exp);
				as.setImgName(achievementArray[i].getImgName());
				if(userAchievementList!=null & userAchievementList.size()>0){
		    		for(int j=0;j<userAchievementList.size();j++){
		    	if(userAchievementList.get(j).getSystemAchievementId()==achievementArray[i].getId()){
					if(userAchievementList.get(j).getValue()>=achievementArray[i].getValue()){
						schedule=achievementArray[i].getValue()+"/"+achievementArray[i].getValue();
						as.setSchedule(schedule);
						state=userAchievementList.get(j).getState();
	    				as.setState(state);
	    				break;
	    			   }else{
	    				    schedule=userAchievementList.get(j).getValue()+"/"+achievementArray[i].getValue();
							as.setSchedule(schedule);
							state=userAchievementList.get(j).getState();
		    				as.setState(state);
		    				break;
	    			       }
		    			}else{
		    				schedule=0+"/"+achievementArray[i].getValue();
							as.setSchedule(schedule);
		    				as.setState(0);
		    			}
		    		}
				}else{
					as.setSchedule(0+"/"+achievementArray[i].getValue());
    				as.setState(0);
				}
				if(achievementArray[i].getId()!=10 || achievementArray[i].getId()!=11){
					as.setType(1);//表示比赛
				}
				if(achievementArray[i].getId()==10){
					as.setType(2);//表示注册
				}
				if(achievementArray[i].getId()==11){
					as.setType(3);//表示手机绑定
				}
				AchievementShowList.add(as);
    		}
    	
    	return AchievementShowList;
	}
	/**
	 * 领取成就
	 */
	@Transactional
	public int userAchievementGet(long userId,long id){
		InputStream is=AchievementService.class.getResourceAsStream("/META-INF/achievement.json");
       	Achievements o=GameContxtConfigurationLoader.loadConfiguration(is,Achievements.class);	
       	Achievement[] achievementArray = o.getAchievements().toArray(new Achievement[]{});
	    int state=-1;
	    
    	for(int i=0;i<achievementArray.length;i++){
	    	if(id==achievementArray[i].getId()){
	    		   int Gold=achievementArray[i].getAchievementAwardCoin();
	    		   int exp=achievementArray[i].getAchievementAwardExp();
	    		   HashMap<String,Object> hashmap=new HashMap<String,Object>();
	    		   hashmap.put("goldAdd", Gold);
	    		   hashmap.put("userId", userId);
	    		   UserItems userCheckUserLog=userItemsMapper.selectByUserId(userId);//添加用户日志时使用
	    		   userItemsMapper.updateUserGold(hashmap);
	    		   UserAccount userAccount=userAccountMapper.selectByPrimaryId(userId);
	    			Map<PropertyTypes,UserExtra> map=userAccount.getPropertiesAsMap();
	    			if(map.get(PropertyTypes.Experience)!=null){
	    				// ä¿®æ¹ç¨æ·ç»éª
	    				hashmap.put("exp",exp);
	    				state=userExtraMapper.updateAddUserExp(hashmap);
	    			}else{
	    			   // æ·»å ç¨è®°ç»éª
	    				UserExtra us=new UserExtra();
	    				us.setPropertyId(8L);
	    				us.setPropertyValue(String.valueOf(exp));
	    				us.setUserAccountId(userId);
	    				state=userExtraMapper.insert(us);	
	    				}
                    hashmap.put("state", 2);
                    hashmap.put("achievementId", id);
	    			userAchievementMapper.updateUserAchievementState(hashmap);
	    			//添加成就  日志
	    			
	    			UserItemLog userItemLog=new UserItemLog();
	    	    	//Long ids=sequenceService.generateUserItemLogId();
	    	    	//userItemLog.setId(ids);
	    	    	userItemLog.setItemId(1001L);
	    	    	userItemLog.setValue(Gold);
	    	    	userItemLog.setItemValue(userCheckUserLog.getItemValue());
	    	    	userItemLog.setResumType("成就领取");
	    	    	userItemLog.setUserAccountId(userId);
	    	    	userItemLog.setUpdateTime(new Date());
	    	    	userItemLogMapper.insertSelective(userItemLog);
	    	    	//end
	    			state=1;
	    	}
	    }
    	return state;
	}
	
	
	
	/**
	 * 比赛中获得的成就
	 */
	public void inserAchievement(long userId,int type,int landlord){//1 农民  2地主  3其它
		InputStream is=AchievementService.class.getResourceAsStream("/META-INF/achievement.json");
       	Achievements o=GameContxtConfigurationLoader.loadConfiguration(is,Achievements.class);	
       	Achievement[] achievementArray = o.getAchievements().toArray(new Achievement[]{});
    	if(type==1 || type==2 || type==3 || type==11 || type==12 ||type==14 ||type==15|| type==16 ||type==17 ||type==25||type==18 ||type==19||type==20||type==26
    			||type==22||type==23||type==24){
			HashMap<String,Object> hashmap =new HashMap<String,Object>();
			hashmap.put("userId", userId);
			hashmap.put("type", type);
			List<UserAchievement> UAList =userAchievementMapper.selectByUseridAndType(hashmap);
			if(UAList!=null&&UAList.size()>0){//如果用户有此成就  修改
				 List<UserAchievement> uhList=new ArrayList<UserAchievement>();
				for(UserAchievement usa:UAList){
				 UserAchievement userAchievement=new UserAchievement();
			    int state=0;
			   // if(usa.getSystemAchievementId()==)
			    if(usa.getState()==0){
			    	for(int i=0;i<achievementArray.length;i++){
			    		if(usa.getSystemAchievementId()==achievementArray[i].getId()){
			    		if(achievementArray[i].getValue()<=usa.getValue()+1){
			    			state=1;
			    		}else{
			    			state=0;
			    		}
			    		}
			    	}
			    	userAchievement.setSystemAchievementId(usa.getSystemAchievementId());
			    	userAchievement.setType(type);
			    	userAchievement.setUserAccountId(userId);
			    	userAchievement.setValue(usa.getValue()+1);
			    	userAchievement.setState(state);
			    	uhList.add(userAchievement);
			      }
				 }
				for(UserAchievement u:uhList){
					userAchievementMapper.updateByPrimaryKeySelective(u);
				}
			}else{//如果用户没有此成就插入
				
				int state=0;
			    for(int i=0;i<achievementArray.length;i++){
			    	  if(achievementArray[i].type==type){
			    	  
			    		  UserAchievement userAchievement=new UserAchievement();
			    		 if(achievementArray[i].getId()==1){
			    				  state=1;
			    		  }else if(achievementArray[i].getId()==2){
			    			      state=0;
			    		  }else{
			    			  if(achievementArray[i].getValue()==1){
			    				  state=1;
			    			  } 
			    		  }
			    	  userAchievement.setState(state);
			    	  userAchievement.setSystemAchievementId(achievementArray[i].getId());
			    	  userAchievement.setType(type);
			    	  userAchievement.setUserAccountId(userId);
			    	  userAchievement.setValue(1);
			    	  userAchievementMapper.insertSelective(userAchievement);
			    	  }
			    }
			}
			
		}
		if(landlord==2){// 地主
			 List<UserAchievement> uhList=new ArrayList<UserAchievement>();
			if(type==10){
				int systemAchievement=7;
				HashMap<String,Object> hashmap=new HashMap<String,Object>();
				hashmap.put("userId", userId);
				hashmap.put("systemAchievement", systemAchievement);
				List<UserAchievement> uaList=userAchievementMapper.selectLoardorNm(hashmap);
				int state1=0;
				int state2=0;
				int state3=0;
				if(uaList.size()>0){
					 for(UserAchievement u:uaList){
						 UserAchievement userAchievement=new UserAchievement();
						 if(u.getSystemAchievementId()==5){
							 if(u.getState()!=2){
							 if(u.getValue()+1>=20){
								 state1=1;
							 }}else{
								 state1=2;
							 }
							 userAchievement.setSystemAchievementId(u.getSystemAchievementId());
						     userAchievement.setType(10);
						     userAchievement.setUserAccountId(userId);
						     userAchievement.setValue(u.getValue()+1);
						     userAchievement.setState(state1);
						     uhList.add(userAchievement);
						 }
						 if(u.getSystemAchievementId()==6){
							 if(u.getState()!=2){
							 if(u.getValue()+1>=60){
								 state2=1;
							 }
							 }else{state2=2;}
							 userAchievement.setSystemAchievementId(u.getSystemAchievementId());
							 userAchievement.setType(10);
							 userAchievement.setUserAccountId(userId);
							 userAchievement.setValue(u.getValue()+1);
							 userAchievement.setState(state2);
							 uhList.add(userAchievement);
						 }
						 if(u.getSystemAchievementId()==7){
							 if(u.getState()!=2){
							 if(u.getValue()+1>=60){
								 state3=1;
							 }}else{
								 state3=2;
							 }
							 System.out.println(u.getValue());
							 userAchievement.setSystemAchievementId(u.getSystemAchievementId());
							 userAchievement.setType(10);
							 userAchievement.setUserAccountId(userId);
							 userAchievement.setValue(u.getValue()+1);
							 userAchievement.setState(state3);
							 uhList.add(userAchievement);
						 }
					 }
					 List<UserAchievement> z=userAchievementMapper.selectDorN(hashmap);
					 if(z.size()==0){
						 UserAchievement userAchievement=new UserAchievement();
							userAchievement.setSystemAchievementId(7L);
						     userAchievement.setType(10);
						     userAchievement.setUserAccountId(userId);
						     userAchievement.setValue(1);
						     userAchievement.setState(0);
							userAchievementMapper.insertSelective(userAchievement);
					 }
					 for(UserAchievement u:uhList){
							userAchievementMapper.updateByPrimaryKeySelective(u);
						}
				}else{
					for(long i=5;i<8;i++){
						UserAchievement userAchievement=new UserAchievement();
						userAchievement.setSystemAchievementId(i);
					     userAchievement.setType(10);
					     userAchievement.setUserAccountId(userId);
					     userAchievement.setValue(1);
					     userAchievement.setState(0);
						userAchievementMapper.insertSelective(userAchievement);
					}
				}
			}
			if(type==13){
				HashMap<String,Object> hashmap=new HashMap<String,Object>();
				hashmap.put("userId", userId);
				hashmap.put("systemAchievement", 18);
				 List<UserAchievement> z=userAchievementMapper.selectDorN(hashmap);
				 if(z.size()==0){
					 UserAchievement userAchievement=new UserAchievement();
						userAchievement.setSystemAchievementId(18L);
					     userAchievement.setType(13);
					     userAchievement.setUserAccountId(userId);
					     userAchievement.setValue(1);
					     userAchievement.setState(0);
						userAchievementMapper.insertSelective(userAchievement);
				 }else{
					 UserAchievement userAchievement=new UserAchievement();
					 userAchievement.setSystemAchievementId(18L);
				     userAchievement.setType(13);
				     userAchievement.setUserAccountId(userId);
				     userAchievement.setValue(z.get(0).getValue()+1);
				     if(z.get(0).getState()!=2){
				     if(z.get(0).getValue()+1<6){
				     userAchievement.setState(0);
				     }else{
				     userAchievement.setState(1);
				     }
				     }else{
				     userAchievement.setState(2); 
				     }
					   userAchievementMapper.updateByPrimaryKeySelective(userAchievement);
				 }
			}
		}else{
			List<UserAchievement> uhList=new ArrayList<UserAchievement>();
			if(type==10){
				int systemAchievement=8;
				HashMap<String,Object> hashmap=new HashMap<String,Object>();
				hashmap.put("userId", userId);
				hashmap.put("systemAchievement", systemAchievement);
				List<UserAchievement> uaList=userAchievementMapper.selectLoardorNm(hashmap);
				int state1=0;
				int state2=0;
				int state3=0;
				if(uaList.size()>0){
					 for(UserAchievement u:uaList){
						 UserAchievement userAchievement=new UserAchievement();
						 if(u.getSystemAchievementId()==5){
							 if(u.getState()!=2){
							 if(u.getValue()+1>=20){
								 state1=1;
							 }}else{
								 state1=2;
							 }
							 userAchievement.setSystemAchievementId(u.getSystemAchievementId());
						     userAchievement.setType(10);
						     userAchievement.setUserAccountId(userId);
						     userAchievement.setValue(u.getValue()+1);
						     userAchievement.setState(state1);
						     uhList.add(userAchievement);
						 }
						 if(u.getSystemAchievementId()==6){
							 if(u.getState()!=2){
							 if(u.getValue()+1>=60){
								 state2=1;
							 }}else{
								 state2=2;
							 }
							 userAchievement.setSystemAchievementId(u.getSystemAchievementId());
							 userAchievement.setType(10);
							 userAchievement.setUserAccountId(userId);
							 userAchievement.setValue(u.getValue()+1);
							 userAchievement.setState(state2);
							 uhList.add(userAchievement);
						 }
						 if(u.getSystemAchievementId()==8){
							 if(u.getState()!=2){
							 if(u.getValue()+1>=60){
								 state3=1;
							 }}else{
								 state3=2;
							 }
							 userAchievement.setSystemAchievementId(u.getSystemAchievementId());
							 userAchievement.setType(10);
							 userAchievement.setUserAccountId(userId);
							 userAchievement.setValue(u.getValue()+1);
							 userAchievement.setState(state3);
							 uhList.add(userAchievement);
						 }
					 }
					 List<UserAchievement> z=userAchievementMapper.selectDorN(hashmap);
					 if(z.size()==0){
						 UserAchievement userAchievement=new UserAchievement();
							userAchievement.setSystemAchievementId(8L);
						     userAchievement.setType(10);
						     userAchievement.setUserAccountId(userId);
						     userAchievement.setValue(1);
						     userAchievement.setState(0);
							userAchievementMapper.insertSelective(userAchievement);
					 }
					 for(UserAchievement u:uhList){
							userAchievementMapper.updateByPrimaryKeySelective(u);
						}
				}else{
					for(long i=5;i<7;i++){
						UserAchievement userAchievement=new UserAchievement();
						userAchievement.setSystemAchievementId(i);
					     userAchievement.setType(10);
					     userAchievement.setUserAccountId(userId);
					     userAchievement.setValue(1);
					     userAchievement.setState(0);
						userAchievementMapper.insertSelective(userAchievement);
					}
					UserAchievement userAchievement=new UserAchievement();
					userAchievement.setSystemAchievementId(8L);
				     userAchievement.setType(10);
				     userAchievement.setUserAccountId(userId);
				     userAchievement.setValue(1);
				     userAchievement.setState(0);
					userAchievementMapper.insertSelective(userAchievement);
				}
			}
		}
	}
	
}
