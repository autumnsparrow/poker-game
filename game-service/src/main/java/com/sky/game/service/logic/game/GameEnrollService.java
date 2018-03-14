/**
 * sparrow
 * game-service 
 * Jan 10, 2015- 11:29:59 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic.game;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.GameEnroll;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.domain.UserLogTypes;
import com.sky.game.service.logic.SequenceService;
import com.sky.game.service.persistence.GameBlockadeLogMapper;
import com.sky.game.service.persistence.GameBlockadeUserMapper;
import com.sky.game.service.persistence.GameEnrollMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserItemsMapper;

/**
 * @author sparrow
 *
 */
@Service
public class GameEnrollService {
	private static final Log logger = LogFactory
			.getLog(GameEnrollService.class);

	@Autowired
	SequenceService sequenceService;

	@Autowired
	UserAccountMapper userAccountMapper;

	@Autowired
	GameEnrollMapper gameEnrollMapper;

	@Autowired
	GameUserItemService gameUserItemService;

	@Autowired
	GameBlockadeUserMapper gameBlockadeUserMapper;

	@Autowired
	GameBlockadeLogMapper gameBlockadeLogMapper;

	@Autowired
	UserItemsMapper userItemsMapper;

	/**
	 * 
	 */
	public GameEnrollService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * checking if the user has min coins.
	 * 
	 * @param userId
	 * @param min
	 * @return
	 */
	public boolean validItemValue(long userId, int itemId, int min) {
		boolean ret = false;

		try {
			// sUserAccount account =
			// userAccountMapper.selectByPrimaryId(userId);
			// Map<ItemTypes, UserItems> items = account.getItemsAsMap();
			// ItemTypes t=ItemTypes.getItemTypes(itemId);
			// UserItems item = items.get(t);
			UserItems item = userItemsMapper.selectUserItemByUserIdAndItemId(
					Long.valueOf(userId), Long.valueOf(itemId));
			if (item != null) {
				ret = item.getItemValue() >= min;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return ret;
	}
	
	@Transactional
	public boolean unenroll(Long userId,Long roomId){
		
		boolean ret=false;
		
		try {
			GameEnroll gameEnroll=getGameEnrollByUserIdAndRoomId(userId, roomId);
			UserItemLog itemLog=gameUserItemService.getUserItemLog(gameEnroll.getUserItemLogId());
			// return the 
			Integer amount=itemLog.getValue()*-1;
			Long logId = gameUserItemService.updateUserItem(userId, itemLog.getItemId(),amount
					, amount >0 ? UserLogTypes.UnEnroll
							: UserLogTypes.Enroll);
			//updateEnrollState(gameEnroll.getId());
			gameEnroll.setEnrolled(Integer.valueOf(-1));
			gameEnroll.setUserItemLogId(logId);
			int affectedRows=gameEnrollMapper.updateByPrimaryKeySelective(gameEnroll);
			if(affectedRows>0){
				ret=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ret;
	}

	/**
	 * 
	 * @param userId
	 * @param roomId
	 * @param itemId
	 * @param amount
	 * @return
	 */
	@Transactional
	public boolean enroll(Long userId, Long roomId, Long teamId, Long itemId,
			Integer amount) {
		boolean ret = false;

		if (itemId == 0) {
			return true;
		}
	

		try {
			Long logId = gameUserItemService.updateUserItem(userId, itemId,
					amount, amount >0 ? UserLogTypes.UnEnroll
							: UserLogTypes.Enroll);
			int affectedRows = 0;
			if (amount <0) {
				GameEnroll o = GameUtil.obtain(GameEnroll.class);
				o.setRoomId(roomId);
				o.setUserId(userId);
				o.setTeamId(teamId);
				o.setEnrolled(Integer.valueOf(0));
				// Long logId = sequenceService.generateUserItemLogId();

				o.setUserItemLogId(logId);

				affectedRows = gameEnrollMapper.insert(o);
			}
			if (affectedRows > 0) {
				ret = true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return ret;
	}

	/**
	 * get the game enroll information by roomid
	 * 
	 * @param roomId
	 * @return
	 * @see GameEnrollMapper#selectByRoomId(Long)
	 */
	public List<GameEnroll> getGameEnrollByState(Long roomId) {
		List<GameEnroll> gameEnrolls = gameEnrollMapper.selectByRoomId(roomId);
		return gameEnrolls;
	}

	/**
	 * {@link GameEnrollMapper#selectByUserId(Long)}
	 * 
	 * @param userId
	 * @return
	 */
	public List<GameEnroll> getGameEnrollByUserId(Long userId) {
		List<GameEnroll> gameEnrolls = gameEnrollMapper.selectByUserId(userId);
		return gameEnrolls;
	}

	/**
	 * {@link GameEnrollMapper#selectByUserIdAndRoomId(Long, Long) }
	 * 
	 * @param userId
	 * @param roomId
	 * @return
	 */
	public GameEnroll getGameEnrollByUserIdAndRoomId(Long userId, Long roomId) {
	
		GameEnroll enroll = gameEnrollMapper.selectByUserIdAndRoomId(userId,
				roomId);
		
		return enroll;
	}

	/**
	 * 
	 * {@link GameEnrollMapper#updateEnrolledState(Long)}
	 * 
	 * @param id
	 * @return
	 */
	public boolean updateEnrollState(Long id) {
		boolean valid = false;
		int affectedRows = gameEnrollMapper.updateEnrolledState(id);
		if (affectedRows > 0) {
			valid = true;
		}
		return valid;
	}

	/**
	 * {@link GameEnrollMapper#updateTeamId(Long, Long)}
	 * 
	 * @param id
	 * @param teamId
	 * @return
	 * 
	 * 
	 */
	public boolean updateTeamId(Long id, Long teamId) {
		boolean valid = false;
		int affectedRows = gameEnrollMapper.updateTeamId(teamId, id);
		if (affectedRows > 0) {
			valid = true;
		}
		return valid;
	}

}
