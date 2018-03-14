/**
 * sparrow
 * game-service 
 * Jan 10, 2015- 4:51:09 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic.game;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserLogTypes;
import com.sky.game.service.domain.UserPropertiesLog;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserExtraMapper;
import com.sky.game.service.persistence.UserPropertiesLogMapper;

/**
 * 
 
 |  8 | Experience      | 经验               |       5 |
 * | 14 | DsFen           | 大师积分           |       5 |
| 15 | TtFen           | 天梯积分           |       5 |
| 16 | RpValue         | RP值               |       5 |
| 17 | CreditValue     | 信誉值             |       5 |
| 18 | MlValue         | 魅力值             |       5 |
| 19 | MaxGot          | 最大获胜倍数       |       5 |
| 20 | BestBet         | 最佳战绩           |       5 |
 * @author sparrow
 *
 */
@Service
public class GameUserPropertyService {
	private static final Log logger=LogFactory.getLog(GameUserPropertyService.class);
	@Autowired
	UserExtraMapper userExtraMapper;
	
	@Autowired
	UserPropertiesLogMapper userPropertiesLogMapper;
	
	@Autowired
	UserAccountMapper userAccountMapper;

	/**
	 * 
	 */
	public GameUserPropertyService() {
		// TODO Auto-generated constructor stub
	}
	
	public Long  updateUserProperty(Long userId,Long itemId,Integer amount,UserLogTypes types){
		boolean ret=false;
		if(userId==null||itemId==null||amount==null||types==null){
			logger.warn("Can't updateUserItem, parameters contains null value.");
			return Long.valueOf(0);
		}
		logger.info("updateUserProperty["+userId+"] - itemId:"+itemId+ " value:"+amount+" types:"+types.toString());
		// first get the item
	//	UserAccount account=userAccountMapper.selectByPrimaryId(userId);
		//Map<PropertyTypes,UserExtra> m= account.getPropertiesAsMap();
		PropertyTypes t=PropertyTypes.getTypes(itemId.intValue());
		UserExtra o=userExtraMapper.selectByAccountIdAndPropertyId(userId, itemId);//m.get(t);
		boolean created=false;
		if(o==null){
			o=new UserExtra();
			o.setPropertyId(itemId);
			o.setPropertyValue(String.valueOf(amount.intValue()));
			o.setUserAccountId(userId.longValue());
			
			int affectedRows=userExtraMapper.insert(o);
			if(affectedRows>0){
				logger.info("updateUserProperty["+userId+"]  create extra :"+t.toString());
				created=true;
			}
		}
		Long logId=null;
		if(o!=null){
			int oldValue=GameUtil.s2i(o.getPropertyValue());
			UserPropertiesLog l=GameUtil.obtain(UserPropertiesLog.class);
			//l.setId(logId);
			l.setPropertyId(t.id);
			l.setCurrentPropertyValue(o.getPropertyValue());
			l.setPropertyValue(amount.toString());
			l.setLogType(types.description);
			l.setUserAccountId(userId);
			int affectedRows=userPropertiesLogMapper.insertSelective(l);
			
			if(!created&&affectedRows>0){
				logId=l.getId();
				int value=oldValue+amount.intValue();
				o.setPropertyValue(String.valueOf(value));
				affectedRows=userExtraMapper.updateByPrimaryKey(o);
				if(affectedRows>0){
					ret=true;
					logger.info("updateUserItem Done!");
				}
			}
			
		}
		
		
		return logId;
	}
	

}
