/**
 * @sparrow
 * @Dec 29, 2014   @4:36:47 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.game.CrontabGameLife;
import com.sky.game.context.lock.IObtain;
import com.sky.game.context.lock.VolatileLocking;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.achievement.AchievementObserver;
import com.sky.game.landlord.blockade.config.EliminationTournamentConfig;
import com.sky.game.landlord.blockade.config.item.EnrollTicket;
import com.sky.game.landlord.blockade.config.item.Reward;
import com.sky.game.landlord.blockade.config.item.TimeWindow;
import com.sky.game.landlord.jmx.JmxRoom;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.ProtocolGlobalContextRemote;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.GL4018Request;
import com.sky.game.protocol.commons.GL0000Beans.LG0014Response;
import com.sky.game.protocol.commons.GL0000Beans.LG4018Response;
import com.sky.game.protocol.commons.GL0000Beans.LG4020Response;
import com.sky.game.protocol.commons.GL0000Beans.PReward;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.service.domain.GameDataCategoryTypes;
import com.sky.game.service.domain.GameDataStaticsTypes;
import com.sky.game.service.domain.GameEnroll;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.domain.Item;

/**
 * <b>The process of user enroll a scheduled time match.</b>
 * 
 * <p>
 * <li>
 * 
 * </li>
 * 
 * 
 * @author sparrow
 *
 */
public class LLETRoom extends LLRoom {

	EliminationTournamentConfig config;

	LLGameObjectMap<LLTeam> teams;
	LLTeam team;
	Map<Integer, List<Reward>> rankRewards;

	CrontabGameLife gameLife;

	boolean free;

	VolatileLocking<LLGameObjectMap<GameEnroll>> gameEnrollMap;

	JmxRoom jmxRoom;

	/**
	 * 
	 * @param id
	 * @param config
	 * 
	 * @see GL4018Request
	 */
	public LLETRoom(Long id, EliminationTournamentConfig config) {
		super(id);
		this.config = config;
		setId(Long.valueOf(this.config.getRoomId()));
		teams = LLGameObjectMap.getMap();
		// loadRewards();
		gameEnrollMap = VolatileLocking.obtain();

		// LLU.regeisterHandler(this, "GL4018");
		// logger.info(id);
		// register the match time.
		TimeWindow match = config.getTimePattern().getMatch();

		if (match.getStart() != null) {

			gameLife = GameUtil.gameLife("match://" + getUri(),
					match.getStart(), this, "notifyMatchTimeChanges");

		} else {
			enableJoinRoom = true;
		}

		GameUtil.gameLife("scheduled://" + getUri(), "0 0/5 * * * *", this,
				"clearCache");
		jmxRoom = LLU.o(JmxRoom.class);
		LLU.regeisterHandler(this, "GL0014", "GL4019", "GL4020");
	}

	public void clearCache() {
		logger.debug("clear the cache.");
		gameEnrollMap.reset();
	}

	boolean enableJoinRoom;

	public void disableJoinRoom() {
		enableJoinRoom = false;
	}

	public LLGameObjectMap<GameEnroll> getGameEnrollMap() {
		return gameEnrollMap.getHelper(
				new IObtain<LLGameObjectMap<GameEnroll>, Object>() {

					public LLGameObjectMap<GameEnroll> obtain(Object a) {
						// TODO Auto-generated method stub
						List<GameEnroll> enrolls = GSPP
								.getGameEnrollByRoomId(id);
						LLGameObjectMap<GameEnroll> gameEnroll = LLGameObjectMap
								.getMap();

						for (GameEnroll enroll : enrolls) {
							gameEnroll.put(enroll.getUserId(), enroll);
						}

						return gameEnroll;
					}

				}, null);
	}

	public LG0014Response getOnlinePlayerNumbers(Long userId) {

		LG0014Response o = LLU.o(LG0014Response.class);
		// o.setId(player.getUserId());
		o.setRoomId(id);
		if (gameLife != null) {
			o.setScheduledTime(gameLife.getFormatedTime());
			o.setTimeRemains(gameLife.remainTimes());
		} else {
			o.setScheduledTime("");
			o.setTimeRemains(0);
		}
		int numberOfOnline = 0;
		for (BasePlayer player : ProtocolGlobalContextRemote.instance()
				.getOnlinePlayers()) {

			// LLGamePlayer p = player.getGamePlayer();

			GameEnroll enroll = getGameEnrollMap().get(player.getUserId());
			boolean isEnrolled = false;
			if (enroll != null) {

				int enrolled = enroll.getEnrolled();
				isEnrolled = enrolled == 0 ? true : false;
				if (isEnrolled) {
					numberOfOnline++;
				}
			}
			if (player.getUserId() == userId.longValue()) {
				o.setEnrolled(isEnrolled);
			}
			// objects.add(o);
		}
		//int numberOnline=players.size();
		
		if(numberOfOnline<1){
			numberOfOnline=GameUtil.getRandom(20);
		}
		o.setNumbers(numberOfOnline);
		return o;

	}

