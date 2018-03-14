/**
 * 
 */
package com.sky.game.texas.domain.table;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.annotation.RegisterEventFilter;
import com.sky.game.context.annotation.RegisterEventHandler;
import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.protocol.BasePlayer;
import com.sky.game.protocol.GameProtocolException;
import com.sky.game.protocol.commons.GT0002Beans;
import com.sky.game.protocol.commons.GT0003Beans;
import com.sky.game.protocol.commons.GT0005Beans;
import com.sky.game.protocol.commons.GT0005Beans.Response;
import com.sky.game.protocol.event.MinaEvent;
import com.sky.game.protocol.event.handler.MinaEventHandler;
import com.sky.game.texas.GameTexasGlobalContext;
import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.OnStateChanged;
import com.sky.game.texas.domain.StateChangedEvent;
import com.sky.game.texas.domain.configuration.GameTexasGameConfiguration;
import com.sky.game.texas.domain.game.GameTexasGame;
import com.sky.game.texas.domain.game.GameTexasGame.IOnTurn;
import com.sky.game.texas.domain.game.IGameStage;
import com.sky.game.texas.domain.pack.GameTexasCards;
import com.sky.game.texas.domain.pack.GameTexasPack;
import com.sky.game.texas.domain.player.GameTexasPlayer;
import com.sky.game.texas.id.GlobalIdGenerator;
import com.sky.game.texas.onturn.IDoOnTurn;
import com.sky.game.texas.onturn.OnTurnManager;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 *
 */
public class GameTexasTable implements IIdentifiedObject,IGameStage,IDoOnTurn {
	
	private static final Log logger=LogFactory.getLog(GameTexasTable.class);
	public static final int MAX_SEATS=9;
	private static final int update_state_without_object = 1;
	private static final int update_state_with_object = 2;
	
	 GameTexasSeat gameTexasSeats[];
	 GameTexasTableStatesEnum state;
	
	 int playerCount;
	
	// should add observer to notify the state changed .
	private OnStateChanged onStateChanged;
	
	private GameTexasGameConfiguration gameTexasGameConfiguration;
	private GameTexasPack gameTexasPack;
	
	private long id;
	
	int stage;
	
	 GameTexasGame gameTexasGame;
	
	
	// bottom cards
	
	GameTexasCards gameTexasCards;
	
	
	

