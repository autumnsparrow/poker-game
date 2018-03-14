package com.sky.game.service.logic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.lottery.Lottery;
import com.sky.game.service.lottery.LotteryRule;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;
import com.sky.game.service.persistence.UserLotteryMapper;
import com.sky.game.service.persistence.UserVipMapper;



@Service
public class LotteryService {
	private static final Log logger = LogFactory.getLog(LotteryService.class);
	
	public static List list = new ArrayList();
	/**
	 *  用户抽奖
	 */
	@Autowired
	UserItemsMapper userItemsMapper;
	@Autowired
	UserVipMapper userVipMapper;
	@Autowired
	UserLotteryMapper userLotteryMapper;
	@Autowired
	SequenceService sequenceService;
	@Autowired
	UserItemLogMapper userItemLogMapper;
	
	
	public LotteryService() {
		// TODO Auto-generated constructor stub
		String url ="lottery:"+1;
		GameUtil.gameLife(url, "0 59 23 * * ?",this,"updateTimming");  //每天免费抽奖次数更新
	}
	
	/**
	 *  抽奖展示
	 */
	public HashMap<String,Object> selectLottery(long userId){
		InputStream is=Lottery.class.getResourceAsStream("/META-INF/lottery.json");
		Lottery[] lottery = GameContxtConfigurationLoader.loadConfiguration(is,Lottery[].class);
		HashMap<String,Object> map=lotteryNums(userId);
		int lotteryNum=(Integer) map.get("lotteryCount");
		int lotteryCount=0;
		if(userLotteryMapper.selectLotteryCount(userId)!=null){
		lotteryCount=userLotteryMapper.selectLotteryCount(userId).getLotteryCount();
		}
		int canCount=0;
		if(lotteryCount<lotteryNum){
			canCount = lotteryNum-lotteryCount;
		}
		HashMap<String,Object> map1=lotteryNums(userId);
		int count=0;
		if(canCount==0){
		count=(Integer) map1.get("gold");
		}
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		map2.put("lottery", lottery);
		map2.put("cancount", canCount);
		map2.put("count", count);
		return map2;
	}
	
