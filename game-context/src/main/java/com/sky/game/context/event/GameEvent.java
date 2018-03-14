/**
 * @sparrow
 * @Jan 7, 2015   @2:55:53 PM
 * @coptyright Beijing BZWT Technology Co ., Ltd .
 */
package com.sky.game.context.event;

/**
 * @author sparrow
 *
 */
public class GameEvent {
	
	public static final String GAME_END="GameEndEvent";
	
	public static final String POKER_TYPE_EVENT="PokerTypeEvent";
	
	public static final String DECK_GAME_END="GameDeckEnd";
	public static final String WINNER_IN_ET="WinnerInElimination";
	public static final String WINNER_IN_BT="WinnerInBlockade";
	public static final String HALF_FINAL_IN_ET="HalfFinalInET";
	
	
	public static final String GAME_SYSTEM_MESSAGE="GameSystemMessage";
	public static final String GAME_PROTOCOL_MESSAGE="GameProtocolMessage";
	public static final String NETWORK_EVENT="network_event";
	public static final String ROOM_EVENT="roomEvent";
	
	
	// event name
	public String name;
	// event parameters
	public Object obj;
	
	public static GameEvent obtain(String name, Object obj){
		return new GameEvent(name, obj);
	}

	public boolean isEvent(String evt){
		return evt.equals(name);
	}
	
	/**
	 * @param name
	 * @param obj
	 */
	public GameEvent(String name, Object obj) {
		super();
		this.name = name;
		this.obj = obj;
	}



	/**
	 * 
	 */
	public GameEvent() {
		// TODO Auto-generated constructor stub
	}

}
