/**
 * 
 */
package com.sky.game.service.logic;


import java.awt.Menu;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.sky.game.service.domain.Store;
import com.sky.game.service.domain.StoreLog;
import com.sky.game.service.domain.StoreOrder;
import com.sky.game.service.domain.StoreShow;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.domain.UserVip;
import com.sky.game.service.domain.Vip;
import com.sky.game.service.domain.Vips;
import com.sky.game.service.persistence.ActivityMapper;
import com.sky.game.service.persistence.ExchangeMapper;
import com.sky.game.service.persistence.ItemMapper;
import com.sky.game.service.persistence.StoreMapper;
import com.sky.game.service.persistence.StoreOrderMapper;
import com.sky.game.service.persistence.StoreTradeLogMapper;
import com.sky.game.service.persistence.UserExtraMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;
import com.sky.game.service.persistence.UserVipMapper;

/**
 * @author Administrator
 *
 */
@Service
public class ShopService {

	private static final Log logger = LogFactory.getLog(ShopService.class);

	private static final int List = 0;
	
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
	StoreMapper storeMapper;
	
    @Autowired
    StoreTradeLogMapper storeTradeLogMapper;
    
    @Autowired
    UserVipMapper userVipMapper;
    @Autowired
    UserItemLogMapper userItemLogMapper;
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    UserExtraMapper userExtraMapper;
    @Autowired
    StoreOrderMapper storeOrderMapper;
    
   
    
    
    
	
	public ShopService() {
		String url ="vip:"+1;
		GameUtil.gameLife(url, "0 59 23 * * ?",this,"updateTimming");  //每日任务更新
		String url1 ="pay" + 1;
		GameUtil.gameLife(url, "0 59 23 * * ?",this,"updateWxTimming");  //每日任务更新
	}
  