	/**
	 * 
	 * notify the match time changes.
	 * 
	 */
	public void notifyMatchTimeChanges() {
		enableJoinRoom = true;
		// logger.info("match time out!");
		TimeWindow match = config.getTimePattern().getMatch();
		int lives = 0;
		if (match.getEnd() != null) {
			lives = Integer.parseInt(match.getEnd()) * 1000 * 60;
		}
		GameUtil.gameLife("matchend://" + getUri(), lives, this,
				"disableJoinRoom");
		List<LG0014Response> objects = GameUtil.getList();

		int numberOfOnline = 0;
		for (BasePlayer player : ProtocolGlobalContextRemote.instance()
				.getOnlinePlayers()) {
			LG0014Response o = LLU.o(LG0014Response.class);
			o.setId(player.getUserId());
			o.setRoomId(id);

			o.setScheduledTime(gameLife.getFormatedTime());
			o.setTimeRemains(gameLife.remainTimes());

			// LLGamePlayer p = player.getGamePlayer();

			GameEnroll enroll = GSPP.getGameEnrollByUserIdAndRoomId(
					player.getUserId(), id);
			boolean isEnrolled = false;
			if (enroll != null) {

				int enrolled = enroll.getEnrolled();
				isEnrolled = enrolled == 0 ? true : false;
				if (isEnrolled) {
					numberOfOnline++;
				}
			}
			o.setEnrolled(isEnrolled);
			objects.add(o);
		}
		// o.setNumbers(numberOfOnline);
		for (LG0014Response o : objects) {
			o.setNumbers(numberOfOnline);
			if (o.isEnrolled()) {
				MinaEvent evt = MinaEvent.obtainMinaEvent(o.getId(), o, false);
				// TODO Auto-generated method stub
				evt.sendMessage();
			}

		}
		// o.setVip(config);
	}

	@Override
	public DeckConfiguration getDeckConfig() {
		// TODO Auto-generated method stub
		conf = new DeckConfiguration();
		conf.winExp = config.getDeckConfig().getWinExp();
		conf.loseExp = config.getDeckConfig().loseExp;
		conf.roomName = config.getRoomName();
		conf.roomType = LLRoomTypes.ET.state;
		// conf.gamePerDeck=config.getDeckConfig().get
		// conf.baseChips=config.getDeckConfig().in

		// conf.winExp = 10;
		// conf.loseExp = 5;
		// conf.gamePerDeck = 3;
		return conf;
	}

	@Override
	public void onReceiveJoinRoomEvent(MinaEvent evt) {
		// TODO Auto-generated method stub
		super.onReceiveJoinRoomEvent(evt);

		LLGamePlayer player = evtAsPlayer(evt);
		if (!enableJoinRoom) {
			sendException(player, LLRoomExceptionTypes.LLRoomdisableJoinEvent);
			throw new RuntimeException("room[" + id + "] disable join event!");
		}
		LLTeam t = player.getObject(player.team);
		if (t != null && !t.state.eq(LLTeamStates.End)) {
			player.enableRobot = false;
			sendException(player, LLRoomExceptionTypes.LLAlreadyInGame);
			throw new RuntimeException("player[" + player.id
					+ "] alread in the ETTeam!");
		} else {

			if (team == null
					|| (team != null && !team.state.eq(LLTeamStates.Waiting))) {

				team = new LLTeam(this);

			}

			if (team.state.eq(LLTeamStates.Born)) {
				team.setState(LLTeamStates.Waiting);
			}

			// if the team state is waiting
			// the joined player add into the team.
			if (team.state.eq(LLTeamStates.Waiting)) {
				// LLGamePlayer player = evtAsPlayer(evt);
				// logger.info("joinRoom[" + getId() + "] - " + player.getId());
				//
				boolean isEnrolled = checkPlayerEnrolled(player.getId());
				// isEnrolled=player.isRobot?true:isEnrolled;
				if (isEnrolled) {
					player.setChips(config.getInitIntegral());
					player.elimination = false;
					player.enableRobot = false;
					
					team.observe(player);
				} else {
					sendException(player,
							LLRoomExceptionTypes.LLEnrollBloackdeUpdateFailed);
				}

			}
		}
	}

