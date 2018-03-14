/**
 * 
 */
package com.sky.game.landlord.room;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.IUniqueIdentifiedObject;
import com.sky.game.context.util.CronUtil;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.jmx.JmxPlayer;
import com.sky.game.landlord.message.IBrokerMessage;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.landlord.room.LLGamePlayerStateObserver.IHandlerPlayer;
import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.GameProtocolException;
import com.sky.game.protocol.ProtocolGlobalContextRemote;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.GamePlayerObject;
import com.sky.game.protocol.commons.GL0000Beans.LG0004Response;
import com.sky.game.protocol.commons.GL0000Beans.LG0009Response;
import com.sky.game.protocol.commons.GL0000Beans.LG0011Response;
import com.sky.game.protocol.commons.GL0000Beans.LG0011TeamObject;
import com.sky.game.protocol.commons.GL0000Beans.LG3001Response;
import com.sky.game.protocol.commons.GL0000Beans.LG4003Response;
import com.sky.game.protocol.commons.GL0000Beans.PlayerScore;
import com.sky.game.protocol.commons.GL0000Beans.Range;
import com.sky.game.protocol.commons.GL0000Beans.ValueChange;
import com.sky.game.protocol.commons.GT0001Beans.State;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.service.domain.GameUser;

/**
 * Player send or recieve message.
 * 
 * 
 * @author sparrow
 *
 */
