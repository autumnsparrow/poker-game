/**
 * @sparrow
 * @Jan 7, 2015   @6:20:55 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.blockade.config.EliminationTournamentConfig.AStage;
import com.sky.game.landlord.exception.LLGameExecption;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.LG4014Response;
import com.sky.game.protocol.commons.GL0000Beans.Range;

/**
 * 
 * Auto Shift Selection Match:
 * 
 * 
 * team elimination has several states:
 * 
 * Elimination if low the score minS
 * 
 *    3*N players(multiple 3) assign to  N Decks. 
 *    After the deck finished, check if the player score low than minS.
 *    if not then add the player into an idle queue,loop the step to eliminate fixed number of player.
 *    
 *    after the fixed number of players ,the stage should be state of StageWaiting.
 *    In StageWaiting state,any player finished the deck game should show the stage promotion UI.
 *    
 *    
 *    
 * Elimination by the rank.
 *   
 *     the rest of player will eliminate by rank.
 *    
 * 
 *                             
 *    players  --> decks  --> match  -- > after elimination by low to reach fixed number --> stage end.   between stage waiting          next stage begin
 *      
 *    									                                               |-------  stage waiting                       ----|
 *      
 *     
 *       
 *  
 * 
 * 
 * @author sparrow
 *
 */
public class LLTeamMatchA implements ITeamMatch {
	private static final Log logger = LogFactory.getLog(LLTeamMatchA.class);

	LLTeam team;
	AStage stage;

	// the total eliminated players
	int totalOfElimination;
	// the total players
	int totalOfPlayers;
	// the total deck
	int totalOfDecks;
	// the total finished decks.
	int totalOfFinishedDecks;

	LLGameObjectMap<LLGamePlayer> idlePlayers;
	
	public static ITeamMatch obtain(LLTeam team) {
		return new LLTeamMatchA(team);
	}

	/**
	 * 
	 */
	public LLTeamMatchA() {
		// TODO Auto-generated constructor stub
	}

	int base;

	/**
	 * @param team
	 */
	public LLTeamMatchA(LLTeam team) {
		super();
		this.team = team;
		this.stage = team.room.config.getaStage();
		this.totalOfElimination = 0;
		this.totalOfPlayers = 0;
		this.totalOfDecks = 0;
		this.totalOfFinishedDecks = 0;
		this.idlePlayers = LLGameObjectMap.getMap();
		this.base = stage.getBase();
		this.team.stageWaiting=false;
	}

	private void configDeck(final LLDeck deck) {
		
		deck.conf.setBaseChips(this.base);
		//deck.conf.baseChips = this.base;
		//deck.conf.gamePerDeck = 1;//this.stage.getGamePerDeck();
		deck.conf.roomType=LLRoomTypes.ET.state;
				deck.conf.roomName=team.room.config.getRoomName();
		deck.conf.aiLevel=stage.getAiLevel();
		deck.conf.setGamePerDeck(1);
		deck.teamSorted = team;
		team.decks.put(deck.getId(), deck);

		//
		wrapStageChanges(deck);
	}

	public void increaseBase() {

		synchronized (stage) {
			

			base = base + stage.getBaseIncreaseAmount();
//			for(LLDeck deck:team.decks.values()){
//				deck.conf.baseChips=base;
//			}
			//stage.setBase(base);
			logger.info("base incease to:" + base);
			GameUtil.gameLife(team.id + "/baseincrease",
					stage.getBaseIncreaseTimeInterval() * 1000, this,
					"increaseBase").setGameSession(team.gameSession);

		}
	}

