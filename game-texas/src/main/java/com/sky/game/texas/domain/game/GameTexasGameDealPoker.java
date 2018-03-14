/**
 * 
 * @Date:Nov 21, 2014 - 1:38:19 PM
 * @Author: sparrow
 * @Project :game-texas 
 * 
 *
 */
package com.sky.game.texas.domain.game;

import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.pack.GameTexasCard;
import com.sky.game.texas.domain.pack.GameTexasPack;
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
public class GameTexasGameDealPoker implements IDoOnTurn, IGameStage {
	
	private static final int move_to_small_blinder = 1;
	private static final int deal_poker = 2;
	private static final int notice_pocket_poker = 3;
	GameTexasGame game;
	CircleOrderArray<GameTexasSeat> seats;
	int stage;
	
	GameTexasPack pack;

	/**
	 * 
	 */
	public GameTexasGameDealPoker() {
		// TODO Auto-generated constructor stub
	}
	
	

	public GameTexasGameDealPoker(GameTexasGame game) {
		super();
		this.game = game;
		this.pack=game.gameTexasTable.getGameTexasPack();
		this.seats=this.game.getSeats().clone();
	}

	

	/* (non-Javadoc)
	 * @see com.sky.game.texas.domain.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		// shuffle the cards
		this.pack.shuffles();
		// move to the small blinder
		setStage(move_to_small_blinder,false);
		
		// deal the private poker
		for(int i=0;i<2;i++){
			setStage(deal_poker,false);
		}
		
		// notify the player private poker
		setStage(notice_pocket_poker,false);
		
		// turn next stage state.
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
		case move_to_small_blinder:
		{
			ret=true;
			GameTexasPlayer player = null;
			player = seat.getPlayer();
			if (player != null) {
				if (seat.comparePositionState(GameTexasGamePositionStatesEnum.SmallBlind)) {

				
					// when the blind ,the small blinder is the first one who
					// get the cards,
					// so we move the holder
					// after that ,every round start from the small blinder.
					seats.holderMoveTo(seat);
					ret=false;
				}
			}
			
		}
			
			break;
			
		case deal_poker:
		{
			GameTexasPlayer player = seat.getPlayer();

			// user active
			// can get a pocket cards.
			if(player!=null)
			if (GameTexasPlayerGameStatesEnum.Active.compare(player
					.getState().value)) {
				GameTexasCard card = pack.dealCard();
				player.getGameTexasCards().addCard(card);
			}
			ret=true;
		}
			break;

		case notice_pocket_poker:
		{
			GameTexasPlayer player = seat.getPlayer();

			// user active
			// can get a pocket cards.
			if(player!=null)
			if (GameTexasPlayerGameStatesEnum.Active.compare(player
					.getState().value)) {
				// notify the user private poker.
				player.send(game.gameTexasTable.wrapTableSeatState(seat,true,true), false);
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