public class LLGamePlayer extends LLGamePlayerMeta implements
		IUniqueIdentifiedObject, IBrokerMessage {
	private static final Log logger = LogFactory.getLog(LLGamePlayer.class);

	long id;

	// record the chips
	int chips;
	PokerCube pokeCube;

	// player should have many properties.

	String icon;
	String nickName;

	ValueChange expChanged;
	ValueChange rankChanged;

	LLDeckSeat seat;

	LLGamePlayerStates state;

	// current rank.
	int rank;
	int exp;
	int totalRanks;
	public boolean winner;

	JmxPlayer jmxPlayer;

	public void addExp(int exp) {
		this.expChanged.setOld(this.exp);
		this.expChanged.setChange(exp);
		this.expChanged.setCurrent(this.exp + exp);
		this.exp = this.expChanged.getCurrent();
	}

	public void changeRank(int rank, int total) {

		this.rankChanged.setOld(this.rank);
		this.rankChanged.setCurrent(rank);
		this.rankChanged.setChange(this.rankChanged.getOld()
				- this.rankChanged.getCurrent());
		this.rank = rank;
		this.totalRanks = total;

	}

	LLGameLifeOfPlayer gameLifeOfPlayer;

	GameUser user;

	public LLGamePlayer(long id, boolean isRobot) {
		super();
		this.id = id;

		this.expChanged = LLU.o(ValueChange.class);
		this.rankChanged = LLU.o(ValueChange.class);
		this.rank = 0;
		this.isRobot = isRobot;
		this.enableRobot = isRobot;

		this.state = LLGamePlayerStates.Idle;

	}

	public LLGamePlayer(long id) {
		super();
		this.id = id;

		init();
		// // register the
		// GameContextGlobals.registerEventHandler("GL0009", this);
		//
		// // register the online offline
		// GameContextGlobals.registerEventHandler("GL0011", this);
	}

	private void init() {

		this.expChanged = LLU.o(ValueChange.class);
		this.rankChanged = LLU.o(ValueChange.class);
		this.rank = 0;
		this.isRobot = false;
		this.state = LLGamePlayerStates.Idle;

		jmxPlayer = LLU.o(JmxPlayer.class);
		jmxPlayer.setId(id);

		append("\n");
		append(String.valueOf(this.id));

		//

		// clearOtherPlayers();
		this.lastAcess = System.currentTimeMillis();
		this.enableRobot = false;
		this.online = true;
		setResume(true, "init");
		this.elimination = false;

		LLU.regeisterHandler(this, "GL0011");
	}

	public void append(String message) {
		if (!isRobot && jmxPlayer != null)
			jmxPlayer.append(message);
		// buffer.append("$").append(CronUtil.getFormatedDateNow()).append(" - ").append(message).append("\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.context.annotation.introspector.IIdentifiedObject#getId()
	 */
	public Long getId() {
		// TODO Auto-generated method stub
		return Long.valueOf(id);
	}

	/**
	 * get the game life to player
	 * 
	 * @return
	 */
	public LLGameLifeOfPlayer getGameLifeOfPlayer() {
		return gameLifeOfPlayer;
	}

	/**
	 * Attach the game life to player.
	 * 
	 * @param gameLifeOfPlayer
	 */
	public void setGameLifeOfPlayer(LLGameLifeOfPlayer gameLifeOfPlayer) {
		this.gameLifeOfPlayer = gameLifeOfPlayer;
	}

	public int getChips() {
		return chips;
	}

	public void setChips(int chips) {
		this.chips = chips;
	}

	public void wrap(GameUser user) {
		this.exp = user.getExp();
		this.expChanged.setCurrent(this.exp);
		this.expChanged.setChange(0);
		this.expChanged.setOld(this.exp);
		this.nickName = user.getNickName();
		// this.rank=0;
		this.rankChanged.setChange(0);
		this.rankChanged.setCurrent(0);
		this.rankChanged.setOld(0);
	}

	/**
	 * 
	 * wrap the basic player object
	 * 
	 * @return
	 */
	public GamePlayerObject wrap() {
		GamePlayerObject obj = GameUtil.obtain(GamePlayerObject.class);
		user = GSPP.getGameUser(this.id);
		obj.setId(id);
		// find the user token

		obj.setBestScore(1);
		obj.setCoins(user.getCoin());
		obj.setGold(user.getGold());
		obj.setLevelName(user.getLevelName());
		obj.setLevel(user.getLevel());
		obj.setIcon(user.getIcon());
		obj.setNickName(user.getNickName());
		obj.setRank(this.rank);
		obj.setTotal(this.totalRanks);
		obj.setChips(this.chips);

		return obj;
	}

	public LG0004Response wrapCards() {
		LG0004Response obj = GameUtil.obtain(LG0004Response.class);
		obj.setId(id);
		obj.setHex(pokeCube.cubeToHexString());
		// obj.setHex(pokerCube.);
		return obj;
	}

	public PokerCube getPokeCube() {
		return pokeCube;
	}

	public void setPokeCube(PokerCube pokeCube) {
		this.pokeCube = pokeCube;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.message.IBrokerMessage#sendBrokerMessage(java.lang
	 * .Object, boolean)
	 */
	public void sendBrokerMessage(Object obj, boolean request) {
		if (!enableRobot && online) {
			//lastAcess = System.currentTimeMillis();
			// when the player state is deck,if resume,then send the message.
			boolean shouldSent = state.eq(LLGamePlayerStates.Deck) ? isResume()
					: true;
			if (shouldSent) {
				sendPrivateMessage(obj, request);
			}

		}

		// when the player send message update the game life object

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.context.annotation.introspector.IIdentifiedObject#setId(
	 * java.lang.Long)
	 */
	public void setId(Long id) {
		// TODO Auto-generated method stub

	}

	public boolean isRobot() {

		return enableRobot;
	}

	/**
	 * @param obj
	 */
	public void wrapPlayerScore(PlayerScore obj) {
		// TODO Auto-generated method stub

		user = GSPP.getGameUser(this.id);
		if (user != null) {
			obj.setId(id);
			// find the user token
			obj.setIcon(user.getIcon());
			obj.setNickName(user.getNickName());

			obj.setExp(expChanged);
			obj.setSort(rankChanged);
		}

	}

	public void reset() {
		logger.info("player[" + id + "] clearup!");
		append("clearup!");
		// this.isRobot = false;
		// this.enableRobot = false;
		if (room != null) {
			LLRoom r = (LLRoom) room;
			BasePlayer basePlayer = null;
			try {

				basePlayer = ProtocolGlobalContextRemote.instance()
						.getOnlinePlayer(id);
				if (basePlayer != null) {
					basePlayer.removeGamePlayer(r.id);
				}
			} catch (GameProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// r.leave(this);
		}
		removeGameObserver(deck);
		removeGameObserver(game);
		removeGameObserver(level);
		removeGameObserver(team);
		removeGameObserver(room);

	}

	/**
	 * when user leave,set the elimiation flag as true, then the team will
	 * elimiate the player ,and mark the player as
	 * {@link LLGamePlayerStates#Reward}
	 * 
	 * 
	 */
	public void destory(LLRoom r) {
		// TODO Auto-generated method stub
		// GameContextGlobals.unregisterEventHandler("GL0009", this);
		// LLGamePlayerStates s=states.get(r.getId());
		// s=LLGamePlayerStates.Leaving;
		// LLU.clearAllHandlers(this);

		if (state.eq(LLGamePlayerStates.Reward)) {
			reset();
		}
	}

	private void removeGameObserver(IGamePlayerObserver observer) {
		if (observer != null) {
			observer.leave(this);
		}
	}

	public LLDeckSeat getSeat() {
		return seat;
	}

	public void setSeat(LLDeckSeat seat) {
		// leave for the old seat to the new seat.

		this.seat = seat;
	}

	public LLGamePlayerStates getState() {
		return state;
	}

	public void jmxState() {
		append( "{enableRobot:" + enableRobot
				+ ",resume:" + resume + ",online:" + online +",elimination:"+elimination+",state " + this.state + "}");
	}

	public void setState(LLGamePlayerStates state) {
		if (!isRobot) {
			logger.info("player[" + id + "] change state " + this.state
					+ " -- > " + state);
			// jmxPlayer.set
			if (jmxPlayer != null)
				jmxPlayer.setState(state.toString());
			String m = String.format("[%10s -- %10s ]", this.state, state);
			append("{enableRobot:" + enableRobot + ",resume:" + resume
					+ ",online:" + online +",elimination:"+elimination+ "} - "+m);
		}
		this.state = state;
	}

	public int getRank() {
		return rank;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	// private void enable(boolea)

	/**
	 * if the user want to leave,then the
	 * 
	 */
	public void handleUserLeave() {
		// this.state=LLGamePlayerStates.Leave;
		// don't listener the event that client sent
		append(LLGamePlayerStates.Leave.toString());
		LLU.clearAllHandlers(this);
		// if player in state of room or level, then reset the player.
		if (state.eq(LLGamePlayerStates.Room)
				|| state.eq(LLGamePlayerStates.Level)) {
			reset();
		}

	}

	public synchronized void sendPrivateMessage(Object obj, boolean resp) {
		
		//logger.info("private["+id+"] -> "+GameContextGlobals.getJsonConvertor().format(obj));
		MinaEvent evt = MinaEvent.obtainMinaEvent(getId(), obj, resp);
		evt.sendMessage();
		if (this.gameLifeOfPlayer != null) {
			gameLifeOfPlayer.refresh();
		}
	}

	public void sendMessage(Object obj, boolean resp) {
		if (!enableRobot && online) {
			sendPrivateMessage(obj, resp);
		}
	}

	public void sendUserState(int s) {
		LG0009Response resp = LLU.o(LG0009Response.class);
		resp.setId(id);
		resp.setState(s);
		sendMessage(resp, false);
		// this.sendBrokerMessage(resp, false);
	}

	/**
	 * 
	 * @param evt
	 */
	@RegisterEventHandler(transcode = "GL0011")
	public void handleOnlineResume(MinaEvent evt) {

		if (isRobot)
			return;
		logger.info("handleOnlineResume:" + evt.transcode + " ," + evt.userId);
		append("---- handleOnlineResume ---- "+state+ " lastAcess:"+CronUtil.getFormatedDate(new Date(lastAcess)));
		BasePlayer p = GSPP.getPlayerById(id);
		LLGamePlayerStateObserver.hanlePlayers(p,
				new IHandlerPlayer() {

					public void handlePlayer(LLGamePlayer p, long roomId) {
						// TODO Auto-generated method stub
						// only the online player can resume.
						if (p.shouldHandleResume) {
							
							p.setResume(true, "online resume:" + roomId);

							p.handleOnlineResume();
						}
					}
				});

	}

	/**
	 * 
	 */
	private void onResumeTeam() {
		LG0011Response resp = LLU.o(LG0011Response.class);
		// state=LLGamePlayerStates.Resuming;
		resp.setState(State.obtain(state.state, state.message));

		// / LLDeck d = LLU.asObject(deck);

		// setting the room
		LLRoom rr = LLU.asObject(room);
		resp.setRoomId(rr.id.intValue());
		resp.setRoomName(roomName);

		resp.setRoomType(roomType);

		if (team != null && team instanceof LLTeam) {

			LLTeam t = LLU.asObject(team);
			LLETRoom r = LLU.asObject(room);
			

			// waiting join the team
			if (t.state.eq(LLTeamStates.Waiting)) {
				resp.setTeamState(GL0000Beans.TEAM_STATE_AWAITING);

			} else if (t.state.inStage()) {
				// waiting an other deck .

				resp.setTeamState(GL0000Beans.TEAM_STATE_BETWEEN_DECKS);

			} else if (t.stageWaiting) {
				LG0011TeamObject teamData = LLU.o(LG0011TeamObject.class);
				// stage waiting between stageA-->StageB ,StageB-->StageC
				if (r != null) {
					teamData.setRewards(r.getRewardList());
					teamData.setScore(getChips());
					teamData.setDeckRank(Range.obtain(deckRank, 3));

					teamData.setTeamRank(Range.obtain(rank, t.players.size()));

					teamData.setUpgradeRank(Range.obtain(
							t.match.getUpgradeNumbers(), t.players.size()));
				}
				resp.setTeamData(teamData);

				resp.setTeamState(GL0000Beans.TEAM_STATE_BETWEEN_STAGE);

			}else{
				throw new RuntimeException("Unknown state!"+state);
			}
			// resp.setState(State.obtain(t.state.state,t.state.toString()));
		}

		// logger.info("GL0011 -"+GameContextGlobals.getJsonConvertor().format(resp));
		sendBrokerMessage(resp, false);
	}

	/**
	 * 
	 * 
	 * @param deck
	 */
	private void sendResume() {

		LG0011Response resp = LLU.o(LG0011Response.class);
		resp.setState(State.obtain(state.state, state.message));
		
		LLRoom rr = LLU.asObject(room);
		resp.setRoomId(rr.id.intValue());
		resp.setRoomName(roomName);

		resp.setRoomType(roomType);


		
		if (deck != null && (deck instanceof LLDeck)) {
			LG3001Response lgResp = ((LLDeck) deck).wrapOffline(this);
			resp.setData(lgResp);

		}else{
			throw new RuntimeException("Deck is empty");
		}

		sendBrokerMessage(resp, false);

	}

	/**
	 * 
	 * 
	 */
	private void handleOnlineResume() {

		// LLGamePlayer player = this;// basePlayer.getGamePlayer(req.getId());

		if (state.eq(LLGamePlayerStates.Team)) {
			onResumeTeam();
		} else if (state.eq(LLGamePlayerStates.Deck)) {
			// player.resume=true;
			sendResume();
		}

	}

	public void sendException(LLRoomExceptionTypes types) {
		LG4003Response obj = LLU.o(LG4003Response.class);
		obj.setId(id);
		obj.setState(types.getState());

		sendBrokerMessage(obj, false);
	}

	public void checkState(LLGamePlayerStates states) {
		if (!state.eq(states)) {
			sendException(LLRoomExceptionTypes.LLAlreadyInGame);
			throw new RuntimeException("player[" + getId() + "] not in the "
					+ states + " state but in " + state);
		}
	}

	@Override
	public String toString() {
		return "\nLLGamePlayer [id=" + id + ", chips=" + chips + ", nickName="
				+ nickName + ", state=" + state + ", online=" + online
				+ ", rank=" + rank + "]";
	}

	/**
	 * @return
	 */
	public Object getJmxPlayer() {
		// TODO Auto-generated method stub
		return jmxPlayer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.room.IUniqueIdentifiedObject#equals(com.sky.game
	 * .landlord.room.IUniqueIdentifiedObject)
	 */
	public boolean equals(IUniqueIdentifiedObject obj) {
		// TODO Auto-generated method stub
		long roomId = getParentId();
		long pRoomId = obj.getParentId();
		logger.info("(" + id + "-" + obj.getId() + "," + roomId + " -"
				+ pRoomId + ")");
		return getId().longValue() == obj.getId().longValue()
				&& roomId == pRoomId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.context.annotation.introspector.IUniqueIdentifiedObject#
	 * getParentId()
	 */
	public long getParentId() {
		long parenId = 0;
		// TODO Auto-generated method stub
		IGamePlayerObserver r = room;
		if (r != null && (r instanceof LLRoom)) {
			// this.destory((LLRoom) r);
			parenId = ((LLRoom) r).id;
		}
		return parenId;
	}

	public boolean isResume() {
		return resume;
	}

	public void setResume(boolean resume, String location) {
		if (!isRobot)
			jmxPlayer.append("resume - " + resume + " " + location);
		this.resume = resume;
	}

}
