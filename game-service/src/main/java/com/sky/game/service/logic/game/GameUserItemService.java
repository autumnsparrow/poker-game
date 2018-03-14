/**
 * sparrow
 * game-service 
 * Jan 10, 2015- 4:48:11 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic.game;

import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.UserLogTypes;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;

/**
 * @author sparrow
 *
 */
@Service
public class GameUserItemService {
	private static final Log logger=LogFactory.getLog(GameRewardService.class);
	
	
	@Autowired
	UserItemLogMapper userItemLogMapper;
	
	@Autowired
	UserItemsMapper userItemsMapper;
	
	@Autowired
	UserAccountMapper userAccountMapper;
	/**
	 * 
	 */
	public GameUserItemService() {
		// TODO Auto-generated constructor stub
	}
	
	public UserItemLog getUserItemLog(Long id){
		UserItemLog log= userItemLogMapper.selectByPrimaryKey(id);
		return log;
	}
	
	/**
	 * ItemTypes defined item
	 * 
	 * 
	 * @param userId
	 * @param itemId
	 * @param amount
	 * @param types
	 * @return
	 */
	
	public Long updateUserItem(Long userId,Long itemId,Integer amount,UserLogTypes types){
		boolean ret=false;
		if(userId==null||itemId==null||amount==null||types==null){
			logger.warn("Can't updateUserItem, parameters contains null value.");
			return Long.valueOf(0);
		}
		logger.info("updateUserItem["+userId+"] - itemId:"+itemId+ " value:"+amount+" types:"+types.description);
		// first get the item
		//UserAccount account=userAccountMapper.selectByPrimaryId(userId);
		//Map<ItemTypes,UserItems >  items=account.getItemsAsMap();
		Long userItemLogId=null;
		//ItemTypes t=ItemTypes.getItemTypes(itemId.intValue());
		UserItems item=userItemsMapper.selectUserItemByUserIdAndItemId(userId, itemId);//items.get(t);
		boolean created=false;
		if(item==null){
			item=GameUtil.obtain(UserItems.class);
			item.setItemId(itemId);
			item.setItemValue(Integer.valueOf(amount));
			item.setUserAccountId(userId);
			
			int affectedRows=userItemsMapper.insertSelective(item);
			if(affectedRows>0){
				logger.info("updateUserItem["+userId+"]  create item :"+itemId);
				created=true;
			}
		}
		
		if(item!=null){
			int oldValue=item.getItemValue();
			UserItemLog o=GameUtil.obtain(UserItemLog.class);
			//o.setId(userItemLogId);
			o.setItemId(itemId);
			o.setItemValue(oldValue);
			o.setUserAccountId(userId);
			o.setValue(amount);
			o.setResumType(types.description);
			int affectedRows=userItemLogMapper.insertSelective(o);
			userItemLogId=o.getId();
			if(!created&&affectedRows>0){
				int value=oldValue+amount;
				HashMap<String,Object> param=new HashMap<String,Object>();
				param.put("value", Integer.valueOf(value));
				param.put("itemId", itemId);
				param.put("userId", Long.valueOf(userId));
				affectedRows=userItemsMapper.updateUserItem(param);
				if(affectedRows>0){
					logger.info("updateUserItem Done!");
					ret=true;
				}
			}
			
			
			
		}
		
		return userItemLogId;
	}

}
