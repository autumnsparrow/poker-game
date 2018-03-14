/**
 * 
 */
package com.sky.game.texas.domain.player;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.protocol.commons.GT0005Beans.Player;
import com.sky.game.protocol.commons.GT0006Beans;
import com.sky.game.protocol.commons.GT0005Beans.Prize;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.protocol.event.handler.MinaEventHandler;
import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.game.GameTexasGame;
import com.sky.game.texas.domain.game.GameTexasGamePositionStatesEnum;
import com.sky.game.texas.domain.game.GameTexasGameStatesEnum;
import com.sky.game.texas.domain.game.IGameStage;
import com.sky.game.texas.domain.game.GameTexasGameBet;
import com.sky.game.texas.domain.pack.GameTexasCardMatrix;
import com.sky.game.texas.domain.pack.GameTexasCards;
import com.sky.game.texas.domain.table.GameTexasSeat;
import com.sky.game.texas.onturn.IDoOnTurn;
import com.sky.game.texas.onturn.OnTurnManager;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * 
 * 
 * 
 * @author sparrow
 *
 */

public class GameTexasPlayer implements IIdentifiedObject,IDoOnTurn,IGameStage{
	
	private static final Log logger=LogFactory.getLog(GameTexasPlayer.class);
	private static final int update_player_state = 1;
	public Long getId() {
		return Long.valueOf(id);
	}


	public void setId(long id) {
		this.id = id;
	}



	private GameTexasCards gameTexasCards;
	private GameTexasSeat seat;
	// game active/inactive state
	// in game state.
	private GameTexasPlayerGameStatesEnum gameState;
	// player active state
	// call,bet,fold,raise,all-in.
	// bet action state
	private GameTexasPlayerBetStatesEnum betState;
	// player operation state
	// waiting ,action,
	// that may be timeout.
	// in turn state
	// waiting or ontrun.
	private GameTexasPlayerOnTurnStatesEnum onTurnState;
	
	private GameTexasGameBet bet;
	
	GameTexasCardMatrix matrix=null;
	
	private long id;
	
	private int chips;
	
	CircleOrderArray<GameTexasSeat> seats;
	GameTexasGame game;
	
	//is the current win the prize.
	boolean winner;
	int prizeChips;
	
	
	public int getPrizeChips() {
		return prizeChips;
	}


	public void setPrizeChips(int prizeChips) {
		this.prizeChips = prizeChips;
	}



	int stage;
	
	public int getChips() {
		return chips;
	}


	public void setChips(int chips) {
		this.winner=true;
		this.chips = chips;
	}


	/**
	 * 
	 */
	public GameTexasPlayer() {
		// TODO Auto-generated constructor stub
		gameTexasCards=new GameTexasCards();
		this.betState=GameTexasPlayerBetStatesEnum.Undefined;
		this.onTurnState=GameTexasPlayerOnTurnStatesEnum.Undefined;
		this.gameState=GameTexasPlayerGameStatesEnum.Undefined;
		this.chips=20000;
		
	}
	
	
	
	public void setBet(GameTexasGameBet bet) {
		this.bet = bet;
	}


	public GameTexasPlayer(long id) {
		super();
		this.id = id;
		gameTexasCards=new GameTexasCards();
		this.betState=GameTexasPlayerBetStatesEnum.Undefined;
		this.onTurnState=GameTexasPlayerOnTurnStatesEnum.Undefined;
		this.gameState=GameTexasPlayerGameStatesEnum.Undefined;
		this.chips=20000;
		GameContextGlobals.registerEventHandler("GT0006", this);
	
	}


	public GameTexasSeat getSeat() {
		return seat;
	}
	public void setSeat(GameTexasSeat seat) {
		this.seat = seat;
		this.game=this.seat.getTable().getGameTexasGame();
		//this.seats=this.game.getSeats();
	}

	
	

	public GameTexasCards getGameTexasCards() {
		return gameTexasCards;
	}


	public GameTexasPlayerGameStatesEnum getState() {
		return gameState;
	}


	public void setState(GameTexasPlayerGameStatesEnum state) {
		this.gameState = state;
	}
	
	
	private void putChips(int chips){
		this.chips=this.chips-chips;
	}
	
	
	public void offerChips(int chips){
		putChips(chips);
		seat.addChips(chips);
		setStage(update_player_state, false);
	}
	
	
	public boolean compareGameState(GameTexasPlayerGameStatesEnum states){
		return this.gameState.compare(states.value);
	}

	public boolean compareBetState(GameTexasPlayerBetStatesEnum states){
		return this.betState.compare(states.value);
	}
	
	public boolean compareOnturnState(GameTexasPlayerOnTurnStatesEnum states){
		return this.onTurnState.compare(states.value);
	}

	public void setGameState(GameTexasPlayerGameStatesEnum gameState) {
		this.gameState = gameState;
		logger.info("gameState:"+this.gameState.toString());
		//updateState();
	}


	public void setBetState(GameTexasPlayerBetStatesEnum betState) {
		this.betState = betState;
		logger.info("betState:"+this.betState.toString());
		if(betState.compare(GameTexasPlayerBetStatesEnum.Fold.value)){
			// for debug not enable.
			setGameState(GameTexasPlayerGameStatesEnum.Inactive);
		}
		//updateState();
	}


