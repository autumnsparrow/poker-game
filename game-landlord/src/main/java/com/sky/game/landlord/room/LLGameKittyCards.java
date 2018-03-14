/**
 * @sparrow
 * @6:09:28 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.context.game.GameDoOnTurnException;
import com.sky.game.poker.robot.poker.PokerCube;

/**
 * @author sparrow
 *
 */
public class LLGameKittyCards extends AbstractGameStage{
	
	
	private static final int FIND_LAND_LORD = 0;


	public static LLGameKittyCards obtain(LLGame game){
		return new LLGameKittyCards(game);
	}
	
	
	
	public LLGameKittyCards(LLGame game) {
		super(game);
		
	}

	
	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		
		//this.game.deck.kittyCards.;
		//game.deck.sendBrokerMessage(game.deck.wrapKittyCard(), false);
		
		// should add the kitty cards.
		//game.deck.sendBrokerMessage(game.deck.wrapKittyCard(), false);
		
		setStage(FIND_LAND_LORD, false);
		// call the next state.
		if(game.state.eq(LLGameStates.KittyCards))
			game.setNextState();
	}

	
	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameDoOnTurn#onTurn(java.lang.Object)
	 */
	public boolean onTurn(LLDeckSeat seat) throws GameDoOnTurnException {
		// TODO Auto-generated method stub
		boolean ret=true;
		switch (stage) {
		case FIND_LAND_LORD:
		
		{
			if(seat.positionState.equals(LLDeckSeatPositionTypes.Landlord)){
				//seat.onKick();
				
				PokerCube pc=seat.player.getPokeCube().addPokerCube(game.deck.kittyCards);
				pc.setBelongToLandloard(true);
				seat.player.setPokeCube(pc);
				game.deck.sendBrokerMessage(seat.wrapKittyCard(), false);
				game.append(seat,"landlord");
				ret=false;
			}
			
		}
			
			break;

		default:
			break;
		}
		
		
		return ret;
	}

}