	@Override
	public void onReceiveLeaveRoomEvent(MinaEvent evt) {
		// TODO Auto-generated method stub
		super.onReceiveLeaveRoomEvent(evt);

	}

	@Override
	public void observe(LLGamePlayer player) {
		// TODO Auto-generated method stub
		super.observe(player);
	}

	/**
	 * when the player leave the room,unsubscrible the message form the team.
	 * 
	 * @see LLRoom#unObserve(LLGamePlayer)
	 * 
	 */
	@Override
	public void unObserve(LLGamePlayer player) {
		// TODO Auto-generated method stub
		super.unObserve(player);
		// only when the team in waiting time,the player can un-enroll the game.
		// NEVER reach that point.
		LLTeam t = player.getObject(player.team);
		if (t != null) {

			if (t.state.eq(LLTeamStates.Waiting)) {

				t.unObserve(player);
				logger.info("leave[" + getId() + "] - " + player.getId());
				boolean isEnrolled = checkPlayerEnrolled(player.getId());
				if (isEnrolled){
					logger.info("unroll ["+player.getId()+"");
					unroll(player);
				}
			}
		}

	}

	public void addTeam(LLTeam team) {
		teams.put(team.getId(), team);
		jmxRoom.add(team.jmxTeam);
	}

	public void removeTeam(LLTeam team) {
		teams.remove(team.getId());
		jmxRoom.remove(team.jmxTeam);
	}

	/**
	 * 
	 * 
	 * @param player
	 */
	void unroll(LLGamePlayer player) {
		boolean ret = GSPP.unEnroll(player.id, getId());
		if (ret) {
			unEnrollOk(player);
		}
		logger.info("player[" + player.getId() + "]  un enroll ");
	}
	
	boolean checkFreeTournament(){
		boolean ret=false;
		Map<Integer, EnrollTicket> tickets = config.getTicketsMap();
		if(tickets!=null){
			EnrollTicket ticket = tickets.get(Integer.valueOf(0));
			if(ticket!=null){
				ret=true;
			}
		}
		return ret;
	}

	/**
	 * enroll a player by item id
	 * 
	 * @param player
	 * @param itemId
	 * @see LLETRoom#enroll(LLGamePlayer, int)
	 */
	void enrollByItem(LLGamePlayer player, Integer itemId) {
		Map<Integer, EnrollTicket> tickets = config.getTicketsMap();
		EnrollTicket ticket = tickets.get(itemId);
		// item amount
		int itemValue = 0;
		if (ticket == null) {
			itemId = 0;
		}
		if (ticket != null) {
			// itemId=0;
			itemValue = ticket.getItemAmount();
		}

		enroll(player, itemId, itemValue);

		logger.info("player[" + player.getId() + "] enroll ");
	}
	
	
	void itemNotEnough(LLGamePlayer player,Integer itemId){
		loadItems();
		Item item=items.get(itemId);
		if(item==null)
			throw new RuntimeException("can't find item by item id:"+itemId);
		sendException(player,
				LLRoomExceptionTypes.LLFEnrollItemValueNotEnough,String.format(LLRoomExceptionTypes.LLFEnrollItemValueNotEnough.message,item.getDescription()));
	}

	/**
	 * enroll the player into the game.
	 * 
	 * @param player
	 * @param amount
	 */
	private void enroll(LLGamePlayer player, Integer itemId, int amount) {

		boolean valid = GSPP.validEnroll(player.getId(), itemId, amount);
		if (itemId == 0)
			free = true;
		if (free)
			valid = true;
		if (!valid) {
			itemNotEnough(player, itemId);
			
		} else {

			GameUser user = GSPP.getGameUser(player.getId());
			if (user != null) {
				player.setExp(user.getExp());

				boolean ret = false;
				if (!free)
					ret = GSPP.enroll(player.getId(), getId(), getId(), itemId,
							amount);
				if (free)
					ret = true;
				if (ret) {
					// player.setChips(c);

					enrollOk(player);
					// sendException(player, LLRoomExceptionTypes.LLEnrollOk);
				}
			}
		}
	}

	private void unEnrollOk(LLGamePlayer player) {
		GL0000Beans.LG4019Response resp = LLU
				.o(GL0000Beans.LG4019Response.class);
		resp.setId(getId());
		resp.setState(LLRoomExceptionTypes.LLUnenrollOk.getState());

		player.sendBrokerMessage(resp, false);
	}

