/**
 * @sparrow
 * @2:42:09 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.landlord.room;

import java.util.HashMap;

import com.sky.game.context.game.CircleOrderArray;
import com.sky.game.context.game.GameOnTurnManager;
import com.sky.game.context.game.IGameDoOnTurn;
import com.sky.game.context.game.IGameStage;

/**
 * AbstractGameStage implements {@link IGameStage} {@link IGameDoOnTurn<LLGame,LLDeckSeat>}
 * 
 * 
 * @author sparrow
 *
 *@see LLGame
 *@see LLDeckSeat
 */
public abstract class AbstractGameStage implements IGameStage,
		IGameDoOnTurn<LLGame, LLDeckSeat> {

	protected int stage;

	LLGame game;
	CircleOrderArray<LLDeckSeat> seats;

	private HashMap<Long, CircleOrderArray<LLDeckSeat>> historyMap;
	
	GameOnTurnManager manager;

	/**
	 * 
	 */
	public AbstractGameStage() {
		// TODO Auto-generated constructor stub
	}

	public AbstractGameStage(LLGame game) {
		super();
		this.manager=GameOnTurnManager.obtain();
		this.game = game;
		this.seats = game.seats.clone();
		this.historyMap = new HashMap<Long, CircleOrderArray<LLDeckSeat>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.context.game.IGameStage#begin()
	 */
	public abstract void begin();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sky.game.context.game.IGameStage#setStage(int, boolean)
	 */
	public  void setStage(int stage, boolean resume) {
		// TODO Auto-generated method stub
		this.stage = stage;
		//if (resume) {
			// when using the on turn manager ,resume should be stored.
			if (!this.historyMap.containsKey(Long.valueOf(stage))) {
				this.historyMap.put(Long.valueOf(stage), getSeats().clone());
			}
			this.seats = this.historyMap.get(Long.valueOf(stage));
		//}

		manager.turn(this, resume);

	}

	public LLGame getGame() {
		// TODO Auto-generated method stub
		return game;
	}

	public CircleOrderArray<LLDeckSeat> getSeats() {
		// TODO Auto-generated method stub
		
		return seats;
	}

}
