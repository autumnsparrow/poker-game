package com.sky.game.service.logic.game;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.GameReward;
import com.sky.game.service.domain.UserLogTypes;
import com.sky.game.service.logic.SequenceService;
import com.sky.game.service.persistence.GameRewardMapper;

@Service
public class GameRewardService {
	private static final Log logger=LogFactory.getLog(GameRewardService.class);
	
	
	@Autowired
	GameUserItemService gameUserItemService;
	@Autowired
	GameUserPropertyService  gameUserPropertyService;
	@Autowired
	SequenceService sequenceService;
	
	@Autowired
	GameRewardMapper gameRewardMapper;
	
	public GameRewardService() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * 
	 * @param userId
	 * @param roomId
	 * @param propertyId
	 * @param propertyValue
	 * @return
	 */
	public boolean updateProperty(Long userId,Long roomId,Long propertyId,Integer propertyValue){
		boolean ret=false;
		//Long userPropertyLogId=sequenceService.generateUserPropertyLogId();
		
		Long propertyLogId=gameUserPropertyService.updateUserProperty( userId, propertyId, propertyValue, UserLogTypes.Reward);
		if(propertyLogId!=null)
			ret=true;
		return ret;
	}

	
	/**
	 * 
	 * @param userId
	 * @param roomId
	 * @param itemId
	 * @param amount
	 * @param rank
	 * @return
	 */
	@Transactional
	public boolean reward(Long userId,Long roomId,Long teamId,Long itemId,Integer amount,Integer rank){
		boolean ret=false;
		
		Long userItemLogId=gameUserItemService.updateUserItem(userId, itemId, amount, UserLogTypes.Reward);
		GameReward o=GameUtil.obtain(GameReward.class);
		o.setRank(rank);
		o.setRoomId(roomId);
		o.setUserId(userId);
		o.setTeamId(teamId);
		//Long userItemLogId=sequenceService.generateUserItemLogId();
		
		o.setUserItemLogId(userItemLogId);
		o.setUserPropertyLogId(Long.valueOf(-1));
		
		int affectedRow=gameRewardMapper.insert(o);
		if(affectedRow>0){
			ret=true;
		}
		
		return ret;
		
	}
}
