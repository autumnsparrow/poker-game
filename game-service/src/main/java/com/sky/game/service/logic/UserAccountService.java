/**
 * 
 */
package com.sky.game.service.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.ServiceException;
import com.sky.game.service.domain.DefaultHead;
import com.sky.game.service.domain.PropertySexTypes;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserAchievement;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserInfo;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.domain.UserLevel;
import com.sky.game.service.persistence.ItemMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserAchievementMapper;
import com.sky.game.service.persistence.UserExtraMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;
import com.sky.game.service.persistence.UserLevelMapper;

/**
 * @author Administrator
 *
 */
@Service
public class UserAccountService {
	
	private static final Log logger = LogFactory.getLog(UserAccountService.class);
	
	@Autowired
	UserAccountMapper userAccountMapper;
	
	/*@Autowired
	SequenceService sequenceService;*/
	
	@Autowired
	UserItemsMapper userItemsMapper;
	
	
	@Autowired
	UserExtraMapper  userExtraMapper;
	
	@Autowired
	ItemMapper  itemMapper;
	@Autowired
	UserLevelMapper userLevelMapper;
	@Autowired
	UserItemLogMapper userItemLogMapper;
	@Autowired
	UserAchievementMapper userAchievementMapper;
	
	/**
	 * GU0001
	 * 
	 * 
	 * @param account
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public long register(String account,String password,long chnalID,String deviceId,int userType)throws ServiceException{
		
		// 根据帐号查询用户是否存在
		UserAccount existAccount=userAccountMapper.selectByPrimaryKey(account);
		// 10001
		if(existAccount!=null){
			//throw new ServiceException(10001, "用户账号已存在");
			return 0;
		}
		
		// insert the user account
		UserAccount userAccount=new UserAccount();
		Long userId=null;
	//	Long userId=sequenceService.generateUserAccountId();
		//userAccount.setId(userId);
		userAccount.setName(account);
		userAccount.setUserPassword(password);
		userAccount.setUserType(userType);
		userAccount.setChannelId(chnalID);
		userAccount.setDeviceId(deviceId);
		int state=0;
		try {
			state = userAccountMapper.insert(userAccount);
			userId=userAccount.getId();
			UserItems item=GameUtil.obtain(UserItems.class);
			item.setItemId(1001L);
			item.setItemValue(1000);
			item.setUserAccountId(userId);
			item.setUpdateTime(new Date());
			state =userItemsMapper.insertReditUser(userId);
			//插入用户物品日志表 相关记录
			//Long id=sequenceService.generateUserItemLogId();
			UserItemLog userItemLog=new UserItemLog();
			//userItemLog.setId(id);
			userItemLog.setItemId(1001L);
			userItemLog.setItemValue(0);
			userItemLog.setValue(1000);
			userItemLog.setResumType("注册");
			userItemLog.setUpdateTime(new Date());
			userItemLog.setUserAccountId(userAccount.getId());
			userItemLogMapper.insertSelective(userItemLog);
			//插入用户成就
			if(userType!=0){
			UserAchievement u=new UserAchievement();
			u.setState(1);
			u.setSystemAchievementId(10L);
			u.setUserAccountId(userId);
			u.setValue(1);
			u.setType(66);
			userAchievementMapper.insertSelective(u);
			}
			//end
			//Long id=userItemLog.getId();
			if(userType==0){
			List<UserExtra> userExtraList=new ArrayList<UserExtra>();	
			UserExtra ue=new UserExtra();
			/*state =userItemsMapper.insertReditUser(userId);
			//userItemLog.setId(id);
			userItemLog.setItemId(1001L);
			userItemLog.setItemValue(0);
			userItemLog.setValue(1000);
			userItemLog.setResumType("注册");
			userItemLog.setUpdateTime(new Date());
			userItemLog.setUserAccountId(userAccount.getId());
			userItemLogMapper.insertSelective(userItemLog);*/
			//插入用户成就
			ue.setUserAccountId(userId);
			ue.setPropertyValue(account);
			ue.setPropertyId(PropertyTypes.NickName.id);
			
