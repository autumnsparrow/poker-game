/**
 * 
 * @Date:Nov 13, 2014 - 3:16:29 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.texas.domain.GameTexasException;

import com.sky.game.texas.domain.configuration.GameTexasGameConfiguration;

import com.sky.game.texas.domain.player.GameTexasPlayer;
import com.sky.game.texas.domain.player.GameTexasPlayerBetStatesEnum;
import com.sky.game.texas.domain.player.GameTexasPlayerGameStatesEnum;
import com.sky.game.texas.domain.player.GameTexasPlayerOnTurnStatesEnum;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.onturn.IDoOnTurn;
import com.sky.game.texas.onturn.OnTurnManager;
import com.sky.game.texas.timer.GameLifeEnum;
import com.sky.game.texas.timer.GameWorldTimer;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 * 
 */
public class GameTexasGameBet implements IDoOnTurn,IGameStage{
	
	private static final Log logger=LogFactory.getLog(GameTexasGameBet.class);

	private static final int clear_player_bet_object = 5;
	GameTexasGame gameTexasGame;
	GameTexasGameBetStatesEnum state;

	GameTexasSeat currentSeat;
	boolean allActiveNotAllinPlayerTheSameChips;

	public GameTexasGameBet(GameTexasGame gameTexasGame) {
		super();
		this.gameTexasGame = gameTexasGame;
		this.seats=this.gameTexasGame.getSeats().clone();
	}

	public boolean isBetEnd() {
		boolean ret= this.state.value == GameTexasGameBetStatesEnum.End.value;
		
		if(ret){
			gameTexasGame.setNextState();
		}
		
		return ret;
	}

	public void checkBetState() {
		allActiveNotAllinPlayerTheSameChips=true;
		setStage(check_bet_state, false);
		
		if (allActiveNotAllinPlayerTheSameChips) {
			setState(GameTexasGameBetStatesEnum.End);
			// should clear all player bet object.
			setStage(clear_player_bet_object,false);
		}
	}

	public GameTexasGameBetStatesEnum getState() {
		return state;
	}

	public void setState(GameTexasGameBetStatesEnum state) {
		this.state = state;
		
	}
	
	private static final int move_to_bb=1;
	private static final int find_left_active_to_bb=2;
	private static final int bet=3;
	private static final int check_bet_state=4;
	private static final int move_to_next_active_player = 6;
	
	
	int stage;
	
	public void setStage(int stage,boolean onResume){
		this.stage=stage;
		OnTurnManager.obtain().turn(this, onResume);
		
	}
	
	/**
	 * preparing the bet .
	 * 
	 * find the first player who should take action.
	 * 
	 */
	CircleOrderArray<GameTexasSeat> seats;
	public void start() {

		GameTexasGameConfiguration config = gameTexasGame.gameTexasTable
				.getGameTexasGameConfiguration();


		
		
		setStage(move_to_bb,false);
		setStage(find_left_active_to_bb,true);
		
		// when the preflop bet the holder should change to the left active
		// player.
		//
		
		setState(GameTexasGameBetStatesEnum.Open);
		bet();
	}
	
	GameBetWaiting gameBetWaiting;
	
	public void clearBetWaiting(){
		this.gameBetWaiting.destory();
	}

	public void bet() {

		if (!this.isBetEnd()) {
			
			setStage(bet,true);

			
			// checking the current bet state ,
			// should be finished the bet.
			checkBetState();
		}

	}

	
	//player should take action
	public void betWaitingTimeout() {
		currentSeat.getPlayer().onBetWaitingTimeout();
		// next one bet
		bet();

	}
	
	

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getGame()
	 */
	public GameTexasGame getGame() {
		// TODO Auto-generated method stub
		return gameTexasGame;
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
	int chips = 0;
	public boolean onTurn(GameTexasSeat seat) throws GameTexasException {
		// TODO Auto-generated method stub
		
		boolean ret=false;
		
		switch (stage) {
		case move_to_bb:
		{
			// move to the BB 
			if (GameTexasGamePositionStatesEnum.BigBlind.compare(seat
					.getPositionState().value)) {
				seats.holderMoveTo(seat);
				ret=false;

			}else{
				ret= true;
			}
		}
			break;
		case find_left_active_to_bb:
		{
			// find left active to bb.
			if(GameTexasPlayer.isActivePlayer(seat.getPlayer())){
				seats.holderMoveTo(seat);
				// should stop the turn.
				// player.setState(GameTexasPlayerStatesEnum.Action);
				ret=false;
			}else {
				ret=true;
			}
			
		}
			break;
			
	
			
		case bet:
		{
			GameTexasPlayer player = seat.getPlayer();
			
			if(GameTexasPlayer.isActivePlayer(seat.getPlayer())){
				logger.info("Active Player:"+seat.getPlayer().getId());
				player.setOnTurnState(GameTexasPlayerOnTurnStatesEnum.OnTurn);
				player.setBet(this);
				currentSeat = seat;
				gameBetWaiting=new GameBetWaiting(GameTexasGameBet.this, 30000L);
				
				// should not move,
				//seats.holderMoveTo(seat);
				//seats.moveNext();
				// should move to next active player.
				
				ret=false;
			}else{
				ret=true;
			}
			
			
		}
			break;
			
		case check_bet_state:
		{
			
			
			GameTexasPlayer player = seat.getPlayer();
			
			if(GameTexasPlayer.isActivePlayer(player)){
				if(!player.compareBetState(GameTexasPlayerBetStatesEnum.Allin)){
						if(chips<seat.getChips())
							chips = seat.getChips();
					
						// don't assign the chips
						if (chips != seat.getChips()) {
							
							allActiveNotAllinPlayerTheSameChips = false;
						}
					
				}
			}
		
			ret=true;
			
		}
			break;
			
		case clear_player_bet_object:
		{
			GameTexasPlayer player = seat.getPlayer();
			// active user and the seat chips is the same.
			if((player!=null)&&(player.compareGameState(GameTexasPlayerGameStatesEnum.Active))) {
				player.setBet(null);
			}
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
		start();
	}
	

}