	private void enrollOk(LLGamePlayer player) {
		GL0000Beans.LG4005Response resp = LLU
				.o(GL0000Beans.LG4005Response.class);
		resp.setId(getId());
		resp.setState(LLRoomExceptionTypes.LLEnrollOk.getState());

		player.sendBrokerMessage(resp, false);
	}

	//
	// region for the reward logic.
	//
	//
	/**
	 * 
	 * 
	 */
	private synchronized void loadRewards() {
		// load the items
		loadItems();

		// if the rankRewards is null,load the rankReward.
		if (rankRewards == null) {
			Map<String, List<Reward>> rewards = config.getRewardsMap();
			rankRewards = GameUtil.getMap();
			for (Entry<String, List<Reward>> entry : rewards.entrySet()) {
				String rank = entry.getKey();
				if (rank.contains("-")) {
					String[] item = rank.split("-");
					int start = Integer.valueOf(item[0]);
					int end = Integer.valueOf(item[1]);

					for (int i = start; i < end + 1; i++) {
						rankRewards.put(Integer.valueOf(i), entry.getValue());
					}
				} else {
					int start = Integer.valueOf(rank);
					rankRewards.put(Integer.valueOf(start), entry.getValue());
				}
			}
		}

	}

	/**
	 * wrap the Reward to PReward
	 * 
	 * @param r
	 * @return
	 */
	private PReward wrapReward(Reward r) {
		PReward pReward = LLU.o(PReward.class);
		Item i = items.get(Long.valueOf(r.getItemId()));
		pReward.wrap(i);
		pReward.setAmount(r.getItemAmount());
		return pReward;
	}

	/**
	 * reward the player when the player eliminated or win.
	 * 
	 * @param player
	 * @param teamId
	 */
	public void reward(LLGamePlayer player, Long teamId) {
		// load the items.
		// load the rewards
		if (player.isRobot||player.isRobot())
			return;

		loadRewards();
		List<Reward> rewards = rankRewards.get(Integer.valueOf(player.rank));

		// create the response object.
		GL0000Beans.LG4010Response resp = LLU
				.o(GL0000Beans.LG4010Response.class);

		resp.setId(player.getId());
		resp.setNickName(player.user.getNickName());
		resp.setRank(player.rank);
		List<PReward> pRewards = GameUtil.getList();
		resp.setRewards(pRewards);
		resp.setTimestamp(CronUtil.getFormatedDateNow());

		// wrap the reward to response object.
		if (rewards != null) {
			for (Reward r : rewards) {
				PReward pw = wrapReward(r);
				GSPP.reward(player.getId(), getId(), teamId, player.getRank(),
						r.getItemId(), r.getItemAmount());
				pRewards.add(pw);
			}

		}

		// send the response object to the player.
		player.sendBrokerMessage(resp, false);

		broadcastWinnerMessage(player);

	}

	private void broadcastWinnerMessage(LLGamePlayer player) {
		if (!player.isRobot) {
			AchievementObserver.observerWinnerET(id.intValue(), player.id,
					player.rank,player.alwaysWin,config.getTournamentType());

			//
			GameDataStaticsTypes types = GameDataStaticsTypes.Undefine;
			if (player.rank == 1) {
				types = GameDataStaticsTypes.Winner1Times;
			} else if (player.rank == 2) {
				types = GameDataStaticsTypes.Winner2Times;
			} else if (player.rank == 3) {
				types = GameDataStaticsTypes.Winner3Times;
			}
			if (types.type > 0) {
				GSPP.updateGameDataStatics(player.id, GameDataCategoryTypes
						.getType(config.getTournamentType()), types);
			}

		}

	}

	// GL4019
	@RegisterEventHandler(transcode = "GL4019")
	public void handleUnenroll(MinaEvent evt) {
		LLGamePlayer player = evtAsPlayer(evt);
		GL0000Beans.GL4019Request req = LLU.evtAsObj(evt);
		boolean isEnrolled = checkPlayerEnrolled(player.getId());
		if (isEnrolled) {
			unroll(player);
		}

	}

	// GL4009 -
	// handle the event ,return the team rank list.
	//
	//
	//
	/**
	 * 
	 * @param evt
	 */

	@Override
	public void handleEnroll(MinaEvent evt) {
		// TODO Auto-generated method stub
		super.handleEnroll(evt);
		LLGamePlayer player = evtAsPlayer(evt);
		GL0000Beans.GL4005Request req = LLU.evtAsObj(evt);
		boolean isEnrolled = checkPlayerEnrolled(player.getId());

		if (isEnrolled) {
			enrollOk(player);
			/* throw new RuntimeException */
			logger.info("player[" + player.id
					+ "] alread enrolled! ,send enrollOk Message.");
			return;
		}

		enrollByItem(player, Integer.valueOf(req.getItemId()));

		// boolean isEnrolled = checkPlayerEnrolled(player.getId());

	}