	public void setOnTurnState(GameTexasPlayerOnTurnStatesEnum onTurnState) {
		this.onTurnState = onTurnState;
		logger.info("onTurnState:["+this.getId().longValue()+"]"+this.onTurnState.toString());
		//updateState();
	}
	
	
	
	public void onBetWaitingTimeout(){
		// for debugging
		 //setState(GameTexasPlayerGameStatesEnum.Inactive);
		
			// for real
		if(getSeat().comparePositionState(GameTexasGamePositionStatesEnum.SmallBlind)){
			if(this.game==null)
				this.game=this.seat.getTable().getGameTexasGame();
			if(game!=null)
			if(game.getState().compare(GameTexasGameStatesEnum.PreflopBet.value)){
				getSeat().addChips(100);
			}
		}
		
		setOnTurnState(GameTexasPlayerOnTurnStatesEnum.OnTurnTimeout);
		//setBetState(GameTexasPlayerBetStatesEnum.Fold);
		
		//setOnTurnState(GameTexasPlayerOnTurnStatesEnum.Waiting);
	}
	
	
	



	/**
	 * 
	 * 
	 * @return
	 */
	public Player wrap(boolean withCards) {
		// TODO Auto-generated method stub
		Player obj=Player.obtain();
		obj.setBetState(betState.getState());
		obj.setChips(chips);
		obj.setGameState(gameState.getState());
		obj.setOnTurnState(onTurnState.getState());
		obj.setId(id);
		if(withCards){
			if(this.gameTexasCards!=null)
				obj.setCards(this.gameTexasCards.getHex());
			if(this.matrix!=null){
				obj.setHands(matrix.wrap());
			}
			if(winner){
				Prize prize=new Prize();
				prize.setWinner(winner);
				prize.setChips(prizeChips);
				obj.setPrize(prize);
			}
		}
		
		
	
		return obj;
	}
	
	
	/**
	 * core of the message push.
	 * @param obj
	 * @param request
	 */
	public void send(Object obj,boolean request){
		logger.info("player.send:"+obj);
		MinaEventHandler.addPushMinaEvent(MinaEvent.obtainMinaEvent(getId(), obj,request));
	}
	
	
	
	

	@Override
	public String toString() {
		return "GameTexasPlayer [gameTexasCards=" + gameTexasCards.getHex() + " , "+
				"gameState=" + gameState.getState() + ", betState=" + betState
				+ ", onTurnState=" + onTurnState.getState() + ", id=" + id + ", chips="
				+ chips + "]";
	}
	
	@RegisterEventHandler(transcode="GT0006")
	public void handleGT0006Event(MinaEvent evt){
		
		if(evt!=null&&evt.obj!=null&&evt.obj instanceof GT0006Beans.Request){
			GT0006Beans.Request req=(GT0006Beans.Request)evt.obj;
			
			//
			//action
			//
			// first clear the game waiting
			offerChips(req.getChips());
			bet.clearBetWaiting();
			GameTexasPlayerBetStatesEnum gameBetState=GameTexasPlayerBetStatesEnum.getBetState(req.getAction());
			setBetState(gameBetState);
			setOnTurnState(GameTexasPlayerOnTurnStatesEnum.Waiting);
			// let's other known the player has been bet.
			seat.getTable().updateState(seat.getTable().wrapTableSeatState(seat,true,false));
			
			bet.bet();
		}
		
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
		seats=this.game==null?null:game.getSeats();
		return seats;
	}


	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#onTurn(com.sky.game.texas.domain.table.GameTexasSeat)
	 */
	public boolean onTurn(GameTexasSeat s) throws GameTexasException {
		// TODO Auto-generated method stub
		
		switch (stage) {
		case update_player_state:
		{
			if(s.getPlayer()!=null)
				s.getPlayer().send(seat.getTable().wrapTableSeatState(seat,true,false), false);
		}
			break;

		default:
			break;
		}
		return false;
	}


	/* (non-Javadoc)
	 * @see com.sky.game.texas.domain.game.IGameStage#begin()
	 */
	public void begin() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.sky.game.texas.domain.game.IGameStage#setStage(int, boolean)
	 */
	public void setStage(int stage, boolean resume) {
		// TODO Auto-generated method stub
		this.stage=stage;
		OnTurnManager.obtain().turn(this, resume);
		
	}
	
	
	
	
	public GameTexasCardMatrix getMatrix() {
		return matrix;
	}


	/**
	 * 
	 * 
	 * @return
	 */
	public void handRanking(){
		
		if(compareGameState(GameTexasPlayerGameStatesEnum.Active)){
			matrix=GameTexasCardMatrix.obtain().handRank(seat.getTable().getGameTexasCards(), gameTexasCards);
			
		}
		
	}
	
	
	
	
	
	public static boolean isActivePlayer(GameTexasPlayer player){
		return player!=null?player.compareGameState(GameTexasPlayerGameStatesEnum.Active):false;
	}
	
	
	

}
