/**
 * @sparrow
 * @Jan 6, 2015   @5:23:27 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.event.EventHandler;
import com.sky.game.context.event.EventProcessTask;
import com.sky.game.context.game.IGameLife;
import com.sky.game.context.id.LLIdTypes;
import com.sky.game.context.util.BeanUtil;
import com.sky.game.context.util.GameUtil;
import com.sky.game.context.event.DefaultGameSession;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.IGameEventObserver;
import com.sky.game.context.event.IGameSession;
import com.sky.game.landlord.jmx.JmxTeam;
import com.sky.game.landlord.message.AbstractBroker;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.LG4011Response;
import com.sky.game.protocol.commons.GL0000Beans.UserRank;
import com.sky.game.service.domain.GameEnroll;

/**
 * @author sparrow
 *
 */
public class LLTeam extends AbstractBroker implements IIdentifiedObject,
		IGameEventObserver, ITeamSorted, IGamePlayerObserver {
	private static final Log logger = LogFactory.getLog(LLTeam.class);

	// team
	// team should have players.
	// team also need the decks,
	// team belongs the rooms

	LLETRoom room;

	Long id;

	LLGameObjectMap<LLGamePlayer> players;
	LLGameObjectMap<LLDeck> decks;
	// Map<Long, LLGamePlayer> players;

	// Map<Long, LLDeck> decks;

	// deckManager;
	ITeamMatch match;

	LLTeamStates state;

	EventHandler<EventProcessTask<?>> eventHandler;

	LLTeam team;
	
	
	// connection broken & resume
	boolean stageWaiting;
	
	

	// StringBuffer buffer;

	JmxTeam jmxTeam;

	IGameSession gameSession;

	public void append(String message) {
		jmxTeam.append(message);
		// buffer.append("$").append(CronUtil.getFormatedDateNow()).append(" - ").append(message).append("\n");
	}

	public void flush() {
		// logger.info(buffer.toString());

	}

	/**
	 * @param room
	 */
	public LLTeam(LLETRoom room) {
		super();
		this.room = room;
		this.id = LLU.getId(LLIdTypes.IdTypeTeam);
		// deck maps
		this.decks = LLGameObjectMap.getMap();
		// players map.
		this.players = LLGameObjectMap.getMap();
		this.gameSession = LLU.o(DefaultGameSession.class);
		this.gameSession.setId(this.id);
		
		jmxTeam = LLU.o(JmxTeam.class);
		jmxTeam.setId(id);

		GameEventHandler.handler.registerObserver(GameEvent.GAME_END, this);
		eventHandler = EventHandler.obtain(GameContextGlobals.getExecutor());

		team = this;
		room.addTeam(team);

		this.state = LLTeamStates.Born;

		append("\n");
		append(String.valueOf(this.id));

	}

	public void destroy() {
		// destroy the
		GameUtil.clearGameLife(id + "/baseincrease");
		for (LLDeck d : decks.values()) {
			d.reset(true);
		}
		decks.clear();

		LLGamePlayer[] pp = players.values().toArray(new LLGamePlayer[] {});
		for (LLGamePlayer p : pp) {
			unObserve(p);
			p.state = LLGamePlayerStates.Reward;
			p.destory(room);
		}

		players.clear();

		GameEventHandler.handler.unRegisterObserver(GameEvent.GAME_END, this);

		room.removeTeam(team);

		// room.teams.remove(id);
		// room.team = null;
	}

	private void wrapState() {
		LG4011Response resp = LLU.o(LG4011Response.class);
		resp.setId(team.room.getId());
		resp.setTeamId(getId());
		resp.setState(state.getState());
		sendBrokerMessage(resp, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.message.AbstractBroker#getUri()
	 */
	@Override
	public String getUri() {
		// TODO Auto-generated method stub
		return room.getUri() + "/team/" + getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.context.annotation.introspector.IIdentifiedObject#getId()
	 */
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
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
		this.id = id;
	}

	public void endGame(long deckId) {
		LLDeck deck = decks.get(Long.valueOf(deckId));
		if (deck != null && deck.game != null) {
			deck.game.setState(LLGameStates.Score);
		}
		// match.onGameEnd(deck);
	}

	protected void onGameEnd(LLDeck deck) {

		try {
			if (state.inStage()) {
				// if player on leaving state.
				// let's the player leaving.
				// checking if the player leaving.
				// for(LLGamePlayer p:deck.players.values()){
				// if(p.state.eq(LLGamePlayerStates.Leave)){
				// p.elimination=true;
				// }
				// }

				match.onGameEnd(deck);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.event.IGameEventObserver#observer(com.sky.game.
	 * landlord.event.GameEvent)
	 */
	public void observer(GameEvent evt) {
		// TODO Auto-generated method stub
		// game end.
		if (evt.isEvent(GameEvent.GAME_END)) {
			LLDeck deck = LLU.asObject(evt.obj);
			if (decks.containsKey(deck.getId())) {

				// deck.reset(true);
				onGameEnd(deck);

			}

		}

	}

	public LLTeamStates getState() {
		return state;
	}

	public void setState(LLTeamStates state) {
		logger.info("state[" + getId() + "] - " + this.state.toString()
				+ " -> " + state.toString());
		this.state = state;
		wrapState();
		jmxTeam.setState(state.getState().toString());

		EventProcessTask<LLTeam> task = new EventProcessTask<LLTeam>(this) {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					onStateChanged();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jmxTeam.append(e);
				}
			}

		};
		task.setSession(this.gameSession);

		this.eventHandler.addEvent(task);
	}

	private void notifyEnrollPlayerChanges() {
		GL0000Beans.LG4006Response req = LLU
				.o(GL0000Beans.LG4006Response.class);
		req.setId(room.getId());
		req.setValue(players.size());
		sendBrokerMessage(req, false);
	}

	@Override
	public void observe(LLGamePlayer player) {
		// TODO Auto-generated method stub
		if (state.eq(LLTeamStates.Waiting)) {
			super.observe(player);
			// add the players
			enter(player);

			// logger.info("joinTeam[" + getId() + "] -  " +
			// Arrays.toString(players.values().toArray(new LLGamePlayer[]{}))
			// + " / " + players.size());
			append(player.getId() + " join ");
			notifyEnrollPlayerChanges();

			// checking if the player should be start match.

			// team.room.players.put(player.getId(), this);
			//

			if (room.config.isEnableRobot()) {
				onOneUserLoginTimeout();
			}

			// should check the state after the player meet the condition
			if (players.size() >= room.config.getPlayerNumberControl()
					.getMinPlayerNumber() && players.size() % 3 == 0) {
				setState(LLTeamStates.Begin);
			}
		}

	}

	public void nextState() {

		LLTeamStates nextState = LLTeamStates.getState(state.state + 1);
		setState(nextState);
	}

	@Override
	public void unObserve(LLGamePlayer player) {
		// TODO Auto-generated method stub
		super.unObserve(player);
		// player.getSeat().deck.unObserve(player);
		leave(player);
		// also need to leave the deck
		// l
		LLDeck deck = BeanUtil.v(player, "seat.deck");
		team.append(player.getId() + " leave ");
		if (deck != null) {
			deck.unObserve(player);
		}

	}

	private void begin() {

		for (LLGamePlayer player : players.values()) {
			if (!room.checkFreeTournament() && !player.isRobot) {
				GameEnroll gameEnroll = GSPP.getGameEnrollByUserIdAndRoomId(
						player.getId(), getId());
				if(gameEnroll!=null&&team!=null){
					GSPP.updateGameEnrollByTeamId(gameEnroll.getId(), team.getId());
				}
			}
		}
	}

	long begin;

	private void gameLife(final String uri, long lives) {
		GameUtil.gameLife(uri + getUri(), lives, this, "nextState")
				.setGameSession(this.gameSession);
	}

	public synchronized void onStateChanged() {
		append(state.toString());
		flush();
		switch (state.state) {
		case LLTeamStates.BORN: {

		}
			break;

		case LLTeamStates.WAITING: {
			// on waiting state ,should send player number changes.

		}
			break;

		case LLTeamStates.BEGIN: {
			// on begin state, the game begins ,show some promt.
			begin = System.currentTimeMillis();
			// setState(LLTeamStates.StageC);
			begin();
			nextState();

		}
			break;
		case LLTeamStates.STAGEA: {
			// before the stage start,send the room base and player integral.
			// on stage a.
			match = LLTeamMatchA.obtain(team);
			match.startMatch();

		}
			break;
		case LLTeamStates.STAGEA_END: {
			logger.info("stageA:" + (System.currentTimeMillis() - begin));
			gameLife("gameA", 10000L);

		}
			break;
		case LLTeamStates.STAGEB: {
			match = LLTeamMatchB.obtain(team);
			match.startMatch();
		}
			break;
		case LLTeamStates.STAGEB_END: {
			logger.info("stageB:" + (System.currentTimeMillis() - begin));
			// nextState();

			gameLife("gameB", 10000L);
		}
			break;
		case LLTeamStates.STAGEC: {
			match = LLTeamMatchC.obtain(team);
			match.startMatch();

		}
			break;
		case LLTeamStates.STAGEC_END: {
			logger.info("stageC:" + (System.currentTimeMillis() - begin));
			setState(LLTeamStates.End);
			gameLife("gameC", 10000L);
		}
			break;
		case LLTeamStates.CANCEL: {
			//team.reward();
			team.destroy();
		}
			break;
		case LLTeamStates.END: {
			// before the team destroy ,
			// show reward the players.
			logger.info("TeamEnd	:" + (System.currentTimeMillis() - begin));
			team.reward();
			team.destroy();
		}
			break;

		default:
			break;
		}

	}

	private void reward() {
		// high <-- low
		LLGamePlayer[] players = getSortedGamePlayerInTeam(false);
		// reward the player by the ranking
		// TODO: reward player.
		// logger.info("Reward-----");
		for (LLGamePlayer p : players) {
			reward(p);
		}
	}

	
	/**
	 * low --> hight
	 * @param asc
	 * @return
	 */
	public LLGamePlayer[] getSortedGamePlayerInTeam(boolean asc) {
		LLGamePlayer[] objs = players.values().toArray(new LLGamePlayer[] {});

		objs = sortGamePlayer(objs, asc,true);
		return objs;
	}

	/**
	 * sort the players.
	 * 
	 * asc  0 1 2 
	 * desc 2 1 0
	 * 
	 * so the desc 
	 *  is the game sort max coins first position.
	 * 
	 * @param objs
	 * @return
	 */
	public static LLGamePlayer[] sortGamePlayer(LLGamePlayer[] objs,
			final boolean asc,boolean rankSort) {

		Arrays.sort(objs, new Comparator<LLGamePlayer>() {

			public int compare(LLGamePlayer o1, LLGamePlayer o2) {
				// TODO Auto-generated method stub
				return asc ? (o1.getChips() - o2.getChips())
						: (o2.getChips() - o1.getChips());
			}

		});

		//
		// BUGFIXED :
		//
		if(rankSort){
		for (int i = 0; i < objs.length; i++) {
			LLGamePlayer p = objs[i];
			if (asc) {
				p.changeRank(objs.length - i, objs.length);
			} else {
				p.changeRank(i + 1, objs.length);
			}
		}
		}
		return objs;
	}

	/**
	 * low -- > high
	 * 
	 * @param deck
	 * @return
	 */
	public static LLGamePlayer[] getSortedGamePlayerInDeck(LLDeck deck,boolean asc) {
		LLGamePlayer[] objs = deck.players.values().toArray(
				new LLGamePlayer[] {});

		sortGamePlayer(objs, asc,false);

		return objs;
	}

	/**
	 * 
	 * Robot join
	 * 
	 * 
	 */
	IGameLife gameLife = null;

	// static long robotUserId = 1;
	public void createRobot() {
		long robotUserId = LLU.getRobotId();
		long userid = GSPP.login("Robot" + robotUserId,
				"96e79218965eb72c92a549dd5a330112", "R0123456789-"
						+ robotUserId);
		if (userid > 0) {
			robotUserId++;
			LLGamePlayer robot = new LLGamePlayer(userid, true);

			// stop adding player into the team.
			// if(team.room.config.get)
			if (team.players.size() < room.config.getPlayerNumberControl()
					.getMinPlayerNumber()) {
				// enroll with the itemId 1
				// room.enrollByItem(robot,Integer.valueOf(0));
				robot.setChips(room.config.getInitIntegral());
				team.observe(robot);
			}
		}

	}

	private void onOneUserLoginTimeout() {
		GameUtil.gameLife(team.getUri(), 1000L, this, "createRobot")
				.setGameSession(this.gameSession);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.ITeamSorted#sorted()
	 */
	public LLGamePlayer[] sorted() {
		// TODO Auto-generated method stub
		return getSortedGamePlayerInTeam(false);
	}

	public void reward(LLGamePlayer player) {

		room.reward(player, getId());
		// unObserve(player);
		player.setState(LLGamePlayerStates.Reward);
		player.destory(room);
		logger.info("player[" + player.getId() + "] - reward ("
				+ player.getChips() + ")  " + player.getRank() + "/"
				+ team.players.size());
		team.append("team[" + id + "]  - reward (player=" + player.getId()
				+ ", chips=" + player.getChips() + " ,rank=" + player.getRank()
				+ ")  " + "/ team.player.size=" + team.players.size());

	}

	public void sendUserRanks(LLGamePlayer player) {
		LLGamePlayer pp[] = getSortedGamePlayerInTeam(false);
		GL0000Beans.LG4009Response ranks = LLU
				.o(GL0000Beans.LG4009Response.class);
		List<UserRank> rr = GameUtil.getList();
		ranks.setRanks(rr);
		for (LLGamePlayer p : pp) {
			if (p != null) {
				UserRank userRank = LLU.o(UserRank.class);
				userRank.setId(p.getId());
				userRank.setNickName(p.user.getNickName());
				userRank.setRank(p.rank);
				userRank.setScore(p.getChips());
				rr.add(userRank);
			}
		}

		player.sendBrokerMessage(ranks, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.room.IGamePlayerObserver#enter(com.sky.game.landlord
	 * .room.LLGamePlayer)
	 */
	public void enter(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().put(player.getId(), player);
		player.team = this;
		player.setState(LLGamePlayerStates.Team);
		player.lastAcess=System.currentTimeMillis();
		// player.team=getGamePlayerObserver();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.room.IGamePlayerObserver#leave(com.sky.game.landlord
	 * .room.LLGamePlayer)
	 */
	public void leave(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().remove(player.getId());
		// player.team=null;
		player.setState(LLGamePlayerStates.Room);
		player.team = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#getGameObjectMap()
	 */
	public LLGameObjectMap<LLGamePlayer> getGameObjectMap() {
		// TODO Auto-generated method stub
		return players;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.room.IGamePlayerObserver#getGamePlayerObserver()
	 */
	public IGamePlayerObserver getGamePlayerObserver() {
		// TODO Auto-generated method stub
		return this;
	}

	public JmxTeam getJmxTeam() {
		// jmxTeam.setDetail(buffer.toString());
		return jmxTeam;
	}

}
