/**
 * @sparrow
 * @Dec 24, 2014   @9:40:34 AM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.sky.game.context.game.GameDoOnTurnException;
import com.sky.game.context.game.IGameLife;
import com.sky.game.context.util.GameUtil;

/**
 * @author sparrow
 *
 */
public class LLGamePull extends AbstractGameStage {
	private final static Log logger=LogFactory.getLog(LLGamePull.class);
	private static final int BEGIN_PULL = 0;
	
	
	public static LLGamePull obtain(LLGame game){
		return new LLGamePull(game);
	}

	/**
	 * 
	 */
	public LLGamePull() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param game
	 */
	public LLGamePull(LLGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameDoOnTurn#onTurn(java.lang.Object)
	 */
	public boolean onTurn(LLDeckSeat seat) throws GameDoOnTurnException {
		// TODO Auto-generated method stub
		boolean ret=false;
		switch (stage) {
		case BEGIN_PULL:
		{
			ret = true;
			if (seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)) {
				//seat.player.getPokeCube().setBelongToLandloard(false);
				seat.actionState=LLDeckSeatActionTypes.Undefined;
				onPull(seat);
				ret=false;
			}
			
		}
			break;

		default:
			break;
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.landlord.room.AbstractGameStage#begin()
	 */
	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
		setStage(BEGIN_PULL, false);
		

	}
	
	

	IGameLife gameLife;
	
	private long getLives(boolean isRobot){
		return isRobot?game.deck.conf.pullWaitingTime:game.deck.conf.pullWaitingTime;
	}
	
	private void onPull(final LLDeckSeat seat) {
		logger.info("onPull - "+seat.getUri());
		//removeGameLife();
		game.append(seat,"onPull");
		long lives = getLives(seat.player!=null&&seat.player.enableRobot);
		GameUtil.gameLife(game.getId()+"/pull", lives, this, "onPullTimeout", seat).setGameSession(game.gameSession);
		
	
	}

	/**
	 * 
	 * 
	 */
	public void onPullTimeout(LLDeckSeat seat) {
		game.append(seat,"onPullTimeout");
		seat.actionState = LLDeckSeatActionTypes.Pass;
		//seat.positionState = LLDeckSeatPositionTypes.Farmer;
		pullMessage(seat);
	}

	private void pullMessage(LLDeckSeat seat) {
		
		seat.deck.sendBrokerMessage(seat.wrapActionState(), false);
		
		//seat.deck.sendBrokerMessage(game.wrapScore(), false);
		checkEnd();
			
	}

	
	public void pull(LLDeckSeat seat,boolean isPull){
		//seat.setKicker(isKicker);
		GameUtil.clearGameLife(game.getId()+"/pull");
		game.append(seat,"pull");
		if(isPull){
			seat.actionState = LLDeckSeatActionTypes.Pull;
			seat.positionState = LLDeckSeatPositionTypes.Pull;
		}else{
			seat.actionState = LLDeckSeatActionTypes.Pass;
		}
		pullMessage(seat);
		
	}
	
	
	public void checkEnd() {
		
		if(game.state.eq(LLGameStates.Pull))
				game.setNextState();
		
	}

}
