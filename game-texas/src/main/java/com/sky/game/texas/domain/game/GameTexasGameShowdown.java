/**
 * 
 * @Date:Nov 21, 2014 - 3:24:12 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.player.GameTexasPlayer;
import com.sky.game.texas.domain.player.GameTexasPlayerGameStatesEnum;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.onturn.IDoOnTurn;
import com.sky.game.texas.onturn.OnTurnManager;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 *
 */
public class GameTexasGameShowdown implements IGameStage, IDoOnTurn {
	
	private static final int show_player_cards = 1;
	private static final int check_active_players = 0;
	GameTexasGame game;
	CircleOrderArray<GameTexasSeat> seats;
	int stage;
	
	int activePlayers;
	

	/**
	 * 
	 */
	private GameTexasGameShowdown() {
		// TODO Auto-generated constructor stub
	}
	
	

	public GameTexasGameShowdown(GameTexasGame game) {
		super();
		this.game = game;
		this.seats=game.gameTexasSeats.clone();
		activePlayers=0;
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
		case show_player_cards:
		{
			GameTexasPlayer player=seat.getPlayer();
			if(player!=null&&player.compareGameState(GameTexasPlayerGameStatesEnum.Active)){
				seat.getTable().updateState(seat.getTable().wrapTableSeatState(seat,true,true));
			}
			ret=true;
		}
			
			break;
		case check_active_players:{
			GameTexasPlayer player=seat.getPlayer();
			if(player!=null&&player.compareGameState(GameTexasPlayerGameStatesEnum.Active)){
				activePlayers++;
			}
			
			ret=true;
		}
		break;

		default:
			break;
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.domain.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		// check the last active players count
		setStage(check_active_players, false);
		if(activePlayers>1){
			// show the players cards.
			
			setStage(show_player_cards, false);
		}
		
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

}
