package com.sky.game.service.logic;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.ChannelItems;
import com.sky.game.service.domain.Exchange;
import com.sky.game.service.domain.ExchangeRecord;
import com.sky.game.service.domain.ExchangeLog;
import com.sky.game.service.domain.GoldExchange;
import com.sky.game.service.domain.Goods;
import com.sky.game.service.domain.ItemCount;
import com.sky.game.service.domain.MyGood;
import com.sky.game.service.domain.PriceExchange;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.persistence.ChannelItemsMapper;
import com.sky.game.service.persistence.ExchangeLogMapper;
import com.sky.game.service.persistence.ExchangeMapper;
import com.sky.game.service.persistence.ItemCountMapper;
import com.sky.game.service.persistence.ItemMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserExtraMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;
@Service
public class ExchangeService {
	
	
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(ExchangeService.class);
	/**
	 * 
	 */
	
	@Autowired
	ExchangeMapper exchangeMapper;
	
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	UserItemsMapper userItemsMapper;
	
	@Autowired
	ItemCountMapper itemCountMapper;

	@Autowired
	ExchangeLogMapper exchangeLogMapper;
	@Autowired
	UserItemLogMapper userItemLogMapper;
	
	@Autowired
	GameSystemMessageService gameSystemMessageService;
	
	@Autowired
	UserExtraMapper userExtraMapper;
	@Autowired
    UserAccountMapper userAccountMapper;
	
	@Autowired
	ChannelItemsMapper channelItemsMapper;
	public ExchangeService() {
		// TODO Auto-generated constructor stub
		Random r=new Random();
		String url ="exchange:"+System.currentTimeMillis()+r.nextInt(9);
		GameUtil.gameLife(url, "0 59 23 * * ?",this,"updateTodayLeftCount");
	}
	/**
	 * 定时修改每日对换量 ，每日领取数量
	 * 查询所有itemCount
	 * {@link com.sky.game.service.persistence.ItemCountMapper.selectAllItemCount()}
	 * @see com.sky.game.service.persistence.ItemCountMapper #selectAllItemCount()
	 * 修改今日剩余数量
	 * {@link com.sky.game.service.persistence.ItemCountMapper.updateTodayNum()}
	 * @see com.sky.game.service.persistence.ItemCountMapper #updateTodayNum(HashMap<String,Object> hashmap)
	 * 
	 */
	@Transactional
    public void updateTodayLeftCount(){
    	List<ItemCount> itemCountList=itemCountMapper.selectAllItemCount();
    	for(ItemCount itemCount:itemCountList){
    		HashMap<String,Object> hashmap=new HashMap<String,Object>();
    		int all=itemCount.getAllNum();
    		int todayCount=itemCount.getTodayNum();
    		long id=itemCount.getId();
    		int allNum=0;
    		int leftNum=0;
    		if(all<=todayCount){
    			allNum=all;
    			leftNum=all;
    		}else{
    			allNum=all;
    			leftNum=todayCount;
    		}
    		hashmap.put("allNum", allNum);
    		hashmap.put("leftNum", leftNum);
    		hashmap.put("id", id);
    		itemCountMapper.updateTodayNum(hashmap);
    	}
    }
	 public ExchangeMapper getExchangeMapper() {
			return exchangeMapper;
		}
    public void setExchangeMapper(ExchangeMapper exchangeMapper) {
			this.exchangeMapper = exchangeMapper;
		}
    /**
     * 查询奖品列表
     * 根据用户id查询用户
     * {@link com.sky.game.service.persistence.UserAccountMapper.selectByPrimaryId(long userId)}}
     * 根据用户所在渠道id查询渠道物品
     * {@link  com.sky.game.service.persistence.ChannelItemsMapper.selectByChannelId(long channelId)}}
     * 查询不同渠道用户展示的奖品列表
     * {@link  com.sky.game.service.persistence.ExchangeMapper.selectPriceExchange(long channelItemId)}}
     * @param userId
     * @return
     */
	public List<PriceExchange> selectPriceExchange(long userId){				//奖品对换
		UserAccount userAccount=userAccountMapper.selectByPrimaryId(userId);
		List<PriceExchange> list=new ArrayList<PriceExchange>();
		ChannelItems chs=channelItemsMapper.selectByChannelId(userAccount.getChannelId());//查询渠道物品，根据用户渠道
		long channelItemId=0;
		if(chs!=null){
			channelItemId=chs.getChannelItemId();
		}
		list=exchangeMapper.selectPriceExchange(channelItemId);
		/*if(userAccount.getChannelId()==2000){
			list=exchangeMapper.selectPriceExchange(userId);
		}else{
			List<PriceExchange> glist=exchangeMapper.selectPriceExchange(userId);
 			for(PriceExchange goods:glist){
 				if(goods.getId()==30||goods.getId()==29){
 					continue;
 				}
 				list.add(goods);
 			}
		}*/
		return list;
	 }
	@Transactional
	public int updateUserGoods(long userId,long id){							//物品对换 修改用户 user_items
		int state = 0;							
		UserExtra userExtra= userExtraMapper.selectUserIsVip(userId);
		HashMap<String ,Object> map=new HashMap<String,Object>();               
		map.put("userId", userId);
		map.put("id", id);
		List<UserItems> userItemsList = userItemsMapper.selectUserItems(map);
		UserItems ui=userItemsMapper.selectUserCoin(userId);
		long fromCount=exchangeMapper.selectByPrimaryKey(id).getFromItemCount();
  if(Integer.parseInt(userExtra.getPropertyValue())==1){
	if(ui!=null && ui.getItemValue()>=fromCount){
	  if(exchangeMapper.selectResDay(id)>0){
		  userItemsMapper.decUserGolds(map);										//éæå´²éâæ§éåº¯ç´é²æç«µéå¿ç¯
		if(userItemsList.size()>0){
			userItemsMapper.updateUserGoods(map);							//éæå´²éâæ§éåº¯ç´éâæ§æ¾§ç²å§
			insertExchangeLog(userId,id);
		}else{
			userItemsMapper.insertUserItems(map);							//æ¿¡åæ¤é´éæ£¤çã§å¢¿éä¾ç´å¨£è¯²å§æ¶ï¿½æ½¯çæ¿ç¶
		}
		itemCountMapper.decResCount(id);
		insertUserItemLog(userId,1001,-(int)fromCount,ui.getItemValue());
		state=1;
		}
	  else{
		  state=-2;															
	  }
	}
	else{
			state=-1;
		}
  }
	else{
			state=-3;
			
		}
  
	//gameSystemMessageService.broadcastMessage(message);
		return state;
	}														
	@Transactional
	public int priceRewardExchange(long userId,long id){		//奖品对换
		Exchange exchange=exchangeMapper.selectInfoById(id);
		int price=exchangeMapper.selectPrice(id);   				//æ¿æ §æ§æµ éç¸
		int resDay=exchangeMapper.selectResDay(id);						//æµ å©æ£©æ©æ¨ºå½²éæå´²æ¿æ §æ§éä¼´åº
		int playerGold=0;
		long fitemId=exchange.getFromItemId();
		HashMap<String,Object> hm=new HashMap<String,Object>();
		hm.put("userId", userId);
		hm.put("itemId",fitemId);
		UserItems userItems=userItemsMapper.selectUserItemByItemId(hm);
		//UserItems userItems=userItemsMapper.selectUserGold(userId);
		if(userItems!=null){
			
			playerGold=userItems.getItemValue();    //éâîéå¨çéä¼´åº
		}else{
			return -3;
		}
		if(resDay>0){
		if(playerGold>=price){
		HashMap<String ,Object> map=new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("id", id);
			   userItemsMapper.decUserGolds(map);	//减少元宝
			   if(id==29||id==30){
				   List<UserItems> ul= userItemsMapper.selectUserItems(map);
				   if(ul.size()>0){
					 UserItems us=ul.get(0);
					 us.setItemValue(us.getItemValue()+1);
					 userItemsMapper.updateByPrimaryKey(us);
				   }else{
					 userItemsMapper.insertUserItems(map);//向背包里加入彩豆
				   }
			   }else{
			   userItemsMapper.insertUserItems(map);//增加奖品
			   }
			   itemCountMapper.decResCount(id);	//减少itemCount
			   insertExchangeLog(userId,id);
			   insertUserItemLog(userId,fitemId,-price,playerGold);
			   return 1;
		}
		else{
			return -1;
		}
		}else{
			return -2;  //æµ å©æ£©éâç¶éä¼´åºæ¶ï¿½
		}
	}
	
