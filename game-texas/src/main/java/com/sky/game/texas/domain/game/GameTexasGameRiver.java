/**
 * 
 * @Date:Nov 21, 2014 - 2:02:51 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import com.sky.game.texas.domain.pack.GameTexasCard;
import com.sky.game.texas.domain.pack.GameTexasPack;

/**
 * @author sparrow
 *
 */
public class GameTexasGameRiver implements IGameStage{
	
	GameTexasGame game;
	
	int stage;

	GameTexasPack pack;

	/**
	 * 
	 */
	private GameTexasGameRiver() {
		// TODO Auto-generated constructor stub
	}
	
	

	public GameTexasGameRiver(GameTexasGame game) {
		super();
		this.game = game;
		pack=game.gameTexasTable.getGameTexasPack();
	}



	/* (non-Javadoc)
	 * @see com.sky.game.texas.domain.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		pack.dealCard();
		for(int i=0;i<1;i++){
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
		
	}

}
