package com.sky.game.service.logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.AwardRecord;
import com.sky.game.service.domain.Flag;
import com.sky.game.service.domain.ItemCount;
import com.sky.game.service.domain.MyGood;
import com.sky.game.service.domain.MyPrice;
import com.sky.game.service.domain.PriceExchange;
import com.sky.game.service.domain.ReceiveInfo;
import com.sky.game.service.domain.SystemChargeCard;
import com.sky.game.service.domain.SystemChargeItems;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserBank;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.persistence.FlagMapper;
import com.sky.game.service.persistence.ItemCountMapper;
//import com.sky.game.service.domain.MyGood;
import com.sky.game.service.persistence.ItemMapper;
import com.sky.game.service.persistence.ReceiveInfoMapper;
import com.sky.game.service.persistence.SystemChargeCardMapper;
import com.sky.game.service.persistence.SystemChargeItemsMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserBankMapper;
import com.sky.game.service.persistence.UserItemsMapper;

/**
 * @author Administrator
 *
 */
@Service
public class BagService {
	
	/**
	 * 
	 */
	
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	ItemCountMapper itemCountMapper;
	@Autowired
	UserItemsMapper userItemsMapper;
	
	@Autowired
	ReceiveInfoMapper receiveInfoMapper;
	@Autowired
	FlagMapper flagMapper;
	@Autowired
	SystemChargeCardMapper systemChargeCardMapper;
	@Autowired
	SystemChargeItemsMapper systemChargeItemsMapper;
	@Autowired
	UserBankMapper userBankMapper;
	@Autowired
	UserAccountMapper userAccountMapper;
	public BagService() {
		// TODO Auto-generated constructor stub
	}
	public List<MyGood> selectMyGood(long id){	
		//背包我的物品
		/*UserAccount userAccount=userAccountMapper.selectByPrimaryId(id);
		List<MyGood> list=new ArrayList<MyGood>();
		if(userAccount.getChannelId()==2000){
         list=userItemsMapper.selectMyGood(id);
         for(MyGood myGood:list){
        	 if(myGood.getItemId()==8000){
        		 String [] sArray=myGood.getGoodsDis().split("/");
        		 myGood.setGoodsDis(sArray[0]);
        	 }
         }
		}*/
		return userItemsMapper.selectMyGood(id);
	}
	public List<MyPrice> selectMyPrices(long userId){
		return userItemsMapper.selectMyPrices(userId);					//背包我的奖品
	}
	public void descPrice(long itemId){
		itemCountMapper.updateReceiveItemCount(itemId);
	}
	
	//@Transactional
	public void insertFlag(HashMap<String,Object> map){
		 flagMapper.insertFlag(map);
		 long itemId=(Long)map.get("itemId");
		 descPrice(itemId);
		 long userId=(Long)map.get("userId");
		 long flagId=(Long)map.get("flagId");
		 String url="exchange://"+flagId;
		 GameUtil.gameLife(url,20*60*1000L,this,"f",itemId,userId).setGameSession(GameUtil.DEFAULT_GAMESESSION);;
	};
	public void addPrice(long itemId){
		itemCountMapper.addReceiveItemCount(itemId);
	}
	public int insertReceiveInfo(ReceiveInfo record){
		return receiveInfoMapper.insert(record);
	}

	@Transactional
	public void userDH(ReceiveInfo record){
		/*åå°ç¨æ·èåä¸­çç©å*/
		userItemsMapper.userPriceDes(record.getUserItemsId());
		/*åé¢å¥è®°å½è¡¨ä¸­æå¥æ°æ®*/
		insertReceiveInfo(record);
	}
	
	
	public ItemCount priceSYValidate(long itemId){
		ItemCount itemCount=itemCountMapper.selectPriceByMap(itemId);
		return itemCount;
	}
	public List<AwardRecord> selectAllRewardPrice(long userId){
		List<AwardRecord> awardRecordList=receiveInfoMapper.selectAllRewardPrice(userId);
		for(AwardRecord awardRecord:awardRecordList){
			if(awardRecord.getOrderId()==null){
				awardRecord.setOrderId("-- --");
			}
		}
		return awardRecordList;
	}
	public int CountByUserId(long userId){
		List<Flag> flagList=flagMapper.countByUserId(userId);
		int size=0;
		if(flagList!=null && flagList.size()>0){
			size=1;
		}else{
			size=0;
			}
		return size;
	}
	public int countByFlagId(long flagId){
		List<Flag> flagList=flagMapper.selectByflagId(flagId);
		int size=0;
		if(flagList!=null && flagList.size()>0){
			size=1;
		}else{
			size=0;
			}
		return size;
	}
	@Transactional
	public int giftBagGot(String cardId,long userId){
		HashMap<String ,Object> hashmap=new HashMap<String,Object>(); 
		hashmap.put("userId",userId);
		SystemChargeCard syscc=systemChargeCardMapper.selectByCardPassword(cardId);
		int state=0;
		if(syscc!=null){
			long attrId=syscc.getChargeCardAttributeId();
			List<SystemChargeItems> syscl=systemChargeItemsMapper.selectAllItems(attrId);
			if(syscc.getState()==1){
			 for(SystemChargeItems s:syscl){
				 long itemId=s.getItemId();
				 int count=s.getCount();
				 hashmap.put("itemId",itemId);
				 UserItems ui=userItemsMapper.selectUserItemByItemId(hashmap);
				 if(ui!=null){
					 hashmap.put("value", count);
					 userItemsMapper.updateLottery(hashmap);
				 }else{
					 hashmap.put("value", count);
					 userItemsMapper.insertLottery(hashmap);
				 }
			 }
			 hashmap.put("cardPassword", syscc.getCardPassword());
			 state=systemChargeCardMapper.updateSystemChargeCardState(hashmap);
			}
			if(syscc.getState()==2){
				state=-2;
			}
		}else{
			state=-3;
		}
		return state;
	}
/*	@Scheduled(fixedRate=1000*60*20)
	public void test(){
			List<Flag> flagList=flagMapper.selectAllFlag();	
			if(flagList.size()>0){
			for(Flag f:flagList){
				itemCountMapper.addReceiveItemCount(f.getItmeId());
				flagMapper.deleteFlagByUserId(f.getUserId());
			}
			}
	}*/
	public void f(long itemId,long userId){
		Flag flag=flagMapper.selectByUserId(userId);
		if(flag!=null){
		itemCountMapper.addReceiveItemCount(itemId);
		flagMapper.deleteFlagByUserId(userId);
		}
	}
 /**
  * 解除绑定
  */
 public void f1(long userId){
	 Flag flag=flagMapper.selectByUserId(userId);
	 long flagId=flag.getFlagId();
	 long itemId=flag.getItmeId();
	 GameUtil.clearGameLife("exchange://"+flagId);
	// flagMapper.deleteFlagByUserId(userId);
	 f(itemId,userId);
 }	
 /**
  * 根据用户id查询用户银行
  * {@link #com.sky.game.service.persistence.UserBankMapper.selectUserBank()}
  * @see com.sky.game.service.persistence.UserBankMapper #selectUserBank()
  * @param userId
  * @return
  */
 public UserBank selectUserBank(long userId){
	 
	 return userBankMapper.selectUserBankByUserId(userId);
 }
}
