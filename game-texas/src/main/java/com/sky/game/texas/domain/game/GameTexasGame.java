/**
 * 
 */
package com.sky.game.texas.domain.game;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sky.game.context.event.EventHandler;
import com.sky.game.context.event.EventProcessTask;
import com.sky.game.protocol.commons.GT0005Beans.Bet;
import com.sky.game.protocol.commons.GT0005Beans.Game;
import com.sky.game.texas.GameTexasGlobalContext;
import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.OnStateChanged;
import com.sky.game.texas.domain.StateChangedEvent;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.domain.table.GameTexasTable;
import com.sky.game.texas.domain.table.GameTexasTableStatesEnum;
import com.sky.game.texas.id.GlobalIdGenerator;
import com.sky.game.texas.id.IdTypesEnum;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * 
 * Texas Game need a circle data structure.
 * 
 * 
 * 
 * 
 * @author sparrow
 * 
 */
public class GameTexasGame implements OnStateChanged {

	private static final Log logger = LogFactory.getLog(GameTexasGame.class);

	GameTexasTable gameTexasTable;
	GameTexasGameStatesEnum state;

	public GameTexasGameStatesEnum getState() {
		return state;
	}

	// one onStateChanged using for the message
	// one onStateChanged using for the game state change.
	OnStateChanged onStateChanged;

	// circle position.

	CircleOrderArray<GameTexasSeat> gameTexasSeats;

	long id;
	
	int palyerInGaming;
	
	EventHandler<EventProcessTask<StateChangedEvent>> handler;

	/**
	 * 
	 */
	public GameTexasGame() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * GameTexasGame Create.
	 * 
	 * @param gameTexasTable
	 */
	public GameTexasGame(GameTexasTable gameTexasTable) {
		super();
		//MinaEventHandler.getHandler().registerMinaRecievedObserver(this);
		// this.state = GameTexasGameStatesEnum.Start;
		this.gameTexasTable = gameTexasTable;
		this.gameTexasTable.setGameTexasGame(this);
		this.gameTexasSeats = new CircleOrderArray<GameTexasSeat>(
				this.gameTexasTable.getGameTexasSeats());

		this.id = GlobalIdGenerator.getId(IdTypesEnum.Game);
		
		handler=EventHandler.obtain(GameTexasGlobalContext.getExecutor());
		
		setState(GameTexasGameStatesEnum.Start);

	}
	
	

	public void setState(GameTexasGameStatesEnum state) {
		this.state = state;
		// checking the current state to execute.
		final GameTexasGame game = this;
		final int v = state.value;
		logger.info("state:"+state.getState());
		//
		//
		// that should be execute by the queue.
		handler.addEvent(new EventProcessTask<StateChangedEvent>(StateChangedEvent.getEvent(v, game)){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				onStateChanged(t);
			}
			
		});
		