	/**
	 *  抽奖
	 */
	@SuppressWarnings("unused")
	@Transactional
	public HashMap<String,Object> randomLottery(long userId){
		InputStream is=Lottery.class.getResourceAsStream("/META-INF/lottery.json");
		Lottery[] lottery = GameContxtConfigurationLoader.loadConfiguration(is,Lottery[].class);
		LotteryService lotteryService = new LotteryService();
		long id = lotteryService.compute(lottery);
		long itemId = 0;
		int value = 0;
		UserItems userItems=userItemsMapper.selectByUserId(userId);
		int itemValue = userItems.getItemValue();
		HashMap<String,Object> map1=lotteryNums(userId);
		int lotteryNum=(Integer) map1.get("lotteryCount");
		int lotteryCount=0;
		if(userLotteryMapper.selectLotteryCount(userId)!=null){
			lotteryCount=userLotteryMapper.selectLotteryCount(userId).getLotteryCount();
		}
		int count=0;
		int state=-1; 
		for(int i = 0;i<lottery.length;i++){
			if(id==lottery[i].getId()){
				itemId=lottery[i].getItemId();
				value=lottery[i].getValue();
			}
		}
		if(lotteryNum>lotteryCount){		
		HashMap<String,Object> map = new HashMap<String, Object>();				
		map.put("itemId", itemId);
		map.put("value", value);
		map.put("userId", userId);
		if(itemId==1001){
    		if(userItemsMapper.selectUserCoin(userId)!=null){
    			userItemsMapper.updateLottery(map);
    		}
    		else{
    			userItemsMapper.insertLottery(map);
    		}
    		insertUserItemLog(userId,itemId,value,itemValue+(-count));//加入金币日志
    	}
    	if(itemId==1002){
    		int gold=0;
    		if(userItemsMapper.selectUserGold(userId)!=null){
    			 gold = userItemsMapper.selectUserGold(userId).getItemValue();
    			userItemsMapper.updateLottery(map);
    		}else{
    			userItemsMapper.insertLottery(map);
    		}
    		insertUserItemLog(userId,itemId,value,gold);//加入金币日志
    	}
		if(itemId==2500){
			if(userItemsMapper.selectBeryl(userId)!=null){
				userItemsMapper.updateLottery(map);
			}
			else{
				userItemsMapper.insertLottery(map);
			}
    	}
		if(userLotteryMapper.selectLotteryCount(userId)!=null){
			userLotteryMapper.updateLotteryCount(userId);
		}
		else{
			userLotteryMapper.insertLotteryCount(userId);
		}
		count=(Integer) map1.get("gold");
		state=1;
        }
		lotteryCount= lotteryCount +1;
      if(userItemsMapper.selectUserCoin(userId)!=null &&
    		  userItemsMapper.selectPlayerCoin(userId)>=(Integer) map1.get("gold") && lotteryNum<lotteryCount){
        	HashMap<String,Object> map = new HashMap<String, Object>();
    		map.put("itemId", itemId);
    		map.put("value", value);
    		map.put("userId", userId);
        	count=(Integer) map1.get("gold");
        	HashMap<String,Object> mapCoin = new HashMap<String, Object>();
        	mapCoin.put("userId", userId);
        	mapCoin.put("count", count);
        	userItemsMapper.decUserCoin(mapCoin);
        	if(itemId==1001){
        		if(userItemsMapper.selectUserCoin(userId)!=null){
        			userItemsMapper.updateLottery(map);
        		}
        		else{
        			userItemsMapper.insertLottery(map);
        			
        		}	
        		insertUserItemLog(userId,1001,-count,itemValue);//加入金币日志
        		insertUserItemLog(userId,itemId,value,itemValue+(-count));//加入金币日志
        	
        	}
        	if(itemId==1002){
        		int gold=0;
        		if(userItemsMapper.selectUserGold(userId)!=null){
        			gold = userItemsMapper.selectUserGold(userId).getItemValue();
        			userItemsMapper.updateLottery(map);
        		}else{
        			userItemsMapper.insertLottery(map);
        		}
        		insertUserItemLog(userId,itemId,value,gold);//加入金币日志
        	}
    		if(itemId==2500){
    			if(userItemsMapper.selectBeryl(userId)!=null){
    				userItemsMapper.updateLottery(map);
    			}
    			else{
    				userItemsMapper.insertLottery(map);
    			}
        	}
        	state=1;
        	}
        int canCount=0;
		if(lotteryCount<lotteryNum){
			canCount = lotteryNum-lotteryCount;
		}
        HashMap<String,Object> map =new HashMap<String,Object>();
        map.put("id", id);
        map.put("count", count);
        map.put("canCount", canCount);
        map.put("state", state);
		return map;
	}
	
//	public long compute(int f,Lottery[] lottery){
//	
//	long id = 0;
//	if(f>0 && f<=40){
//		id = lottery[2].getId();	
//		System.out.println(f+"0.4的抽中几率");
//	}
//	if(f>40.4 && f<80){
//		id = lottery[3].getId();	
//		System.out.println(f+"0.4的抽中几率");
//	}
//	if(f>80 && f<=85){
//		id = lottery[4].getId();	
//		System.out.println(f+"0.05的抽中几率");
//	}
//	if(f>85 && f<=90){
//		id = lottery[5].getId();	
//		System.out.println(f+"0.05的抽中几率");
//	}
//	if(f>90 && f<=95){
//		id = lottery[6].getId();	
//		System.out.println(f+"0.05的抽中几率");
//	}
//	if(f>95 && f<=100){
//		id = lottery[7].getId();	
//		System.out.println(f+"0.05的抽中几率");
//	}
//	return id;
//	}
	public HashMap<String,Object> lotteryNums(long userId){
		InputStream is=LotteryRule.class.getResourceAsStream("/META-INF/lotteryRule.json");
		LotteryRule[] lotteryRule = GameContxtConfigurationLoader.loadConfiguration(is,LotteryRule[].class);
		long level=0;
		if(userVipMapper.selectVipOn(userId)!=null){
		level=userVipMapper.selectVipLevel(userId);}
		else{
			level=8;
		}
		int lotteryCount=0;
		int gold=0;
		for(int i=0;i<lotteryRule.length;i++){
			if(level==lotteryRule[i].getLevel()){
				lotteryCount=lotteryRule[i].getCount();
				gold=lotteryRule[i].getGold();
			}
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("lotteryCount", lotteryCount);
		map.put("gold", gold);
		return map;
	}
//	@Scheduled(corn="0 0 24 * * ?")
//	public void test(){
//		userLotteryMapper.updateLotteryTiming();					
//			}
	
	
	/**
	 *  抽奖队列移除
	 */
	public long compute(Lottery[] lottery){
		
		long id=0;
		newList();
		int temp=(Integer) list.get(0);
		for(int i=1;i<=lottery.length-1;i++){
		if(lottery[i].getOdds()<temp && temp<=lottery[i+1].getOdds()){
			id=lottery[i+1].getId();
		}
		}	
		System.out.println(list.size());
		if(list.size()==0){
			newList();
		}
		else{
			list.remove(0);
			
		}
		System.out.println(list.size());
		return id;
	}
	/**
	 *  打乱抽奖角标
	 */
	public List newList(){
		if(list.size()==0){
		for(int i=1;i<=100;i++){
			list.add(i);
		}
		Collections.shuffle(list);
		return list;
		}
		else
		return list;
	}

	public void insertUserItemLog(long userId,long itemId,int value,int itemValue){
    	UserItemLog userItemLog=new UserItemLog();
    	//Long ids=sequenceService.generateUserItemLogId();
    	//userItemLog.setId(ids);
    	userItemLog.setItemId(itemId);
    	userItemLog.setValue(value);
    	userItemLog.setItemValue(itemValue);
    	userItemLog.setResumType("抽奖");
    	userItemLog.setUserAccountId(userId);
    	userItemLog.setUpdateTime(new Date());
    	userItemLogMapper.insertSelective(userItemLog);
	}
	public void updateTimming(){
	    userLotteryMapper.updateLotteryTiming();
	}
	
	public static void main(String args[]){
		
		List list = new ArrayList();
		for(int i=0;i<100;i++){
			list.add(i);

		}
		System.out.println(list.size());
		list.remove(0);
		System.out.println(list.size());
		list.remove(0);
		System.out.println(list.size());
		
		
	}
	
}
