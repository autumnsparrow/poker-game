/**
 * 
 * @Date:Nov 21, 2014 - 1:52:39 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.pack.GameTexasCard;
import com.sky.game.texas.domain.pack.GameTexasPack;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.onturn.IDoOnTurn;
import com.sky.game.texas.onturn.OnTurnManager;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 *
 */
public class GameTexasGameFlop implements IDoOnTurn, IGameStage {
	
	GameTexasGame game;
	CircleOrderArray<GameTexasSeat> seats;
	int stage;
	
	GameTexasPack pack;

	/**
	 * 
	 */

	
	

	public GameTexasGameFlop(GameTexasGame game) {
		super();
		this.game = game;
		// have nothing to do with the seats.
		//seats=this.game.gameTexasSeats.clone();
		pack=game.gameTexasTable.getGameTexasPack();
		
	}



	/* (non-Javadoc)
	 * @see com.sky.game.texas.domain.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		
		// burn card
		pack.dealCard();
		for(int i=0;i<3;i++){
			GameTexasCard card=pack.dealCard();
			game.gameTexasTable.getGameTexasCards().addCard(card);
		}
		
		// send the message 
		// send the flog cards.
		game.gameTexasTable.updateState(game.gameTexasTable.wrapTableGameState());
		
		game.setNextState();

	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.domain.game.IGameStage#setStage(int, boolean)
	 */
	public void setStage(int stage, boolean resume) {
		// TODO Auto-generated method stub
		this.stage=stage;
		OnTurnManager.obtain().turn(this, resume);

	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getGame()
	 */
	public GameTexasGame getGame() {
		// TODO Auto-generated method stub
		return game;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getSeats()
	 */
	public CircleOrderArray<GameTexasSeat> getSeats() {
		// TODO Auto-generated method stub
		return seats;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#onTurn(com.sky.game.texas.domain.table.GameTexasSeat)
	 */
	public boolean onTurn(GameTexasSeat seat) throws GameTexasException {
		// TODO Auto-generated method stub
		return false;
	}

}