	public List<Goods> selectItemGoods(long userId){//物品对换
		return exchangeMapper.selectItemGoods(userId);				
		
	}
	public List<GoldExchange> selectItemGold(){		
		return exchangeMapper.selectItemGold();							
		
	}
	/*
	 * 金币对换
	 */
	@Transactional
	public int updateUserGolds(long userId,long id)		
	{
		int state =0;
		HashMap<String,Object> hashmap=new HashMap<String,Object>();
		hashmap.put("userId", userId);
		hashmap.put("exchangeId",id);
		UserItems userItem=userItemsMapper.selectUserExchangeItem(hashmap);
		Exchange exchange=exchangeMapper.selectByPrimaryKey(id);
	   if(userItem!=null && userItem.getItemValue()>=exchange.getFromItemCount()){
		if(exchangeMapper.selectResDay(id)>=exchange.getTotalCount()){
		HashMap<String ,Object> map=new HashMap<String,Object>();	
		map.put("userId", userId);
		map.put("exchangeId", id);
		userItemsMapper.updateUserGolds(map);						//éå¨çéæå´²é²æç«µéåº¯ç´é²æç«µæ¾§ç²å§
		userItemsMapper.decUserGoldingot(map);						//éå¨çéæå´²é²æç«µéåº¯ç´éå¨çéå¿ç¯
		insertExchangeLog(userId,id);
		itemCountMapper.decResCount(id);
		state=1;
		insertUserItemLog(userId,exchange.getFromItemId(),-exchange.getFromItemCount().intValue(),userItem.getItemValue());
		}
		else{
			state=-2;
		}
	}
	else{
		state=-1;
	}
	return state;
}
	
	public List<ExchangeRecord> selectExchangeRecord(){
		
		return exchangeLogMapper.selectExchangeRecord();
	}
	public void insertExchangeLog(long userId,long id){				//å¨£è¯²å§éæ¿å¬é¹ã¡æ£©è¹ï¿½
		ExchangeLog exchangeLog = new ExchangeLog();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("id", id);
		exchangeLog.setExchangeId(id);
		exchangeLog.setUserAccountId(userId);
		exchangeLog.setTotalCount(userItemsMapper.selectItemValue(map));
		exchangeLog.setCount(userItemsMapper.selectTotalCount(id));
		Date date = new Date();
		exchangeLog.setUpdateTime(date);
		exchangeLogMapper.insertExchangeLog(exchangeLog);
	}
	
	
	public void insertUserItemLog(long userId,long itemId,int value,int itemValue){
    	UserItemLog userItemLog=new UserItemLog();
    	userItemLog.setItemId(itemId);
    	userItemLog.setValue(value);
    	userItemLog.setItemValue(itemValue);
    	userItemLog.setResumType("兑换");
    	userItemLog.setUserAccountId(userId);
    	userItemLog.setUpdateTime(new Date());
    	userItemLogMapper.insertSelective(userItemLog);
	}
}