		//handler.addEvent(new StateChangeEventProcessTask(StateChangedEvent.getEvent(v, game)));
		
//		GameTexasGlobalContext.postTask(new Runnable() {
//
//			public void run() {
//				// TODO Auto-generated method stub
//				logger.info(" -----------ThreadId-- :" + Thread.currentThread().getId());
//				onStateChanged(StateChangedEvent.getEvent(v, game));
//				
//			}
//		});
		
		

	}

	public void setOnStateChanged(OnStateChanged onStateChanged) {
		this.onStateChanged = onStateChanged;

	}

	/**
	 * 
	 * 
	 * 1. set the seat position states: Dealer,SmallBlinder,BigBlinder 2. change
	 * the player as active state.
	 * 
	 */
	private void start() {

		new GameTexasGameStart(this).begin();
	}

	/**
	 * blinds.
	 * 
	 */
	private void blinder() {

		// decrease the smallblinder and bigblider chips.
		//
		new GameTexasGameBlinder(this).begin();

	}

	/**
	 * 
	 * all current is passive.
	 * 
	 * deal the poker
	 */
	private void dealPocketPoker() {
	
		new GameTexasGameDealPoker(this).begin();
	}

	public void bet() {
		gameTexasGameBet = new GameTexasGameBet(this);			
		gameTexasGameBet.start();
	}

	public void setNextState() {
		// GameTexasGameStatesEnum currentState=state;
		setState(GameTexasGameStatesEnum
				.getGameTexasGameStatesEnum(state.value + 1));
	}

	/**
	 * 
	 */
	public void flop() {
		// burn a card
		new GameTexasGameFlop(this).begin();
	}

	public void turn() {
		new GameTexasGameTurn(this).begin();

	}

	public void river() {
		new GameTexasGameRiver(this).begin();

	}

	private void showdown() {
		new GameTexasGameShowdown(this).begin();
		//setState(GameTexasGameStatesEnum.HandRanking);
	}

	private void handsRanking() {

		new GameTexasGameHandRanking(this).begin();
	}

	private void prizePot() {
		new GameTexasGamePrizePot(this).begin();
	}

	public void end() {
		
		this.gameTexasTable.setGameTexasGame(null);
		this.gameTexasTable.setState(GameTexasTableStatesEnum.Idle);
	}

	GameTexasGameBet gameTexasGameBet;

	/**
	 * 
	 * Core the state processed.
	 * 
	 */
	public void onStateChanged(StateChangedEvent event) {
		// TODO Auto-generated method stub

		if (GameTexasGameStatesEnum.Start.compare(event.state)) {
			// TODO:
			// 1, mark the position of player
			// the current holder is the dealer.
			// checking the seats states.
			//
			// setting the game seat position state
			// GameTexasSeat holderSeat=gameTexasSeats.currentHolder();
			// position and blinder.
			start();

		} else if (GameTexasGameStatesEnum.Blinds.compare(event.state)) {
			//
			// blinder.
			blinder();
		} else if (GameTexasGameStatesEnum.DealPocketCards.compare(event.state)) {
			dealPocketPoker();
		} else if (GameTexasGameStatesEnum.PreflopBet.compare(event.state)
				|| GameTexasGameStatesEnum.PreTurnBet.compare(event.state)
				|| GameTexasGameStatesEnum.PreRiverBet.compare(event.state)
				|| GameTexasGameStatesEnum.FinalBet.compare(event.state)) {
			
			bet();

		} else if (GameTexasGameStatesEnum.Flop.compare(event.state)) {
			flop();
		} else if (GameTexasGameStatesEnum.Turn.compare(event.state)) {
			turn();
		} else if (GameTexasGameStatesEnum.River.compare(event.state)) {
			river();
		} else if (GameTexasGameStatesEnum.Showdown.compare(event.state)) {
			// show the player's cards.
			showdown();
		} else if (GameTexasGameStatesEnum.HandRanking.compare(event.state)) {
			handsRanking();
		} else if (GameTexasGameStatesEnum.PrizePot.compare(event.state)) {
			prizePot();
		} else if (GameTexasGameStatesEnum.End.compare(event.state)) {
			end();
		}

		// every stage finished should  change the state.
		updateState();

	}

	/**
	 * 
	 * 
	 * 
	 * @param onTrun
	 */
	public void doOnTurn(IOnTurn onTrun) {
		CircleOrderArray<GameTexasSeat> seats=gameTexasSeats.clone();
		GameTexasSeat seat = seats.currentHolder();
		// GameTexasPlayer player=null;
		do {
			// player=seat.getPlayer();
			try {
				if (!onTrun.onTurn(this, seat))
					break;
			} catch (GameTexasException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seat=seats.moveNext();
		} while (!seats.isMoveAroud());

	}
	
	public void doOnTurnClone(IOnTurnClone onTrun) {
		CircleOrderArray<GameTexasSeat> seats=gameTexasSeats.clone();
		GameTexasSeat seat = seats.currentHolder();
		// GameTexasPlayer player=null;
		do {
			// player=seat.getPlayer();
			try {
				if (!onTrun.onTurn(this,seats, seat))
					break;
			} catch (GameTexasException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seat=seats.moveNext();
		} while (!seats.isMoveAroud());

	}

	/**
	 * 
	 * @param onTurn
	 */
	public void doOnTurnResume(IOnTurn onTurn) {
		GameTexasSeat seat = gameTexasSeats.currentMover();
		// GameTexasPlayer player=null;
		do {
			// player=seat.getPlayer();
			try {
				if (!onTurn.onTurn(this, seat))
					break;
			} catch (GameTexasException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seat=gameTexasSeats.moveNext();
		} while (!gameTexasSeats.isMoveAroud());

	}

	public static interface IOnTurn {
		/**
		 * when the return is not turn
		 * 
		 * @param game
		 * @param seat
		 * @return should we continue the turn. true - loop continue false -
		 *         loop pause.
		 * @throws GameTexasException
		 */
		public boolean onTurn(GameTexasGame game,GameTexasSeat seat)
				throws GameTexasException;
	}

	
	public static interface IOnTurnClone {
		/**
		 * when the return is not turn
		 * 
		 * @param game
		 * @param seat
		 * @return should we continue the turn. true - loop continue false -
		 *         loop pause.
		 * @throws GameTexasException
		 */
		public boolean onTurn(GameTexasGame game,CircleOrderArray<GameTexasSeat> seats,GameTexasSeat seat)
				throws GameTexasException;
	}

	
	
	
	public Game wrap(){
		Game obj=Game.obtain();
		obj.setId(id);
		obj.setState(state.getState());
		Bet bet=Bet.obtain();
		if(gameTexasGameBet!=null)
			bet.setState(gameTexasGameBet.state.getState());
		obj.setBet(bet);
		return obj;
	}
	
	private void updateState(){
		
		gameTexasTable.updateState(gameTexasTable.wrapTableGameState());

	}

	/**
	 * @return
	 */
	public CircleOrderArray<GameTexasSeat> getSeats() {
		// TODO Auto-generated method stub
		return gameTexasSeats;
	}

	
	
	
	
	

}
