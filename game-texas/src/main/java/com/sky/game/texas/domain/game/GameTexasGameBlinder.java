/**
 * 
 * @Date:Nov 21, 2014 - 1:16:38 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.configuration.GameTexasGameConfiguration;
import com.sky.game.texas.domain.player.GameTexasPlayer;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.onturn.IDoOnTurn;
import com.sky.game.texas.onturn.OnTurnManager;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 * 
 */
public class GameTexasGameBlinder implements IDoOnTurn, IGameStage {

	private static final int off_small_blinder = 1;
	private static final int off_big_blinder = 2;
	GameTexasGame game;
	CircleOrderArray<GameTexasSeat> seats;

	int smallBlinderChips;
	int bigBlinderChips;

	/**
	 * 
	 */
	public GameTexasGameBlinder() {
		// TODO Auto-generated constructor stub
	}

	int stage;

	public GameTexasGameBlinder(GameTexasGame game) {
		super();
		this.game = game;
		this.seats = game.gameTexasSeats.clone();
		GameTexasGameConfiguration config = game.gameTexasTable
				.getGameTexasGameConfiguration();
		smallBlinderChips = config.getSmallBlinderChips();
		bigBlinderChips = config.getBigBlinderChips();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.domain.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub

		setStage(off_small_blinder, false);
		setStage(off_big_blinder, true);

		
		game.setNextState();
		//setState(GameTexasGameStatesEnum.DealPocketCards);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.domain.game.IGameStage#setStage(int, boolean)
	 */
	public void setStage(int stage, boolean resume) {
		// TODO Auto-generated method stub
		this.stage = stage;
		OnTurnManager.obtain().turn(this, resume);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getGame()
	 */
	public GameTexasGame getGame() {
		// TODO Auto-generated method stub
		return game;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getSeats()
	 */
	public CircleOrderArray<GameTexasSeat> getSeats() {
		// TODO Auto-generated method stub
		return seats;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.texas.onturn.IDoOnTurn#onTurn(com.sky.game.texas.domain.
	 * table.GameTexasSeat)
	 */
	public boolean onTurn(GameTexasSeat seat) throws GameTexasException {
		// TODO Auto-generated method stub
		boolean ret = false;
		switch (stage) {
		case off_big_blinder: {

			GameTexasPlayer player = null;
			player = seat.getPlayer();
			if (player != null) {
				if (seat.comparePositionState(GameTexasGamePositionStatesEnum.BigBlind)) {
					player.offerChips(bigBlinderChips);
					// send the seat info.
					game.gameTexasTable.updateState(game.gameTexasTable.wrapTableSeatState(seat,true,false));
					
				}
			}
			// send the seat to the table.
			// send the message
			
			ret=true;
		
		}
			break;
		case off_small_blinder: {

			GameTexasPlayer player = null;
			player = seat.getPlayer();
			if (player != null) {
				if (seat.comparePositionState(GameTexasGamePositionStatesEnum.SmallBlind)) {

					player.offerChips(smallBlinderChips);
					// when the blind ,the small blinder is the first one who
					// get the cards,
					// so we move the holder
					// after that ,every round start from the small blinder.
					//seats.holderMoveTo(seat);
					// int position=gameTexasSeats.getObjectPosition(seat);
					// gameTexasSeats.holderMoveTo(position);
					game.gameTexasTable.updateState(game.gameTexasTable.wrapTableSeatState(seat,true,false));

				}
				
				
			}
			ret = false;
		}
			break;

		default:
			break;

		}
		return ret;
	}
}
