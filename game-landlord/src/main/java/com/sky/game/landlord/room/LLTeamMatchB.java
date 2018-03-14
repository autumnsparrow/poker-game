/**
 * @sparrow
 * @Jan 7, 2015   @8:51:36 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.achievement.AchievementObserver;
import com.sky.game.landlord.blockade.config.EliminationTournamentConfig.BStage;
import com.sky.game.landlord.exception.LLGameExecption;
import com.sky.game.landlord.proxy.GSPP;
import com.sky.game.protocol.commons.GL0000Beans;
import com.sky.game.protocol.commons.GL0000Beans.LG4014Response;
import com.sky.game.protocol.commons.GL0000Beans.Range;
import com.sky.game.service.domain.GameDataCategoryTypes;
import com.sky.game.service.domain.GameDataStaticsTypes;

/**
 * @author sparrow
 *
 */
public class LLTeamMatchB implements ITeamMatch {

	private static final Log logger = LogFactory.getLog(LLTeamMatchB.class);
	LLTeam team;
	BStage stage;

	List<LLGamePlayer> secondPlayers;

	// the total eliminated players
	int totalOfElimination;
	// the total players
	int totalOfPlayers;
	// the total deck
	int totalOfDecks;
	// the total finished decks.
	int totalOfFinishedDecks;

	public static ITeamMatch obtain(LLTeam team) {
		return new LLTeamMatchB(team);
	}

	/**
	 * @param team
	 */
	public LLTeamMatchB(LLTeam team) {
		super();
		this.team = team;
		this.stage = team.room.config.getbStage();
		this.secondPlayers = GameUtil.getList();
		this.totalOfElimination = 0;
		this.totalOfPlayers = 0;
		this.totalOfDecks = 0;
		this.totalOfFinishedDecks = 0;
		this.team.stageWaiting=false;
	}

	/**
	 * 
	 */
	public LLTeamMatchB() {
		// TODO Auto-generated constructor stub
	}

	private void configDeck(final LLDeck deck) {
		deck.conf.baseChips = stage.getBase();
		deck.conf.setGamePerDeck( stage.getGameRoundPerDeck());
		deck.teamSorted = team;
		deck.conf.roomType=LLRoomTypes.ET.state;
		deck.conf.roomName=team.room.config.getRoomName();
		deck.conf.aiLevel=stage.getAiLevel();
		team.decks.put(deck.getId(), deck);
		wrapStageChanges(deck);

	}

