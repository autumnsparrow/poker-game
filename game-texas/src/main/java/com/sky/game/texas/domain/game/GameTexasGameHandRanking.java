/**
 * 
 * @Date:Nov 21, 2014 - 4:04:12 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.pack.GameTexasCardMatrix;
import com.sky.game.texas.domain.player.GameTexasPlayer;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.onturn.IDoOnTurn;
import com.sky.game.texas.onturn.OnTurnManager;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 *
 */
public class GameTexasGameHandRanking implements IDoOnTurn, IGameStage {
	
	private static final int check_game_hands_ranking = 1;
	GameTexasGame game;
	CircleOrderArray<GameTexasSeat> seats;
	int stage;
	

	/**
	 * 
	 */
	private GameTexasGameHandRanking() {
		// TODO Auto-generated constructor stub
	}
	
	

	public GameTexasGameHandRanking(GameTexasGame game) {
		super();
		this.game = game;
		this.seats=game.getSeats()==null?null:game.getSeats().clone();
	}



	/* (non-Javadoc)
	 * @see com.sky.game.texas.domain.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		setStage(check_game_hands_ranking, false);
		
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
		boolean ret=false;
		
		switch (stage) {
		case check_game_hands_ranking:
		{
			GameTexasPlayer player=seat.getPlayer();
			if(GameTexasPlayer.isActivePlayer(player)){
				//GameTexasCardMatrix.obtain().handRank(seat.getTable().getGameTexasCards(), player.getGameTexasCards());
				player.handRanking();
				seat.getTable().updateState(seat.wrap(true, true));
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
