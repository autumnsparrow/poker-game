/**
 * 
 */
package com.sky.game.service.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.AuctionGoods;
import com.sky.game.service.domain.Freeze;
import com.sky.game.service.domain.GameBlockadeUser;
import com.sky.game.service.domain.MyAuctionGoods;
import com.sky.game.service.domain.PoinCard;
import com.sky.game.service.domain.PointCardAttribute;
import com.sky.game.service.domain.UserBank;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.persistence.FreezeMapper;
import com.sky.game.service.persistence.GameBlockadeUserMapper;
import com.sky.game.service.persistence.PointCardAttributeMapper;
import com.sky.game.service.persistence.PoinCardMapper;
import com.sky.game.service.persistence.UserBankMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;

/**
 * @author Administrator
 *
 *   拍买行
 */
@Service
public class AuctionService {

	/**
	 * 
	 */
	
	public AuctionService() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserItemsMapper userItemsMapper;
	@Autowired
	PoinCardMapper poinCardMapper;
	@Autowired
	UserBankMapper userBankMapper;
	@Autowired
	FreezeMapper freezeMapper;
	@Autowired
	PointCardAttributeMapper pointCardAttributemapper;
	@Autowired
	GameBlockadeUserMapper gameBlockUserMapper;
	@Autowired
	UserItemLogMapper userItemLogMapper;
	public static enum OutTypes{
		Auction(2,"竞拍"),
		FixedPrice(1,"一口价");
		
		public int value;
		public String message;
		
		
		private OutTypes(int value, String message) {
			this.value = value;
			this.message = message;
		}

		public boolean eq(OutTypes types){
			return this.value==types.value;
		}
		public String toString(){
			return message;
		}
	}
	public static enum TradeTypes{
		Gold(1,"金币"),
		Sycee(2,"元宝"),
		Diamond(3,"钻石");
		
		public int value;
		public String message;
		
		
		private TradeTypes(int value, String message) {
			this.value = value;
			this.message = message;
		}
		
