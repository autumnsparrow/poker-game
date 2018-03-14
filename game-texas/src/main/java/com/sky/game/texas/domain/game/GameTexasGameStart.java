/**
 * 
 * @Date:Nov 21, 2014 - 11:30:33 AM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class GameTexasGameStart implements IDoOnTurn, IGameStage {

	private static Log logger = LogFactory.getLog(GameTexasGameStart.class);

	private static final int active_all_players = 1;
	private static final int set_dealer = 2;

	private static final int set_blinder = 3;
	GameTexasGame gameTexasGame;
	CircleOrderArray<GameTexasSeat> seats;
	Stack<GameTexasGamePositionStatesEnum> positionStack;

	/**
	 * 
	 */
	public GameTexasGameStart() {
		// TODO Auto-generated constructor stub
	}

	public GameTexasGameStart(GameTexasGame gameTexasGame) {
		super();
		this.gameTexasGame = gameTexasGame;
		this.seats = this.gameTexasGame.gameTexasSeats.clone();
		this.positionStack = new Stack<GameTexasGamePositionStatesEnum>();
		positionStack.push(GameTexasGamePositionStatesEnum.BigBlind);
		positionStack.push(GameTexasGamePositionStatesEnum.SmallBlind);
		positionStack.push(GameTexasGamePositionStatesEnum.Dealer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getGame()
	 */
	public GameTexasGame getGame() {
		// TODO Auto-generated method stub
		return gameTexasGame;
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
		case active_all_players: {
			GameTexasPlayer player = null;
			player = seat.getPlayer();

			if (player != null) {
				// user is active player.
				// who is the dealer?

				player.setGameState(GameTexasPlayerGameStatesEnum.Active);

			}
			ret = true;
		}

			break;
		case set_dealer: {
			GameTexasPlayer player = null;
			player = seat.getPlayer();
			logger.info(seat.toString());
			if (player != null) {
				// user is active player.
				// who is the dealer?

				player.setGameState(GameTexasPlayerGameStatesEnum.Active);
				if (!positionStack.empty()) {
					seat.setPositionState(positionStack.pop());
				}
				// we should move the holder current seat position.
				// the dealer is the holder.
				//

				if (seat.comparePositionState(GameTexasGamePositionStatesEnum.Dealer)) {
					seats.holderMoveTo(seat);
				}

			}

			ret = false;

		}
			break;

		case set_blinder: {
			GameTexasPlayer player = seat.getPlayer();

			if (player != null
					&& player
							.compareGameState(GameTexasPlayerGameStatesEnum.Active)) {
				// user is active player.
				// who is the dealer?
				if (!positionStack.isEmpty()) {
					seat.setPositionState(positionStack.pop());
					ret = true;
				} else {
					ret = false;
				}

			}
		}
			break;

		default:
			break;
		}

		return ret;
	}

	int stage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.texas.domain.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub

		// set all user active.
		// set all user as active
		setStage(active_all_players, false);

		setStage(set_dealer, false);

		// if the current player is 2
		// that happens.
		// the position state don't process finished.
		setStage(set_blinder, true);

		// set the first dealer
		// update the full table state
		gameTexasGame.gameTexasTable.updateState(gameTexasGame.gameTexasTable.wrapFullTableState());
		
		gameTexasGame.setNextState();

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

}