	private void configPlayers(LLGamePlayer[] players) {
		// change the players chips
		int percent = stage.getaStageScorePercent();
		int maxScoreFromAStage = stage.getMaxScoreFromAStage();
		for (LLGamePlayer player : players) {
			if (player == null)
				continue;
			int oldChips = player.getChips();
			int chips = (int) (oldChips * percent * 0.01);
			if (chips > maxScoreFromAStage) {
				player.setChips(maxScoreFromAStage);
			} else {
				player.setChips(chips);
			}
			// TODO:send message to player,chips changed.

			player.setChips(stage.getIntegral() + player.getChips());

			GameDataCategoryTypes category = GameDataCategoryTypes
					.getType(team.room.config.getTournamentType());
			if(!player.isRobot)
				GSPP.updateGameDataStatics(player.id, category,
					GameDataStaticsTypes.StageBTimes);
			
			
			AchievementObserver.observerHalfFinalEvent(team.room.id.intValue(), player.id, player.rank);
			//GSPP.updateTaskRate(player.getId(), team.room.config.getTaskId(), 1);
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
		team.getSortedGamePlayerInTeam(false);
		LLGamePlayer[] objs = team.players.values().toArray(
				new LLGamePlayer[] {});

		totalOfPlayers = objs.length;
		for (int i = 0; i < objs.length; i += 3) {
			if (i + 3 > objs.length)
				break;
			LLDeck deck = new LLDeck(team.room);

			configDeck(deck);
			
			team.append(deck.getId()+" created  "+team.decks.size());
			LLGamePlayer[] subPlayers = Arrays.copyOfRange(objs, i, i + 3);

			configPlayers(subPlayers);
			try {
				deck.seat(subPlayers);
			} catch (LLGameExecption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			team.jmxTeam.getDecks().add(deck.getJmxDeck());
		}
		totalOfDecks = team.decks.size();
		logger.info("total decks::"+Arrays.toString(team.decks.values().toArray(new LLDeck[]{})));
	}

	private void notifyStageRoundDeck(LLDeck deck) {
		GL0000Beans.LG4008Response req = LLU
				.o(GL0000Beans.LG4008Response.class);
		req.setId(team.room.getId());
		req.setStage(Range.obtain(2, 3));
		req.setRound(Range.obtain(1, 1));
		req.setDeck(Range.obtain(deck.gamePerDeck, deck.conf.getGamePerDeck()));
		// send the message to deck
		deck.sendBrokerMessage(req, false);

	}

	private void notifyRoundFinished(LLDeck deck) {
		
		Range deckFinished =Range.obtain(totalOfFinishedDecks, totalOfDecks);
		team.getSortedGamePlayerInTeam(false);
		GL0000Beans.LG4007Response req = LLU
				.o(GL0000Beans.LG4007Response.class);
		req.setId(team.room.getId());
		req.setDeckFinished(deckFinished);

		LLGamePlayer[] objs = LLTeam.getSortedGamePlayerInDeck(deck,false);
		for (int i = 0; i < objs.length; i++) {
			LLGamePlayer p = objs[i];
			req.setScore(p.getChips());
			req.setDeckRank(Range.obtain(i + 1, objs.length));
			p.deckRank=i+1;
			req.setTeamRank(Range.obtain(p.rank , team.players.size()));
			req.setUpgradeRank(Range.obtain(
					stage.getRank2UpradePlayerNumbers(), team.players.size()));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.landlord.room.ITeamMatch#onGameEnd(com.sky.game.landlord
	 * .room.LLDeck)
	 */
	public synchronized void onGameEnd(LLDeck deck) {
		// TODO Auto-generated method stub
		// deck players.
		// onGameEnd
		// low -> high.
		boolean stageFinished = false;
		logger.info("game[" + deck.getUri() + "/game://" + deck.game.getId()
				+ "]  - Finished " + deck.gamePerDeck + "/"
				+ deck.conf.getGamePerDeck());
		notifyStageRoundDeck(deck);
		
		LLGamePlayer[] objs =LLTeam.getSortedGamePlayerInDeck(deck,true);
		// onDeckEnd
		if (deck.shouldEndGames()) {
			totalOfFinishedDecks = totalOfFinishedDecks + 1;
			
			team.stageWaiting=true;
			team.append("waiting decks finished ("+totalOfFinishedDecks+"/"+totalOfDecks+") - state "+team.stageWaiting);
			
			
			notifyRoundFinished(deck
					);

			// clear the deck.
			deck.reset(true);
			team.decks.remove(deck.getId());
			team.append(deck.getId()+" remove/  "+team.decks.size());
			//logger.info("left decks::"+Arrays.toString(team.decks.values().toArray(new LLDeck[]{})));
			
			logger.info("deck[" + deck.getId() + "]  - Finished "
					+ totalOfFinishedDecks + " /" + totalOfDecks);
			
			LLGamePlayer p = objs[0];
			// the last must be elimination
			objs[0].elimination = true;
			logger.info("player[" + p.getId() + "] - eliminated ("
					+ p.getChips() + ")  " + p.getRank() + "/"
					+ team.players.size());
			totalOfElimination = totalOfElimination + 1;
			secondPlayers.add(objs[1]);

			// onStageEnd

			// the game per deck finished.
			if (totalOfFinishedDecks == totalOfDecks) {
				logger.info("team[" + team.getUri() + "] - stage finished 2/3 ");
				stageFinished = true;
			}
				
			if(stageFinished){
				// should elimination the second players.
				// sort the secondPlayers.
				
				// low coins --> max coins.
				LLGamePlayer[] sortedSecondPlayers = LLTeam.sortGamePlayer(
						secondPlayers.toArray(new LLGamePlayer[] {}), true,false);

				int shouldElimination = secondPlayers.size()
						- stage.getRank2UpradePlayerNumbers();
				//
				for (int i = 0; i < shouldElimination; i++) {
					p = sortedSecondPlayers[i];
					

						p.elimination = true;

						totalOfElimination = totalOfElimination + 1;
					

				}
			}

			// onElimination
			// shold sort as max  -->low
			objs = team.getSortedGamePlayerInTeam(false);

			for (int i = 0; i < objs.length; i++) {
				p = objs[i];
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

		} else {
			// restart a game.
			// TODO: how to deal with that situation?
			logger.info(deck.toString()+" should schedule the deck timout");
			if (deck.state.equals(LLDeckStates.DeckGameEnd)) {
				deck.reset(false);
				
				GameUtil.gameLife("timeout://" + deck.getUri(), 10000L, deck,
						"createGame").setGameSession(team.gameSession);
				// deck.createGame();
			}
		}

		// nextState
		if (stageFinished) {
			// notify the stage.

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
		 return stage.getRank2UpradePlayerNumbers();
	}

}
