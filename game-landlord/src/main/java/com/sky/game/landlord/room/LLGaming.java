/**
 * @sparrow
 * @6:19:38 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.game.CircleOrderArray;
import com.sky.game.context.game.GameDoOnTurnException;
import com.sky.game.context.game.IGameStage;
import com.sky.game.context.util.BeanUtil;
import com.sky.game.context.util.GameUtil;
import com.sky.game.landlord.achievement.AchievementObserver;
import com.sky.game.poker.robot.ai.PokerCubeType;
import com.sky.game.poker.robot.poker.PokerCube;
import com.sky.game.protocol.commons.GL0000Beans.LG0006Response;

/**
 * @author sparrow
 *
 */
public class LLGaming extends AbstractGameStage {

	private static final Log logger = LogFactory.getLog(LLGaming.class);

	private static final int GO_FIND_LANDLORD = 0;
	private static final int ON_TURN = 1;
	private static final int CHECK_SHOULD_SHOW_HAND_ACTIVE = 2;
	private static final int CHECK_SHOULD_END = 3;
	private static final int RESET_SEAT = 4;

	private static final int RESET = 5;

	public static IGameStage obtain(LLGame game) {
		return new LLGaming(game);
	}

	public LLGaming(LLGame game) {
		super(game);
		

	}

	/**
	 * 
	 */
	public LLGaming() {
		// TODO Auto-generated constructor stub
	}

