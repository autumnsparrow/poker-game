/**
 * @sparrow
 * @Jan 8, 2015   @10:02:05 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.blockade.config.EliminationTournamentConfig.CStage;
import com.sky.game.landlord.exception.LLGameExecption;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.LG4014Response;
import com.sky.game.protocol.commons.GL0000Beans.Range;

/**
 * @author sparrow
 *
 */
public class LLTeamMatchC implements ITeamMatch {

	private static final Log logger = LogFactory.getLog(LLTeamMatchC.class);
	LLTeam team;
	CStage stage;

	// the total eliminated players
	int totalOfElimination;
	// the total players
	int totalOfPlayers;
	// the total deck
	int totalOfDecks;
	// the total finished decks.
	int totalOfFinishedDecks;

	int gameRound;
	int totalGameRound;

	/**
	 * 
	 */
	public LLTeamMatchC() {
		// TODO Auto-generated constructor stub
	}

	public static ITeamMatch obtain(LLTeam team) {
		return new LLTeamMatchC(team);
	}

	/**
	 * @param team
	 */
	public LLTeamMatchC(LLTeam team) {
		super();
		this.team = team;
		this.stage = team.room.config.getcStage();

		this.totalOfElimination = 0;
		this.totalOfPlayers = 0;
		this.totalOfDecks = 0;
		this.totalOfFinishedDecks = 0;
		this.gameRound = stage.getGamePerRound();
		this.totalGameRound = 0;
		this.team.stageWaiting=false;
	}

	private void configDeck(final LLDeck deck) {
		deck.conf.baseChips = stage.getBase();
		deck.conf.setGamePerDeck(stage.getGamePerDeck());
		deck.conf.roomType=LLRoomTypes.ET.state;
		deck.conf.roomName=team.room.config.getRoomName();
		deck.teamSorted = team;
		deck.conf.aiLevel=stage.getAiLevel();
		team.decks.put(deck.getId(), deck);

		wrapStageChanges(deck);
		
		//team.jmxTeam.getDecks().add(deck.getJmxDeck());
		// GameUtil.gameLife(deck.getUri()+"/timeout", 50000L, this,
		// "onGameEnd", deck);

	}

	private void configPlayers(LLGamePlayer[] players) {
		// change the players chips
		int percent = stage.getbStageScorePercent();
		int maxScoreFromAStage = stage.getMaxScoreFromBStage();
		for (LLGamePlayer player : players) {
			if (player != null) {
				int oldChips = player.getChips();
				int chips = (int) (oldChips * percent * 0.01);
				if (chips > maxScoreFromAStage) {
					player.setChips(maxScoreFromAStage);
				} else {
					player.setChips(chips);
				}
				// TODO:send message to player,chips changed.
				player.setChips(stage.getIntegral() + player.getChips());
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.ITeamMatch#startMatch()
	 */
	public void startMatch() {
		// TODO Auto-generated method stub
		team.decks.clear();
		team.jmxTeam.getDecks().clear();
		
		LLGamePlayer[] objs =null;
		objs = team.getSortedGamePlayerInTeam(false);// LLTeam.sortGamePlayer(objs, false,false);

		logger.debug("StageC:" + team.players.size());
		logger.debug(Arrays.toString(objs));
		// high< --- low
		
		totalOfPlayers = objs.length;
		for (int i = 0; i < objs.length; i += 3) {
			
			if (i + 3 > objs.length)
				break;
			LLDeck deck = new LLDeck(team.room);

			configDeck(deck);
			
			
			LLGamePlayer[] subPlayers = Arrays.copyOfRange(objs, i, i + 3);
			if (subPlayers.length == 3) {
				configPlayers(subPlayers);
				try {
					deck.seat(subPlayers);
				} catch (LLGameExecption e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				team.append(deck.getId() + " created  " + team.decks.size());
				team.jmxTeam.getDecks().add(deck.getJmxDeck());
			}

		}
		totalOfDecks = team.decks.size();

	}

	private void notifyStageRoundDeck(LLDeck deck, Range round) {
		GL0000Beans.LG4008Response req = LLU
				.o(GL0000Beans.LG4008Response.class);
		req.setId(team.room.getId());
		req.setStage(Range.obtain(3, 3));
		req.setRound(round);
		req.setDeck(Range.obtain(deck.gamePerDeck, deck.conf.getGamePerDeck()));
		// send the message to deck
		deck.sendBrokerMessage(req, false);

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
		if (deck == null) {
			logger.warn("deck is empty!");
			return;
		}

		// onGameEnd
		//logger.debug("left decks::"
			//	+ Arrays.toString(team.decks.values().toArray(new LLDeck[] {})));
		// logger.info(message);

		logger.info("game[" + deck.getUri() + "/game://" + deck.game.getId()
				+ "]  - Finished " + deck.gamePerDeck + "/"
				+ deck.conf.getGamePerDeck());
		// onDeckEnd
		if (deck.shouldEndGames()) {
			totalOfFinishedDecks = totalOfFinishedDecks + 1;
			logger.info("deck[" + deck.getId() + "]  - Finished "
					+ totalOfFinishedDecks + " /" + totalOfDecks);
			// clear the deck.
			deck.reset(true);
			team.decks.remove(deck.getId());
			team.append(deck.getId() + " removed  " + team.decks.size());

		} else {
			if (deck.state.equals(LLDeckStates.DeckGameEnd)) {
				deck.reset(false);
				GameUtil.gameLife("timeout://" + deck.getUri(), 10000L, deck,
						"createGame").setGameSession(team.gameSession);
				// deck.createGame();
			}
		}

		// the game per deck finished.
		// onAllDeckEnd
		// onRoundEnd
		boolean roundEnd = false;

		if (totalOfFinishedDecks == totalOfDecks) {
			roundEnd = true;
		}
		if (roundEnd) {
			logger.info("team[" + team.getUri() + "] - game roud finished  "
					+ totalGameRound + "/" + stage.getGamePerRound());
			totalGameRound++;
			// reset the totalOfFinsihedDecks.
			totalOfFinishedDecks = 0;
			totalOfDecks = 0;
			totalOfPlayers = 0;

			// need to elimination some players.
			// low ->high
			LLGamePlayer[] objs = team.getSortedGamePlayerInTeam(true);

			for (int i = 0; i < stage.getEliminationPerRoundPlayerNumbers(); i++) {
				LLGamePlayer p = objs[i];
				p.elimination = true;
				totalOfElimination++;
			}

		}

		notifyStageRoundDeck(deck, Range.obtain(gameRound, totalGameRound));
		// onStageEnd
		boolean stageFinished = false;
		if (gameRound == totalGameRound) {
			stageFinished = true;
			logger.info("team[" + team.getUri() + "] - stage finished  3/3");

		}

		// onRoundEnd elimination.
		// after the all round finsihed.
		// sort the player
		// low -> high
		// elimination players.
		if (roundEnd) {
			LLGamePlayer[] objs = team.getSortedGamePlayerInTeam(false);
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

		}

		// onRoundEnd and onStageNotEnd,
		// show some informations,then start the match again.
		// round end and the stage not end.
		if (roundEnd && !stageFinished) {

			// TODO: send to client some notification
			// then start a new match
			// start new round match.
			//GameUtil.gameLife("timeout://" + team.id+"stagec", 10000L, deck,
			//		"startMatch");
			startMatch();
		}

		if (stageFinished) {
			team.nextState();
		}

	}

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
		}

	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.ITeamMatch#getUpgradeNumbers()
	 */
	public int getUpgradeNumbers() {
		// TODO Auto-generated method stub
		return 0;
	}

}
