/**
 * @sparrow
 * @2:47:06 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.game.GameDoOnTurnException;
import com.sky.game.context.game.IGameStage;
import com.sky.game.context.util.GameUtil;
import com.sky.game.poker.robot.RobotAiAdapter;
import com.sky.game.poker.robot.RobotAiLevels;
import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.poke.util.PokeHelper;

/**
 * 
 * shuffles the the poker cards,then send the poker to the player(private)
 * 
 * @author sparrow
 *
 */
public class LLGameDealCards extends AbstractGameStage {
	private static final Log logger = LogFactory.getLog(LLGameDealCards.class);

	PokerCube[] pcubes;

	public static IGameStage obtain(LLGame game) {
		return new LLGameDealCards(game);
	}

	/**
	 * 
	 */
	public LLGameDealCards(LLGame game) {
		// TODO Auto-generated constructor stub
		super(game);

	}

	private static final int SHUFFLES_CARDS = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * 
	 * 
	 * @see com.sky.game.context.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		// begin stage shuffles cards.
		LLDeckSeat[] seats = game.deck.seats;
		int aiLevel = game.deck.conf.aiLevel;
		//aiLevel=3;
		if (aiLevel == RobotAiLevels.LevelMax.state) {
			
			long[]  cards= PokeHelper.dealPokeCheat();
			pcubes=new PokerCube[4];
			for(int i=0;i<cards.length;i++){
				pcubes[i]=RobotAiAdapter.convertPokerCube(cards[i]);
			}
			
			// low -> hight
			Arrays.sort(seats, new Comparator<LLDeckSeat>() {

				public int compare(LLDeckSeat o1, LLDeckSeat o2) {
					// TODO Auto-generated method stub
					int v1 = o1.player.isRobot ? 1 : 0;
					int v2 = o2.player.isRobot ? 1 : 0;
					return  v2-v1;
				}

			});
		} else {

			aiLevel = aiLevel > 0 ? aiLevel : GameUtil.getRandomPokerAiLevel();
			pcubes = RobotAiAdapter.shuffles(aiLevel);
			// best in the first.

			// low -> hight
			Arrays.sort(seats, new Comparator<LLDeckSeat>() {

				public int compare(LLDeckSeat o1, LLDeckSeat o2) {
					// TODO Auto-generated method stub
					int v1 = o1.player.win;
					int v2 = o2.player.win;
					return v2 - v1;
				}

			});

		}

		game.deck.kittyCards = pcubes[3];

		for (int i = 0; i < 3; i++) {
			LLDeckSeat seat = seats[i];
			LLGamePlayer player = seat.player;
			if (player != null) {
				// player.g=game;
				PokerCube pc = pcubes[i];
				pc.setDeckPoistion(seat.location);
				player.setPokeCube(pc);
				// logger.info(pc.toString());
				logger.info("seat["+seat.location+"]  PokerCubes["+i+"]");
				game.append(seat, pc.cubeToHexString());
				player.sendBrokerMessage(player.wrapCards(), false);

			}
		}
		
		
		Arrays.sort(seats, new Comparator<LLDeckSeat>() {

			public int compare(LLDeckSeat o1, LLDeckSeat o2) {
				// TODO Auto-generated method stub
				
				return o1.location - o2.location;
			}

		});

		game.append("kittycards - " + game.deck.kittyCards.cubeToHexString());
		// process the on turn
		// setStage(SHUFFLES_CARDS, false);

		if (game.state.eq(LLGameStates.DealCards))
			game.setNextState();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.context.game.IGameDoOnTurn#onTurn(java.lang.Object)
	 */
	public boolean onTurn(LLDeckSeat seat) throws GameDoOnTurnException {
		// TODO Auto-generated method stub

		boolean ret = false;

		switch (stage) {
		case SHUFFLES_CARDS: {
			LLGamePlayer player = seat.player;

			if (player != null) {
				// player.g=game;
				PokerCube pc = pcubes[seat.location];
				pc.setDeckPoistion(seat.location);
				player.setPokeCube(pc);
				// logger.info(pc.toString());
				game.append(seat, pc.cubeToHexString());
				player.sendBrokerMessage(player.wrapCards(), false);

			}
			ret = true;

		}

			break;

		default:
			break;
		}

		return ret;
	}

}