	int passes;
	boolean gameEnd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.context.game.IGameDoOnTurn#onTurn(java.lang.Object)
	 */
	public  boolean onTurn(LLDeckSeat seat) throws GameDoOnTurnException {
		// TODO Auto-generated method stub

		boolean ret = true;

		switch (stage) {
	
		case GO_FIND_LANDLORD: {
			//logger.info("GO_FIND_LAND:"+seat.toString());
			if (seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)||seat.positionState.equals(LLDeckSeatPositionTypes.Pull)) {
				// the first seat should be
				seats.holderMoveTo(seat);
				//
				// add the kitty cards to player.
				PokerCube pCube = seat.player.getPokeCube().addPokerCube(
						seat.deck.kittyCards);
				seat.player.setPokeCube(pCube);
				seat.showHandState = LLDeckShowHandsTypes.Active;
				isActive = true;

				// land lord first a
				seat.deck.sendBrokerMessage(seat.wrapShowHandState(), false);
				logger.debug("player[" + seat.player.getId() + "]  landlord!"+seat.deck);
				//onTurnShowHands(seat);
				ret = false;
			}
		}

			break;
		case ON_TURN: {

			// check should be active
			passes = 0;
			CircleOrderArray<LLDeckSeat> cloneSeats = seats.clone();
			cloneSeats.holderMoveTo(seat);
			// /logger.info(" OnTurn - " + seat.toString());
			for (int i = 0; i < 3; i++) {

				LLDeckSeat s = cloneSeats.moveNext();
				if (s.location != seat.location) {

					if (s.actionState.equals(LLDeckSeatActionTypes.Pass)) {
						passes = passes + 1;
					}

				}
			}
			if (!isActive)
				isActive = (passes == 2);

			if (isActive) {
				seat.showHandState = LLDeckShowHandsTypes.Active;
				seat.deck.lastHandPokerCube = null;
			} else {
				seat.showHandState = LLDeckShowHandsTypes.Passive;

			}
			logger.debug("turn["+game.id+"/" +seat.id + "]  ->"+isActive+"  " + seat.toString());
			onTurnShowHands(seat);
			// seats.holderMoveTo(seat);
			// }

			ret = false;
		}
			break;

		case CHECK_SHOULD_SHOW_HAND_ACTIVE: {
			if (seat.actionState.equals(LLDeckSeatActionTypes.Pass)) {
				passes++;
			} else {
				passes = 0;
			}
		}

			break;
		case CHECK_SHOULD_END: {
			
			if (seat.player != null&&seat.player.getPokeCube()!=null && seat.player.getPokeCube().isEmpty()) {
				// here sending the left
				logger.debug("Remains: " + seat.player.getPokeCube().remains());
				game.deck
						.sendBrokerMessage(game.wrapPlayerResultCards(), false);
				gameEnd = true;
				ret = false;
			}
		}
			break;
		case RESET_SEAT: {
			ret = true;
			seat.actionState = LLDeckSeatActionTypes.Undefined;
			seat.showHandState = LLDeckShowHandsTypes.Undefined;
		}
			break;

		default:
			break;
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.AbstractGameStage#begin()
	 */
	@Override
	public  synchronized void begin() {
		// TODO Auto-generated method stub
		// go find the landlord.
		setStage(RESET_SEAT, false);
		setStage(GO_FIND_LANDLORD, false);

		// set the game

		 setStage(ON_TURN, true);
		 
		// GameUtil.gameLife("game://timeout"+game.id, 2*60*1000, this, "onTimeout");

	}
	
	
	

	boolean isActive;

	private void reset() {
		passes = 0;
		gameEnd = false;
		isActive = false;
	}

	public synchronized void next(LLDeckSeat seat) {
		reset();
		// shoulde move the seats
		// when the two other pass.
		if (!isGameEnd()) {

			setStage(ON_TURN, true);
		}
	}

	public synchronized boolean isGameEnd() {
		reset();
		setStage(CHECK_SHOULD_END, false);
		if (gameEnd) {
			if (game.state.eq(LLGameStates.Gaming))
				game.setNextState();
		}
		return gameEnd;
	}

	/**
	 * 
	 * Game Show Hands.
	 * 
	 * 
	 */



	private long getLives(boolean isRobot) {

		return isRobot ? game.deck.conf.robotShowHandWaitingTime
				: game.deck.conf.showHandWaitingTime;
	}

	private void onTurnShowHands(final LLDeckSeat seat) {
		seat.actionState = LLDeckSeatActionTypes.Turn;
		game.append(seat,"onTurnShowHands");
		//game.deck.sendBrokerMessage(seat.wrapActionState(), false);
		//
		//
		boolean isRobot=seat.player != null && (seat.player.isRobot||seat.player.isRobot());
		// send the resume message
//		if(seat.player.resume){
//			seat.player.sendResume(seat.deck);
//			seat.player.resume=false;
//			isRobot=false;
//		}
//		
		
		long lives = getLives(isRobot);
		lives=!seat.player.online?lives/5:lives;
		GameUtil.gameLife(game.getId() + "/gaming", lives, this,
				"onTurnShowHandsTimeout", seat).setGameSession(game.gameSession);
	}

	public  void onTurnShowHandsTimeout(LLDeckSeat seat) {
		GameUtil.clearGameLife(game.getId() + "/gaming");
		if (seat.player == null)
			return;
		
		game.append(seat,  "onTurnShowHandsTimeout");
		// logger.info("onTurnShowHandsTimeout:"+seat.toString());
		if (seat.player.enableRobot
				|| seat.showHandState.equals(LLDeckShowHandsTypes.Active)) {
			// robot show hands.
			seat.deck.robot.handle(seat);
			PokerCube pCube = LLU.exp(seat, "deck.robot.pc");
			if (pCube != null) {
				seat.pokerCube = pCube;
			}

			turnHands(seat);
		} else {

			// if the seat is active must out some cards

			seat.actionState = LLDeckSeatActionTypes.Pass;
			seat.pokerCube = PokerCube.getPokerCubeByHex(PokerCube.ZERO);
			handMessage(seat, PokerCube.ZERO);

		}

	}

	/**
	 * 
	 * @param seat
	 */
	public synchronized void turnHands(LLDeckSeat seat) {

		GameUtil.clearGameLife(game.getId() + "/gaming");
		
		// checking
		// first hand
		if (seat.pokerCube != null && seat.pokerCube.remains() > 0) {
			if (seat.deck.lastHandPokerCube == null) {

				//seat.deck.lastHandPokerCube = seat.pokerCube;
			} else {

				PokerCubeType hType = null;
				// seat.pokerCube.getPokerCubeType();
				if (hType == null) {
					hType = seat.pokerCube.judgePokerCubeType();

				}
				PokerCubeType oType = null;
				// seat.deck.lastHandPokerCube
				// .getPokerCubeType();

				if (oType == null) {
					oType = seat.deck.lastHandPokerCube.judgePokerCubeType();

				}

				boolean samePokerCubeType = false;
				if (hType != null && oType != null) {
					samePokerCubeType = hType.state == oType.state;

				}

				if (samePokerCubeType) {
					// compare the current hand is lager thant previous hand.
					// boolean isLarger=hand.
					// TODO: compare the
					boolean isLarger = false;

					isLarger = seat.pokerCube.gt(seat.deck.lastHandPokerCube);
					if (!isLarger) {
						// TODO: exception.

						logger.info("[PokerTypeException:"+" seat.deck.lastHand:"+seat.deck.lastHandPokerCube.cubeToHexString()+",seat="+seat.pokerCube.cubeToHexString()+"]" + seat.toString());
						game.deck.sendException(seat,
								LLRoomExceptionTypes.LLDeckPokerTypeSizeLess);
						seat.pokerCube = PokerCube
								.getPokerCubeByHex(PokerCube.ZERO);

					}

				} else {

					// check if the hand is the bomb
					if (seat.pokerCube.isPokerCubeBomb()
							|| seat.pokerCube.isPokerCubeRocket()) {
						seat.deck.lastHandPokerCube = seat.pokerCube;
						// seat.deck.showHandsRoundType.pokerCubeType=hand.getPokerCubeType().state;
					} else {
						// TODO:
						// throw exception.

						logger.info("PokerTypeException:" + seat.toString());
						if (hType != null) {
							logger.info(" currentPokerType:" + hType.toString()
									+ seat.pokerCube);
						}
						if (oType != null) {
							logger.info(" lastPokerType:" + oType.toString()
									+ seat.deck.lastHandPokerCube);
						}
						seat.pokerCube = PokerCube
								.getPokerCubeByHex(PokerCube.ZERO);

						game.deck.sendException(seat,
								LLRoomExceptionTypes.LLDeckPokerTypeMismatch);
					}

				}

			}
		}
		// pokerCube = player.getPokeCube().subPokerCube(hand);
		// pokerCube.calculate();

		// player.getPokeCube().subPokerCubeByStatics();

		// for the AI
		// TODO:
		// check if the hand is the bomb
		
		boolean previosBombOrRocket=false;
		if(seat.deck.lastHandPokerCube!=null&&(seat.deck.lastHandPokerCube.isPokerCubeBomb()||seat.deck.lastHandPokerCube.isPokerCubeRocket())){
			previosBombOrRocket=true;
		}
		
		if (seat.pokerCube.isPokerCubeBomb()) {
			game.score.bomb = game.score.bomb + 1;

			game.deck.sendBrokerMessage(game.wrapScore(), false);
			// seat.deck.showHandsRoundType.pokerCubeType=hand.getPokerCubeType().state;
			
		}

		if (seat.pokerCube.isPokerCubeRocket()) {
			game.score.rocket = 1;
			game.deck.sendBrokerMessage(game.wrapScore(), false);
		}
		
		
		
		
		if (seat.pokerCube.remains() > 0) {
			if (seat.showHandState.equals(LLDeckShowHandsTypes.Active))
				seat.showHandState = LLDeckShowHandsTypes.Passive;

			PokerCube pCube = BeanUtil.v(seat, "player.pokeCube");
			if (pCube != null) {
				pCube.maskPokerCubeWithValue(seat.pokerCube);
			}
			// seat.player.getPokeCube().maskPokerCubeWithValue(seat.pokerCube);
			seat.pokerCubes.add(seat.pokerCube);
			
			seat.deck.lastHandPokerCube = seat.pokerCube;
			seat.actionState = LLDeckSeatActionTypes.ShowHand;
		} else if (seat.pokerCube.remains() == 0) {
			seat.actionState = LLDeckSeatActionTypes.Pass;
		}
		
		//
		//  achievement observer position.
		if(!seat.player.isRobot){
			// previouse hand bomb or rocket?
			
			// landlord ?
			boolean landlord=seat.positionState.equals(LLDeckSeatPositionTypes.Pull)||seat.positionState.equals(LLDeckSeatPositionTypes.Landlord);
			// first hand ?
			boolean firstHand=false;	
			if(landlord){
				if(seat.player.pokeCube.remains()+seat.pokerCube.remains()==20){
					firstHand=true;
				}
			}
			AchievementObserver.observerPokerType(seat.id, seat.pokerCube, landlord, firstHand, previosBombOrRocket);
		}
		
		game.append(seat,  "turnHands -"+seat.pokerCube.cubeToHexString());
		// broadcast
		handMessage(seat, seat.pokerCube.cubeToHexString());

	}

	private  void handMessage(LLDeckSeat seat, String hex) {
		LG0006Response obj = LLU.o(LG0006Response.class);

		obj.setHex(hex);
		obj.setId(seat.player.getId());
		obj.setRemains(seat.player.getPokeCube().remains());
		seat.deck.sendBrokerMessage(obj, false);

		next(seat);

	}

}