		public boolean eq(TradeTypes types){
			return this.value==types.value;
		}
		public String toString(){
			return message;
		}
	}
	/*
     * 获取拍买行列表
     */
	public List<AuctionGoods> selectAuctionList(){
		
		List<AuctionGoods> auctionGoodsList=poinCardMapper.selectAuctionList();
		
		return auctionGoodsList;
	}
	
	
	/**
	 *  竞拍
	 *  
	 *   tradeType 1 金币¸  2 元宝   3 钻石
	 *   
	 *   outType   2 竞拍           1一口价
	 */
	HashMap<String,Object> cacheMap=new HashMap<String,Object>();
	public int auction(long userId,long id,String bankPassword,int count){
		int state=0;
		PoinCard pointCard=poinCardMapper.selectAuctionById(id);
		UserBank userBank=userBankMapper.selectUserBankByUserId(userId);
		
		if(pointCard!=null){
			//poinCardMapper.s
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("roomId",pointCard.getRoomId());
			PoinCard p=poinCardMapper.selectUserIsOne(map);
			if(p==null){
			if(Integer.valueOf(pointCard.getOutType())==OutTypes.FixedPrice.value){//一口价  
					if(Integer.valueOf(pointCard.getTradeType())==TradeTypes.Gold.value){//金币交易方式
						HashMap<String,Object> hashmap=new HashMap<String,Object>();
			    		 hashmap.put("userId", userId);
			    		 hashmap.put("itemId", 1001);
			    		UserItems userItems= userItemsMapper.selectUserItemByItemId(hashmap);
						if(userItems!=null){
			    		if(userItems.getItemValue()>pointCard.getNowPrice()){
							//GameWorldTimer.getGameWorldTimer().removeGameLifeObject(RemovalGameLife.obtain(id));
						//  日志  start
		    				UserItems userItem=userItemsMapper.selectByUserId(pointCard.getUserAccountId());
		        	    	UserItemLog userItemLog=new UserItemLog();
		        	    	//Long ids=sequenceService.generateUserItemLogId();
		        	    	//userItemLog.setId(ids);
		        	    	userItemLog.setItemId(userItem.getItemId());
		        	    	userItemLog.setValue(pointCard.getNowPrice());
		        	    	userItemLog.setItemValue(userItem.getItemValue());
		        	    	userItemLog.setResumType("一口价出售");
		        	    	userItemLog.setUserAccountId(pointCard.getUserAccountId());
		        	    	userItemLog.setUpdateTime(new Date());
		        	    	userItemLogMapper.insertSelective(userItemLog);
		        	    	//end
		        	    	//  日志  start
		        	    	UserItems userIt=userItemsMapper.selectByUserId(pointCard.getUserAccountId());
		        	    	UserItemLog userItemL=new UserItemLog();
		        	    	//Long ids=sequenceService.generateUserItemLogId();
		        	    	//userItemLog.setId(ids);
		        	    	userItemL.setItemId(userIt.getItemId());
		        	    	userItemL.setValue(-pointCard.getNowPrice()/20);
		        	    	userItemL.setItemValue(userIt.getItemValue());
		        	    	userItemL.setResumType("一口价佣金");
		        	    	userItemL.setUserAccountId(pointCard.getUserAccountId());
		        	    	userItemL.setUpdateTime(new Date());
		        	    	userItemLogMapper.insertSelective(userItemL);
		        	    	//end
							hashmap.put("ownerId", pointCard.getUserAccountId());
							hashmap.put("money", pointCard.getNowPrice()-pointCard.getNowPrice()/20);
							userItemsMapper.updateOwnerCoin(hashmap);//拥有者金币增加
							hashmap.put("pointCardId", id);
							hashmap.put("auctionUserId", userId);
						//  日志  start
		        	    	UserItems userIte=userItemsMapper.selectByUserId(userId);
		        	    	UserItemLog userItemLo=new UserItemLog();
		        	    	//Long ids=sequenceService.generateUserItemLogId();
		        	    	//userItemLog.setId(ids);
		        	    	userItemLo.setItemId(userIte.getItemId());
		        	    	userItemLo.setValue(-pointCard.getNowPrice());
		        	    	userItemLo.setItemValue(userIte.getItemValue());
		        	    	userItemLo.setResumType("一口价购卖");
		        	    	userItemLo.setUserAccountId(userId);
		        	    	userItemLo.setUpdateTime(new Date());
		        	    	userItemLogMapper.insertSelective(userItemLo);
		        	    	//end
							poinCardMapper.updateAuctionGiveAuctioner(hashmap);//购买者增加合成卡
							hashmap.put("value", pointCard.getNowPrice());//购买者金币减少
		    				userItemsMapper.updateUserCoinNum(hashmap);
		    			
		        	    	
		    				//竞拍成功改变积分卡的拥有者
		    				if(pointCard.getRoomId()==4000){//4000 房间
		    					hashmap.put("itId", 7000);
		    					}
		    					if(pointCard.getRoomId()==4001){//4001 房间
		    					hashmap.put("itId", 7100);
		    					}
		    					if(pointCard.getRoomId()==4002){//4002 房间
		    						hashmap.put("itId", 7200);	
		    					}
		    				userItemsMapper.updateUserBagPointCard(hashmap);
		    				//end
		    				state=1;
						}else{
							state=-4;//用户资金币不足
						}
						}else{
							state=-9;
						}
					}
					if(Integer.valueOf(pointCard.getTradeType())==TradeTypes.Sycee.value){//元宝交易
						HashMap<String,Object> hashmap=new HashMap<String,Object>();
						hashmap.put("userId", userId);
						hashmap.put("itemId", 1002);
						UserItems userItems= userItemsMapper.selectUserItemByItemId(hashmap);
						if(userItems!=null){
						if(userItems.getItemValue()>pointCard.getNowPrice()){
							//GameWorldTimer.getGameWorldTimer().removeGameLifeObject(RemovalGameLife.obtain(id));
						//  日志  start
		    				UserItems userItem=userItemsMapper.selectByUserId(pointCard.getUserAccountId());
		        	    	UserItemLog userItemLog=new UserItemLog();
		        	    	//Long ids=sequenceService.generateUserItemLogId();
		        	    	//userItemLog.setId(ids);
		        	    	userItemLog.setItemId(userItem.getItemId());
		        	    	userItemLog.setValue(pointCard.getNowPrice());
		        	    	userItemLog.setItemValue(userItem.getItemValue());
		        	    	userItemLog.setResumType("一口价出售");
		        	    	userItemLog.setUserAccountId(pointCard.getUserAccountId());
		        	    	userItemLog.setUpdateTime(new Date());
		        	    	userItemLogMapper.insertSelective(userItemLog);
		        	    	//end
		        	    //  日志  start
		        	    	UserItems userIt=userItemsMapper.selectByUserId(pointCard.getUserAccountId());
		        	    	UserItemLog userItemL=new UserItemLog();
		        	    	//Long ids=sequenceService.generateUserItemLogId();
		        	    	//userItemLog.setId(ids);
		        	    	userItemL.setItemId(userIt.getItemId());
		        	    	userItemL.setValue(-pointCard.getNowPrice()/20);
		        	    	userItemL.setItemValue(userIt.getItemValue());
		        	    	userItemL.setResumType("一口价佣金");
		        	    	userItemL.setUserAccountId(pointCard.getUserAccountId());
		        	    	userItemL.setUpdateTime(new Date());
		        	    	userItemLogMapper.insertSelective(userItemL);
		        	    	//end
							hashmap.put("ownerId", pointCard.getUserAccountId());
							hashmap.put("money", pointCard.getNowPrice()-pointCard.getNowPrice()/20);
							userItemsMapper.updateOwnerSys(hashmap);//拥有者元宝增加
							hashmap.put("pointCardId", id);
							hashmap.put("auctionUserId", userId);
							poinCardMapper.updateAuctionGiveAuctioner(hashmap);//购买者增加合成卡
						//  日志  start
		        	    	UserItems userIte=userItemsMapper.selectByUserId(userId);
		        	    	UserItemLog userItemLo=new UserItemLog();
		        	    	//Long ids=sequenceService.generateUserItemLogId();
		        	    	//userItemLog.setId(ids);
		        	    	userItemLo.setItemId(userIte.getItemId());
		        	    	userItemLo.setValue(-pointCard.getNowPrice());
		        	    	userItemLo.setItemValue(userIte.getItemValue());
		        	    	userItemLo.setResumType("一口价购卖");
		        	    	userItemLo.setUserAccountId(userId);
		        	    	userItemLo.setUpdateTime(new Date());
		        	    	userItemLogMapper.insertSelective(userItemLo);
		        	    	//end
							hashmap.put("value", pointCard.getNowPrice());//购买者金币减少
							userItemsMapper.updateUserSysNum(hashmap);
		        	    
							//竞拍成功改变积分卡的拥有者
							if(pointCard.getRoomId()==4000){//4000 房间
								hashmap.put("itId", 7000);
							}
							if(pointCard.getRoomId()==4001){//4001 房间
								hashmap.put("itId", 7100);
							}
							if(pointCard.getRoomId()==4002){//4002 房间
								hashmap.put("itId", 7200);	
							}
							userItemsMapper.updateUserBagPointCard(hashmap);
							//end
							state=1;
						}else{
							state=-4;//用户资金币不足
						}
						}else{
							state=-9;//用户没有对应的物品
						}
					}
					if(Integer.valueOf(pointCard.getTradeType())==TradeTypes.Diamond.value){//钻石交易
						HashMap<String,Object> hashmap=new HashMap<String,Object>();
						hashmap.put("userId", userId);
						hashmap.put("itemId", 1003);
						UserItems userItems= userItemsMapper.selectUserItemByItemId(hashmap);
						if(userItems!=null){
						if(userItems.getItemValue()>pointCard.getNowPrice()){
							//GameWorldTimer.getGameWorldTimer().removeGameLifeObject(RemovalGameLife.obtain(id));
						//  日志  start
		    				UserItems userItem=userItemsMapper.selectByUserId(pointCard.getUserAccountId());
		        	    	UserItemLog userItemLog=new UserItemLog();
		        	    	//Long ids=sequenceService.generateUserItemLogId();
		        	    	//userItemLog.setId(ids);
		        	    	userItemLog.setItemId(userItem.getItemId());
		        	    	userItemLog.setValue(pointCard.getNowPrice());
		        	    	userItemLog.setItemValue(userItem.getItemValue());
		        	    	userItemLog.setResumType("一口价出售");
		        	    	userItemLog.setUserAccountId(pointCard.getUserAccountId());
		        	    	userItemLog.setUpdateTime(new Date());
		        	    	userItemLogMapper.insertSelective(userItemLog);
		        	    	//end
		        	    	 //  日志  start
		        	    	UserItems userIt=userItemsMapper.selectByUserId(pointCard.getUserAccountId());
		        	    	UserItemLog userItemL=new UserItemLog();
		        	    	//Long ids=sequenceService.generateUserItemLogId();
		        	    	//userItemLog.setId(ids);
		        	    	userItemL.setItemId(userIt.getItemId());
		        	    	userItemL.setValue(-pointCard.getNowPrice()/20);
		        	    	userItemL.setItemValue(userIt.getItemValue());
		        	    	userItemL.setResumType("一口价佣金");
		        	    	userItemL.setUserAccountId(pointCard.getUserAccountId());
		        	    	userItemL.setUpdateTime(new Date());
		        	    	userItemLogMapper.insertSelective(userItemL);
		        	    	//end
							hashmap.put("ownerId", pointCard.getUserAccountId());
							hashmap.put("money", pointCard.getNowPrice()-pointCard.getNowPrice()/20);
							userItemsMapper.updateOwnerDim(hashmap);//拥有者钻石增加
							hashmap.put("pointCardId", id);
							hashmap.put("auctionUserId", userId);
							poinCardMapper.updateAuctionGiveAuctioner(hashmap);//购买者增加合成卡
						//  日志  start
		        	    	UserItems userIte=userItemsMapper.selectByUserId(userId);
		        	    	UserItemLog userItemLo=new UserItemLog();
		        	    	//Long ids=sequenceService.generateUserItemLogId();
		        	    	//userItemLog.setId(ids);
		        	    	userItemLo.setItemId(userIte.getItemId());
		        	    	userItemLo.setValue(-pointCard.getNowPrice());
		        	    	userItemLo.setItemValue(userIte.getItemValue());
		        	    	userItemLo.setResumType("一口价购卖");
		        	    	userItemLo.setUserAccountId(userId);
		        	    	userItemLo.setUpdateTime(new Date());
		        	    	userItemLogMapper.insertSelective(userItemLo);
		        	    	//end
							hashmap.put("value", pointCard.getNowPrice());//购买者金币减少
							userItemsMapper.updateUserDimNum(hashmap);
							
							//竞拍成功改变积分卡的拥有者
							if(pointCard.getRoomId()==4000){//4000 房间
								hashmap.put("itId", 7000);
							}
							if(pointCard.getRoomId()==4001){//4001 房间
								hashmap.put("itId", 7100);
							}
							if(pointCard.getRoomId()==4002){//4002 房间
								hashmap.put("itId", 7200);	
							}
							userItemsMapper.updateUserBagPointCard(hashmap);
							//end
							state=1;
						}else{
							state=-4;//用户资金币不足
						}
						}else{
							state=-9;
						}
					}
			}
			if(Integer.valueOf(pointCard.getOutType())==OutTypes.Auction.value){//竞拍   
				if(userBank!=null){
				     if(bankPassword.equals(userBank.getBankPw())){
				    	 /* 交易货币方式是金币时*/
				    	 if(Integer.valueOf(pointCard.getTradeType())== TradeTypes.Gold.value){
				    		 HashMap<String,Object> hashmap=new HashMap<String,Object>();
				    		 hashmap.put("userId", userId);
				    		 hashmap.put("itemId", 1001);
				    		 hashmap.put("id",id);
				    		UserItems userItems= userItemsMapper.selectUserItemByItemId(hashmap);
				    		int value=pointCard.getNowPrice()+count;
				    		if(userItems!=null){
				    			if(userItems.getItemValue()>value){
				    				//GameWorldTimer.getGameWorldTimer().removeGameLifeObject(RemovalGameLife.obtain(id));
				    				hashmap.put("pointCardId", id);
				    				Freeze freeze=freezeMapper.selectFreezeUpdate(hashmap);
				    				if(freeze!=null){//如果存在修改冻结表
				    					HashMap<String,Object> m=new HashMap<String,Object>();
				    					m.put("value", freeze.getFreezeCount());
				    					m.put("userId", freeze.getUserAccountId());
				    					m.put("itemId", freeze.getFreezeItemId());
				    				    userItemsMapper.updateLottery(m);
				    					freezeMapper.deleteByPointId(freeze.getId());
									}
				    				hashmap.put("value", value);
			    					userItemsMapper.updateUserCoinNum(hashmap);
					    				Freeze fz=new Freeze();
					    				fz.setId(null);
										fz.setUserAccountId(userId);
										fz.setOwnerId(pointCard.getUserAccountId());
										fz.setFreezeCount(value);
										fz.setPointCardId(id);
										fz.setFreezeItemId(1001L);
										freezeMapper.insert(fz);//冻结用户资金
									poinCardMapper.updateNowPrice(hashmap);//修改积分卡当前价格
									state=2;
				    			}else{
				    				state=-4;//用户账户余额不足
				    			}
				    		}else{
				    			state=-9;
				    		}
				    	 }
				    	 /* 交易货币方式是元宝时*/
				    	 if(Integer.valueOf(pointCard.getTradeType())== TradeTypes.Sycee.value){
				    		 HashMap<String,Object> hashmap=new HashMap<String,Object>();
				    		 hashmap.put("userId", userId);
				    		 hashmap.put("itemId", 1002);
				    		 hashmap.put("id",id);
				    		 UserItems userItems= userItemsMapper.selectUserItemByItemId(hashmap);
				    		 int value=pointCard.getNowPrice()+count;
				    		 if(userItems!=null){
				    			 if(userItems.getItemValue()>value){
				    				 //GameWorldTimer.getGameWorldTimer().removeGameLifeObject(RemovalGameLife.obtain(id));
				    				 /*Freeze freeze=freezeMapper.selectFreeze(hashmap);
				    				 hashmap.put("value", value);
				    				 userItemsMapper.updateUserCoinNum(hashmap);
				    				 if(freeze==null){//如果没有插入冻结表
				    					 Freeze fz=new Freeze();
				    					 fz.setId(null);
				    					 fz.setUserAccountId(userId);
				    					 fz.setOwnerId(pointCard.getUserAccountId());
				    					 fz.setFreezeCount(value);
				    					 fz.setPointCardId(id);
				    					 fz.setFreezeItemId(1002L);
				    					 freezeMapper.insert(fz);//冻结用户资金
				    				 }else{//如果存在修改冻结表
				    					 freeze.setFreezeCount(value); 
				    					 freezeMapper.updateByPrimaryKeySelective(freeze);
				    				 }
				    				 poinCardMapper.updateNowPrice(hashmap);//修改积分卡当前价格
				    				 state=2;
				    			 }else{
				    				 state=-4;//用户账户余额不足
*/		
				    				 hashmap.put("pointCardId", id);
					    				Freeze freeze=freezeMapper.selectFreezeUpdate(hashmap);
					    				if(freeze!=null){//如果存在修改冻结表
					    					HashMap<String,Object> m=new HashMap<String,Object>();
					    					m.put("value", freeze.getFreezeCount());
					    					m.put("userId", freeze.getUserAccountId());
					    					m.put("itemId", freeze.getFreezeItemId());
					    				    userItemsMapper.updateLottery(m);
					    					freezeMapper.deleteByPointId(freeze.getId());
										}
					    				hashmap.put("value", value);
				    					userItemsMapper.updateUserSysNum(hashmap);
						    				Freeze fz=new Freeze();
						    				fz.setId(null);
											fz.setUserAccountId(userId);
											fz.setOwnerId(pointCard.getUserAccountId());
											fz.setFreezeCount(value);
											fz.setPointCardId(id);
											fz.setFreezeItemId(1002L);
											freezeMapper.insert(fz);//冻结用户资金
										poinCardMapper.updateNowPrice(hashmap);//修改积分卡当前价格
										state=2;
					    			}else{
					    				state=-4;//用户账户余额不足
					    				}
				    		 }else{
				    			 state=-9;
				    		 }
				    	 }
				    	 /* 交易货币方式是钻石时*/
				    	 if(Integer.valueOf(pointCard.getTradeType())== TradeTypes.Diamond.value){
				    		 HashMap<String,Object> hashmap=new HashMap<String,Object>();
				    		 hashmap.put("userId", userId);
				    		 hashmap.put("itemId", 1003);
				    		 hashmap.put("id",id);
				    		 UserItems userItems= userItemsMapper.selectUserItemByItemId(hashmap);
				    		 int value=pointCard.getNowPrice()+count;
				    		 if(userItems!=null){
				    			 if(userItems.getItemValue()>value){
				    				 //GameWorldTimer.getGameWorldTimer().removeGameLifeObject(RemovalGameLife.obtain(id));
				    				/* Freeze freeze=freezeMapper.selectFreeze(hashmap);
				    				 hashmap.put("value", value);
				    				 userItemsMapper.updateUserCoinNum(hashmap);
				    				 if(freeze==null){//如果没有插入冻结表
				    					 Freeze fz=new Freeze();
				    					 fz.setId(null);
				    					 fz.setUserAccountId(userId);
				    					 fz.setOwnerId(pointCard.getUserAccountId());
				    					 fz.setFreezeCount(value);
				    					 fz.setPointCardId(id);
				    					 fz.setFreezeItemId(1003L);
				    					 freezeMapper.insert(fz);//冻结用户资金
				    				 }else{//如果存在修改冻结表
				    					 freeze.setFreezeCount(value); 
				    					 freezeMapper.updateByPrimaryKeySelective(freeze);
				    				 }
				    				 poinCardMapper.updateNowPrice(hashmap);//修改积分卡当前价格
				    				 state=2;
				    			 }else{
				    				 state=-4;//用户账户余额不足
*/				    			
				    				 hashmap.put("pointCardId", id);
					    				Freeze freeze=freezeMapper.selectFreezeUpdate(hashmap);
					    				if(freeze!=null){//如果存在修改冻结表
					    					HashMap<String,Object> m=new HashMap<String,Object>();
					    					m.put("value", freeze.getFreezeCount());
					    					m.put("userId", freeze.getUserAccountId());
					    					m.put("itemId", freeze.getFreezeItemId());
					    				    userItemsMapper.updateLottery(m);
					    					freezeMapper.deleteByPointId(freeze.getId());
										}
					    				hashmap.put("value", value);
				    					userItemsMapper.updateUserDimNum(hashmap);
						    				Freeze fz=new Freeze();
						    				fz.setId(null);
											fz.setUserAccountId(userId);
											fz.setOwnerId(pointCard.getUserAccountId());
											fz.setFreezeCount(value);
											fz.setPointCardId(id);
											fz.setFreezeItemId(1003L);
											freezeMapper.insert(fz);//冻结用户资金
										poinCardMapper.updateNowPrice(hashmap);//修改积分卡当前价格
										state=2;
					    			}else{
					    				state=-4;//用户账户余额不足
					    				}
				    		 }else{
				    			 state=-9;
				    		 }
				    	 }
				     }else{
				    	 state=-3;//银行密码错误
				     }
				}else{
					state=-2;//用户没有开通银行
				}
			}
			}else{
				state=-5;//用户已有本关积分卡
			}
		}else{
			state=-1;//积分卡不存在
		}
		return state;
	}
	/*
     * 我的拍买行列表
     */
	public List<MyAuctionGoods> selectMyAuctionList(long userId){
		
		List<MyAuctionGoods> myAuctionGoodsList=poinCardMapper.selectMyAuctionList(userId);
		
		return myAuctionGoodsList;
	}
	/*
	 *  拍买行  上拍   确定
	 *   
	 */
	public int arsis(long id,String type,int price,String chooseCoin){
		int state=0;
		long typeId=poinCardMapper.selectByIdFindTypeId(id);
		PointCardAttribute poinCardAttribute=pointCardAttributemapper.selectByPrimaryKey(typeId);
		Date d=poinCardAttribute.getLoseEffectTime();
		java.util.Calendar c=java.util.Calendar.getInstance();
        c.setTime(d);
        java.util.Calendar c1=java.util.Calendar.getInstance();
        c1.setTime(new Date());
        int a= c.compareTo(c);
        if(a>=0){
		if(poinCardAttribute!=null){
			if(Integer.valueOf(chooseCoin)==TradeTypes.Gold.value){
			if(poinCardAttribute.getLowOutPrice()<=price && poinCardAttribute.getHightOutPrice()>=price){
				HashMap<String,Object> hashmap=new HashMap<String,Object>();
				hashmap.put("id", id);
				hashmap.put("price", price);
				hashmap.put("type", type);
				hashmap.put("chooseCoin", chooseCoin);
				hashmap.put("startTime", new Date());//起拍时间
				hashmap.put("endTime",new Date(new Date().getTime()+2*60*60*1000));//起拍时间
				state=poinCardMapper.updateMyAuction(hashmap);//物品上拍
				/******上拍定时  start****/
				final long ids=id;//物品定时的唯一标识
				String url="jl://"+id;
				GameUtil.gameLife(url,2*3600*1000L,this,"auctionSuccessOrPass",ids).setGameSession(GameUtil.DEFAULT_GAMESESSION);;
				/*GameUtil.gameLife(url, "0 0 0/2 * * *",this,"auctionSuccessOrPass",ids);*/
				/******end*******/
			   }else{
				   state=-1;//上拍价格不在规定区间内
			   }
			}
			if(Integer.valueOf(chooseCoin)==TradeTypes.Sycee.value){
				if(poinCardAttribute.getLowOutPrice()<=price*10 && poinCardAttribute.getHightOutPrice()>=price*10){
					HashMap<String,Object> hashmap=new HashMap<String,Object>();
					hashmap.put("id", id);
					hashmap.put("price", price);
					hashmap.put("type", type);
					hashmap.put("chooseCoin", chooseCoin);
					hashmap.put("startTime", new Date());//起拍时间
					hashmap.put("endTime",new Date(new Date().getTime()+2*60*60*1000));//起拍时间
					state=poinCardMapper.updateMyAuction(hashmap);//物品上拍
					/******上拍定时  start****/
					final long ids=id;//物品定时的唯一标识
					String url="jl://"+id;
					GameUtil.gameLife(url,2*3600*1000L,this,"auctionSuccessOrPass",ids);
					/*new AbstractGameLife(2*3600*1000L){
					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return String.valueOf(ids);
					}
					@Override
					public void timeout() {
						// TODO Auto-generated method stub
						super.timeout();
						auctionSuccessOrPass(ids);
					}
				}.start();*/
					/******end*******/
				}else{
					state=-1;//上拍价格不在规定区间内
				}
			}
			if(Integer.valueOf(chooseCoin)==TradeTypes.Diamond.value){
				if(poinCardAttribute.getLowOutPrice()<=price*100 && poinCardAttribute.getHightOutPrice()>=price*100){
					HashMap<String,Object> hashmap=new HashMap<String,Object>();
					hashmap.put("id", id);
					hashmap.put("price", price);
					hashmap.put("type", type);
					hashmap.put("chooseCoin", chooseCoin);
					hashmap.put("startTime", new Date());//起拍时间
					hashmap.put("endTime",new Date(new Date().getTime()+2*60*60*1000));//起拍时间
					state=poinCardMapper.updateMyAuction(hashmap);//物品上拍
					/******上拍定时  start****/
					final long ids=id;//物品定时的唯一标识
					String url="jl://"+id;
					GameUtil.gameLife(url,2*3600*1000L,this,"auctionSuccessOrPass",ids);
					/*new AbstractGameLife(2*3600*1000L){
					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return String.valueOf(ids);
					}
					@Override
					public void timeout() {
						// TODO Auto-generated method stub
						super.timeout();
						auctionSuccessOrPass(ids);
					}
				}.start();*/
					/******end*******/
				}else{
					state=-1;//上拍价格不在规定区间内
				}
			}
		}
        }else{
        	state=-2;//时间超出有效期
        }
		return state;
	}
	