	volatile boolean shouldEnd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.ITeamMatch#startMatch()
	 */
	public void startMatch() {
		team.jmxTeam.getDecks().clear();
		shouldEnd = false;

		// TODO Auto-generated method stub
		// start the base increase gamelife.
		GameUtil.gameLife(team.id + "/baseincrease",
				stage.getBaseIncreaseTimeInterval() * 1000, this,
				"increaseBase").setGameSession(team.gameSession);

		// sorted players
		team.getSortedGamePlayerInTeam(false);
		LLGamePlayer[] objs = team.players.values().toArray(
				new LLGamePlayer[] {});
		totalOfPlayers = objs.length;

		for (int i = 0; i < objs.length; i += 3) {
			LLDeck deck = new LLDeck(team.room);

			configDeck(deck);
			
			LLGamePlayer[] subPlayers = Arrays.copyOfRange(objs, i, i + 3);
			try {

				deck.seat(subPlayers);
				
				team.append(deck.getId()+" created /"+team.decks.size());
				team.jmxTeam.getDecks().add(deck.getJmxDeck());
			} catch (LLGameExecption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		totalOfDecks = team.decks.size();

	}

	private void notifyStageRoundDeck(LLDeck deck) {
		GL0000Beans.LG4008Response req = LLU
				.o(GL0000Beans.LG4008Response.class);
		req.setId(team.room.getId());
		req.setStage(Range.obtain(1, 3));
		req.setRound(Range.obtain(1, 1));
		req.setDeck(Range.obtain(deck.gamePerDeck, deck.conf.getGamePerDeck()));
		// send the message to deck
		deck.sendBrokerMessage(req, false);

	}

	public void notifyRoundFinished(LLDeck deck) {
	    Range deckFinished=Range.obtain(totalOfFinishedDecks, totalOfDecks);
		team.getSortedGamePlayerInTeam(false);
		GL0000Beans.LG4007Response req = LLU
				.o(GL0000Beans.LG4007Response.class);
		req.setId(team.room.getId());
		req.setDeckFinished(deckFinished);

		LLGamePlayer[] objs = LLTeam.getSortedGamePlayerInDeck(deck,false);
		
		for (int i = 0; i < objs.length; i++) {
			LLGamePlayer p = objs[i];
			req.setScore(p.getChips());
			p.deckRank=i+1;
			req.setDeckRank(Range.obtain(i + 1, objs.length));

			req.setTeamRank(Range.obtain(p.rank , team.players.size()));
			req.setUpgradeRank(Range.obtain(stage.getUpgradedPlayerNumbers(),
					team.players.size()));
			p.sendBrokerMessage(req, false);
		}

		notifyRankChanges(deckFinished);

	}

	private void notifyRankChanges(Range deckFinished) {
		LLGamePlayer players[] = team.getSortedGamePlayerInTeam(false);
		GL0000Beans.LG4012Response resp = LLU
				.o(GL0000Beans.LG4012Response.class);
		resp.setDeckFinished(deckFinished);

		for (LLGamePlayer p : players) {
			resp.setTeamRank(Range.obtain(p.getRank(), players.length));
			p.sendBrokerMessage(resp, false);
		}

	}

	private void createGameByIdlePlayers() {
		logger.info(GameUtil.formatId(team.id, this)
				+ " createGameByIdlePlayers  scheduled.");
		GameUtil.gameLife(team.getUri() + "teama", 10000, this, "createMoreDeck").setGameSession(team.gameSession);

	}

	/**
	 * must provide the idle player only create once.
	 * 
	 * 
	 */
	public synchronized void createMoreDeck() {
		logger.info("idle player:" + idlePlayers.size());

		if (idlePlayers.size() >= 3) {
			LLGamePlayer[] objs = idlePlayers.values().toArray(
					new LLGamePlayer[] {});
			for (int i = 0; i < objs.length; i += 3) {

				if (i + 3 > objs.length)
					break;
				LLDeck deck = new LLDeck(team.room);

				configDeck(deck);
				
			
				LLGamePlayer[] subPlayers = Arrays.copyOfRange(objs, i, i + 3);
				try {
					if (subPlayers.length == 3) {
						deck.seat(subPlayers);
						synchronized (this) {
							totalOfDecks = totalOfDecks + 1;
							// after 5 minues must end the game.
							// GameUtil.gameLife(deck.getUri()+"/timeout",
							// 300000, deck, "reset", Boolean.valueOf(true));
							logger.info("totalOfDecks: (+1)" + totalOfDecks
									+ " " + deck.getId());
							team.jmxTeam.getDecks().add(deck.getJmxDeck());
						}

						team.append(deck.getId()+" created /"+team.decks.size());
						for (LLGamePlayer p : subPlayers) {
							if (p != null){
								
								idlePlayers.remove(p.getId());
								team.append(p.getId()+" leave idle "+idlePlayers.size());
							}
						}
					}
				} catch (LLGameExecption e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// totalOfDecks = team.decks.size();

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.room.ITeamMatch#onGameEnd(com.sky.game.landlord
	 * .room.LLDeck)
	 */
	public synchronized void onGameEnd(LLDeck deck) {
		// TODO Auto-generated method stub
		// checking if the current players larger than the elimination numbers.
		// sorted the players.
		// elimination the players.
		// on deck end
		// elimination the players by sorted.
		// must wait for the all deck stopped,then sorted.

		// if all deck finished.
		// on stage end.
		boolean stageFinished = false;

		boolean shouldStopCreateDeckByIdlePlayers = false;
		boolean shouldWaitingDecksFinished = false;
		
		// on game end
		LLGamePlayer[] objs = null;
		//deck.conf.gamePerDeck=this.stage.getGamePerDeck();
		deck.conf.setGamePerDeck(1);
		team.append(deck.getId()+" onGameEnd \n"+deck.shouldEndGames()+" - " +deck.conf.getGamePerDeck()+"\n"+deck+"\n");
		// the current deck finished.
		if (deck.shouldEndGames()) {
			// on the deck end
			// clear timeout game life
			// GameUtil.clearGameLife(deck.getUri() + "/timeout");
			logger.info("deck[" + deck.getUri() + "/game://"
					+ deck.game.getId() + "]  - Finished /"
					+ deck.conf.getGamePerDeck());

			objs = LLTeam.getSortedGamePlayerInDeck(deck,false);

			// notify GL4008
			notifyStageRoundDeck(deck);

			//
			// when teamSize <=eliminatedByScorePlayerNumbers
			// should stop create the side decks for players
			int teamSize = team.players.size();
			int eliminatedByScorePlayerNumbers = stage
					.getEliminationByScoreStoppedPlayerNumbers();

			logger.info(GameUtil.formatId(team.id, this) + " - "
					+ eliminatedByScorePlayerNumbers + " /" + teamSize);

			if (teamSize <= eliminatedByScorePlayerNumbers) {
				shouldStopCreateDeckByIdlePlayers = true;
				team.append("stop create deck by idles players "+eliminatedByScorePlayerNumbers+"/"+teamSize);
				
			}

			// before
			if (!shouldStopCreateDeckByIdlePlayers) {
				// current can elimination by score.
				// the deck player elimination.
				// high --> low
				for (int i = 0; i < objs.length; i++) {
					LLGamePlayer p = objs[i];
					if (totalOfPlayers - totalOfElimination >= stage
							.getEliminationByScoreStoppedPlayerNumbers()) {

						if (p.getChips() < stage.getEliminationScore()) {
							p.elimination = true;
							totalOfElimination = totalOfElimination + 1;
							team.append(
									"player[" + p.getId()
											+ "] - eliminated by score low ("
											+ stage.getEliminationScore() + ") ("
											+ p.getChips() + ")  " + p.getRank() + "/"
											+ team.players.size());
							logger.info("player[" + p.getId()
									+ "] - eliminated by score low ("
									+ stage.getEliminationScore() + ") ("
									+ p.getChips() + ")  " + p.getRank() + "/"
									+ team.players.size());
						}
					}

				}

			}

			totalOfFinishedDecks = totalOfFinishedDecks + 1;

			logger.info("deck[" + deck.getId() + "]  - Finished "
					+ totalOfFinishedDecks + " /" + totalOfDecks);

			// only the player
			// collect the idle players

			for (int i = 0; i < objs.length; i++) {
				LLGamePlayer p = objs[i];
				if (!shouldStopCreateDeckByIdlePlayers) {
					if (!p.elimination) {
						idlePlayers.put(p.getId(), p);
						team.append(p.getId()+" join idle "+idlePlayers.size());
					}
				}
			}

			// checking the can the idle player create new deck.
			// create new deck from
			// stop create game by idle player.
			if (!shouldStopCreateDeckByIdlePlayers) {
				createGameByIdlePlayers();
			} else {
				logger.info(GameUtil.formatId(team.id, this) + " - "
						+ idlePlayers.size());

				idlePlayers.clear();
				shouldWaitingDecksFinished = true;

			}

			// check if the stage should end.
			if (shouldWaitingDecksFinished) {
				notifyRoundFinished(deck);
				team.stageWaiting=true;
				team.append("waiting decks finished ("+totalOfFinishedDecks+"/"+totalOfDecks+") - state "+team.stageWaiting);
				
				
				
				if (totalOfFinishedDecks == totalOfDecks) {
					team.append("stage finished");
					
					stageFinished = true;
				}
			}

			
			// clear the deck.
			team.append(deck.getId()+" removed /"+team.decks.size());
			team.append("\n"+Arrays.toString(team.decks.values().toArray(new LLDeck[]{})));
			deck.reset(true);
			team.decks.remove(deck.getId());
			
			if (stageFinished) {

				logger.info("team[" + team.getUri() + "] - stage finished 1/3 ");
				// all deck finished.
				// sorted
				//
				// high---> low
				// upgrade numbers

			}

			if (stageFinished) {
				LLGamePlayer[] gamePlayers = team.getSortedGamePlayerInTeam(false);

				for (int i = stage.getUpgradedPlayerNumbers(); i < gamePlayers.length; i++) {
					LLGamePlayer p = gamePlayers[i];
					p.elimination = true;
					team.append("player[" + p.getId()
							+ "] - eliminated by rank("
							+ stage.getUpgradedPlayerNumbers() + ") ("
							+ p.getChips() + ")  " + p.getRank() + "/"
							+ team.players.size());
					logger.info("player[" + p.getId()
							+ "] - eliminated by rank("
							+ stage.getUpgradedPlayerNumbers() + ") ("
							+ p.getChips() + ")  " + p.getRank() + "/"
							+ team.players.size());

				}

			}

			// onElimination.
			// sort the all players
			// hight ->low
			objs = team.getSortedGamePlayerInTeam(false);

			for (int i = 0; i < objs.length; i++) {
				LLGamePlayer p = objs[i];
				if (p.elimination) {
					// remove the user from the team.
					
					// team.players.remove(p.getId());
					// TODO: send the reward or elimination information.
					team.reward(p);
					
				} else {

					logger.info("player[" + p.getId() + "] - upgrade ("
							+ p.getChips() + ")  " + p.getRank() + "/"
							+ team.players.size());

				}
			}

			//
			// if stage finished,then move next state.
			// nextState.
			if (stageFinished) {

				GameUtil.clearGameLife(team.id + "/baseincrease");
				team.nextState();
			}

		}

	}

	//
	// code region - wrap the stage changes.
	//

	/**
	 * <p>
	 * send the room and player chips changes.
	 * 
	 * @param player
	 * @return
	 */
	private void wrapStageChanges(LLDeck deck) {
		for (LLGamePlayer p : deck.players.values()) {
			LG4014Response o = LLU.o(LG4014Response.class);
			o.setBase(stage.getBase());

			o.setIntegral(p.getChips());
			o.setName(stage.getName());
			o.setStage(Range.obtain(1, 3));
			p.sendBrokerMessage(o, false);
		}

	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.ITeamMatch#getUpgradeNumbers()
	 */
	public int getUpgradeNumbers() {
		// TODO Auto-generated method stub
		return stage.getUpgradedPlayerNumbers();
	}

}