			UserExtra ue1=new UserExtra();
			ue1.setUserAccountId(userId);
			ue1.setPropertyValue(String.valueOf(PropertySexTypes.MALE.value));
			ue1.setPropertyId(PropertyTypes.Sex.id);
			
			UserExtra ue2=new UserExtra();
			ue2.setUserAccountId(userId);
			ue2.setPropertyValue("head_5.png");
			ue2.setPropertyId(PropertyTypes.StartHead.id);
			
			UserExtra ue3=new UserExtra();
			ue3.setUserAccountId(userId);
			ue3.setPropertyValue("100RP");
			ue3.setPropertyId(PropertyTypes.RpValue.id);
			UserExtra ue4=new UserExtra();// 和下一条，重复
			ue4.setUserAccountId(userId);
			ue4.setPropertyValue("5000");
			ue4.setPropertyId(PropertyTypes.ReputationValue.id);
			UserExtra ue5=new UserExtra();
			ue5.setUserAccountId(userId);
			ue5.setPropertyValue("5000");
			ue5.setPropertyId(PropertyTypes.CreditValue.id);
			
			userExtraList.add(ue);
			userExtraList.add(ue1);
			userExtraList.add(ue2);
			userExtraList.add(ue3);
			userExtraList.add(ue4);
			userExtraList.add(ue5);
			
