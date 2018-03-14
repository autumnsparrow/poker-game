/**
 * @sparrow
 * @Dec 28, 2014   @2:20:08 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.blockade.config.FreeTournamentConfig;
import com.sky.game.protocol.commons.GL0000Beans.GL4004Request;
import com.sky.game.protocol.event.MinaEvent;

/**
 * @author sparrow
 *
 */
public class LLFTRoom extends LLRoom {

	private static final Log logger = LogFactory.getLog(LLFTRoom.class);
	FreeTournamentConfig config;

	LLGameObjectMap<LLFTTeam> teams;
	LLFTTeam team;

	public LLFTRoom(Long id, FreeTournamentConfig config) {
		super(id);

		this.config = config;
		this.teams = LLGameObjectMap.getMap();// GameUtil.getMap();
		setId(Long.valueOf(config.getRoomId()));

		LLU.regeisterHandler(this, "GL4004");
	}

	
	@Override
	public DeckConfiguration getDeckConfig() {
		// TODO Auto-generated method stub
		conf = config.getDeckConfig();
		conf.baseChips = config.getBase();
		conf.roomName=config.getRoomName();
		conf.roomType=LLRoomTypes.FT.state;
		// conf.firstCallRandom=false;
//		conf.winExp = 10;
//		conf.loseExp = 5;
		return conf;
	}

	boolean debug = false;

	private void testLeave(final MinaEvent event) {
		if (debug) {
			// final MinaEvent event = evt;
			GameUtil.gameLife(getUri() + "/userLeave", 20000L, this,
					"onReceiveLeaveRoomEvent", event);
		}
	}

	@Override
	public void onReceiveJoinRoomEvent(MinaEvent evt) {
		// TODO Auto-generated method stub
		super.onReceiveJoinRoomEvent(evt);

		// testLeave(evt);
	}

	@Override
	public void unObserve(LLGamePlayer player) {
		// TODO Auto-generated method stub
		super.unObserve(player);
		// checking if user can join th
		// BasePlayer basePlayer = GSPP.getPlayer(evt.token);
		// check if user can join
		// only in the waiting stage user can leave.
		LLFTTeam t = getTeam(player);
		if (t != null) {
			
			if (t.state.eq(LLFTTeamStates.Waiting)) {
				t.unObserve(player);
				logger.info("leave[" + getId() + "] - " + player.getId());
				//team.unEnroll(player);
			}
		}

		// team.unObserve(player);

	}

	@Override
	public void onReceiveLeaveRoomEvent(MinaEvent evt) {
		// TODO Auto-generated method stub
		super.onReceiveLeaveRoomEvent(evt);
		//LLGamePlayer p = evtAsPlayer(evt);
		// GSPP.updateGameDataStatics(p.id,GameDataCategoryTypes.CATEGORY_COINS,
		// GameDataStaticsTypes.TournamentUnfinished);

	}

	public LLFTTeam getTeam(LLGamePlayer player) {
		LLFTTeam tt = player.getObject(player.team);

		return tt;
	}

	//
	//
	// protocol user ready
	//

	/**
	 * GL4004 - free room read the player.
	 * {@link GL4004Request}
	 * @param evt
	 */
	@RegisterEventHandler(transcode = "GL4004")
	public void onReceiveUserReadyRoomEvent(MinaEvent evt) {
		// switch the born state to the waiting state
		LLGamePlayer player = evtAsPlayer(evt);//LLU.evtAsObj(evt);
		
		// user already in a game just enable the message.
		LLFTTeam t = getTeam(player);
		if (t != null && !t.state.eq(LLFTTeamStates.End)) {
			player.enableRobot = false;
			sendException(player,
					LLRoomExceptionTypes.LLAlreadyInGame);
			throw new RuntimeException("player["+player.id+"] alread in the FTTeam!");
		} else {
			if (team == null
					|| (team != null && !team.state.eq(LLFTTeamStates.Waiting))) {

				team = new LLFTTeam(this);
			}

			if (team.state.eq(LLFTTeamStates.Born)) {
				team.setState(LLFTTeamStates.Waiting);
			}

			// if the team state is waiting
			// the joined player add into the team.
			if (team.state.eq(LLFTTeamStates.Waiting)) {
				player = evtAsPlayer(evt);
				player.elimination=false;
				player.enableRobot=false;
				player.append("team["+team.id+"]");
				team.observe(player);
				// GSPP.updateGameDataStatics(player.id,GameDataCategoryTypes.CATEGORY_COINS,
				// GameDataStaticsTypes.TournamentTimes);

			}
		}
		setState(LLRoomStates.UserRead);
	}

}
