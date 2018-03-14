/**
 * @sparrow
 * @Jan 8, 2015   @1:22:36 PM
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
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.achievement.AchievementObserver;
import com.sky.game.landlord.blockade.config.BlockadeTournamentConfig.Stage;
import com.sky.game.context.event.DefaultGameSession;
import com.sky.game.context.event.GameEvent;
import com.sky.game.context.event.GameEventHandler;
import com.sky.game.context.event.IGameEventObserver;
import com.sky.game.context.event.IGameSession;
import com.sky.game.landlord.exception.LLGameExecption;
import com.sky.game.landlord.message.AbstractBroker;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.BTLevelReward;
import com.sky.game.protocol.commons.GL0000Beans.LG4015Response;
import com.sky.game.protocol.commons.GL0000Beans.Range;
import com.sky.game.protocol.commons.GS0000Beans.PGameBlockadeUser;
import com.sky.game.service.logic.game.GameBlockadeUserStates;

/**
 * @author sparrow
 *
 */
public class LLBTTeam extends AbstractBroker implements IIdentifiedObject,
		IGameEventObserver, ITeamSorted,IGamePlayerObserver {
	private static final Log logger = LogFactory.getLog(LLBTTeam.class);

	LLBTLevel level;

	// a team can only have 3players
	LLGameObjectMap<LLGamePlayer> players;
	// a team can only have 1 deck
	LLGameObjectMap<LLDeck> decks;

	Long id;

	LLBTTeamStates state;
	LLBTTeam team;

	EventHandler<EventProcessTask<?>> eventHandler;
	
	IGameSession gameSession;

	/**
	 * 
	 */
	public LLBTTeam() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param level
	 */
	public LLBTTeam(LLBTLevel level) {
		super();
		this.level = level;

		this.id = LLU.getId(LLIdTypes.IdTypeTeam);
		this.decks = LLU.getGameObjectMap();// GameUtil.getMap();
		this.players = LLU.getGameObjectMap();// GameUtil.getMap();
		GameEventHandler.handler.registerObserver(GameEvent.GAME_END, this);
		eventHandler = EventHandler.obtain(GameContextGlobals.getExecutor());
		gameSession=LLU.o(DefaultGameSession.class);
		this.gameSession.setId(this.id);
		level.teams.put(this.getId(), this);
		team = this;
		this.state = LLBTTeamStates.Born;

	}

	public void destroy() {
		// destroy the
		for (LLDeck d : decks.values()) {
			d.reset(true);
		}
		decks.clear();

		LLGamePlayer[] pp = players.values().toArray(new LLGamePlayer[] {});
		for (LLGamePlayer p : pp) {
			unObserve(p);
		}

		GameEventHandler.handler.unRegisterObserver(GameEvent.GAME_END, this);
		level.teams.remove(this.getId());
		team = null;
	}

	public void onGameEnd(LLDeck deck) {
		// setState(LLBTTeamStates.StageEnd);
		
		// check should end the game when one players chips is zero.
		boolean shouldEndGames=false;
		for(LLDeckSeat seat:deck.seats){
			if(seat.player.getChips()<1)
			{
				shouldEndGames=true;
				break;
			}
		}
		
		shouldEndGames=shouldEndGames?true:deck.shouldEndGames();

		if (!shouldEndGames) {
			
			if (deck.state.equals(LLDeckStates.DeckGameEnd)) {
				
				deck.reset(false);

				GameUtil.gameLife("timeout://" + deck.getUri(), 5000L, deck,
						"createGame").setGameSession(gameSession);;
				// deck.createGame();
			}
		} else {
			// deck.reset(true);
			team.reward(deck);
			setState(LLBTTeamStates.StageEnd);
		}
	}

	private void log(String message) {
		logger.info(GameUtil.formatId(id, this) + " - " + message);
	}

	/**
	 * @param stageend
	 */
	public void setState(LLBTTeamStates stageend) {
		// TODO Auto-generated method stub
		log(this.state.toString() + " -> " + state.toString());

		this.state = stageend;
		EventProcessTask<LLBTTeam> task=new EventProcessTask<LLBTTeam>(this) {

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
		task.setSession(team.gameSession);
		this.eventHandler.addEvent(task);

	}

	public void nextState() {
		LLBTTeamStates nextState = LLBTTeamStates.getState(state.state + 1);
		setState(nextState);
	}

	public synchronized void onStateChanged() {

		switch (state.state) {
		case LLBTTeamStates.BORN: {

		}
			break;

		case LLBTTeamStates.WAITING: {
			// on waiting state ,should send player number changes.
		}
			break;

		case LLBTTeamStates.BEGIN: {
			// on begin state, the game begins ,show some promt.

			team.nextState();
		}
			break;
		case LLBTTeamStates.STAGE: {
			// on stage a.
			team.startMatch();
		}
			break;
		case LLBTTeamStates.STAGE_END: {
			setState(LLBTTeamStates.End);

		}
			break;

		case LLBTTeamStates.CANCEL: {
			team.destroy();
		}
			break;
		case LLBTTeamStates.END: {
			// before the team destroy ,
			// show reward the players.
			
			team.destroy();
		}
			break;

		default:
			break;
		}

	}

	private void configDeck(LLDeck deck) {
		deck.teamSorted = this;
		deck.conf=new DeckConfiguration();
		deck.conf.baseChips=level.stage.getBase();
		deck.conf.gamePerDeck =level.room.config.getGamePerDeck();
		deck.conf.aiLevel=level.stage.getAiLevel();
		
		deck.conf.winExp=level.room.config.getDeckConfig().getWinExp();
		deck.conf.loseExp=level.room.config.getDeckConfig().getLoseExp();
		deck.conf.roomName=level.room.config.getRoomName();
		deck.conf.roomType=LLRoomTypes.BT.state;
		team.decks.put(deck.getId(), deck);

	}

	private void startMatch() {
		// before match begin,sorted user.
		getSortedGamePlayerInTeam();
		LLDeck deck = new LLDeck(team.level.room);

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

	private LLGamePlayer[] getSortedGamePlayerInTeam() {
		LLGamePlayer[] objs = players.values().toArray(new LLGamePlayer[] {});

		objs = LLTeam.sortGamePlayer(objs, false, true);
		return objs;
	}

	
	/**
	 *  
	 * 
	 */
	private void reward(LLDeck deck) {
		// reward the player.
		// LLBTLevel nextLevel=level.room.getLevel(level)
		//LLBTLevel oldLevel =level;
		LLBTLevel maxLevel = level.room.getLevel(level.maxLevel);
		
		//LLBTLevel minLevel=level.room.getLevel(Integer.valueOf(1));

		
		
		for (LLDeckSeat seat : deck.seats) {
			LLGamePlayer p = seat.player;
			if (!p.isRobot) {
				Stage  oldStage=level.stage;
				Stage currentStage=level.room.getStageByPlayer(Long.valueOf(seat.player.getChips()));
				LLBTLevel currentLevel=level.room.getLevel(Integer.valueOf(currentStage.getId()));
				
				int current = seat.player.getChips();//nextLevel.stage.getMinIntegeral()
						//- seat.player.getChips();
				
				PGameBlockadeUser user = GSPP.getGameBlockadeUser(p.getId(),
						level.room.id);
				int oldIntegral=0;
				if(user!=null){
					oldIntegral=user.getIntegral().intValue();
				}
				int rewardIntegral=current-oldIntegral;
				
				//LLBTLevel currentLevel=level.room.getLevel(Integer.valueOf(currentStage.getId()));
				LG4015Response o = LLU.o(LG4015Response.class);
				o.setRewardIntegral(rewardIntegral);
//				//if(seat.scoreBroad.currentScore<seat.scoreBroad.oldScore){
//					o.setRewardIntegral(-1*seat.scoreBroad.score);
//				}else{
//					o.setRewardIntegral(seat.scoreBroad.score);
//				}
				
				o.setStatus(GameBlockadeUserStates.Process.status);
				//
				BTLevelReward obj = LLU.o(BTLevelReward.class);
				obj.setLevel(currentLevel.id.intValue());
				// PItem item=LLU.o(PItem.class);
				// item.wrap(nextLevel.getReward());
				obj.setReward(currentLevel.getReward());
				
				obj.setIntegral(Range.obtain(current,
						currentLevel.stage.getMaxIntegeral()));
				o.setCurrentLevel(obj);
				
				if(currentLevel.id.intValue()==1&&currentLevel.stage.getMinIntegeral()>=current){
					o.setStatus(GameBlockadeUserStates.Lose.status);
				}
				
				
				obj = LLU.o(BTLevelReward.class);
				if(current>level.room.config.getMaxIntegral()){
					//nextLevel=maxLevel;
					o.setStatus(GameBlockadeUserStates.Win.status);
				
				}
				
				obj.setLevel(level.id.intValue());
				obj.setReward(level.getReward());
				obj.setIntegral(Range.obtain(seat.scoreBroad.oldScore,
						level.stage.getMaxIntegeral()));
				o.setOldLevel(obj);

				obj = LLU.o(BTLevelReward.class);
				obj.setLevel(maxLevel.id.intValue());
				obj.setReward(maxLevel.getReward());
				obj.setIntegral(Range.obtain(current,
						maxLevel.stage.getMaxIntegeral()));
				o.setMaxLevel(obj);
				
				logger.info(GameContextGlobals.getJsonConvertor().format(o));
				
				//Stage currentStage=level.room.getStageByPlayer(Long.valueOf(p.getChips()));
				// update the current chips into the database.
				boolean ret=GSPP.updateBlockadeIntegral(Long.valueOf(p.id), level.room.id,Long.valueOf(o.getRewardIntegral()), Integer.valueOf(currentStage.getId()));
				if(!ret){
					logger.info(GameUtil.formatId(id, this)+" - updateBlockadeIntegral failed " +level.room.id);
				}
				
				
				AchievementObserver.observerWinnerBT(level.room.id.intValue(),p.getId(), p.rank);
				
				// level up.
				if(p.getChips()>level.stage.getMaxIntegeral()){
					
					ret=GSPP.reward(p.id, level.room.id, team.id, p.rank, level.getReward().getId(), level.getReward().getAmount());
				}
				
			
				
				if(o.getStatus()!=0){
					
					//GameEnroll gameEnroll=GSPP.getGameEnrollByUserIdAndRoomId(p.id, level.room.id);
					if(user!=null){
						GSPP.updateBlockadeUserStatus(user.getId(), o.getStatus());
						
					}
				}
				
				seat.player.sendBrokerMessage(o, false);

			}
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
		if (evt.isEvent(GameEvent.GAME_END)) {
			LLDeck deck = LLU.asObject(evt.obj);
			if (decks.containsKey(deck.getId())) {
				// process
				// deck.reset(true);
				onGameEnd(deck);

			}

		}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.message.AbstractBroker#getUri()
	 */
	@Override
	public String getUri() {
		// TODO Auto-generated method stub
		return level.room.getUri() + "/team://" + id;
	}

	private void notifyEnrollPlayerChanges() {
		GL0000Beans.LG4006Response req = LLU
				.o(GL0000Beans.LG4006Response.class);
		req.setId(level.room.getId());
		req.setValue(players.size());
		sendBrokerMessage(req, false);
	}

	@Override
	public void observe(LLGamePlayer player) {
		// TODO Auto-generated method stub

		// checking if the player should be start match.

		if (state.eq(LLBTTeamStates.Waiting)) {
			super.observe(player);
			notifyEnrollPlayerChanges();
			// checking if the player should be start match.
			enter(player);

			if (level.room.config.isEnableRobot()) {
				onOneUserLoginTimeout();
			}
		}

		// should check the state after the player meet the condition
		if (players.size() == 3 && players.size() % 3 == 0) {
			setState(LLBTTeamStates.Begin);
		}

	}

	@Override
	public void unObserve(LLGamePlayer player) {
		// TODO Auto-generated method stub
		super.unObserve(player);
		leave(player);
		//players.remove(player.getId());
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

	//IGameLife gameLife = null;
	//static long robotUserId = 500;

	public void createRobot() {
		long robotUserId=LLU.getRobotId();
		long userid = GSPP.login("Robot" + robotUserId, "96e79218965eb72c92a549dd5a330112",
				"R0123456789-" + robotUserId);
		if (userid > 0) {
			robotUserId++;
			LLGamePlayer robot = new LLGamePlayer(userid);
			robot.isRobot = true;
			robot.enableRobot = true;

			// stop adding player into the team.
			// if(team.room.config.get)
			if (team.players.size() < 3) {
				// enroll with the itemId 1
				// room.enrollByItem(robot,Integer.valueOf(ItemTypes.Coin.id));
				LLBTLevel nextLevel = level;//level.room.getLevel( level.nextStage().getId());
				robot.setChips(nextLevel.stage.getMaxIntegeral());
				team.observe(robot);
			}
		}

	}

	public void onOneUserLoginTimeout() {
		GameUtil.gameLife(team.getUri(), 5000L, this, "createRobot").setGameSession(gameSession);;

	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#enter(com.sky.game.landlord.room.LLGamePlayer)
	 */
	public void enter(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().put(player.getId(), player);
		//player.team=getGamePlayerObserver();
		player.team=this;
		player.setState(LLGamePlayerStates.Team);
		player.lastAcess=System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.IGamePlayerObserver#leave(com.sky.game.landlord.room.LLGamePlayer)
	 */
	public void leave(LLGamePlayer player) {
		// TODO Auto-generated method stub
		getGameObjectMap().remove(player.getId());
		player.setState(LLGamePlayerStates.Level);
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