	public void auctionSuccessOrPass(long pointCardId){
		List<Freeze> freezeList=freezeMapper.selectByPointId(pointCardId);
		PoinCard pi=poinCardMapper.selectAuctionById(pointCardId);
		if(freezeList!=null&&freezeList.size()>0){
			if(freezeList.size()==1){
				long value=freezeList.get(0).getFreezeCount();
				int a=Long.valueOf(value).intValue();
				long itemId=freezeList.get(0).getFreezeItemId();
				long ownId=freezeList.get(0).getOwnerId();
				long userId=freezeList.get(0).getUserAccountId();
				long id=freezeList.get(0).getId();
				freezeMapper.deleteByPointId(id);
				HashMap<String,Object> hashmap =new HashMap<String,Object>();
				hashmap.put("userId", ownId);
				hashmap.put("itemId",itemId);
				UserItems userItems=userItemsMapper.selectUserItemByItemId(hashmap);
				value=value-Long.valueOf(value).intValue()/20;
				hashmap.put("value", value);
			//  日志  start
							UserItems userItem=userItemsMapper.selectByUserId(ownId);
			    	    	UserItemLog userItemLog=new UserItemLog();
			    	    	//Long ids=sequenceService.generateUserItemLogId();
			    	    	//userItemLog.setId(ids);
			    	    	userItemLog.setItemId(userItem.getItemId());
			    	    	userItemLog.setValue(a);
			    	    	userItemLog.setItemValue(userItem.getItemValue());
			    	    	userItemLog.setResumType("拍卖者获得");
			    	    	userItemLog.setUserAccountId(ownId);
			    	    	userItemLog.setUpdateTime(new Date());
			    	    	userItemLogMapper.insertSelective(userItemLog);
			    	    	//end
				if(userItems!=null){
					userItemsMapper.updateAddUserItem(hashmap);
				//  日志  start
					UserItems userIte=userItemsMapper.selectByUserId(ownId);
	    	    	//Long ids=sequenceService.generateUserItemLogId();
	    	    	//userItemLog.setId(ids);
	    	    	UserItemLog userItemLo=new UserItemLog();
	    	    	userItemLo.setItemId(userIte.getItemId());
	    	    	userItemLo.setValue(-a/20);
	    	    	userItemLo.setItemValue(userIte.getItemValue()+a/20);
	    	    	userItemLo.setResumType("拍卖者佣金");
	    	    	userItemLo.setUserAccountId(ownId);
	    	    	userItemLo.setUpdateTime(new Date());
	    	    	userItemLogMapper.insertSelective(userItemLo);
	    	    	//end
				}else{
					userItemsMapper.insertLottery(hashmap);
				//  日志  start
					UserItems userIte=userItemsMapper.selectByUserId(ownId);
	    	    	//Long ids=sequenceService.generateUserItemLogId();
	    	    	//userItemLog.setId(ids);
	    	    	UserItemLog userItemLo=new UserItemLog();
	    	    	userItemLo.setItemId(userIte.getItemId());
	    	    	userItemLo.setValue(-a/20);
	    	    	userItemLo.setItemValue(userIte.getItemValue());
	    	    	userItemLo.setResumType("拍卖者佣金");
	    	    	userItemLo.setUserAccountId(ownId);
	    	    	userItemLo.setUpdateTime(new Date());
	    	    	userItemLogMapper.insertSelective(userItemLo);
	    	    	//end
				}
				
			//  日志  start
    	    	UserItems userIt=userItemsMapper.selectByUserId(userId);
    	    	//Long ids=sequenceService.generateUserItemLogId();
    	    	//userItemLog.setId(ids);
    	    	UserItemLog userItemL=new UserItemLog();
    	    	userItemL.setItemId(userIt.getItemId());
    	    	userItemL.setValue(-a);
    	    	userItemL.setItemValue(userIt.getItemValue()+a);
    	    	userItemL.setResumType("竞拍者花费");
    	    	userItemL.setUserAccountId(userId);
    	    	userItemL.setUpdateTime(new Date());
    	    	userItemLogMapper.insertSelective(userItemL);
    	    	//end
				hashmap.put("pointCardId", pointCardId);
				hashmap.put("auctionUserId", userId);
				poinCardMapper.updateAuctionGiveAuctioner(hashmap);
    	    	
				//竞拍成功改变积分卡的拥有者
				HashMap<String,Object> map=new HashMap<String,Object>();
				map.put("userId", userId);
				map.put("ownId", ownId);
				if(pi.getRoomId()==4000){//4000 房间
					map.put("itId", 7000);
					}
					if(pi.getRoomId()==4001){//4001 房间
					map.put("itId", 7100);
					}
					if(pi.getRoomId()==4002){//4002 房间
						map.put("itId", 7200);	
					}
				userItemsMapper.updateUserBagPointCard(map);
				//改变用户背包的积分卡的拥有者
				//userItemsMapper.updateAddUserItem(map);
				//end
			}else{
			for(int i=0;i<freezeList.size()-1;i++){
				freezeMapper.deleteByPointId(freezeList.get(i).getId());
				//long userId=freezeList.get(i).getOwnerId();
				int value= freezeList.get(i).getFreezeCount();
				long itemId=freezeList.get(i).getFreezeItemId();
				long auctionUserId=freezeList.get(i).getUserAccountId();
				HashMap<String,Object> hashmap =new HashMap<String,Object>();
				hashmap.put("value", value);
				hashmap.put("userId", auctionUserId);
				hashmap.put("itemId",itemId);
				UserItems userItems=userItemsMapper.selectUserItemByItemId(hashmap);
				hashmap.put("value", value);
				if(userItems!=null){
					userItemsMapper.updateAddUserItem(hashmap);
				}else{
					userItemsMapper.insertLottery(hashmap);
				}
			     }
			long value=freezeList.get(freezeList.size()-1).getFreezeCount();
			long itemId=freezeList.get(freezeList.size()-1).getFreezeItemId();
			long ownId=freezeList.get(freezeList.size()-1).getOwnerId();
			long userId=freezeList.get(freezeList.size()-1).getUserAccountId();
			long id=freezeList.get(freezeList.size()-1).getId();
			freezeMapper.deleteByPointId(id);
			HashMap<String,Object> hashmap =new HashMap<String,Object>();
			hashmap.put("userId", ownId);
			hashmap.put("itemId",itemId);
			UserItems userItems=userItemsMapper.selectUserItemByItemId(hashmap);
			value=value-value/20;
			hashmap.put("value", value);
			if(userItems!=null){
				userItemsMapper.updateUserItem(hashmap);
			}else{
				userItemsMapper.insertLottery(hashmap);
			}
			hashmap.put("pointCardId", pointCardId);
			hashmap.put("auctionUserId", userId);
			poinCardMapper.updateAuctionGiveAuctioner(hashmap);
			
			
			//竞拍成功改变积分卡的拥有者
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("ownId", ownId);
			if(pi.getRoomId()==4000){//4000 房间
				map.put("itId", 7000);
				}
				if(pi.getRoomId()==4001){//4001 房间
				map.put("itId", 7100);
				}
				if(pi.getRoomId()==4002){//4002 房间
					map.put("itId", 7200);	
				}
			userItemsMapper.updateUserBagPointCard(map);
			//end
			}
		}
		else{
			poinCardMapper.updateAuctionOutTime(pointCardId);//流拍
		}
	}
	
	
	/*@Scheduled(fixedRate=1000)
	public void scheduledTime(){
	
	}*/
	
