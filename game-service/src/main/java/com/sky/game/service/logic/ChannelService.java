package com.sky.game.service.logic;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.service.domain.ChannelExchangeInfo;
import com.sky.game.service.domain.ChannelItems;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.persistence.ChannelExchangeInfoMapper;
import com.sky.game.service.persistence.ChannelItemsMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserItemsMapper;

@Service
public class ChannelService {

	public ChannelService() {
		// TODO Auto-generated constructor stub
	}
	@Autowired
	UserItemsMapper userItemsMapper;
	@Autowired
	UserAccountMapper userAccountMapper;
	@Autowired
	ChannelExchangeInfoMapper channelExchangeInfoMapper;
	@Autowired
	ChannelItemsMapper channelItemsMapper;
	/**
	 * 用户渠道物品判断
	 * 根据用户id和物品id查询用户的物品数量
	 * {@link com.sky.game.service.persistence.UserItemsMapper.selectUserItemByItemId(hashmap)}}
	 * @see com.sky.game.service.persistence.UserItemsMapper#selectUserItemByItemId(hashmap)
	 * 根据用户id查询用户判断用户channel_id
	 * {@link com.sky.game.service.persistence.UserAccountMapper.selectByPrimaryId(long userId)}}
	 * @see com.sky.game.service.persistence.UserAccountMapper#selectByPrimaryId(long userId)
	 * @param userId
	 * @param itemId
	 * @return
	 */
    public int userChannelGoods(long userId,long itemId){
    	HashMap<String,Object> hashmap=new HashMap<String,Object>();
    	hashmap.put("userId",userId);
    	hashmap.put("itemId",itemId);
    	UserItems userItems=userItemsMapper.selectUserItemByItemId(hashmap);
    	UserAccount userAccount=userAccountMapper.selectByPrimaryId(userId);
    	int state=0;
    	if(userItems!=null){
    		if(userItems.getItemValue()>0){
    			if(userAccount.getChannelId()==2000){
    				if(userItems.getItemValue()>=20){//20表示兑换彩券需要的彩豆数，看看用户的彩豆数是否满足条件
    					state=1;
    				}else{
    					state=-1;//用户物品不足
    				}
    			}
    		}else{
    		 state=-1;
    		 //用户物品不足
    		}
    	}else{
    		state=-3;
    		//用户没有该物品
    	}
    	return state;
    }
    /**
     * 查入用户渠道兑换物品记录
     * {@link com.sky.game.service.persistence.ChannelExchangeInfoMapper.insert(ChannelExchangeInfo record)}}
     * @see com.sky.game.service.persistence.ChannelExchangeInfoMapper#insert(ChannelExchangeInfo record)
     * @param record
     * @return
     */
    public int insertCEI(long userId,int num,String channelAccount){
    	ChannelExchangeInfo record=new ChannelExchangeInfo();
    	UserAccount userAccount=userAccountMapper.selectByPrimaryId(userId);
    	long channelId=userAccount.getChannelId();
    	ChannelItems cis=channelItemsMapper.selectByChannelId(channelId);
    	long channelItemId=cis.getChannelItemId();
    	record.setUserAccountId(userId);
    	record.setItemValue(num);
    	record.setChannelId(channelId);
    	record.setChannelItemId(channelItemId);
    	record.setChannelItemValue(1);
    	record.setChannelAccount(channelAccount);
    	record.setStatus(0);
		int result=channelExchangeInfoMapper.insert(record);
		return result;
	}
}
