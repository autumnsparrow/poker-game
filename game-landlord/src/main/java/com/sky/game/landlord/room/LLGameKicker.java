/**
 * @sparrow
 * @5:18:48 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.game.GameDoOnTurnException;
import com.sky.game.context.util.GameUtil;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class LLGameKicker extends AbstractGameStage {

	private static final Log logger = LogFactory.getLog(LLGameKicker.class);

	public static LLGameKicker obtain(LLGame game) {
		return new LLGameKicker(game);
	}

	private static final int BEGIN_KICK = 0;
	private static final int CHECK_SHOULD_KICK_END = 1;
	private static final int GO_FIND_LANDLORD = 2;
	private static final int SET_LANDLORD_POSITION = 3;

	public LLGameKicker(LLGame game) {
		super(game);

	}

	boolean shouldEnd;

	int allKickePass;

	boolean isKicked;

	/*
	 * (non-Javadoc) give the
	 * 
	 * @see com.sky.game.context.game.IGameDoOnTurn#onTurn(java.lang.Object)
	 */
	public boolean onTurn(LLDeckSeat seat) throws GameDoOnTurnException {
		// TODO Auto-generated method stub
		boolean ret = false;
		switch (stage) {
		
		case GO_FIND_LANDLORD:
		{
			ret=true;
			if (!seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)) {
				seat.positionState=LLDeckSeatPositionTypes.Farmer;
				
			}
		}
			break;
		case SET_LANDLORD_POSITION:
			ret=true;
			if (seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)) {
				//seat.positionState=LLDeckSeatPositionTypes.Farmer;
				seats.holderMoveTo(seat);
				ret=false;
			}
			break;
		
		case BEGIN_KICK: {
			ret=true;
			if (seat.positionState.equals(LLDeckSeatPositionTypes.Farmer)) {
				// seat.player.getPokeCube().setBelongToLandloard(false);
				// BeanUtil.call(seat, "player.pokercube",
				// "setBelongToLandloard", Boolean.valueOf(false));
				PokerCube pCube = null;// BeanUtil.v(seat, "player.pokerCube");
				if (seat.player != null) {
					pCube = seat.player.getPokeCube();
					if (pCube != null) {
						pCube.setBelongToLandloard(false);
					}
				}

				seat.actionState = LLDeckSeatActionTypes.Undefined;
				onKick(seat);
				ret = false;
			}
			
		}
			break;
		case CHECK_SHOULD_KICK_END: {
			ret = true;

			if (seat.actionState.equals(LLDeckSeatActionTypes.Pass)
					|| seat.actionState.equals(LLDeckSeatActionTypes.Kick)) {

				if (seat.actionState.equals(LLDeckSeatActionTypes.Kick)) {
					isKicked = true;
				}
				allKickePass++;
			}

		}

			break;

		default:
			break;
		}

		return ret;
	}

	boolean isEnd;

	public void checkEnd() {
		reset();
		setStage(CHECK_SHOULD_KICK_END, false);
		if (shouldNextStage()) {

			if (game.state.eq(LLGameStates.Kicker)) {
				if (isKicked){
					game.setNextState();
				}
				else
					game.setState(LLGameStates.Gaming);
			}

		}else{
			setStage(BEGIN_KICK,true);
		}
	}

	private boolean shouldNextStage() {
		return allKickePass == 2;
	}

	private void reset() {
		shouldEnd = false;
		allKickePass = 0;
		isEnd = false;
		isKicked = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.landlord.room.AbstractGameStage#begin()
	 */
	@Override
	public synchronized void begin() {
		// TODO Auto-generated method stub
		reset();
		// start kick
		setStage(GO_FIND_LANDLORD,false);
		setStage(SET_LANDLORD_POSITION,false);
		setStage(BEGIN_KICK, true);
		//

	}

	//
	// game life
	// IGameLife gameLife;

	private long getLives(boolean isRobot) {
		return isRobot ? game.deck.conf.kickWaitingTime
				: game.deck.conf.kickWaitingTime;
	}

	public void onKick(final LLDeckSeat seat) {
		logger.info("onKick - " + seat.getUri());
		game.append(seat,"onKick");
		seat.actionState=LLDeckSeatActionTypes.Turn;
		
		seat.deck.sendBrokerMessage(seat.wrapActionState(), false);
		long lives = getLives(seat.player != null && seat.player.enableRobot);
		GameUtil.gameLife(seat.getUri() + "/kick", lives, this,
				"onKickTimeout", seat).setGameSession(game.gameSession);

	}

	/**
	 * 
	 * 
	 */
	public void onKickTimeout(LLDeckSeat seat) {
		game.append(seat,"onKickTimeout");
		seat.actionState = LLDeckSeatActionTypes.Kick;
		seat.positionState = LLDeckSeatPositionTypes.Kicker;
		kickMessage(seat);
	}

	private void kickMessage(LLDeckSeat seat) {
		GameUtil.clearGameLife(seat.getUri() + "/kick");
		seat.deck.sendBrokerMessage(seat.wrapActionState(), false);
		
		checkEnd();
		
	}

	public void kick(LLDeckSeat seat, boolean isKicker) {
		game.append(seat,"kick");
		seat.setKicker(isKicker);
		kickMessage(seat);

	}
}
