/**
 * 
 */
package com.sky.game.texas.domain.game;

import com.sky.game.protocol.commons.GT0001Beans.State;



/**
 * 
 * 
 * @author sparrow
 *
 */
public enum GameTexasGameStatesEnum {
	
	
	
	
	Undefined((byte)-1),
	
	Start((byte)1),
	Blinds((byte)2),
	DealPocketCards((byte)3),
	PreflopBet((byte)4),
	Flop((byte)5),
	PreTurnBet((byte)6),
	Turn((byte)7),
	PreRiverBet((byte)8),
	River((byte)9),
	FinalBet((byte)10),
	Showdown((byte)11),
	HandRanking((byte)12),
	PrizePot((byte)13),
	End((byte)14);
	
	public byte value;

	private GameTexasGameStatesEnum(byte value) {
		this.value = value;
	}
	
	
	private static GameTexasGameStatesEnum[]  states=new GameTexasGameStatesEnum[]{
		Undefined,
		
		Start,
		Blinds,
		DealPocketCards,
		PreflopBet,
		Flop,
		PreTurnBet,
		Turn,
		PreRiverBet,
		River,
		FinalBet,
		Showdown,
		HandRanking,
		PrizePot,
		End,
	
		
		
		
		
		
		
	};
	
	
	/**
	 * 
	 * @param state
	 * @return
	 */
	public static GameTexasGameStatesEnum getGameTexasGameStatesEnum(int state){
		return states[state];
	}
	
	/**
	 * 
	 * @param state
	 * @return
	 */
	public  boolean compare(int state){
		return this.value==state;
	}
	
	public static boolean compare(GameTexasGameStatesEnum gameState,int state){
		return gameState.compare(state);
	}

	/**
	 * @return
	 */
	public State getState() {
		// TODO Auto-generated method stub
		return State.obtain(value, toString());
	}
	

}