	/*
	 * 积分卡合成  
	 */
	@Transactional
	public int insertPointCard(long userId,long roomId){
		int state=-1;//合成失败
		HashMap<String,Object> hashmap=new HashMap<String,Object>();
		hashmap.put("userId",userId);
		hashmap.put("roomId",roomId);
		GameBlockadeUser gameBlockadeUser=gameBlockUserMapper.selectByUserId(hashmap);
	    PointCardAttribute po=pointCardAttributemapper.selectAll(hashmap);
	    PoinCard p4=poinCardMapper.selectUserIsOne(hashmap);
	    if(p4!=null){
	    	//积分卡限制
	    	state=-3;
	    }else{
		if(po!=null){
			PoinCard p=new PoinCard();
			p.setTypeId(po.getId());//积分卡属性id
			p.setStartPrice(0);//起拍价
			p.setNowPrice(0);//当前价格
			p.setUserAccountId(userId);//用户id
			p.setName(po.getTypeName());//积分卡名称
			p.setRoomId(roomId);//积分卡所属赛场
			p.setOwnFen(new Long(gameBlockadeUser.getIntegral()).intValue());//拥有积分
			p.setGetTime(new Date());//合成时间
			p.setStartTime(new Date());//起拍时间
			p.setEndTime(new Date());//结束时间
			p.setLastTardePrice(0);//最后一次成交价格
			p.setFlag(2);//标志  1 表示正在拍买  2 表示没有进行拍买
			p.setTradeType(po.getTradeType());//积分卡货币交易类型
			p.setOutType(po.getAllowOutType());//拍卖方式 1:一口价 2:竞拍
			p.setIconId(po.getIconId());//图片id
			p.setEffectTime(new Date(new Date().getTime() + po.getEffectDate()* 24 * 60 * 60 * 1000));
			state=poinCardMapper.inserPointCard(p);
			//清空积分
			gameBlockUserMapper.deleteById(gameBlockadeUser.getId());
			//插入用户背包物品
			if(roomId==4000){//4000 房间
			hashmap.put("itemId", 7000);
			}
			if(roomId==4001){//4001 房间
			hashmap.put("itemId", 7100);
			}
			if(roomId==4002){//4002 房间
				hashmap.put("itemId", 7200);	
			}
			/*UserItems useri=userItemsMapper.selectUserItemByItemId(hashmap);
			if(useri!=null){
				int value=useri.getItemValue()+1;
				hashmap.put("value", value);
				userItemsMapper.updateUserItem(hashmap);
			}else{*/
			 userItemsMapper.insertIntoPoint(hashmap);
			/*}*/
			}else{
				state=-2;//没有该房间对应级别的积分卡
			}
	    }
		return state;
	}
	/**
	 * 积分卡使用  
	 */
	@Transactional
    public int userUsePoint(long userId,long roomId){
    	int state=-1;
    	HashMap<String,Object> hashmap=new HashMap<String,Object>();
		hashmap.put("userId",userId);
		hashmap.put("roomId",roomId);
    	PoinCard p4=poinCardMapper.selectxzUserUse(hashmap);
    	if(p4!=null){
    		/*int level=pointCardAttributemapper.selectByPrimaryKey(p4.getTypeId()).getLevel();*/
    		if(roomId==4001){
    			hashmap.put("itemId",7100);
    		}
    		userItemsMapper.deleteUserPointCard(hashmap);//删除用户背包中的积分卡
    		poinCardMapper.deletePointCardById(p4.getId());//删除积分卡根据积分卡id
    		//poinCardMapper
    		int ownFen=p4.getOwnFen();
    		hashmap.put("ownFen",ownFen);
    		/*hashmap.put("level", 2);*/
    		state=gameBlockUserMapper.updateFenAndLevel(hashmap);
    		}
    	return state;
    }
public static void main(String[] args) {
	/*SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
	System.out.println(df.format(new Date(new Date().getTime() + 2* 24 * 60 * 60 * 1000)));*/
	long a=2000L;
	int b=Long.valueOf(a/20).intValue();
    System.out.println(b);
}
}
