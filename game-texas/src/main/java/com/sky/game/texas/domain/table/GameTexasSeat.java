/**
 * 
 */
package com.sky.game.texas.domain.table;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.game.protocol.commons.GT0005Beans.Seat;
import com.sky.game.texas.domain.GameTexasException;
import com.sky.game.texas.domain.game.GameTexasGame;
import com.sky.game.texas.domain.game.IGameStage;
import com.sky.game.texas.domain.game.GameTexasGamePositionStatesEnum;
import com.sky.game.texas.domain.player.GameTexasPlayer;
import com.sky.game.texas.onturn.IDoOnTurn;
import com.sky.game.texas.onturn.OnTurnManager;
import com.sky.game.texas.util.CircleOrderArray;

/**
 * @author sparrow
 *
 */
public class GameTexasSeat implements IDoOnTurn,IGameStage {
	private static final Log logger=LogFactory.getLog(GameTexasSeat.class);

	private static final int update_seat_state = 1;
	
	 int location;
	 
	 GameTexasTable table;
	
	 GameTexasPlayer player;
	
	 GameTexasSeatStatesEnum state;
	 
	 GameTexasGamePositionStatesEnum positionState;
	 
	 int chips;
	 
	 int prizePotLevel;
	 int stage;
	 

	/**
	 * 
	 */
	public GameTexasSeat() {
		// TODO Auto-generated constructor stub
	}

	public GameTexasSeat(int location,GameTexasTable table) {
		super();
		this.table=table;
		this.location = location;
		this.state=GameTexasSeatStatesEnum.Idle;
		this.positionState=GameTexasGamePositionStatesEnum.Normal;
	}

	public int getPrizePotLevel() {
		return prizePotLevel;
	}

	public void setPrizePotLevel(int prizePotLevel) {
		this.prizePotLevel = prizePotLevel;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public GameTexasPlayer getPlayer() {
		return player;
	}

	public void setPlayer(GameTexasPlayer player) {
		this.player = player;
		
	}
	
	public void subSeatChips(int chip){
		this.chips=this.chips-chip;
	}

	public GameTexasSeatStatesEnum getState() {
		return state;
	}

	public void setState(GameTexasSeatStatesEnum state) {
		this.state = state;
		// update the seat state.
		logger.info("updateState:"+state.getState());
		//updateState();
		
	}

	public GameTexasGamePositionStatesEnum getPositionState() {
		return positionState;
	}

	public void setPositionState(GameTexasGamePositionStatesEnum positionState) {
		this.positionState = positionState;
	}

	public int getChips() {
		return chips;
	}
	
	public boolean comparePositionState(GameTexasGamePositionStatesEnum states){
		return states.compare(positionState.value);
	}

	
	
	public void addChips(int chips){
		this.chips=this.chips+chips;
	}

	
	public Seat wrap(boolean withPlayer,boolean withCards){
		Seat obj=Seat.obtain();
		obj.setChips(chips);
		obj.setLocation(location);
		if(player!=null&&withPlayer)
			obj.setPlayer(player.wrap(withCards));
		obj.setPositionState(positionState.getState());
		obj.setState(state.getState());
		
		return obj;
		
	}
	
	
	
	public GameTexasTable getTable() {
		return table;
	}

	@Override
	public String toString() {
		return "GameTexasSeat [location=" + location + ", table=" + table
				+ ", player=" + player + ", state=" + state.getState()
				+ ", positionState=" + positionState.getState() + ", chips=" + chips + "]";
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

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getGame()
	 */
	public GameTexasGame getGame() {
		// TODO Auto-generated method stub
		
		return getTable().getGameTexasGame();
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#getSeats()
	 */
	public CircleOrderArray<GameTexasSeat> getSeats() {
		// TODO Auto-generated method stub
		
		return getGame()==null?null:getGame().getSeats().clone();
	}

	/* (non-Javadoc)
	 * @see com.sky.game.texas.onturn.IDoOnTurn#onTurn(com.sky.game.texas.domain.table.GameTexasSeat)
	 */
	public boolean onTurn(GameTexasSeat s) throws GameTexasException {
		// TODO Auto-generated method stub
		boolean ret=false;
		switch (stage) {
		case update_seat_state:
		{
			if(s.getPlayer()!=null)
				s.getPlayer().send(table.wrapTableSeatState(this,true,false), false);
			ret=true;
		}
			
			break;

		default:
			break;
		}
		return ret;
	}
	
	
	
	

}