	@Override
	public void handleUserRankList(MinaEvent evt) {
		// TODO Auto-generated method stub
		super.handleUserRankList(evt);
		// // get the player by token
		LLGamePlayer player = evtAsPlayer(evt);

		// get the player team from the memory
		LLTeam t = player.getObject(player.team);
		if (t != null) {
			t.sendUserRanks(player);
		}
	}

	@RegisterEventHandler(transcode = "GL0014")
	public void handleRoomStateUpdate(MinaEvent evt) {
		LLGamePlayer player = evtAsPlayer(evt);
		LG0014Response resp = getOnlinePlayerNumbers(player.getId());
		player.sendBrokerMessage(resp, false);
	}

	/**
	 * @see GL4018Request
	 *      {@link GSPP#getGameEnrollByUserIdAndRoomId(Long, Long)}
	 * 
	 *      {@link GSPP#getGameEnrollByRoomId(Long)}
	 * @param evt
	 * 
	 * 
	 */
	@RegisterEventHandler(transcode = "GL4018")
	public void handleEnrolledStateCheck(MinaEvent evt) {
		LLGamePlayer player = evtAsPlayer(evt);
		boolean isEnrolled = false;
		isEnrolled = checkPlayerEnrolled(player.getId());
		LG4018Response o = LLU.o(LG4018Response.class);
		o.setValid(isEnrolled);
		player.sendBrokerMessage(o, false);
	}

	@RegisterEventHandler(transcode = "GL4020")
	public void handleValidMatch(MinaEvent evt) {
		LLGamePlayer player = evtAsPlayer(evt);
		// GL4020Request request=LLU.asObject(evt.)
		// GL0000Beans.GL4020Request req = LLU.evtAsObj(evt);
		LG4020Response resp = LLU.o(LG4020Response.class);
		resp.setId(id);
		List<GameEnroll> enrolles = GSPP.getGameEnrollByRoomId(this.id);
		boolean valid = true;

		int minOnlinePlayer = 0;
		int sizeOfEnrolled = enrolles != null ? enrolles.size() : 0;
		int sizeOfOnline = 0;

		// when don't enable the robot
		if (!config.isEnableRobot()) {
			minOnlinePlayer = config.getMinOnlinePlayer() != null ? config
					.getMinOnlinePlayer().intValue() : 0;
			for (GameEnroll g : enrolles) {
				if (GSPP.isPlayerOnline(g.getUserId())) {
					sizeOfOnline++;
				}

			}

			if ((sizeOfEnrolled < minOnlinePlayer)
					|| (sizeOfOnline < minOnlinePlayer)) {
				valid = false;
			}

			if (!valid && enrolles != null) {

				for (GameEnroll g : enrolles) {
					if (GSPP.isPlayerOnline(g.getUserId())) {
						BasePlayer p = GSPP.getPlayerById(g.getUserId());
						if (p != null) {
							LLGamePlayer pp = new LLGamePlayer(g.getUserId());
							unroll(pp);

						}
					}

				}
			}
		}

		resp.setValid(valid);
		player.sendBrokerMessage(resp, false);

	}
	
	
	public List<PReward> getRewardList(){
		loadItems();
		List<PReward> reward=GameUtil.getList();
		
		for(Reward r:config.getRewards()){
			PReward obj=LLU.o(PReward.class);
			Item h=items.get(Long.valueOf(r.getItemId()));
			obj.wrap(h);
			obj.setAmount(r.getItemAmount());
			obj.setRank(r.getRanking());
			reward.add(obj);
			
		}
		return reward;
		
	}

	/**
	 * check if the current player already enrolled
	 * 
	 * @param player
	 * @return
	 */
	private boolean checkPlayerEnrolled(Long playerId) {
		// GL4018Request request=LLU.asObject(evt);
		boolean isEnrolled = false;
		if (free) {
			return true;
		}
		GameEnroll gameEnroll = GSPP.getGameEnrollByUserIdAndRoomId(playerId,
				getId());
		// boolean isEnrolled=false;
		if (gameEnroll != null) {
			int enrolled = gameEnroll.getEnrolled();
			isEnrolled = enrolled == 0 ? true : false;

		}
		return isEnrolled;
	}

	public JmxRoom getJmxRoom() {
		return jmxRoom;
	}
}