			inserDefaultValue(userExtraList);
			}
			if(userType==1){
				List<UserExtra> userExtraList=new ArrayList<UserExtra>();
				UserExtra ue3=new UserExtra();
				ue3.setUserAccountId(userId);
				ue3.setPropertyValue("100RP");
				ue3.setPropertyId(PropertyTypes.RpValue.id);
				UserExtra ue4=new UserExtra();// 和下一条，重复
				ue4.setUserAccountId(userId);
				ue4.setPropertyValue("5000");
				ue4.setPropertyId(PropertyTypes.ReputationValue.id);
				UserExtra ue5=new UserExtra();
				ue5.setUserAccountId(userId);
				ue5.setPropertyValue("5000");
				ue5.setPropertyId(PropertyTypes.CreditValue.id);
				UserExtra ue6=new UserExtra();
				ue6.setUserAccountId(userId);
				ue6.setPropertyValue("2");
				ue6.setPropertyId(PropertyTypes.Vip.id);
				userExtraList.add(ue3);
				userExtraList.add(ue4);
				userExtraList.add(ue5);
				userExtraList.add(ue6);
				inserDefaultValue(userExtraList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException(10002, "注册个人用户默认值设置异常");
		}
		
		//10002
		
		if(state<0){
			throw new ServiceException(10002, "注册异常");
		}
		logger.info("reigster user :"+userId);
		return userId;
		
		
	}
	/**
	 * GU0002
	 * 用户登录
	 * 
	 * @param account
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unused")
	public UserAccount login(String account,String password)throws ServiceException{
		
		// 用户登录
		
		UserAccount acc=null;
		try {
			UserAccount ua=new UserAccount();
			ua.setName(account);
			ua.setUserPassword(password);
			acc = userAccountMapper.selectByNP(ua);
			UserExtra u=userExtraMapper.selectUserIsVip(acc.getId());
			if(u!=null){
			if(u.getPropertyValue().equals("1")){//user_extra isVip 1 是vip 2不是vip
			acc.setUserType(2);//userAccount userType 0 游客 1 普通用户，2 vip 
			}
			if(u.getPropertyValue().equals("2")){
			acc.setUserType(1);
			}
			}else{
				acc.setUserType(0);
			}
		 }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException(10003, "10003 service Exception");
		}
		if(acc==null){
		    throw new ServiceException(10004,"service Exception");
		}
		  return acc;
	}
    
      public void updateUsergm(HashMap<String,Object> map){
    	  userItemsMapper.updateUsergm(map);
	  }
      
      public int selectUserAccountByName(String account){
    	  int state=0;
    	  UserAccount uac=userAccountMapper.selectUserAccountByName(account);
    	  if(uac==null){
    		  state=-1;//å¸å·ä¸å­å¨
    	  }else{
    		  state=1;
    	  }
    	  return state;
      }
      
      
      /*
       * 默认头像列表
       */
      public List<DefaultHead> userDefaultValue(){
    	  List<DefaultHead> iconList=itemMapper.selectDefaultHeadList();
    	  return iconList;
      }
      /*
       * 判断用户是否设置默认状态
       */
      public int DefaultFlag(String account,String password)throws ServiceException{
    		
    		int  flag=0;
    		UserAccount acc=null;
    		try {
    			UserAccount ua=new UserAccount();
    			ua.setName(account);
    			ua.setUserPassword(password);
    			acc = userAccountMapper.selectByNP(ua);
    			
    			//List<UserExtra> properties=acc.getProperties();
    			
    			//Map<PropertyTypes,UserExtra> propertiesMap=acc.getPropertiesAsMap();
    			UserExtra extra=userExtraMapper.selectNickNameByUserId(acc.getId());
    			if(extra!=null){
    				flag=1;
    			}else{
    				flag=-1;
    			}
    			//extra.getProperty().getDescription();
    			/*userExtraMapper.updateByPrimaryKey(extra);*/
    			
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			
    		}
    		
    		  return flag;
    	}
      /*
       * 插入用户初始值
       */
        public int inserDefaultValue(List<UserExtra> userExtraList){
        	int state=-1;
        	if(userExtraList.size()>0){
        	for(UserExtra userExtra:userExtraList){
        		state=userExtraMapper.insert(userExtra);
        	  }
        	}
        	return state;
        }
        
        /*
         *大厅用户个人信息
         */
        public UserInfo selectUserInfo(long userId){
        	UserInfo userinfo=new UserInfo();
        	String nickName=userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap().get(PropertyTypes.NickName).getPropertyValue();
        	String imgName=userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap().get(PropertyTypes.StartHead).getPropertyValue();
        	String sex=userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap().get(PropertyTypes.Sex).getPropertyValue();
        	int exp=0;
        	if(userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap().get(PropertyTypes.Experience)==null){
        		exp=0;
        	}else{
        		exp=Integer.valueOf(userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap().get(PropertyTypes.Experience).getPropertyValue());
        	    
        	}
        	
        	String isVip=null;
        	if(userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap().get(PropertyTypes.Vip)==null ||"2".equals(userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap().get(PropertyTypes.Vip).getPropertyValue())){
        		isVip="1";//不是vip
        	}else{
        		isVip="2";//是
        	}
        	List<UserLevel> uList=userLevelMapper.selectUserLevel();
        	UserLevel us=null;
        	for(int i=0;i<uList.size();i++){
        		if(uList.get(i).getExp()<=exp){
        			us=uList.get(i);
        		}
        	}
        	
        	UserItems userItems=userItemsMapper.selectByUserId(userId);//æ¥è¯¢item id ä¸º1ï¼è¡¨ç¤ºéå¸ç  userItemè¡¨ä¸­çä¸æ¡è®¡å½
        	UserItems ui=userItemsMapper.selectUserGold(userId);
        	userinfo.setExp(exp);
        	userinfo.setIsVip(isVip);
        	userinfo.setSex(sex);
        	userinfo.setUserId(userId);
        	userinfo.setNickName(nickName);
            userinfo.setImgName(imgName);
           if(userItems!=null){
            userinfo.setGold(userItems.getItemValue());
            }
            if(ui!=null){
            userinfo.setYb(ui.getItemValue());
            }else{
            userinfo.setYb(0);
            }
            userinfo.setLevel("LV."+us.getLevelValue()+" "+us.getLevelName());
            
          //======
        	if(exp<=userExtraMapper.selectMaxExp()){
        		int e=0;
        		if(exp!=0){
        			e=userExtraMapper.selectLevelExp(exp);
        		}
        		
        		int TotalExp=userExtraMapper.selectNextExp(exp)-e;
        		
        		userinfo.setTotalExp(TotalExp);
        		//åæ¯

        		userinfo.setCurrentExp(exp-e);
        		//åå­
        		}
        		else{
        			userinfo.setTotalExp(100);
        			userinfo.setCurrentExp(100);
        		}
            //====	
            return userinfo;
        }
        /*
         * æ¾åå¯ç  
         */
        public int findUserPassWord(String phone,String password){
        	int state=0;
        	UserExtra userExtra=userExtraMapper.selectUserExtraByPhone(phone);
        	if(userExtra!=null){
        	long userId=userExtra.getUserAccountId();
        	
        	HashMap<String,Object> hashmap=new HashMap<String,Object>();
        	hashmap.put("password", password);
        	hashmap.put("userId", userId);
        	int i=userAccountMapper.updateUserPassword(hashmap);
        	state=i;
        	}else{
        		state=-2;//ç¨æ·ææºæ²¡æç»å®
        	}
        	return state;
        }
        /**
         * 根据设备号查询用户
         * {@link com.sky.game.service.persistence.UserAccountMapper}
         * @see com.sky.game.service.persistence.UserAccountMapper#selectByDeviceId(String deviceId)
         * 
         * 用在游客与设备绑定时 GU0030Handler
         */
        public UserAccount findUserByDeviceId(String deviceId){
        	UserAccount userAccount=userAccountMapper.selectByDeviceId(deviceId);
        	return userAccount;
        }
        /**
         * 修改用户登录时的设备号
         * {@link com.sky.game.service.persistence.UserAccountMapper}
         * @see com.sky.game.service.persistence.UserAccountMapper#updateDeviceId(hashmap
         * 
         * 用在一个正式用户的账号不能在两个设备上同时登录 GU0002Handler
         */
        public int updateDeviceId(HashMap<String,Object> hashmap){
        	return userAccountMapper.updateDeviceId(hashmap);
        }
        /**
         * 游客注册
         * 
         */
        public long ykregister(String account,String password,int userType,long userId)throws ServiceException{
    		
    		// 根据帐号查询用户是否存在
    		UserAccount existAccount=userAccountMapper.selectByPrimaryKey(account);
    		// 10001
    		if(existAccount!=null){
    			throw new ServiceException(10001, "用户账号已存在");
    		}
    		
    		// insert the user account
    		UserAccount userAccount=new UserAccount();
    		userAccount.setId(userId);
    		userAccount.setName(account);
    		userAccount.setUserPassword(password);
    		userAccount.setUserType(userType);
    		int state=0;
    		try {
    			state = userAccountMapper.updateByPrimaryKeySelective(userAccount);
    			List<UserExtra> userExtraList=new ArrayList<UserExtra>();
    			UserExtra ue6=new UserExtra();
				ue6.setUserAccountId(userId);
				ue6.setPropertyValue("2");
				ue6.setPropertyId(PropertyTypes.Vip.id);
				userExtraList.add(ue6);
				inserDefaultValue(userExtraList);
				userExtraMapper.deleteUserPropertyId(userId);
				//插入用户成就
				if(userType!=0){
				UserAchievement u=new UserAchievement();
				u.setState(1);
				u.setSystemAchievementId(10L);
				u.setUserAccountId(userId);
				u.setValue(1);
				u.setType(66);
				userAchievementMapper.insertSelective(u);
				}
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			throw new ServiceException(10002, "注册个人用户默认值设置异常");
    		}
    		if(state<0){
    			throw new ServiceException(10002, "注册异常");
    		}
    		return userId;
        }	
        /**
         * 根据userId查询用户
         * @param userId
         * @return
         */
    		public UserAccount selectUserAccountByid(long userId){
    			UserAccount userAccount=userAccountMapper.selectByPrimaryId(userId);
    			return userAccount;
    		}
}
