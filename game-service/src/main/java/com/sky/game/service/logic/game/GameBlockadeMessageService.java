/**
 * sparrow
 * game-service 
 * Jan 27, 2015- 10:07:21 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic.game;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.GameBlockadeMessage;
import com.sky.game.service.domain.GameBlockadeMessageMap;
import com.sky.game.service.domain.GameBlockadeMessageStateTypes;
import com.sky.game.service.domain.GameBlockadeUser;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.logic.SequenceService;
import com.sky.game.service.persistence.GameBlockadeMessageMapMapper;
import com.sky.game.service.persistence.GameBlockadeMessageMapper;

/**
 * @author sparrow
 *
 */
@Service
public class GameBlockadeMessageService {

	private static final Log logger = LogFactory
			.getLog(GameBlockadeMessageService.class);
	@Autowired
	GameBlockadeMessageMapMapper gameBlockadeMessageMapMapper;

	@Autowired
	GameBlockadeMessageMapper gameBlockadeMessageMapper;

	@Autowired
	SequenceService sequenceService;
	@Autowired
	GameBlockadeService gameBlockadeService;
	
	@Autowired
	GameUserService gameUserService;
	

	/**
	 * 
	 */
	public GameBlockadeMessageService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param gameBlockadeMessageIds
	 * @return
	 */
	public boolean updateMessageReadState(Long gameBlockadeMessageIds[]) {
		
		if (gameBlockadeMessageIds == null) {
			logger.info("update message read state parameters is null");
			return false;
		}

		int affectedRows = 0;
		int updatedRows = 0;
		boolean ret = false;
		try {
			if (gameBlockadeMessageIds != null) {
				for (Long id : gameBlockadeMessageIds) {
					GameBlockadeMessageMap record = new GameBlockadeMessageMap();
					record.setId(id);
					affectedRows = gameBlockadeMessageMapMapper
							.updateStateByPrimaryKey(record);
					if (affectedRows > 0) {
						updatedRows++;
					}
				}
				ret = (updatedRows == gameBlockadeMessageIds.length);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * get blockade user message.
	 * 
	 * @param userId
	 * @param roomId
	 * @return
	 */
	public List<GameBlockadeMessage> getGameBlockadeMessages(Long userId,
			Long roomId) {
		
		if (userId == null || roomId == null) {
			logger.info("update message read state parameters is null");
			return null;
		}
		
		List<GameBlockadeMessage> messages = GameUtil.getList();

		try {
			GameBlockadeUser user = gameBlockadeService.getGameBlockadeUser(
					userId, roomId);
			Long gameBlockadeUserId = user.getId();
			List<GameBlockadeMessageMap> messageMaps = gameBlockadeMessageMapMapper
					.selectByToUserId(gameBlockadeUserId);

			for (GameBlockadeMessageMap m : messageMaps) {
				Long msgid = m.getMessageId();
				GameBlockadeMessage message = gameBlockadeMessageMapper
						.selectByPrimaryKey(msgid);
				GameBlockadeUser gameBlockUser=gameBlockadeService.getBlockadeUser(m.getGbuFromId());
				GameUser gameUser=gameUserService.getGameUser(gameBlockUser.getUserId());
				message.setGameUser(gameUser);
				message.setGameBlockadeMessageMapId(m.getId());
				messages.add(message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messages;

	}

	/**
	 * one player send message to other players.
	 * 
	 * @param gameBlockadeUserId
	 * @param toGameBlockadeUserId
	 * @param message
	 * @return
	 */
	public boolean inviteSameLevelPlayers(Long gameBlockadeUserId,
			Long[] toGameBlockadeUserId, String message) {

		if (gameBlockadeUserId == null || toGameBlockadeUserId == null
				|| message == null) {
			logger.info("inviteSameLevelPlayer parameters is null");
			return false;
		}
		boolean ret = false;

		try {
			GameBlockadeMessage m = GameUtil.obtain(GameBlockadeMessage.class);
			Long messageId =null; //sequenceService.genGameMessageId();
			//m.setId(messageId);
			m.setContent(message);

			int affectedRows = gameBlockadeMessageMapper.insertSelective(m);
			messageId=m.getId();
			if (affectedRows > 0) {

				// insert into the game blockade
				GameBlockadeMessageMap obj = GameUtil
						.obtain(GameBlockadeMessageMap.class);
				//Long id = sequenceService.genGameMessageMapId();
				//obj.setId(id);
				obj.setGbuFromId(gameBlockadeUserId);
				obj.setMessageId(messageId);
				obj.setFlag(GameBlockadeMessageStateTypes.Unread);
				int totalAffectedRows = 0;
				for (Long toId : toGameBlockadeUserId) {
					obj.setGbuToId(toId);
					affectedRows = gameBlockadeMessageMapMapper.insert(obj);
					if (affectedRows > 0) {
						totalAffectedRows = totalAffectedRows + 1;
					}
				}

				if (totalAffectedRows == toGameBlockadeUserId.length) {
					ret = true;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	
	/**
	 * 
	 * @param gbuToId
	 * @return
	 * {@link GameBlockadeMessageMapMapper#selectUnreadMessageCount(Long)}
	 */
	public Long getUnreadMessageCount(Long gbuToId){
		Long count=gameBlockadeMessageMapMapper.selectUnreadMessageCount(gbuToId);
		return count;
	}
}
