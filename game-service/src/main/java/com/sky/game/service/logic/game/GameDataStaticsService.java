/**
 * sparrow
 * game-service 
 * Jan 21, 2015- 2:44:33 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic.game;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.GameDataCategoryTypes;
import com.sky.game.service.domain.GameDataStatics;
import com.sky.game.service.domain.GameDataStaticsTypes;
import com.sky.game.service.persistence.GameDataStaticsMapper;

/**
 * 
 * <p>
 * <b>Game data statics system</b> Game data statics system can analyze the
 * player behaviors.
 * 
 * <li></li>
 * 
 * 
 * @author sparrow
 *
 */
@Service
public class GameDataStaticsService {
	private static final Log logger = LogFactory
			.getLog(GameDataStaticsService.class);

	/**
	 * 
	 */
	public GameDataStaticsService() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	GameDataStaticsMapper gameDataStaticsMapper;

	/**
	 * 
	 * @param userId
	 * @param landlord
	 *            1=landlord ,0=farmer
	 * @param types
	 */
	public void updateGameData(long userId,GameDataCategoryTypes category, GameDataStaticsTypes types) {

		GameDataStatics o = gameDataStaticsMapper.selectByUserId(userId);
		if (o == null) {
			// need to create the row data
			o = GameUtil.obtain(GameDataStatics.class);
			o.setUserId(Long.valueOf(userId));
			o.setTournamentType(category.type);
			// round
			o.setRoundWinTimes(Integer.valueOf(0));
			o.setRoundLoseTimes(Integer.valueOf(0));
			o.setRoundTimes(Integer.valueOf(0));

			if (types.eq(GameDataStaticsTypes.RoundLoseTimes)) {
				o.setRoundLoseTimes(Integer.valueOf(1));
				o.setRoundTimes(Integer.valueOf(1));
			}

			if (types.eq(GameDataStaticsTypes.RoundWinTimes)) {
				o.setRoundWinTimes(Integer.valueOf(1));
				o.setRoundTimes(Integer.valueOf(1));
			}

			// tournament

			o.setStagebTimes(Integer.valueOf(0));
			o.setTournamentTimes(Integer.valueOf(0));
			o.setTournamentUnfinished(Integer.valueOf(0));

			if (types.eq(GameDataStaticsTypes.StageBTimes)) {
				o.setStagebTimes(Integer.valueOf(1));
			}

			if (types.eq(GameDataStaticsTypes.TournamentTimes)) {
				o.setTournamentTimes(Integer.valueOf(1));
			}
			if (types.eq(GameDataStaticsTypes.TournamentUnfinished)) {
				o.setTournamentUnfinished(Integer.valueOf(1));
			}

			// not this.
			o.setWinner1Times(Integer.valueOf(0));
			o.setWinner2Times(Integer.valueOf(0));
			o.setWinner3Times(Integer.valueOf(0));
			if (types.eq(GameDataStaticsTypes.Winner1Times)) {
				o.setWinner1Times(Integer.valueOf(1));
			}
			if (types.eq(GameDataStaticsTypes.Winner2Times)) {
				o.setWinner2Times(Integer.valueOf(1));
			}
			if (types.eq(GameDataStaticsTypes.Winner3Times)) {
				o.setWinner3Times(Integer.valueOf(1));
			}

			int affectRows = gameDataStaticsMapper.insert(o);
			if (affectRows > 0) {
				logger.info("player[" + userId + "] - " + types.toString());
			}
		} else {
			GameDataStatics obj = GameUtil.obtain(GameDataStatics.class);
			obj.setTournamentType(category.type);
			obj.setId(o.getId());
			switch (types.type) {
			case GameDataStaticsTypes.RLT: {

				obj.setRoundLoseTimes(o.getRoundLoseTimes() + 1);
				obj.setRoundTimes(o.getRoundTimes() + 1);

			}
				break;
			case GameDataStaticsTypes.RWT: {

				obj.setRoundWinTimes(o.getRoundWinTimes() + 1);
				obj.setRoundTimes(o.getRoundTimes() + 1);
			}
				break;

			case GameDataStaticsTypes.SBT: {
				obj.setStagebTimes(o.getStagebTimes() + 1);
			}
				break;
			case GameDataStaticsTypes.TT: {
				obj.setTournamentTimes(o.getTournamentTimes() + 1);
			}
				break;
			case GameDataStaticsTypes.TU: {
				obj.setTournamentUnfinished(o.getTournamentUnfinished() + 1);
			}
				break;

			case GameDataStaticsTypes.WT1: {
				obj.setWinner1Times(o.getWinner1Times() + 1);
			}
				break;

			case GameDataStaticsTypes.WT2: {
				obj.setWinner2Times(o.getWinner2Times() + 1);
			}
				break;
			case GameDataStaticsTypes.WT3: {
				obj.setWinner3Times(o.getWinner3Times() + 1);
			}
				break;

			default:
				break;
			}

			int affectedRows=gameDataStaticsMapper.updateByPrimaryKeySelective(obj);
			if(affectedRows>0){
				logger.info("player[" + userId + "] - update" + types.toString());
			}
		}

	}

}
