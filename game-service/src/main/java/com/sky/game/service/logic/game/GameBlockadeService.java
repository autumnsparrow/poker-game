/**
 * sparrow
 * game-service 
 * Jan 16, 2015- 3:10:36 PM
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
import com.sky.game.service.domain.GameBlockadeLog;
import com.sky.game.service.domain.GameBlockadeUser;
import com.sky.game.service.logic.SequenceService;
import com.sky.game.service.persistence.GameBlockadeLogMapper;
import com.sky.game.service.persistence.GameBlockadeUserMapper;


/**
 * 
 * 
 * bloacked service
 * @author sparrow
 *
 */
@Service
public class GameBlockadeService {
	private static final Log logger = LogFactory
			.getLog(GameBlockadeService.class);

	@Autowired
	SequenceService sequenceService;

	@Autowired
	GameBlockadeUserMapper gameBlockadeUserMapper;

	@Autowired
	GameBlockadeLogMapper gameBlockadeLogMapper;
	/**
	 * 
	 */
	public GameBlockadeService() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * get the game blockade user in the same level
	 * return the list of {@link GameBlockadeUser}
	 * @param roomId
	 * @param level
	 * @return  List<GameBlockadeUser>
	 */
	public List<GameBlockadeUser> getGameBlockadeUserByRoomLevel(Long roomId,Long level){
		List<GameBlockadeUser> users=null;
		
		try {
			if (level == null || roomId == null) {
				logger.warn("Can't getGameBlockadeUserByRoomLevel, parameters contains null value.");
				return null;
			}
			
			users=gameBlockadeUserMapper.selectByRoomIdAndLevel(roomId, level);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return users;
	}
	
	
	/**
	 * get the blockade user by id.
	 * @param id
	 * @return
	 */
	public GameBlockadeUser getBlockadeUser(Long id){
		GameBlockadeUser user=gameBlockadeUserMapper.selectByPrimaryKey(id);
		return user;
	}
	
	
	/**
	 * get the blockade user by userid.
	 * @param userId
	 * @param roomId
	 * @return
	 */
	public GameBlockadeUser getGameBlockadeUser(Long userId,Long roomId){
		
		GameBlockadeUser user = null;
		
		try {
			if (userId == null || roomId == null) {
				logger.warn("Can't getGameBlockadeUser, parameters contains null value.");
				return null;
			}
			
			user=gameBlockadeUserMapper
					.selectByUserIdAndRoomId(userId, roomId);
			
			
			if(user==null){
				user=GameUtil.obtain(GameBlockadeUser.class);
				user.setId(Long.valueOf(0));
				user.setIntegral(Long.valueOf(0));
				user.setLevel(Integer.valueOf(0));
				user.setRoomId(roomId);
				user.setUserId(userId);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	
	
	/**
	 * 
	 * @param roomId
	 * @param level
	 * @return
	 */
	public int selectBlockadeUserCountByRoomIdAndLevel(Long roomId,Integer level){
		int rows=0;
		try {
			if (roomId == null || level == null) {
				logger.warn("Can't selectBlockadeUserByRoomIdAndLevel, parameters contains null value.");
				return rows;
			}
			logger.info("selectBlockadeUserByRoomIdAndLevel[" + roomId + "] - level:" + level
					);
			Long result=gameBlockadeUserMapper.selectByRoomAndLevel(roomId,Long.valueOf( level.intValue()));
			if(result!=null){
				rows=result.intValue();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rows;
	}
	
	
	/**
	 * 
	 * 
	 * update the blockade user status.
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean updateBlockadeUserStatus(Long id,Integer status){
		boolean ret=false;
		
		try {
			if (id == null || status == null) {
				logger.warn("Can't updateBlockadeUserStatus, parameters contains null value.");
				return ret;
			}
			logger.info("updateBlockadeUserStatus[" + id + "] - status:" + status
					);
			int affectedRows=gameBlockadeUserMapper.updateStatus(id, status);
			if(affectedRows>0)
				ret=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	

	/**
	 * update the blockade
	 * 
	 * @param userId
	 * @param roomId
	 * @param value reward integral.
	 * @return
	 */
	@Transactional
	public boolean updateBlockade(Long userId, Long roomId, Long value,Integer level) {
		boolean ret = false;

		try {
			if (userId == null || roomId == null || value == null) {
				logger.warn("Can't updateBlockade, parameters contains null value.");
				return ret;
			}
			logger.info("updateBlockade[" + userId + "] - userId:" + userId
					+ " roomId:" + roomId + " types:" + value);
			int affectedRows = 0;
			GameBlockadeUser user = gameBlockadeUserMapper
					.selectByUserIdAndRoomId(userId, roomId);
			
			if (user == null) {
				user = GameUtil.obtain(GameBlockadeUser.class);
				user.setLevel(Integer.valueOf(1));
				user.setRoomId(roomId);
				user.setUserId(userId);
				user.setIntegral(Long.valueOf(0));
				user.setLevel(Integer.valueOf(level));

				//Long id = sequenceService.genGameBlockadeUserId();
				//user.setId(id);
				affectedRows = gameBlockadeUserMapper.insertSelective(user);
				if (affectedRows > 0)
					ret = true;
			} else {
			
					ret = true;
			}

			if (ret) {
				GameBlockadeLog o = GameUtil.obtain(GameBlockadeLog.class);
				o.setGameBlockadeUserId(user.getId());
				o.setOldValue(user.getIntegral());
				o.setValue(value);
				
				affectedRows = gameBlockadeLogMapper.insertSelective(o);
				if (affectedRows > 0) {
					long integral = o.getOldValue().longValue()
							+ o.getValue().longValue();
					user.setIntegral(Long.valueOf(integral));
					user.setLevel(level);
					
					affectedRows = gameBlockadeUserMapper
							.updateByPrimaryKeySelective(user);
				}
				if (affectedRows > 0) {
					logger.info("updateBlockade Done!");
					ret = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return ret;
	}


}