	public List<StoreShow> selectStore(){				//购买金币展示
		
		return storeMapper.selectAllStore();
	}
    public Store selectStoreById(long id){              //根据列表id查询一条购买 的记录
    	
    	return storeMapper.selectByPrimaryKey(id);
    }
    public StoreOrder selectByTradeNum(String tradeNum){
    	
    	return storeOrderMapper.selectByTradeNum(tradeNum);
    }
    public int updateTradeStatus(String tradeNum,int state,int ordering){
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("tradeNum", tradeNum);
    	map.put("state", state);
    	map.put("stateing", ordering);
    	int ret = storeOrderMapper.updateTradeStatus(map);
    	return ret;
    }
    
    
    public Integer getPrice(long id){
    	Integer price=storeMapper.selectByPrimaryKey(id).getPrice();
    	return price;
    }
    public String getItemName(long id){
    	String itemName = storeMapper.selectByPrimaryKey(id).getName();
    	return itemName;
    }
    public void insertStoreOrder(String tradeNum,long id,long userId){
    	Store store = storeMapper.selectByPrimaryKey(id);
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("tradeNum", tradeNum);
    	map.put("price", store.getPrice());
    	map.put("description", store.getDescription());
    	map.put("id", id);
    	map.put("tradeTime", new Date());
    	map.put("storeId", store.getId());
    	map.put("userId", userId);
    	storeOrderMapper.insertStoreOrder(map);
    	
    }
    
    
    public void updateUserGold(long id,long userId){    //
    	Store store= storeMapper.selectByPrimaryKey(id);//根据storeId查询商品
    	if(store.getItemId()==1001){
	    	long goldAdd=store.getTotalCount();//商品数量
	    	long goldGift=store.getGiftGold();//赠送数量
	    	int  vipDay=store.getGiftVip();//赠送VIP天数
	    	HashMap<String,Object> hashmap = new HashMap<String,Object>();
	    	hashmap.put("userId", userId);      //用户ID
	    	hashmap.put("goldAdd", goldAdd+goldGift);//用户需要增加的总数量
	    	hashmap.put("vipDay", vipDay);    //赠送VIP天数
	
	    	int price=store.getPrice();//价格
	    	hashmap.put("id", id);     //商品ID
	    	hashmap.put("count", goldAdd);   //购买数量
	    	//select * from user_items where user_account_id=#{userId} and item_id=1001;
	    	UserItems userItems=userItemsMapper.selectByUserId(userId);//查询用户的总金币数
	    	//if(userItems!=null){
	    	long totalCount=userItems.getItemValue();//查询用户拥有的物品数量
	    	//	}
	    	hashmap.put("totalCount", totalCount);  //用户拥有的物品数量
	    	Timestamp ts = new Timestamp(new Date().getTime());
	    	hashmap.put("updateTime", ts);//当前时间
	    	hashmap.put("price", price);  //商品价格	
	    	//update user_items set item_value=item_value+#{goldAdd},update_time=now() where user_account_id=#{userId} and item_id=1001; 
	    	userItemsMapper.updateUserGold(hashmap);
	    	//充值日志
	    	UserItemLog userItemLog=new UserItemLog();
	    	//Long ids=sequenceService.generateUserItemLogId();
	    	//userItemLog.setId(ids);
	    	userItemLog.setItemId(userItems.getItemId());
	    	userItemLog.setValue(Long.valueOf(goldAdd+goldGift).intValue());
	    	userItemLog.setItemValue(userItems.getItemValue());
	    	userItemLog.setResumType("购买");
	    	userItemLog.setUserAccountId(userId);
	    	userItemLog.setUpdateTime(new Date());
	    	userItemLogMapper.insertSelective(userItemLog);
	    	//end
	    	storeTradeLogMapper.insertStoreLog(hashmap);	
    	}
    	
    	if(store.getItemId()>=2500){
	    	long goldAdd=store.getTotalCount();//商品数量
	    	long goldGift=store.getGiftGold();//赠送数量
	    	int  vipDay=store.getGiftVip();//赠送VIP天数
	    	HashMap<String,Object> hashmap = new HashMap<String,Object>();
	    	hashmap.put("userId", userId);      //用户ID
	    	hashmap.put("goldAdd", goldAdd+goldGift);//用户需要增加的总数量
	    	hashmap.put("vipDay", vipDay);    //赠送VIP天数
	
	    	int price=store.getPrice();//价格
	    	hashmap.put("id", id);     //商品ID
	    	hashmap.put("count", goldAdd);   //购买数量
	    	//select * from user_items where user_account_id=#{userId} and item_id=1001;
	    	UserItems userItems=userItemsMapper.selectByUserId(userId);//查询用户的总金币数
	    	//if(userItems!=null){
	    	long totalCount=userItems.getItemValue();//查询用户拥有的物品数量
	    	//	}
	    	hashmap.put("totalCount", totalCount);  //用户拥有的物品数量
	    	Timestamp ts = new Timestamp(new Date().getTime());
	    	hashmap.put("updateTime", ts);//当前时间
	    	hashmap.put("price", price);  //商品价格	
	    	//update user_items set item_value=item_value+#{goldAdd},update_time=now() where user_account_id=#{userId} and item_id=1001; 
	    	userItemsMapper.updateAddUserItem(hashmap);
	    	//充值日志
	    	UserItemLog userItemLog=new UserItemLog();
	    	//Long ids=sequenceService.generateUserItemLogId();
	    	//userItemLog.setId(ids);
	    	userItemLog.setItemId(userItems.getItemId());
	    	userItemLog.setValue(Long.valueOf(goldAdd+goldGift).intValue());
	    	userItemLog.setItemValue(userItems.getItemValue());
	    	userItemLog.setResumType("购买");
	    	userItemLog.setUserAccountId(userId);
	    	userItemLog.setUpdateTime(new Date());
	    	userItemLogMapper.insertSelective(userItemLog);
	    	//end
	    	storeTradeLogMapper.insertStoreLog(hashmap);	
    	}
    	if(store.getItemId()==1003){
    		long zsAdd=store.getTotalCount();//商品数量
        	int  vipDay=store.getGiftVip();//赠送VIP天数
        	HashMap<String,Object> hashmap = new HashMap<String,Object>();
        	hashmap.put("userId", userId);
        	hashmap.put("value", zsAdd);
        	hashmap.put("vipDay", vipDay);
        	//日志hashmap
        	hashmap.put("id", id);
        	hashmap.put("count", zsAdd);
        	//select * from user_items where user_account_id=#{userId} and item_id=1001;
//        	UserItems userItems=userItemsMapper.selectByUserId(userId);//查询用户的总金币数
//        	long totalCount=0;
//        	if(userItems!=null){
//        		totalCount=userItems.getItemValue();//物品数量
//        		}
//        	hashmap.put("totalCount", totalCount);
        	Timestamp ts = new Timestamp(new Date().getTime());
        	hashmap.put("updateTime", ts);
        	int price=store.getPrice();
        	hashmap.put("price", price); 
        	//添加itemId
        	hashmap.put("itemId", store.getItemId());
        	//select * from user_items where user_account_id=#{userId} and item_id=#{itemId};
        	UserItems ui=userItemsMapper.selectUserItemByItemId(hashmap);
        	if(ui==null){
        		//insert into user_items(id,user_account_id,item_id,item_value,update_time) values(NULL,#{userId},#{itemId},#{value},now());
        		userItemsMapper.insertLottery(hashmap);
        	}else{
        		//update user_items set item_value=item_value+#{value} where user_account_id=#{userId} and item_id=#{itemId};
        		userItemsMapper.updateLottery(hashmap);
        	}
        		storeTradeLogMapper.insertStoreLog(hashmap);
    	}
    	InputStream is=ShopService.class.getResourceAsStream("/META-INF/vip.json");
    	Vips s=GameContxtConfigurationLoader.loadConfiguration(is,Vips.class);	
    	Vip[] vips = s.getVip().toArray(new Vip[]{});
    	int point=store.getPrice();
    	Date startDate = new Date();
		Calendar now1 =Calendar.getInstance();  
	    Date stopDate =now1.getTime();
	    UserVip userVip=userVipMapper.selectVipOn(userId);
    	if(userVip!=null){
    		if(userVip.getVipEndTime().getTime()>System.currentTimeMillis()){
    	        now1.setTime(userVip.getVipEndTime());
    	        now1.set(Calendar.DATE,now1.get(Calendar.DATE)+store.getGiftVip());
    	    }else{
    	    	now1.setTime(startDate);  
    		    now1.set(Calendar.DATE,now1.get(Calendar.DATE)+store.getGiftVip());
    	    }
    		int level=userVip.getVipLevel();
    		int spoint=userVip.getVipPoint();
    		int epoint=spoint+point;
    	    HashMap<String,Object> map = new HashMap<String,Object>();
    	    map.put("userId", userId);
			map.put("stopDate", stopDate);
			map.put("point", epoint);
    		for(int i=0;i<vips.length;i++){
    			if(epoint>vips[i].getPoint() && epoint<vips[i+1].getPoint()){ 
    				level=vips[i].getLevel();
        			map.put("level",level);
        			userVipMapper.updateVipLevel(map);
    			}
    			if(userVip.getVipPoint()>=20000){
    				map.put("level",7);
    				userVipMapper.updateVipLevel(map);
    			}
    		  }
    		}
    	else{
    		  now1.setTime(startDate);  
		      now1.set(Calendar.DATE,now1.get(Calendar.DATE)+store.getGiftVip());
    		  HashMap<String,Object> map = new HashMap<String,Object>();
    		  map.put("userId", userId);
  			  map.put("stopDate", stopDate);
  			  map.put("point", store.getPrice());
  			  for(int i=0;i<vips.length;i++){
    			if(store.getPrice()>vips[i].getPoint() && store.getPrice()<vips[i+1].getPoint()){
        			map.put("level", vips[i].getLevel());
        			userVipMapper.insertVipLevel(map);
    			}
    		}
    	}
    	if(userExtraMapper.selectUserIsVip(userId)!=null){
    		userExtraMapper.updateUserExtraVip(userId);
    	}else{
    		userExtraMapper.insertUserExtraVip(userId);
    	}
    	
    }
    public List<StoreLog> selectStoreLog(long userId){
    	return storeTradeLogMapper.selectStoreLog(userId);
    }
    /**
     * 向stroe_order表中插入订单信息
     * {@link #com.sky.game.service.persistence.StroeOrderMapper}
     * @see   com.sky.game.service.persistence #StroeOrderMapper
     * @param storeOrder
     * @return
     */
    public int insertStroeOrder(StoreOrder storeOrder){
    	return storeOrderMapper.insert(storeOrder);
    }
    /**
     * 根据回调orderId 查询 store_order
     * {@link #com.sky.game.service.persistence.StroeOrderMapper.distinctStoreOrder(long orderId)}
     * @see  com.sky.game.service.persistence.StoreOrderMapper #distinctStoreOrder(long orderId)
     * {@link #com.sky.game.service.persistence.StroeOrderMapper.updateStoreOrderStatus(long orderId)}
     * @see  com.sky.game.service.persistence.StoreOrderMapper #updateStoreOrderStatus(long orderId)
     * @return
     */
    @Transactional
    public StoreOrder selectAndUpdateDistinctStoreOrder(String orderId){
    	StoreOrder s=storeOrderMapper.distinctStoreOrder(orderId);
    	int a=0;
    	if(s!=null){
    		a=storeOrderMapper.updateStoreOrderStatus(orderId);
    	}else{
    		s=new StoreOrder();
    	}
    	return s;
    }
    public void updateTimming(){
    	Date date = new Date();
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss"); 
    	String s = sdf.format(date);
    	long d= Long.parseLong(s);
    	List<UserVip> list=userVipMapper.selectViP();
    	for(int i=0;i<list.size();i++){
    		//int dd=Integer.parseInt(sdf.format(list.get(i).getVipStartTime()));
    		long ddd= Long.parseLong(sdf.format(list.get(i).getVipEndTime()));
    		if(d>ddd){
    			long userId=list.get(i).getUserAccountId();
    			userExtraMapper.updateUserVipType(userId);
    		}
    	}
    	
    }
   
   
    
    
    public static void main(String[] args) {
    	Calendar now1 =Calendar.getInstance();  
		now1.setTime(new Date());  
	    now1.set(Calendar.DATE,now1.get(Calendar.DATE)+5);  
	    SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
	    
	    String a=sim.format(now1.getTime());
	    System.out.println(a);
	}
}
