/**
 * @sparrow
 * @Jan 12, 2015   @1:19:46 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.event.EventHandler;
import com.sky.game.context.event.EventProcessTask;
import com.sky.game.context.id.LLIdTypes;
import com.sky.game.context.util.BeanUtil;
import com.sky.game.context.util.GameUtil;
import com.sky.game.context.event.DefaultGameSession;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.IGameEventObserver;
import com.sky.game.context.event.IGameSession;
import com.sky.game.landlord.exception.LLGameExecption;
import com.sky.game.landlord.message.AbstractBroker;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.domain.Item;

/**
 * @author sparrow
 *
 */
public class LLFTTeam extends AbstractBroker implements IIdentifiedObject,
		IGameEventObserver, ITeamSorted ,IGamePlayerObserver{
	private static final Log logger = LogFactory.getLog(LLFTTeam.class);
	LLFTRoom room;

	Long id;

	LLGameObjectMap<LLGamePlayer> players;
	LLGameObjectMap< LLDeck> decks;
	

	// deckManager;
	ITeamMatch match;
	LLFTTeam team;
	LLFTTeamStates state;

	EventHandler<EventProcessTask<?>> eventHandler;

	IGameSession gameSession;

	/**
	 * @param room
	 */
	public LLFTTeam(LLFTRoom room) {
		super();
		
		this.room = room;
		this.id = LLU.getId(LLIdTypes.IdTypeTeam);
		this.decks = LLGameObjectMap.getMap();//GameUtil.getMap();
		this.players=LLGameObjectMap.getMap();
		this.gameSession=LLU.o(DefaultGameSession.class);
		this.gameSession.setId(this.id);
		//this.players = GameUtil.getMap();
		// this.playerDeckMap=GameUtil.getMap();
		
		GameEventHandler.handler.registerObserver(GameEvent.GAME_END, this);
		eventHandler = EventHandler.obtain(GameContextGlobals.getExecutor());
		room.teams.put(this.id, this);
		team = this;
		
		this.state = LLFTTeamStates.Born;
	}

	public void destroy() {
		// destroy the
		
		LLGamePlayer[] pp = players.values().toArray(new LLGamePlayer[] {});
		for (LLGamePlayer p : pp) {
			//unObserve(p);
			p.destory(room);
		}

		GameEventHandler.handler.unRegisterObserver(GameEvent.GAME_END, this);
		room.teams.remove(this.getId());
		team=null;
	}

	/**
	 * 
	 */
	public LLFTTeam() {
		// TODO Auto-generate d constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.message.AbstractBroker#getUri()
	 */
	@Override
	public String getUri() {
		// TODO Auto-generated method stub
		return room.getUri() + "/team://" + getId();
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
		if (evt.isEvent(GameEvent.GAME_END)) {
			LLDeck deck = LLU.asObject(evt.obj);
			if (decks.containsKey(deck.getId())) {
				// process
				// deck.reset(true);
				onGameEnd(deck);

			}

		}

	}

	private void prizePlayers() {
		// leave decks
		for (LLDeck d : decks.values()) {
			d.reset(true);
		}
		decks.clear();
		
		LLGamePlayer[] players = getSortedGamePlayerInTeam();// room.getSortedPlayers(deck);
		
		for (int i = 0; i < players.length; i++) {
			LLGamePlayer player = players[i];
			player.setState(LLGamePlayerStates.Reward);
			// reward.
			//if(!player.isRobot)
			//float radio=player.winner?0.95f:1.0f;
			int chips= (player.getChips()-room.config.getEnrollRestrict().getMinCoins());
			if(chips>0){
				chips=(int)(chips*0.95f);
			}
		
				GSPP.reward(player.getId(), room.getId(), getId(),
					player.getRank(), room.config.getReward().getItemId(),chips);
			//player.destory(room);
			// add exp

		}

	}

	private LLGamePlayer[] getSortedGamePlayerInTeam() {
		LLGamePlayer[] objs = players.values().toArray(new LLGamePlayer[] {});

		objs = LLTeam.sortGamePlayer(objs, false, true);
		return objs;
	}

	
	// protected void onGameEnd(LLDeck deck) {
	// // before the all reset ,let's reward the players.
	//
	// prizePlayers(deck);
	// deck.reset(true);
	// deck.createGame();
	// }

	protected void onGameEnd(LLDeck deck) {
		// deck.reset(true);
		setState(LLFTTeamStates.StageEnd);
		// reward ()

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

	}

	public void unEnroll(LLGamePlayer player) {
		GameUser user = GSPP.getGameUser(player.getId());
		if (user != null) {
			player.setExp(user.getExp());
			boolean ret = GSPP.enroll(player.getId(), room.getId(), getId(),
					room.config.getEnrollTicket().getItemId(),  room.config.getEnrollRestrict().getMinCoins());
			if (ret) {
				player.setChips(0);
			}
		}
	}

	private void enroll(LLGamePlayer player) {
		int leastCoins=BeanUtil.getInt(room, "config.enrollRestrict.minCoins");
		Integer itemId=Integer.valueOf(room.config.getEnrollTicket().getItemId());
		boolean valid = GSPP.validEnroll(player.getId(),room.config.getEnrollTicket().getItemId(), leastCoins);
			
		if (!valid) {
			room.itemNotEnough(player, itemId);
		} else {

			GameUser user = GSPP.getGameUser(player.getId());
			if (user != null) {
				player.setExp(user.getExp());

				boolean ret = true;//GSPP.enroll(player.getId(), room.getId(),
						//getId(), room.config.getEnrollTicket().getItemId(), room.config
							//	.getEnrollRestrict().getMinCoins());
				if (ret) {
					player.setChips(room.config.getEnrollRestrict()
							.getMinCoins());

				}
			}
		}
	}

	@Override
	public void observe(LLGamePlayer player) {
		// TODO Auto-generated method stub
		if (state.eq(LLFTTeamStates.Waiting)) {
			super.observe(player);
			//player.elimination=false;
			// checking if the player should be start match.
			enter(player);

			enroll(player);
			if (room.config.isEnableRobot()) {
				onOneUserLoginTimeout();
			}
		}

		// should check the state after the player meet the condition
		if (players.size() >= 3 && players.size() % 3 == 0) {
			setState(LLFTTeamStates.Begin);
		}

	}

	public LLFTTeamStates getState() {
		return state;
	}

	public void setState(LLFTTeamStates state) {
		logger.info("state[" + getId() + "] - " + this.state.toString()
				+ " -> " + state.toString());
		this.state = state;
		EventProcessTask<LLFTTeam> task=new EventProcessTask<LLFTTeam>(this) {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					onStateChanged();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		task.setSession(this.gameSession);
		this.eventHandler.addEvent(task);
	}

	public void nextState() {
		LLFTTeamStates nextState = LLFTTeamStates.getState(state.state + 1);
		setState(nextState);
	}

	@Override
	public void unObserve(LLGamePlayer player) {
		// TODO Auto-generated method stub

		super.unObserve(player);
		//players.remove(player.getId());
		leave(player);

	}

	private void configDeck(LLDeck deck) {
		deck.teamSorted = this;
		deck.conf.roomName=room.config.getRoomName();
		deck.conf.roomType=LLRoomTypes.FT.state;
		deck.conf.aiLevel=room.config.getAiLevel();
		team.decks.put(deck.getId(), deck);

	}

	private void startMatch() {
		// before match begin,sorted user.
		getSortedGamePlayerInTeam();
		LLDeck deck = new LLDeck(team.room);

		configDeck(deck);

		LLGamePlayer[] subPlayers = team.players.values().toArray(
				new LLGamePlayer[] {});

		try {
			// playerDeckMap.put(key, value);
			deck.seat(subPlayers);
		} catch (LLGameExecption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onStateChanged() {

		switch (state.state) {
		case LLFTTeamStates.BORN: {

		}
			break;

		case LLFTTeamStates.WAITING: {
			// on waiting state ,should send player number changes.
		}
			break;

		case LLFTTeamStates.BEGIN: {
			// on begin state, the game begins ,show some promt.
			
			nextState();
		}
			break;
		case LLFTTeamStates.STAGE: {
			// on stage a.
			startMatch();
		}
			break;
		case LLFTTeamStates.STAGE_END: {
			setState(LLFTTeamStates.End);

		}
			break;

		case LLFTTeamStates.CANCEL: {
			team.destroy();
		}
			break;
		case LLFTTeamStates.END: {
			// before the team destroy ,
			// show reward the players.
			team.reward();
			team.destroy();
		}
			break;

		default:
			break;
		}

	}

	private void reward() {
		prizePlayers();
	}

	/**
	 * 
	 * Robot join
	 * 
	 * 
	 */
	
	
	//static long robotUserId = 1;
	
	public void createRobot(){
		long robotUserId=LLU.getRobotId();
		long userid = GSPP.login("Robot" + robotUserId, "96e79218965eb72c92a549dd5a330112",
				"R0123456789-" + robotUserId);
		if (userid > 0) {
			
			robotUserId++;
			LLGamePlayer robot = new LLGamePlayer(userid,true);
			
			logger.info("createRobot["+userid+"]");
			// stop adding player into the team.
			//if(team.room.config.get)
			if(team.players.size()<3)
				team.observe(robot);
		}

	}

	private void onOneUserLoginTimeout() {
		
		GameUtil.gameLife(team.getUri()+"/createRobot", 2000L, this, "createRobot").setGameSession(this.gameSession);;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.ITeamSorted#sorted()
	 */
	public LLGamePlayer[] sorted() {
		// TODO Auto-generated method stub
		LLGamePlayer[] players = getSortedGamePlayerInTeam();
		return players;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#enter(com.sky.game.landlord.room.LLGamePlayer)
	 */
	public void enter(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().put(player.getId(), player);
		//player.team=this;
		player.setState(LLGamePlayerStates.Team);
		player.team=this;
		player.lastAcess=System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#leave(com.sky.game.landlord.room.LLGamePlayer)
	 */
	public void leave(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().remove(player.getId());
		player.setState(LLGamePlayerStates.Room);
		//player.team=null;
		player.team=null;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#getGameObjectMap()
	 */
	public LLGameObjectMap<LLGamePlayer> getGameObjectMap() {
		// TODO Auto-generated method stub
		return players;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#getGamePlayerObserver()
	 */
	public IGamePlayerObserver getGamePlayerObserver() {
		// TODO Auto-generated method stub
		return this;
	}

	

}
