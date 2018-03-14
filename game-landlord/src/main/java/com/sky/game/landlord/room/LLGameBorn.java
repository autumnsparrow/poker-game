/**
 * @sparrow
 * @2:39:48 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import com.sky.game.context.game.GameDoOnTurnException;
import com.sky.game.context.game.IGameStage;
import com.sky.game.protocol.commons.GL0000Beans.LG1007Response;

/**
 * 
 * when the game born notify each player current information of the game
 * 
 * deck state.
 * 
 * 
 * 
 * @author sparrow
 *
 */
public class LLGameBorn extends AbstractGameStage {
	
	
	
	private static final int UPDATE_PLAYER_STATE = 0;


	public static IGameStage obtain(LLGame game){
		return new LLGameBorn(game);
	}

	/**
	 * 
	 */
	public LLGameBorn(LLGame game) {
		// TODO Auto-generated constructor stub
		super(game);
	}

	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		// notify every one the deck postion.
		// 
		//DeckObject deckObject=game.deck.wrapPlayers();
		//game.deck.sendBrokerMessage(deckObject, false);
		// when the player 
		setStage(UPDATE_PLAYER_STATE, false);
		
		//　change to the next stage.
		// after send the deck information ,switch to the next stage.
		if(game.state.eq(LLGameStates.Born))
			game.setNextState();
		//
	}

	
	/* (non-Javadoc)
	 * @see com.sky.game.context.game.IGameDoOnTurn#onTurn(java.lang.Object)
	 */
	public boolean onTurn(LLDeckSeat seat) throws GameDoOnTurnException {
		// TODO Auto-generated method stub
		boolean ret=false;
		switch (stage) {
		case UPDATE_PLAYER_STATE:
		{
			
			if(seat.player!=null){
				LG1007Response resp=game.deck.wrapSeatObject();
				resp.setRank(seat.player.rank);
				resp.setIntegral(seat.player.chips);
				seat.player.sendBrokerMessage(resp, false);
			}
			
			ret=true;
		}
			break;

		default:
			break;
		}
		
		return ret;
	}
	

}