	/**
	 * 
	 */
	public GameTexasTable() {
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	/**
	 * initialize the table seats.
	 * 
	 */
	private void initialize(){
		gameTexasSeats=new GameTexasSeat[MAX_SEATS];
		for(int i=0;i<MAX_SEATS;i++){
			gameTexasSeats[i]=new GameTexasSeat(i,this);
		}
		state=GameTexasTableStatesEnum.Idle;
		gameTexasGameConfiguration=new GameTexasGameConfiguration();
		gameTexasPack=new GameTexasPack();
		id=GlobalIdGenerator.getTableId();
		
		gameTexasCards=new GameTexasCards();
		GameContextGlobals.registerEventHandler("GT0003", this);
		GameContextGlobals.registerEventHandler("GT0004", this);
	}
	
	public Long getId(){
		return Long.valueOf(id);
	}
	
	
	
	
	
	public GameTexasCards getGameTexasCards() {
		return gameTexasCards;
	}

	public void setGameTexasCards(GameTexasCards gameTexasCards) {
		this.gameTexasCards = gameTexasCards;
	}

	public GameTexasPack getGameTexasPack() {
		return gameTexasPack;
	}

	/**
	 * 
	 * @return
	 * @throws GameTexasException
	 */
	private GameTexasSeat findEmptySeat() throws GameTexasException{
		GameTexasSeat seat=null;
		for(int i=0;i<MAX_SEATS;i++){
			seat=gameTexasSeats[i];
			if(seat.state.isState(GameTexasSeatStatesEnum.Idle)){
				break;
			}
		}
		if(seat==null){
			setState(GameTexasTableStatesEnum.NoEmptySeat);
			throw new GameTexasException(GameTexasException.TABLE_NO_EMPY_SEAT, "no empty seat");
		}
		return seat;
	}
	
	
	/**
	 * Player join the table
	 * @param player
	 * @throws GameTexasException
	 */
	public void join(GameTexasPlayer player) throws GameTexasException{
		
		GameTexasSeat seat=findEmptySeat();
		seat.player=player;
		player.setSeat(seat);
		seat.setState(GameTexasSeatStatesEnum.Used);
		setState(GameTexasTableStatesEnum.PlayerJoin);
		changePlayerCount(true);
		GameTexasGlobalContext.mapPlayerAndTable(player.getId(), this);
		
	}
	
	/**
	 * Player leave the table
	 * @param player
	 */
	public void leave(GameTexasPlayer player){
		GameTexasSeat seat=player.getSeat();
		seat.player=null;
		player.setSeat(null);
		setState(GameTexasTableStatesEnum.PlayerLeave);
		changePlayerCount(false);
		GameTexasGlobalContext.umapPlayerAndTable(player.getId());
		
	}
	
	
	/**
	 * 
	 * @param increase true - increase the player count,false - decrease the player count
	 */
	private void changePlayerCount(boolean increase){
		if(increase){
			this.playerCount++;
		}else{
			this.playerCount--;
		}
		if(this.playerCount==1){
			setState(GameTexasTableStatesEnum.Waiting);
		}else if(this.playerCount>1&this.gameTexasGame==null){
			
			setState(GameTexasTableStatesEnum.Gaming);
			gameTexasGame=new GameTexasGame(this);
			// push the game state,
		}
	}
	
	/**
	 * 
	 * @return current table state.
	 */
	public GameTexasTableStatesEnum getState() {
		return state;
	}
	
	/**
	 * change the table state ,if the onStateChanged interface not null,operate teh interface.
	 * @param state
	 */
	public void setState(GameTexasTableStatesEnum state) {
		this.state = state;
		
		logger.info("updateState:"+state.getState());
		if(this.onStateChanged!=null){
			this.onStateChanged.onStateChanged(StateChangedEvent.getEvent(state.value, this));
			updateState();
		}
		
		// update the table state.
		
	}
	
	/**
	 * 
	 * @param onStateChanged
	 */
	public void setOnStateChanged(OnStateChanged onStateChanged) {
		this.onStateChanged = onStateChanged;
	}
	
	
	/**
	 * 
	 * @return the current player count.
	 */
	public int getPlayerCount(){
		return this.playerCount;
	}

	public GameTexasSeat[] getGameTexasSeats() {
		return gameTexasSeats;
	}

	public GameTexasGameConfiguration getGameTexasGameConfiguration() {
		return gameTexasGameConfiguration;
	}

	public void setGameTexasGameConfiguration(
			GameTexasGameConfiguration gameTexasGameConfiguration) {
		this.gameTexasGameConfiguration = gameTexasGameConfiguration;
	}

	public GameTexasGame getGameTexasGame() {
		return gameTexasGame;
	}

	public void setGameTexasGame(GameTexasGame gameTexasGame) {
		this.gameTexasGame = gameTexasGame;
	}
	
	
	
	public GameTexasPlayer getPlayer(long userId){
		GameTexasPlayer player=null;
		for(GameTexasSeat seat:getGameTexasSeats()){
			player=seat.getPlayer();
			if(player!=null&&player.getId()==userId){
				break;
			}
		}
		return player;
	}
	
	
	
	
	@RegisterEventHandler(transcode="GT0003")
	public void handleGT0003Event(final MinaEvent evt){
		
		
		BasePlayer player=BasePlayer.getPlayer(evt);
		final GameTexasPlayer texasPlayer=new GameTexasPlayer(player.getUserId());
		
		try {
			join(texasPlayer);
		} catch (GameTexasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		texasPlayer.send(wrapFullTableState(),false);
		
		
		
	}
	
	
	
	@RegisterEventHandler(transcode="GT0004")
	public void handleGT0004Event(final MinaEvent evt){
		BasePlayer player=BasePlayer.getPlayer(evt);
		final GameTexasPlayer texasPlayer=this.getPlayer(player.getUserId());
		this.leave(texasPlayer);
	//	texasPlayer.send(wrapGt0005(), true);
	//MinaEventHandler.addPushMinaEvent(MinaEvent.obtainMinaEvent(texasPlayer.getId(), wrapGt0005(),true));
		
	}
	
	
	
	
	
	// protocol wrapper.
	/**
	 * 
	 * list the brief table.
	 * 
	 * @return
	 */
	public GT0002Beans.Table wrapBriefTableState(){
		GT0002Beans.Table object=new GT0002Beans.Table();
		object.setId(id);
		object.setPlayerCount(playerCount);
		object.setState(state.getState());
	
		return  object;
	}
	
	
	// wrap the full state
	/**
	 * 
	 * send the full state of the table.
	 * @return
	 */
	public GT0005Beans.Response wrapFullTableState(){
		GT0005Beans.Response obj=GT0005Beans.Response.obtain();
		obj.setId(id);
		obj.setPlayerCount(playerCount);
		for(GameTexasSeat seat:gameTexasSeats){
			if(seat.getPlayer()!=null)
				obj.getSeats().add(seat.wrap(true,false));
		}
		obj.setState(state.getState());
		obj.setCards(gameTexasCards.getHex());
		if(gameTexasGame!=null)
			obj.setGame(gameTexasGame.wrap());
		
		return obj;
	}
	
	/**
	 * 
	 * obtain the seat state.
	 * @param seat
	 * @return
	 */
	public GT0005Beans.Response wrapTableSeatState(GameTexasSeat seat,boolean withPlayer,boolean withCards){
		GT0005Beans.Response obj=GT0005Beans.Response.obtain();
		obj.setId(id);
		obj.setPlayerCount(playerCount);
		obj.getSeats().add(seat.wrap(withPlayer,withCards));
		obj.setState(state.getState());
		obj.setCards(gameTexasCards.getHex());
		
		return obj;
	}
	
	
	public GT0005Beans.Response wrapTableGameState(){
		GT0005Beans.Response obj=GT0005Beans.Response.obtain();
		obj.setId(id);
		obj.setPlayerCount(playerCount);
		obj.setState(state.getState());
		obj.setCards(gameTexasCards.getHex());
		if(gameTexasGame!=null)
			obj.setGame(gameTexasGame.wrap());
		
		return obj;
	}
	
	/**
	 * 
	 * table state
	 * @return
	 */
	public GT0003Beans.Response wrapTableState(){
		GT0003Beans.Response obj=GT0003Beans.Response.obtain();
		
		obj.setId(id);
		obj.setState(state.getState());
		obj.setCards(gameTexasCards.getHex());
		obj.setPlayerCount(playerCount);
	
		
		return obj;
		
	}
	
	
	/**
	 * update the current table state.
	 * 
	 * 
	 */
	private void updateState(){
		setStage(update_state_without_object, false);
	}
	
	Object obj;
	
	/**
	 * that should be thread safe.
	 * @param obj
	 */
	public synchronized void updateState(final Object obj){
		this.obj=obj;
		setStage(update_state_with_object, false);
		
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getGame()
	 */
	public GameTexasGame getGame() {
		// TODO Auto-generated method stub
		return this.gameTexasGame;
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getSeats()
	 */
	public CircleOrderArray<GameTexasSeat> getSeats() {
		// TODO Auto-generated method stub
		return this.gameTexasGame==null?null:this.gameTexasGame.getSeats();
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#onTurn(com.sky.game.texas.domain.table.GameTexasSeat)
	 */
	public boolean onTurn(GameTexasSeat seat) throws GameTexasException {
		// TODO Auto-generated method stub
		boolean ret=false;
		switch (stage) {
		case update_state_without_object:
		{
			if(seat.getPlayer()!=null){
				seat.getPlayer().send(wrapTableState(),false);
			}
			ret=true;
		}
			
			break;
		case update_state_with_object:
		{
			if(seat.getPlayer()!=null){
				seat.getPlayer().send(obj,false);
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
